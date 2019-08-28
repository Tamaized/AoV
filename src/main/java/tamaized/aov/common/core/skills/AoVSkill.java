package tamaized.aov.common.core.skills;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.common.core.abilities.AbilityBase;

import java.util.Arrays;
import java.util.List;

public class AoVSkill {

	private final List<AbilityBase> abilities;
	private final List<TranslationTextComponent> description = Lists.newArrayList();

	private final TranslationTextComponent name;
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

	public AoVSkill(TranslationTextComponent name, ResourceLocation icon, int level, int spentpoints, int charges, int cost, int spellpower, int dodge, int doublestrike, boolean core, AoVSkill parent, List<AbilityBase> spells, TranslationTextComponent... desc) {
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

	public AoVSkill setupTooltip(TranslationTextComponent desc) {
		description.clear();
		addToDescription(name);
		if (isClassCore())
			addToDescription(new TranslationTextComponent("aov.skill.global.core"));

		addToDescription();
		if (getCharges() > 0)
			addToDescription(new TranslationTextComponent("aov.skill.global.charge", getCharges()));
		if (spellpower > 0)
			addToDescription(new TranslationTextComponent("aov.skill.global.spellpower", spellpower));
		if (dodge > 0)
			addToDescription(new TranslationTextComponent("aov.skill.global.dodge", dodge));
		if (doublestrike > 0)
			addToDescription(new TranslationTextComponent("aov.skill.global.doublestrike", doublestrike));

		if (!isClassCore()) {
			addToDescription();
			if (level > 0)
				addToDescription(new TranslationTextComponent("aov.skill.global.minlevel", level));
			if (spentpoints > 0)
				addToDescription(new TranslationTextComponent("aov.skill.global.minpoint", spentpoints));
			if (parent != null)
				addToDescription(new TranslationTextComponent("aov.skill.global.parent"), parent.getName());
		}

		if (desc != null) {
			addToDescription();
			addToDescription(desc);
		}
		return this;
	}

	private void addToDescription(TranslationTextComponent... components) {
		description.addAll(Arrays.asList(components));
		description.add(new TranslationTextComponent("\n"));
	}

	public final int getID() {
		return AoVSkills.getID(this);
	}

	public TranslationTextComponent getName() {
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
		for (TranslationTextComponent s : description) {
			Object[] args = new Object[s.getFormatArgs().length];
			for (int index = 0; index < args.length; index++)
				args[index] = I18n.format(String.valueOf(s.getFormatArgs()[index]));
			list.add(I18n.format(s.getKey(), args));
		}
		return list;
	}

	@OnlyIn(Dist.CLIENT)
	public final ITextComponent getDescriptionAsTextComponent() {
		TranslationTextComponent component = new TranslationTextComponent(description.get(0).getKey(), description.get(0).getFormatArgs());
		for (int i = 1; i < description.size(); i++)
			component.appendSibling(new TranslationTextComponent(description.get(i).getKey(), description.get(i).getFormatArgs()));
		return component.setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, component)));
	}

}
