package tamaized.aov.common.core.abilities.caster;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.ProjectileFlameStrike;
import tamaized.aov.registry.SoundEvents;
import tamaized.tammodized.common.helper.CapabilityHelper;
import tamaized.tammodized.common.helper.RayTraceHelper;

import java.util.HashSet;

public class FlameStrike extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid, "textures/spells/flamestrike.png");

	private static final int damage = 6;
	private static final int charges = 5;
	private static final int distance = 45;

	public FlameStrike() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", charges),

				new TextComponentTranslation("aov.spells.global.range", distance),

				new TextComponentTranslation("aov.spells.global.damage", damage),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.flamestrike.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.flamestrike.name";
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName() {
		return I18n.format(getStaticName());
	}

	@Override
	public int getCoolDown() {
		return 8;
	}

	@Override
	public int getMaxCharges() {
		return charges;
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	public double getMaxDistance() {
		return distance;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
		return IAoVCapability.selectiveTarget(caster, cap, target);
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		IAoVCapability cap = CapabilityHelper.getCap(caster, CapabilityList.AOV, null);
		if (cap == null)
			return false;
		int a = (int) (damage * (1f + (cap.getSpellPower() / 100f)));
		ProjectileFlameStrike strike = new ProjectileFlameStrike(caster.world, caster, a);
		if(target == null) {
			HashSet<Entity> exclude = new HashSet<>();
			exclude.add(caster);
			RayTraceResult result = RayTraceHelper.tracePath(caster.world, caster, distance, 1, exclude);
			if (result != null && result.typeOfHit != null) {
				switch (result.typeOfHit) {
					case BLOCK:
						BlockPos pos = result.getBlockPos();
						strike.setPosition(pos.getX(), pos.getY() + 15, pos.getZ());
						break;
					case ENTITY:
						pos = result.entityHit.getPosition();
						strike.setPosition(pos.getX(), pos.getY() + 15, pos.getZ());
						break;
					default:
						pos = caster.getPosition();
						strike.setPosition(pos.getX(), pos.getY() + 15, pos.getZ());
						break;
				}
			}
		} else {
			strike.setPosition(target.posX, target.posY + 15, target.posZ);
		}
		caster.world.spawnEntity(strike);
		strike.world.playSound(null, strike.posX, strike.posY - 20, strike.posZ, SoundEvents.firestrike, SoundCategory.NEUTRAL, 1.0F, 1.0F);
		return true;
	}

}
