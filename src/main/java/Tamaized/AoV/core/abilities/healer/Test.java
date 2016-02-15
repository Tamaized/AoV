package Tamaized.AoV.core.abilities.healer;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.entity.EntityTest;

public class Test extends AbilityBase {

	public Test() {
		super(getStaticName(), 0, 20, "");
	}

	@Override
	protected void doAction(EntityPlayer player, AoVData data, EntityLivingBase e) {
		player.worldObj.spawnEntityInWorld(new EntityTest(player.worldObj, player.getPosition()));
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

}
