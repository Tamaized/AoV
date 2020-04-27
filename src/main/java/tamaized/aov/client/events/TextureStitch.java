package tamaized.aov.client.events;

import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tamaized.aov.AoV;

@Mod.EventBusSubscriber(modid = AoV.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TextureStitch {

	public static final ResourceLocation HEART = new ResourceLocation(AoV.MODID, "heart");
	public static AtlasTexture PARTICLE_MAP;

	@SubscribeEvent
	public static void stitch(TextureStitchEvent.Pre e) {
		if (e.getMap().getTextureLocation() == AtlasTexture.LOCATION_PARTICLES_TEXTURE) {
			PARTICLE_MAP = e.getMap();
			e.addSprite(HEART);
		}
	}

}
