package tamaized.aov.registry;

import tamaized.aov.AoV;
import tamaized.aov.network.ClientPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import tamaized.tammodized.common.helper.PacketHelper;
import tamaized.tammodized.common.helper.PacketHelper.PacketWrapper;

import java.io.IOException;

@Mod.EventBusSubscriber
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

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		aura = registerSound(event, "aura");
		bladebarrier = registerSound(event, "bladebarrier");
		boost = registerSound(event, "boost");
		burst = registerSound(event, "burst");
		cast = registerSound(event, "cast");
		cast_2 = registerSound(event, "cast_2");
		destruction = registerSound(event, "destruction");
		firestrike = registerSound(event, "firestrike");
		heal = registerSound(event, "heal");
		implosion = registerSound(event, "implosion");
		restore = registerSound(event, "restore");

	}

	private static SoundEvent registerSound(RegistryEvent.Register<SoundEvent> event, String soundName) {
		SoundEvent sound = new SoundEvent(new ResourceLocation(AoV.modid, soundName)).setRegistryName(soundName);
		event.getRegistry().register(sound);
		return sound;
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
