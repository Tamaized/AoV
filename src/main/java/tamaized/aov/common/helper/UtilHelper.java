package tamaized.aov.common.helper;

import cpw.mods.modlauncher.api.INameMappingService;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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
		if (width != e.width || height != e.height) {
			float f = e.width;
			e.width = width;
			e.height = height;

			if (e.width < f) {
				double d0 = (double) width / 2.0D;
				e.setBoundingBox(new AxisAlignedBB(e.posX - d0, e.posY, e.posZ - d0, e.posX + d0, e.posY + (double) e.height, e.posZ + d0));
				return;
			}

			AxisAlignedBB axisalignedbb = e.getBoundingBox();
			e.setBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double) e.width, axisalignedbb.minY + (double) e.height, axisalignedbb.minZ + (double) e.width));

			if (e.width > f && !e.world.isRemote) {
				e.move(MoverType.SELF, (double) (f - e.width), 0.0D, (double) (f - e.width));
			}
		}
	}

	public static Vec3d getSpellLocation(PlayerEntity caster, int maxDistance, @Nullable Entity target) {
		if (target == null) {
			HashSet<Entity> exclude = new HashSet<>();
			exclude.add(caster);
			RayTraceResult result = RayTraceHelper.tracePath(caster.world, caster, maxDistance, 1, exclude);
			if (result != null && result.type != null) {
				switch (result.type) {
					case BLOCK: {
						BlockPos pos = result.getBlockPos();
						return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
					}
					case ENTITY: {
						return result.entity.getPositionVector();
					}
				}
			}
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
