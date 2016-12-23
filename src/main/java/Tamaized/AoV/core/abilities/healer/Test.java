package Tamaized.AoV.core.abilities.healer;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.entity.EntityTest;

public class Test extends AbilityBase {

	public Test() {
		super(getStaticName(), 50, 20, false, "");
	}

	@Override
	protected void doAction(EntityPlayer player, AoVData data, EntityLivingBase e) {
		player.world.spawnEntity(new EntityTest(player.world, player.getPosition()));
	}

	@Override
	public ResourceLocation getIcon() {
		return null;
	}

	@Override
	public String getName() {
		return getStaticName();
	}
	
	public static String getStaticName() {
		return "Test";
	}

	@Override
	public int getCoolDown() {
		return 0;
	}

}
