package tamaized.aov.common.core.abilities;

import net.minecraft.entity.player.PlayerEntity;
import tamaized.aov.common.capabilities.aov.IAoVCapability;

public interface IAura {

	default Aura createAura(Ability ability) {
		return new Aura(ability, getLife());
	}

	void castAsAura(PlayerEntity caster, IAoVCapability cap, int life);

	int getLife();

}
