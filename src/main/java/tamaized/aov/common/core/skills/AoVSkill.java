package tamaized.aov.common.core.skills;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.core.abilities.AbilityBase;

import java.util.Arrays;
import java.util.List;

public abstract class AoVSkill {

	private static final List<AoVSkill> registry = Lists.newArrayList();
	private final List<AbilityBase> abilities;
	private final List<TextComponentTranslation> description = Lists.newArrayList();

	public AoVSkill(List<AbilityBase> spells, TextComponentTranslation... desc) {
		description.clear();
		description.addAll(Arrays.asList(desc));
		abilities = spells;
		registry.add(this);
	}

	public static int getID(AoVSkill skill) {
		return registry.contains(skill) ? registry.indexOf(skill) : -1;
	}

	public static AoVSkill getSkillFromID(int id) {
		return id >= 0 && id < registry.size() ? registry.get(id) : null;
	}

	public final int getID() {
		return getID(this);
	}

	public final List<AbilityBase> getAbilities() {
		return abilities;
	}

	public abstract int getCharges();

	public abstract int getSpellPower();

	public abstract int getDodge();

	public abstract int getDoubleStrike();

	public abstract boolean grantsSelectiveFocus();

	public abstract boolean isClassCore();

	public abstract AoVSkill getParent();

	public abstract int getCost();

	public abstract int getLevel();

	public abstract int getSpentPoints();

	public abstract ResourceLocation getIcon();

	@SideOnly(Side.CLIENT)
	public final List<String> getDescription() {
		List<String> list = Lists.newArrayList();
		for (TextComponentTranslation s : description) {
			list.add(I18n.format(s.getKey(), s.getFormatArgs()));
		}
		return list;
	}

}
