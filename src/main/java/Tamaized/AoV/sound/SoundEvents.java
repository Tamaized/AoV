package Tamaized.AoV.sound;

import java.io.IOException;

import Tamaized.AoV.AoV;
import Tamaized.AoV.network.ClientPacketHandler;
import Tamaized.TamModized.helper.PacketHelper;
import Tamaized.TamModized.helper.PacketHelper.PacketWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SoundEvents {

	public static SoundEvent aura = null;
	public static SoundEvent bladebarrier = null;
	public static SoundEvent boost = null;
	public static SoundEvent burst = null;
	public static SoundEvent cast = null;
	public static SoundEvent cast_2 = null;
	public static SoundEvent destruction = null;
	public static SoundEvent firestrike = null;
	public static SoundEvent heal = null;
	public static SoundEvent implosion = null;
	public static SoundEvent restore = null;

	public static void register() {
		aura = registerSound("aura");
		bladebarrier = registerSound("bladebarrier");
		boost = registerSound("boost");
		burst = registerSound("burst");
		cast = registerSound("cast");
		cast_2 = registerSound("cast_2");
		destruction = registerSound("destruction");
		firestrike = registerSound("firestrike");
		heal = registerSound("heal");
		implosion = registerSound("implosion");
		restore = registerSound("restore");

	}

	private static SoundEvent registerSound(String soundName) {
		ResourceLocation soundID = new ResourceLocation(AoV.modid, soundName);
		return GameRegistry.register(new SoundEvent(soundID).setRegistryName(soundID));
	}

	public static void playMovingSoundOnServer(SoundEvent sound, Entity entity) {
		try {
			PacketWrapper packet = PacketHelper.createPacket(AoV.channel, AoV.networkChannelName, ClientPacketHandler.getPacketTypeID(ClientPacketHandler.PacketType.MovingSound));
			packet.getStream().writeInt(entity.getEntityId());
			packet.getStream().writeInt(SoundEvent.REGISTRY.getIDForObject(sound));
			packet.sendPacket(new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 32));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
