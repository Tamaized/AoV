package tamaized.aov.common.helper;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;

public class RayTraceHelper {

	private RayTraceHelper() {

	}

	public static Vec3d[] getPlayerTraceVec(PlayerEntity player, int distance) {
		Vec3d vec3d = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		Vec3d vec3d1 = player.getLook(1.0f);
		Vec3d vec3d2 = vec3d.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);
		return new Vec3d[]{vec3d, vec3d2};
	}

	public static RayTraceResult tracePath(World world, PlayerEntity player, int distance, float size, HashSet<Entity> excluded) {
		Vec3d[] vecs = getPlayerTraceVec(player, distance);
		return tracePath(world, vecs[0], vecs[1], size, excluded);
	}

	public static RayTraceResult tracePath(World world, Vec3d vec1, Vec3d vec2, float size, HashSet<Entity> excluded) {
		return tracePath(world, (float) vec1.x, (float) vec1.y, (float) vec1.z, (float) vec2.x, (float) vec2.y, (float) vec2.z, size, excluded);
	}

	private static RayTraceResult tracePath(World world, float x, float y, float z, float tx, float ty, float tz, float size, HashSet<Entity> excluded) {
		Vec3d startVec = new Vec3d(x, y, z);
		Vec3d endVec = new Vec3d(tx, ty, tz);
		float minX = x < tx ? x : tx;
		float minY = y < ty ? y : ty;
		float minZ = z < tz ? z : tz;
		float maxX = x > tx ? x : tx;
		float maxY = y > ty ? y : ty;
		float maxZ = z > tz ? z : tz;
		AxisAlignedBB bb = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ).grow(size, size, size);
		List<Entity> allEntities = excluded == null ? Lists.newArrayList() : world.getEntitiesWithinAABBExcludingEntity(null, bb);
		RayTraceResult blockHit = world.rayTraceBlocks(startVec, endVec);
		startVec = new Vec3d(x, y, z);
		endVec = new Vec3d(tx, ty, tz);
		Entity closestHitEntity = null;
		float closestHit = Float.POSITIVE_INFINITY;
		float currentHit;
		AxisAlignedBB entityBb;
		RayTraceResult intercept;
		for (Entity ent : allEntities) {
			if (ent.canBeCollidedWith() && !excluded.contains(ent)) {
				float entBorder = ent.getCollisionBorderSize();
				entityBb = ent.getBoundingBox();
				entityBb = entityBb.grow(entBorder, entBorder, entBorder);
				intercept = entityBb.calculateIntercept(startVec, endVec);
				if (intercept != null) {
					currentHit = (float) intercept.hitVec.distanceTo(startVec);
					if (currentHit < closestHit || currentHit == 0) {
						closestHit = currentHit;
						closestHitEntity = ent;
					}
				}
			}
		}
		if (closestHitEntity != null && (blockHit == null || blockHit.type != RayTraceResult.Type.BLOCK || closestHitEntity.getDistance(x, y, z) <= startVec.distanceTo(blockHit.hitVec))) {
			blockHit = new RayTraceResult(closestHitEntity);
		}
		return blockHit;
	}

}
