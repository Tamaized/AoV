package Tamaized.AoV.network;

import java.io.IOException;

import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.skills.AoVSkill;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ServerPacketHandler {

	public static enum PacketType {
		SKILLEDIT_CHECK_CANOBTAIN, RESETSKILLS_FULL, RESETSKILLS_MINOR, SPELLBAR_REMOVE, SPELLBAR_ADDNEAR, CHARGES_RESET, CAST_SPELL
	}

	public static int getPacketTypeID(PacketType type) {
		return type.ordinal();
	}

	public static PacketType getPacketTypeFromID(int id) {
		return id < PacketType.values().length ? PacketType.values()[id] : null;
	}

	@SubscribeEvent
	public void onServerPacket(ServerCustomPacketEvent event) {
		EntityPlayerMP player = ((NetHandlerPlayServer) event.getHandler()).player;
		player.getServer().addScheduledTask(new Runnable() {
			public void run() {
				try {
					processPacket(event.getPacket().payload(), Side.SERVER, player);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void processPacket(ByteBuf parBB, Side parSide, EntityPlayerMP player) throws IOException {
		if (parSide == Side.SERVER) {
			ByteBufInputStream bbis = new ByteBufInputStream(parBB);
			IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
			switch (getPacketTypeFromID(bbis.readInt())) {
				case CAST_SPELL: {
					int slot = bbis.readInt();
					if(cap != null){
						Ability ability = cap.getSlot(slot);
						if(ability == null) break;
						ability.cast(player);
					}
				}
				break;
				case SKILLEDIT_CHECK_CANOBTAIN: {
					if (cap == null) break;
					AoVSkill skillToCheck = AoVSkill.getSkillFromID(bbis.readInt());
					if (skillToCheck == null) break;
					if (skillToCheck.getParent() == null || cap.hasSkill(skillToCheck.getParent())) {
						if (cap.getSkillPoints() >= skillToCheck.getCost() && cap.getLevel() >= skillToCheck.getLevel() && cap.getSpentSkillPoints() >= skillToCheck.getSpentPoints()) {
							cap.addObtainedSkill(skillToCheck);
							cap.setSkillPoints(cap.getSkillPoints() - skillToCheck.getCost());
						}
					}
				}
					break;
				case RESETSKILLS_FULL: {
					if (cap == null) break;
					cap.reset(true);
				}
					break;
				case RESETSKILLS_MINOR: {
					if (cap == null) break;
					cap.reset(false);
				}
					break;
				case SPELLBAR_REMOVE: {
					if (cap == null) break;
					cap.removeSlot(bbis.readInt());
				}
					break;
				case SPELLBAR_ADDNEAR: {
					if (cap == null) break;
					cap.addToNearestSlot(Ability.construct(cap, bbis));
				}
					break;
				case CHARGES_RESET: {
					if (cap == null) break;
					cap.resetCharges();
				}
					break;
				default:
					break;
			}
			bbis.close();
		}
	}

}
