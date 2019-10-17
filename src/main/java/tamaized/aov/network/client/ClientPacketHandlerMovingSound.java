package tamaized.aov.network.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;
import tamaized.aov.client.sound.EntityMovingSound;
import tamaized.aov.network.NetworkMessages;

public class ClientPacketHandlerMovingSound implements NetworkMessages.IMessage<ClientPacketHandlerMovingSound> {

	private int e;
	private ResourceLocation soundID;
	private float volume;
	private float pitch;

	public ClientPacketHandlerMovingSound(Entity entity, SoundEvent sound) {
		this(entity, sound, 1, 1);
	}

	public ClientPacketHandlerMovingSound(Entity entity, SoundEvent sound, float v, float p) {
		e = entity.getEntityId();
		soundID = sound.getRegistryName();
		volume = v;
		pitch = p;
	}

	@Override
	public void handle(PlayerEntity player) {
		classLoadingIsVeryFuckingStupidWhatTheFuck(player);
	}

	@OnlyIn(Dist.CLIENT)
	private void classLoadingIsVeryFuckingStupidWhatTheFuck(PlayerEntity player) {
		Minecraft.getInstance().getSoundHandler().play(new EntityMovingSound(ForgeRegistries.SOUND_EVENTS.getValue(soundID), SoundCategory.PLAYERS, player.world.getEntityByID(e), false, 0, volume, pitch));
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeInt(e);
		packet.writeResourceLocation(soundID);
		packet.writeFloat(volume);
		packet.writeFloat(pitch);
	}

	@Override
	public ClientPacketHandlerMovingSound fromBytes(PacketBuffer packet) {
		e = packet.readInt();
		soundID = packet.readResourceLocation();
		volume = packet.readFloat();
		pitch = packet.readFloat();
		return this;
	}
}
