package Tamaized.AoV.core.abilities.healer;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.helper.ParticleHelper;

import com.mojang.realmsclient.gui.ChatFormatting;

public abstract class CureWounds extends AbilityBase{
	
	private int damage = 0;

	public CureWounds(String n, int c, double r, int dmg) {
		super(n, c, r, true,
				ChatFormatting.YELLOW+n,
				"",
				ChatFormatting.AQUA+"Cost: "+c,
				ChatFormatting.AQUA+"Range: "+r,
				ChatFormatting.AQUA+"Base Healing: "+dmg,
				"",
				ChatFormatting.DARK_PURPLE+"Heals yourself or an entity if",
				ChatFormatting.DARK_PURPLE+"your crosshair is over the entity."
				);
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
				if(data.invokeMass){
					castAsMass(player, a, data);
				}else{
					player.heal(a);
				}
			}else{
				if(e != null){
					if(data.invokeMass){
						castAsMass(e, a, data);
					}else{
						if(e.isEntityUndead()) e.attackEntityFrom(DamageSource.magic, a);
						else if(data.hasSelectiveFocus() && (e instanceof IMob)) ;
						else e.heal(a);
					}
				}
			}
		}
		this.addXP(data, 20);
	}
	
	private void castAsMass(EntityLivingBase target, int dmg, AoVData data){
		int range = (int) (maxDistance*2);
		ParticleHelper.sendPacketToClients(ParticleHelper.Type.BURST, target, range);
		List<EntityLivingBase> list = target.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(target.getPosition().add(-range, -range, -range), target.getPosition().add(range, range, range)));
		for(EntityLivingBase entity : list){
			if(entity.isEntityUndead()){
				entity.attackEntityFrom(DamageSource.magic, dmg);
				this.addXP(data, 20);
			}
			else if(data.hasSelectiveFocus() && (entity instanceof IMob)){
				//Do Nothing
			}
			else{
				entity.heal(dmg);
				this.addXP(data, 20);
			}
		}
	}

}
