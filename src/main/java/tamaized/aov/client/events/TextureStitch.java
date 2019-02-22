package tamaized.aov.client.events;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tamaized.aov.AoV;

@Mod.EventBusSubscriber(modid = AoV.MODID, value = Dist.CLIENT)
public class TextureStitch {

	public static final ResourceLocation HEART = new ResourceLocation(AoV.MODID, "particle/heart");

	@SubscribeEvent
	public static void stitch(TextureStitchEvent e) {
		e.getMap().registerSprite(Minecraft.getInstance().getResourceManager(), HEART);
	}

}
