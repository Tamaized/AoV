package tamaized.aov.registry;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import tamaized.aov.AoV;
import tamaized.aov.network.client.ClientPacketHandlerMovingSound;

@Mod.EventBusSubscriber(modid = AoV.modid)
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

	public static SoundEvent activate = null;
	public static SoundEvent burn = null;
	public static SoundEvent draw1 = null;
	public static SoundEvent draw2 = null;
	public static SoundEvent spread = null;
	public static SoundEvent redraw = null;
	public static SoundEvent timedilation = null;
	public static SoundEvent aspectedbenefic = null;
	public static SoundEvent aspectedhelios = null;
	public static SoundEvent benefic = null;
	public static SoundEvent essentialdignity = null;
	public static SoundEvent helios = null;
	public static SoundEvent malefic_cast = null;
	public static SoundEvent malefic_hit = null;
	public static SoundEvent gravity = null;
	public static SoundEvent celestialopposition = null;

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

		activate = registerSound(event, "activate");
		burn = registerSound(event, "burn");
		draw1 = registerSound(event, "draw1");
		draw2 = registerSound(event, "draw2");
		spread = registerSound(event, "spread");
		redraw = registerSound(event, "redraw");
		timedilation = registerSound(event, "timedilation");
		aspectedbenefic = registerSound(event, "aspectedbenefic");
		aspectedhelios = registerSound(event, "aspectedhelios");
		benefic = registerSound(event, "benefic");
		essentialdignity = registerSound(event, "essentialdignity");
		helios = registerSound(event, "helios");
		malefic_cast = registerSound(event, "malefic_cast");
		malefic_hit = registerSound(event, "malefic_hit");
		gravity = registerSound(event, "gravity");
		celestialopposition = registerSound(event, "celestialopposition");
	}

	private static SoundEvent registerSound(RegistryEvent.Register<SoundEvent> event, String soundName) {
		SoundEvent sound = new SoundEvent(new ResourceLocation(AoV.modid, soundName)).setRegistryName(soundName);
		event.getRegistry().register(sound);
		return sound;
	}

	public static void playMovingSoundOnServer(SoundEvent sound, Entity entity) {
		AoV.network.sendToAllAround(new ClientPacketHandlerMovingSound.Packet(entity, SoundEvent.REGISTRY.getIDForObject(sound)), new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 32));
	}

}
