package Tamaized.AoV.config;

import java.io.IOException;

import Tamaized.AoV.AoV;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {

	private Configuration config;

	private int default_recharge = -1;
	private boolean default_experience = false;

	private int recharge = default_recharge;
	private boolean experience = default_experience;

	public ConfigHandler(Configuration c) {
		config = c;
		config.load();
		sync(true);
	}

	public Configuration getConfig() {
		return config;
	}

	public void sync(boolean firstLoad) {
		try {
			loadData(firstLoad);
			cleanupFile();
			config.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadData(boolean firstLoad) {
		recharge = config.get(Configuration.CATEGORY_GENERAL, "recharge", default_recharge, "Sets the recharge rate per second, -1 disables this").getInt();
		experience = config.get(Configuration.CATEGORY_GENERAL, "experience", default_experience, "Determines whether or not vanilla experience contributes to AoV experience gain").getBoolean();
	}

	private void cleanupFile() throws IOException {
		AoV.configFile.delete();
		AoV.configFile.createNewFile();
		config = new Configuration(AoV.configFile);
		config.get(Configuration.CATEGORY_GENERAL, "recharge", default_recharge, "Sets the recharge rate per second, -1 disables this").set(recharge);
		config.get(Configuration.CATEGORY_GENERAL, "experience", default_experience, "Determines whether or not vanilla experience contributes to AoV experience gain").set(experience);
	}

	@SubscribeEvent
	public void configChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(AoV.modid)) sync(false);
	}

	public int getRechargeRate() {
		return recharge;
	}

	public boolean getExperience() {
		return experience;
	}

}
