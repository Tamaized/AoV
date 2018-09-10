package tamaized.aov.common.helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.AxisAlignedBB;

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

}
