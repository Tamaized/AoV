package tamaized.aov.common.helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import tamaized.tammodized.common.helper.RayTraceHelper;

import javax.annotation.Nullable;
import java.util.HashSet;

public class UtilHelper {

	public static void setSize(Entity e, float width, float height) {
		if (width != e.width || height != e.height) {
			float f = e.width;
			e.width = width;
			e.height = height;

			if (e.width < f) {
				double d0 = (double) width / 2.0D;
				e.setEntityBoundingBox(new AxisAlignedBB(e.posX - d0, e.posY, e.posZ - d0, e.posX + d0, e.posY + (double) e.height, e.posZ + d0));
				return;
			}

			AxisAlignedBB axisalignedbb = e.getEntityBoundingBox();
			e.setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double) e.width, axisalignedbb.minY + (double) e.height, axisalignedbb.minZ + (double) e.width));

			if (e.width > f && !e.world.isRemote) {
				e.move(MoverType.SELF, (double) (f - e.width), 0.0D, (double) (f - e.width));
			}
		}
	}

	public static Vec3d getSpellLocation(EntityPlayer caster, int maxDistance, @Nullable Entity target) {
		if (target == null) {
			HashSet<Entity> exclude = new HashSet<>();
			exclude.add(caster);
			RayTraceResult result = RayTraceHelper.tracePath(caster.world, caster, maxDistance, 1, exclude);
			if (result != null && result.typeOfHit != null) {
				switch (result.typeOfHit) {
					case BLOCK: {
						BlockPos pos = result.getBlockPos();
						return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
					}
					case ENTITY: {
						return result.entityHit.getPositionVector();
					}
				}
			}
			return caster.getPositionVector();
		} else
			return target.getPositionVector();
	}

}
