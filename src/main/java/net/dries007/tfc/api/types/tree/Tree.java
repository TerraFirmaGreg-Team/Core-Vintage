package net.dries007.tfc.api.types.tree;

import net.dries007.tfc.api.types.tree.util.ITreeGenerator;
import net.dries007.tfc.types.DefaultTrees;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dries007.tfc.types.DefaultTrees.GEN_NORMAL;


public class Tree {

	private final int maxGrowthRadius;
	private final float dominance;
	private final int maxHeight;
	private final int maxDecayDistance;
	private final boolean isConifer;
	private final ITreeGenerator bushGenerator;
	private final boolean canMakeTannin;
	private final float minGrowthTime;
	private final float minTemp;
	private final float maxTemp;
	private final float minRain;
	private final float maxRain;
	private final float minDensity;
	private final float maxDensity;
	private final float burnTemp;
	private final int burnTicks;

	/* This is open to be replaced, i.e. for dynamic trees */
	private ITreeGenerator generator;

	/**
	 * This is a registry object that will create a number of things:
	 * 1. Wood logs, planks, and leaf blocks, and all the respective variants
	 * 2. A Tree object to be used in TFC world gen
	 * <p>
	 * Addon mods that want to add trees should subscribe to the registry event for this class
	 * They also must put (in their mod) the required resources in /assets/tfc/...
	 * <p>
	 * When using this class, use the provided Builder to create your trees. This will require all the default values, as well as
	 * provide optional values that you can change
	 *
	 * @param generator        the generator that should be called to generate this tree, both during world gen and when growing from a sapling
	 * @param minTemp          min temperature
	 * @param maxTemp          max temperature
	 * @param minRain          min rainfall
	 * @param maxRain          max rainfall
	 * @param minDensity       min density. Use -1 to get all density values. 0.1 is the default, to create really low density regions of no trees
	 * @param maxDensity       max density. Use 2 to get all density values
	 * @param dominance        how much this tree is chosen over other trees. Range 0 <> 10 with 10 being the most common
	 * @param maxGrowthRadius  used to check growth conditions
	 * @param maxHeight        used to check growth conditions
	 * @param maxDecayDistance maximum decay distance for leaves
	 * @param isConifer        todo: make this do something
	 * @param bushGenerator    a generator to make small bushes, null means the tree won't generate bushes
	 * @param minGrowthTime    the amount of time (in in-game days) that this tree requires to grow
	 * @param burnTemp         the temperature at which this will burn in a fire pit or similar
	 * @param burnTicks        the number of ticks that this will burn in a fire pit or similar
	 */
	public Tree(@Nonnull ITreeGenerator generator, float minTemp, float maxTemp, float minRain, float maxRain, float minDensity, float maxDensity, float dominance, int maxGrowthRadius, int maxHeight, int maxDecayDistance, boolean isConifer, @Nullable ITreeGenerator bushGenerator, boolean canMakeTannin, float minGrowthTime, float burnTemp, int burnTicks) {
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
		this.minRain = minRain;
		this.maxRain = maxRain;
		this.dominance = dominance;
		this.maxGrowthRadius = maxGrowthRadius;
		this.maxHeight = maxHeight;
		this.maxDecayDistance = maxDecayDistance;
		this.isConifer = isConifer;
		this.minGrowthTime = minGrowthTime;
		this.minDensity = minDensity;
		this.maxDensity = maxDensity;
		this.bushGenerator = bushGenerator;
		this.canMakeTannin = canMakeTannin;
		this.burnTemp = burnTemp;
		this.burnTicks = burnTicks;

		this.generator = generator;
	}

	public boolean isValidLocation(float temp, float rain, float density) {
		return minTemp <= temp && maxTemp >= temp && minRain <= rain && maxRain >= rain && minDensity <= density && maxDensity >= density;
	}

	@SuppressWarnings("unused")
	public void setTreeGenerator(ITreeGenerator generator) {
		this.generator = generator;
	}

	public int getMaxGrowthRadius() {
		return maxGrowthRadius;
	}

	public float getDominance() {
		return dominance;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public int getMaxDecayDistance() {
		return maxDecayDistance;
	}

	public boolean isConifer() {
		return isConifer;
	}

	public boolean canMakeTannin() {
		return canMakeTannin;
	}

	public boolean hasBushes() {
		return bushGenerator != null;
	}

	@Nullable
	public ITreeGenerator getBushGen() {
		return bushGenerator;
	}

	public ITreeGenerator getGenerator() {
		return generator;
	}

	public float getMinGrowthTime() {
		return minGrowthTime;
	}

	public float getBurnTemp() {
		return burnTemp;
	}

	public int getBurnTicks() {
		return burnTicks;
	}

	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (GuiScreen.isShiftKeyDown()) {
			tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.climate_info"));
			tooltip.add(TextFormatting.BLUE + I18n.format("tfc.tooltip.climate_info_rainfall", (int) minRain, (int) maxRain));
			tooltip.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.climate_info_temperature", String.format("%.1f", minTemp), String.format("%.1f", maxTemp)));
		} else {
			tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.hold_shift_for_climate_info"));
		}
	}

	public static class Builder {
		private final float minTemp;
		private final float maxTemp;
		private final float minRain;
		private final float maxRain;
		private ITreeGenerator gen;
		private float minDensity;
		private float maxDensity;
		private float dominance;
		private int maxHeight;
		private int maxGrowthRadius;
		private int maxDecayDistance;
		private boolean isConifer;
		private ITreeGenerator bushGenerator;
		private boolean canMakeTannin;
		private float minGrowthTime;
		private float burnTemp;
		private int burnTicks;

		public Builder(float minRain, float maxRain, float minTemp, float maxTemp) {
			this.minTemp = minTemp; // required values
			this.maxTemp = maxTemp;
			this.minRain = minRain;
			this.maxRain = maxRain;
			this.gen = GEN_NORMAL; // Заменить на ген DT по дефолту, и убрать setGenerator(), так как для кустов вызывается setBushes()
			this.maxGrowthRadius = 1; // default values
			this.dominance = 0.001f * (maxTemp - minTemp) * (maxRain - minRain);
			this.maxHeight = 6;
			this.maxDecayDistance = 4;
			this.isConifer = false;
			this.bushGenerator = null;
			this.canMakeTannin = false;
			this.minGrowthTime = 7;
			this.minDensity = 0.1f;
			this.maxDensity = 2f;
			this.burnTemp = 675;
			this.burnTicks = 1500;
		}

		public Builder setGenerator(@Nonnull ITreeGenerator gen) {
			this.gen = gen;
			return this;
		}

		public Builder setRadius(int maxGrowthRadius) {
			this.maxGrowthRadius = maxGrowthRadius;
			return this;
		}

		public Builder setDecayDist(int maxDecayDistance) {
			this.maxDecayDistance = maxDecayDistance;
			return this;
		}

		public Builder setConifer() {
			isConifer = true;
			return this;
		}

		public Builder setBushes() {
			bushGenerator = DefaultTrees.GEN_BUSHES;
			return this;
		}

		public Builder setBushes(ITreeGenerator bushGenerator) {
			this.bushGenerator = bushGenerator;
			return this;
		}

		public Builder setTannin() {
			canMakeTannin = true;
			return this;
		}

		public Builder setHeight(int maxHeight) {
			this.maxHeight = maxHeight;
			return this;
		}

		public Builder setGrowthTime(float growthTime) {
			this.minGrowthTime = growthTime;
			return this;
		}

		public Builder setDensity(float min, float max) {
			this.minDensity = min;
			this.maxDensity = max;
			return this;
		}

		public Builder setDominance(float dom) {
			this.dominance = dom;
			return this;
		}

		public Builder setBurnInfo(float burnTemp, int burnTicks) {
			this.burnTemp = burnTemp;
			this.burnTicks = burnTicks;
			return this;
		}

		public Tree build() {
			return new Tree(gen, minTemp, maxTemp, minRain, maxRain, minDensity, maxDensity, dominance, maxGrowthRadius, maxHeight, maxDecayDistance, isConifer, bushGenerator, canMakeTannin, minGrowthTime, burnTemp, burnTicks);
		}
	}
}
