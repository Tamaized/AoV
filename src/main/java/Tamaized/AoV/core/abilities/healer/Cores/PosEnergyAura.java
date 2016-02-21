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
import Tamaized.AoV.core.abilities.AuraBase;
import Tamaized.AoV.core.abilities.IAura;
import Tamaized.AoV.helper.ParticleHelper;

import com.mojang.realmsclient.gui.ChatFormatting;

public class PosEnergyAura extends AbilityBase implements IAura{
	
	private final static int cost = 10;
	private final static int range = 10;
	private final static int dmg = 2;
	private final static int life = 45;

	public PosEnergyAura() {
		super(getStaticName(), cost, range, false,
				ChatFormatting.YELLOW+getStaticName(),
				"",
				ChatFormatting.AQUA+"Cost: "+cost,
				ChatFormatting.AQUA+"Range: "+range,
				ChatFormatting.AQUA+"Base Healing: "+dmg,
				ChatFormatting.AQUA+"Lasts: "+life+" Seconds",
				"",
				ChatFormatting.DARK_PURPLE+"Creates an aura around yourself",
				ChatFormatting.DARK_PURPLE+"to heal you and everything",
				ChatFormatting.DARK_PURPLE+"around you for a period of time."
				);
	}

	@Override
	protected void doAction(EntityPlayer player, AoVData data, EntityLivingBase e) {
		if(player.worldObj.isRemote){
			sendPacketTypeSelf(getName());
		}else{
			data.addAura(this);
			this.addXP(data, 20);
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
		return "Positive Energy Aura";
	}
	
	@Override
	public AuraBase createAura() {
		return new PosAura();
	}

	@Override
	public int getLife() {
		return life;
	}
	
	private class PosAura extends AuraBase {
		
		private int currLife = 0;

		@Override
		public void update(AoVData data) {
			EntityPlayer player = data.getPlayer();
			ParticleHelper.sendPacketToClients(ParticleHelper.Type.BURST, player, range);
			int a = (int) (dmg*(1f+(data.getSpellPower()/100f)));
			List<EntityLivingBase> list = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.getPosition().add(-range, -range, -range), player.getPosition().add(range, range, range)));
			for(EntityLivingBase entity : list){
				if(entity.isEntityUndead()) entity.attackEntityFrom(DamageSource.magic, a);	
				else if(data.hasSelectiveFocus() && !(entity instanceof IMob)) continue;
				else entity.heal(a);
			}
			currLife++;
		}

		@Override
		public int getCurrentLife() {
			return currLife;
		}
		
	}

}
