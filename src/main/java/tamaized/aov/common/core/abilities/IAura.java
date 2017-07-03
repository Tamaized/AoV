package tamaized.aov.common.core.abilities;

import tamaized.aov.common.capabilities.aov.IAoVCapability;
import net.minecraft.entity.player.EntityPlayer;

public interface IAura {

	default Aura createAura(Ability ability) {
		return new Aura(ability, getLife());
	}

	void castAsAura(EntityPlayer caster, IAoVCapability cap, int life);

	int getLife();

}
