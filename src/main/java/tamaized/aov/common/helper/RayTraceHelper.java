package tamaized.aov.common.helper;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class RayTraceHelper {

	private RayTraceHelper() {

	}

	public static Vec3d[] getPlayerTraceVec(PlayerEntity player, int distance) {
		Vec3d vec3d = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		Vec3d vec3d1 = player.getLook(1.0f);
		Vec3d vec3d2 = vec3d.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);
		return new Vec3d[]{vec3d, vec3d2};
	}

	public static RayTraceResult tracePath(Entity context, World world, PlayerEntity player, int distance, float size, HashSet<Entity> excluded) {
		Vec3d[] vecs = getPlayerTraceVec(player, distance);
		return tracePath(context, world, vecs[0], vecs[1], size, excluded);
	}

	public static RayTraceResult tracePath(Entity context, World world, Vec3d vec1, Vec3d vec2, float size, HashSet<Entity> excluded) {
		return tracePath(context, world, (float) vec1.x, (float) vec1.y, (float) vec1.z, (float) vec2.x, (float) vec2.y, (float) vec2.z, size, excluded);
	}

	private static RayTraceResult tracePath(Entity context, World world, float x, float y, float z, float tx, float ty, float tz, float size, HashSet<Entity> excluded) {
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
		RayTraceResult blockHit = world.rayTraceBlocks(new RayTraceContext(startVec, endVec, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, context));
		startVec = new Vec3d(x, y, z);
		endVec = new Vec3d(tx, ty, tz);
		Entity closestHitEntity = null;
		double currentHit = 0;
		AxisAlignedBB entityBb;
		Vec3d vecIntercept;
		for (Entity ent : allEntities) {
			if (ent.canBeCollidedWith() && !excluded.contains(ent)) {
				float entBorder = ent.getCollisionBorderSize();
				entityBb = ent.getBoundingBox();
				entityBb = entityBb.grow(entBorder, entBorder, entBorder);
				Optional<Vec3d> intercept = entityBb.rayTrace(startVec, endVec);
				if (entityBb.contains(startVec)) {
					if (currentHit >= 0.0D) {
						closestHitEntity = ent;
						vecIntercept = intercept.orElse(startVec);
						currentHit = 0.0D;
					}
				} else if (intercept.isPresent()) {
					Vec3d vec3d1 = intercept.get();
					double d1 = startVec.squareDistanceTo(vec3d1);
					if (d1 < currentHit || currentHit == 0.0D) {
						if (ent.getLowestRidingEntity() == context.getLowestRidingEntity()) {
							if (currentHit == 0.0D) {
								closestHitEntity = ent;
								vecIntercept = vec3d1;
							}
						} else {
							closestHitEntity = ent;
							vecIntercept = vec3d1;
							currentHit = d1;
						}
					}
				}
			}
		}
		if (closestHitEntity != null && (blockHit.getType() == RayTraceResult.Type.MISS || closestHitEntity.getDistanceSq(x, y, z) <= startVec.squareDistanceTo(blockHit.getHitVec()))) {
			blockHit = new EntityRayTraceResult(closestHitEntity);
		}
		return blockHit;
	}

}
