package Tamaized.AoV.core.abilities.healer;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;

public class CureLightWounds extends AbilityBase{
	
	private int damage = 0;

	public CureLightWounds(int c, double d, int dmg) {
		super(c, d,
				ChatFormatting.YELLOW+getStaticName(),
				"",
				ChatFormatting.AQUA+"Cost: "+c,
				ChatFormatting.AQUA+"Range: "+d,
				ChatFormatting.AQUA+"Base Healing: "+dmg,
				"",
				ChatFormatting.DARK_PURPLE+"Heals yourself or an entity",
				ChatFormatting.DARK_PURPLE+"if your crosshair is over the entity."
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
			System.out.println(e);
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
	}
	
	public String getName(){
		return getStaticName();
	}

	public static String getStaticName() {
		return "Cure Light Wounds";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid+":textures/spells/test.png");
	}

}
