package tamaized.aov.common.core.skills;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.common.core.abilities.AbilityBase;

import java.util.List;

public class AoVSkill {

	private final List<AbilityBase> abilities;
	private final List<TextComponentTranslation> description = Lists.newArrayList();

	private final TextComponentTranslation name;
	private final ResourceLocation icon;
	private final int level;
	private final int spentpoints;
	private final int charges;
	private final int cost;
	private final int spellpower;
	private final int dodge;
	private final int doublestrike;
	private final boolean core;
	private final AoVSkill parent;

	public AoVSkill(TextComponentTranslation name, ResourceLocation icon, int level, int spentpoints, int charges, int cost, int spellpower, int dodge, int doublestrike, boolean core, AoVSkill parent, List<AbilityBase> spells, TextComponentTranslation... desc) {
		this.name = name;
		this.icon = icon;
		this.level = level;
		this.spentpoints = spentpoints;
		this.charges = charges;
		this.cost = cost;
		this.spellpower = spellpower;
		this.dodge = dodge;
		this.doublestrike = doublestrike;
		this.core = core;
		this.parent = parent;
		abilities = spells;
	}

	public AoVSkill setupTooltip(TextComponentTranslation desc) {
		description.clear();
		description.add(name);
		if (isClassCore()) {
			description.add(new TextComponentTranslation("aov.skill.global.core"));
			description.add(new TextComponentTranslation(""));
		}
		if (getCharges() > 0)
			description.add(new TextComponentTranslation("aov.skill.global.charge", getCharges()));
		if (spellpower > 0)
			description.add(new TextComponentTranslation("aov.skill.global.spellpower", spellpower));
		if (dodge > 0)
			description.add(new TextComponentTranslation("aov.skill.global.dodge", dodge));
		if (doublestrike > 0)
			description.add(new TextComponentTranslation("aov.skill.global.doublestrike", doublestrike));

		if (!isClassCore()) {
			description.add(new TextComponentTranslation(""));
			if (level > 0)
				description.add(new TextComponentTranslation("aov.skill.global.minlevel", level));
			if (spentpoints > 0)
				description.add(new TextComponentTranslation("aov.skill.global.minpoint", spentpoints));
			if (parent != null)
				description.add(new TextComponentTranslation("aov.skill.global.parent", parent.getName().getKey()));
		}

		if (desc != null) {
			description.add(new TextComponentTranslation(""));
			description.add(desc);
		}
		return this;
	}

	public final int getID() {
		return AoVSkills.getID(this);
	}

	public TextComponentTranslation getName() {
		return name;
	}

	public final List<AbilityBase> getAbilities() {
		return abilities;
	}

	public int getCharges() {
		return charges;
	}

	public int getSpellPower() {
		return spellpower;
	}

	public int getDodge() {
		return dodge;
	}

	public int getDoubleStrike() {
		return doublestrike;
	}

	public boolean isClassCore() {
		return core;
	}

	public AoVSkill getParent() {
		return parent;
	}

	public int getCost() {
		return cost;
	}

	public int getLevel() {
		return level;
	}

	public int getSpentPoints() {
		return spentpoints;
	}

	public ResourceLocation getIcon() {
		return icon;
	}

	@OnlyIn(Dist.CLIENT)
	public final List<String> getDescription() {
		List<String> list = Lists.newArrayList();
		for (TextComponentTranslation s : description) {
			Object[] args = new Object[s.getFormatArgs().length];
			for (int index = 0; index < args.length; index++)
				args[index] = I18n.format(String.valueOf(s.getFormatArgs()[index]));
			list.add(I18n.format(s.getKey(), args));
		}
		return list;
	}

}
