package Tamaized.AoV.common.client;

import Tamaized.AoV.AoV;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ScrewModelResourceLocation extends ModelResourceLocation {

	public ScrewModelResourceLocation(String path, String file, String v) {
		super(0, new String[]{AoV.modid, path.concat(file), v});
	}

}
