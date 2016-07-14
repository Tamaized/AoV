package Tamaized.AoV.helper;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import Tamaized.AoV.AoV;
import Tamaized.AoV.common.handlers.ClientPacketHandler;

public class ParticleHelper {
	
	public enum Type{
		BURST
	}
	
	public static void sendPacketToClients(Type type, EntityLivingBase target, int range){
		switch(type){
			case BURST:
				packetTypeBurst(target, range);
				break;
			default:
				break;
		}
	}
	
	private static void packetTypeBurst(EntityLivingBase target, int range){
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ClientPacketHandler.TYPE_PARTICLE_BURST);
			outputStream.writeDouble(target.posX);
			outputStream.writeDouble(target.posY);
			outputStream.writeDouble(target.posZ);
			outputStream.writeInt(range);
			FMLProxyPacket pkt = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
			if(pkt != null) AoV.channel.sendToAllAround(pkt, new TargetPoint(target.worldObj.provider.getDimension(), target.posX, target.posY, target.posZ, 32*3));
			bos.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SideOnly(Side.CLIENT) //Crash if this runs on server, because it's not supposed to! (Even though the method itself wouldn't crash anything :D)
	public static void burstParticles(World world, double posX, double posY, double posZ, Random rand, int range){
		double d0 = (double) ((float) (posX-.5) + 0.4F + rand.nextFloat() * 0.2F);
		double d1 = (double) ((float) (posY) + 0.0F + rand.nextFloat() * 0.3F);
		double d2 = (double) ((float) (posZ-.5) + 0.4F + rand.nextFloat() * 0.2F);
		for(int x=-range; x<=range; x++){
			for(int z=-range; z<=range; z++){
				world.spawnParticle(EnumParticleTypes.PORTAL, d0+x, d1, d2+z, -x, 1, -z);
			}
		}
	}

}
