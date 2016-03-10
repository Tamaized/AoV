package Tamaized.AoV.core.abilities.healer.Cores;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.helper.ParticleHelper;

import com.mojang.realmsclient.gui.ChatFormatting;

public class Burst extends AbilityBase{

	private final static int cost = 10;
	private final static int range = 20;
	private final static int dmg = 4;
	
	public Burst() {
		super(getStaticName(), cost, range, false,
				ChatFormatting.YELLOW+getStaticName(),
				"",
				ChatFormatting.AQUA+"Cost: "+cost,
				ChatFormatting.AQUA+"Range: "+range,
				ChatFormatting.AQUA+"Base Healing: "+dmg,
				"",
				ChatFormatting.DARK_PURPLE+"Heals everything and cures",
				ChatFormatting.DARK_PURPLE+"poison, blind, and slows.",
				ChatFormatting.DARK_PURPLE+"around you, including you.",
				ChatFormatting.DARK_PURPLE+"Deals damage to Undead."
				);
	}

	@Override
	protected void doAction(EntityPlayer player, AoVData data, EntityLivingBase e) {
		if(player.worldObj.isRemote){
			sendPacketTypeSelf(getName());
		}else{
			ParticleHelper.sendPacketToClients(ParticleHelper.Type.BURST, player, range);
			int a = (int) (dmg*(1f+(data.getSpellPower()/100f)));
			List<EntityLivingBase> list = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.getPosition().add(-range, -range, -range), player.getPosition().add(range, range, range)));
			for(EntityLivingBase entity : list){
				if(entity.isEntityUndead()) entity.attackEntityFrom(DamageSource.magic, a);	
				else if(data.hasSelectiveFocus() && !(entity instanceof IMob)) continue;
				else entity.heal(a);
				this.addXP(data, 20);
			}
		}
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
		return "Positive Energy Burst";
	}
	
	@Override
	public int getCoolDown() {
		return 3;
	}

}
