package tamaized.aov.common.core.abilities.cleric.Cores;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.client.ParticleHelper;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.abilities.IAura;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.aov.registry.SoundEvents;

import java.util.List;

public class PosEnergyAura extends AbilityBase implements IAura {

	private final static int charges = 6;
	private final static int range = 10;
	private final static int dmg = 2;
	private final static int life = 45;

	public PosEnergyAura() {
		super(

				new TranslationTextComponent(getStaticName()),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", charges),

				new TranslationTextComponent("aov.spells.global.range", range),

				new TranslationTextComponent("aov.spells.global.healing", dmg),

				new TranslationTextComponent("aov.spells.global.length", life),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.posaura.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.posaura.name";
	}

	@Override
	public boolean cast(Ability ability, PlayerEntity player, LivingEntity e) {
		IAoVCapability cap = CapabilityList.getCap(player, CapabilityList.AOV);
		if (cap == null)
			return false;
		cap.addAura(createAura(ability));
		SoundEvents.playMovingSoundOnServer(SoundEvents.aura, player);
		cap.addExp(player, 30, this);
		return true;
	}

	@Override
	public void castAsAura(PlayerEntity caster, IAoVCapability cap, int life) {
		int tick = (PosEnergyAura.life * 20) - life;
		if (tick > 0 && tick % 20 == 0) {
			ParticleHelper.spawnParticleMesh(ParticleHelper.MeshType.BURST, ParticleHelper.ParticleType.Heart, caster.world, caster.getPositionVector(), range, 0xFFFF00FF);
			int a = (int) (dmg * (1f + (cap.getSpellPower() / 100f)));
			List<LivingEntity> list = caster.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(caster.getPosition().add(-range, -range, -range), caster.getPosition().add(range, range, range)));
			for (LivingEntity entity : list) {
				if (entity.isEntityUndead())
					entity.attackEntityFrom(AoVDamageSource.createEntityDamageSource(DamageSource.MAGIC, caster), a);
				else if (IAoVCapability.canBenefit(caster, cap, entity))
					entity.heal(a);
			}
		}
	}

	@Override
	public int getLife() {
		return life;
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.MODID, "textures/spells/posaura.png");
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return I18n.format(getStaticName());
	}

	@Override
	public int getCoolDown() {
		return 60;
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
		return range;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public boolean isCastOnTarget(PlayerEntity caster, IAoVCapability cap, LivingEntity target) {
		return false;
	}

}
