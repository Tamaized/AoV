package Tamaized.AoV.core.abilities.healer;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;

import com.mojang.realmsclient.gui.ChatFormatting;

public abstract class CureWounds extends AbilityBase{
	
	private int damage = 0;

	public CureWounds(String n, int c, double r, int dmg) {
		super(n, c, r,
				ChatFormatting.YELLOW+n,
				"",
				ChatFormatting.AQUA+"Cost: "+c,
				ChatFormatting.AQUA+"Range: "+r,
				ChatFormatting.AQUA+"Base Healing: "+dmg,
				"",
				ChatFormatting.DARK_PURPLE+"Heals yourself or an entity if",
				ChatFormatting.DARK_PURPLE+"your crosshair is over the entity."
				);
		System.out.println(name);
		damage = dmg;
	}

	@Override
	protected void doAction(EntityPlayer player, AoVData data, EntityLivingBase e) {
		if(player.worldObj.isRemote){
			if(e != null){
				sendPacketTypeTarget(getName(), e.getEntityId());
			}else{
				sendPacketTypeSelf(getName());
			}
		}else{
			int a = (int) (damage*(1f+(data.getSpellPower()/100f)));
			if(e == null){
				player.heal(a);
			}else{
				if(e != null){
					if(e.isEntityUndead()) e.attackEntityFrom(DamageSource.magic, a);
					else e.heal(a);
				}
			}
		}
		this.addXP(data, 20);
	}

}
