package tamaized.aov.client.events;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tamaized.aov.AoV;

@Mod.EventBusSubscriber(modid = AoV.MODID, value = Dist.CLIENT)
public class TextureStitch {

	public static final ResourceLocation HEART = new ResourceLocation(AoV.MODID, "particle/heart");

	@SubscribeEvent
	public static void stitch(TextureStitchEvent e) {
		e.getMap().registerSprite(HEART);
	}

}
