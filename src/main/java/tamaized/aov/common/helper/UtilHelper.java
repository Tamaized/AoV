package tamaized.aov.common.helper;

import cpw.mods.modlauncher.api.INameMappingService;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.HashSet;

public class UtilHelper {

	public static Field findField(Class<?> clazz, String name) {
		try {
			Field f = clazz.getDeclaredField(ObfuscationReflectionHelper.remapName(INameMappingService.Domain.FIELD, name));
			f.setAccessible(true);
			return f;
		} catch (Exception e) {
			throw new UnableToFindFieldException(e);
		}
	}

	public static void setSize(Entity e, float width, float height) {
		if (true)
			return; // TODO
		if (width != e.getWidth() || height != e.getHeight()) {
			float f = e.getWidth();
			//			e.getWidth() = width;
			//			e.getHeight() = height;

			if (e.getWidth() < f) {
				double d0 = (double) width / 2.0D;
				e.setBoundingBox(new AxisAlignedBB(e.posX - d0, e.posY, e.posZ - d0, e.posX + d0, e.posY + (double) e.getHeight(), e.posZ + d0));
				return;
			}

			AxisAlignedBB axisalignedbb = e.getBoundingBox();
			e.setBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double) e.getWidth(), axisalignedbb.minY + (double) e.getHeight(), axisalignedbb.minZ + (double) e.getWidth()));

			if (e.getWidth() > f && !e.world.isRemote) {
				e.move(MoverType.SELF, new Vec3d(f - e.getWidth(), 0.0D, f - e.getWidth()));
			}
		}
	}

	public static Vec3d getSpellLocation(PlayerEntity caster, int maxDistance, @Nullable Entity target) {
		if (target == null) {
			HashSet<Entity> exclude = new HashSet<>();
			exclude.add(caster);
			RayTraceResult result = RayTraceHelper.tracePath(caster, caster.world, caster, maxDistance, 1, exclude);
			if (result instanceof BlockRayTraceResult)
				return new Vec3d(((BlockRayTraceResult) result).getPos());
			if (result instanceof EntityRayTraceResult)
				return ((EntityRayTraceResult) result).getEntity().getPositionVec();
			return caster.getPositionVector();
		} else
			return target.getPositionVector();
	}

	private static class UnableToFindFieldException extends RuntimeException {
		private UnableToFindFieldException(Exception e) {
			super(e);
		}
	}

}
