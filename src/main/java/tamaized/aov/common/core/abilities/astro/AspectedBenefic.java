package tamaized.aov.common.core.abilities.astro;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntitySpellAoVParticles;
import tamaized.aov.common.entity.EntitySpellVanillaParticles;
import tamaized.aov.proxy.CommonProxy;
import tamaized.aov.registry.SoundEvents;

public class AspectedBenefic extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid, "textures/spells/aspectedbenefic.png");

	private static final int charges = 2;
	private static final int distance = 10;
	private static final int heal = 8;

	public AspectedBenefic() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", charges),

				new TextComponentTranslation("aov.spells.global.range", distance),

				new TextComponentTranslation("aov.spells.global.healing", heal),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.aspectedbenefic.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.aspectedbenefic.name";
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
		return 3;
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
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		IAoVCapability cap = caster.hasCapability(CapabilityList.AOV, null) ? caster.getCapability(CapabilityList.AOV, null) : null;
		if (cap == null)
			return false;
		EntityLivingBase entity = target != null && IAoVCapability.selectiveTarget(cap, target) ? target : caster;
		int a = (int) (heal * (1f + (cap.getSpellPower() / 100f)));
		entity.heal(a);
		SoundEvents.playMovingSoundOnServer(SoundEvents.aspectedbenefic, entity);
		entity.world.spawnEntity(new EntitySpellAoVParticles(entity.world, entity, CommonProxy.ParticleType.Heart, 0x00FFD8FF, 5));
		entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600));
		cap.addExp(caster, 15, this);
		return true;
	}

}
