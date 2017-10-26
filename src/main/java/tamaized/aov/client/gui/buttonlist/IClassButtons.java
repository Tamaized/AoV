package tamaized.aov.client.gui.buttonlist;

import tamaized.aov.client.gui.AoVSkillsGUI;
import tamaized.aov.common.capabilities.aov.IAoVCapability;

public interface IClassButtons {

	void register(AoVSkillsGUI instance);

	boolean active(IAoVCapability cap);

}
