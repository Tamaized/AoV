package tamaized.aov.common.core.abilities;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;

import javax.annotation.Nullable;

public final class Aura {

	private Ability spell;
	private int life;

	public Aura(Ability ability, int duration) {
		spell = ability;
		life = duration * 20;
	}

	public static Aura construct(ByteBuf stream) {
		return new Aura(Ability.construct(stream), stream.readInt());
	}

	public void encode(ByteBuf stream) {
		spell.encode(stream);
		stream.writeInt(life);
	}

	/**
	 * If the caster dies or is null or does not have the AOV capability, the aura dies.
	 */
	public final void update(@Nullable PlayerEntity caster) {
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV, null);
		if (caster == null || caster.removed || cap == null)
			life = 0;
		if (life > 0) {
			spell.castAsAura(this, caster, cap, life);
		}
		life--;
	}

	public final boolean removed() {
		return life <= 0;
	}

	public final void kill() {
		life = 0;
	}

	public Ability getSpell() {
		return spell;
	}

	public IAura getAsAura() {
		return spell.getAbility() instanceof IAura ? (IAura) spell.getAbility() : null;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Aura && ((Aura) obj).getSpell().compare(getSpell());
	}

}
