package tamaized.aov.common.core.skills;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;

public class NullSkill extends AoVSkill {

	public static final ResourceLocation ICON = new ResourceLocation("missingno");

	public NullSkill() {
		super(Lists.newArrayList());
	}

	@Override
	public int getCharges() {
		return 0;
	}

	@Override
	public int getSpellPower() {
		return 0;
	}

	@Override
	public int getDodge() {
		return 0;
	}

	@Override
	public int getDoubleStrike() {
		return 0;
	}

	@Override
	public boolean grantsSelectiveFocus() {
		return false;
	}

	@Override
	public boolean isClassCore() {
		return false;
	}

	@Override
	public AoVSkill getParent() {
		return null;
	}

	@Override
	public int getCost() {
		return 0;
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public int getSpentPoints() {
		return 0;
	}

	@Override
	public ResourceLocation getIcon() {
		return ICON;
	}
}
