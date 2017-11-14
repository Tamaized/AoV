package tamaized.aov.proxy;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tamaized.tammodized.proxy.AbstractProxy;

public abstract class CommonProxy extends AbstractProxy {

	public CommonProxy(Side side) {
		super(side);
	}

	public void spawnFluffParticle(World world, Vec3d pos, Vec3d target, int life, float gravity, float scale, int color){

	}

}
