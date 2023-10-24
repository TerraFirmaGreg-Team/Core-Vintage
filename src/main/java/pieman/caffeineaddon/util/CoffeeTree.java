package pieman.caffeineaddon.util;

import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.api.util.IFruitTreeGenerator;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.world.classic.worldgen.WorldGenFruitTrees;
import net.dries007.tfc.world.classic.worldgen.trees.FruitTreeGen;
import net.minecraft.item.ItemStack;
import pieman.caffeineaddon.CoffeeTreeGen;
import pieman.caffeineaddon.init.ModItems;

public enum CoffeeTree implements IFruitTree {
	COFFEE(Month.APRIL, 2, Month.SEPTEMBER, 3, 5f, 35f, 100f, 400f, 0.33f);

	static {
		for (IFruitTree tree : values()) {
			WorldGenFruitTrees.register(tree);
		}
	}

	public static final IFruitTreeGenerator COFFEETREEGEN = new CoffeeTreeGen();
	private final Month flowerMonthStart;
	private final int floweringMonths;
	private final Month harvestMonthStart;
	private final int harvestingMonths;
	private final float growthTime;
	private final float minTemp;
	private final float maxTemp;
	private final float minRain;
	private final float maxRain;

	CoffeeTree(Month flowerMonthStart, int floweringMonths, Month harvestMonthStart, int harvestingMonths,
			float minTemp, float maxTemp, float minRain, float maxRain, float growthTime) {
		this.flowerMonthStart = flowerMonthStart;
		this.floweringMonths = floweringMonths;
		this.harvestMonthStart = harvestMonthStart;
		this.harvestingMonths = harvestingMonths;
		this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
		this.minRain = minRain;
		this.maxRain = maxRain;
	}

	@Override
	public float getGrowthTime() {
		return this.growthTime;
	}

	@Override
	public boolean isFlowerMonth(Month month) {
		Month testing = this.flowerMonthStart;
		for (int i = 0; i < this.floweringMonths; i++) {
			if (testing.equals(month))
				return true;
			testing = testing.next();
		}
		return false;
	}

	@Override
	public boolean isHarvestMonth(Month month) {
		Month testing = this.harvestMonthStart;
		for (int i = 0; i < this.harvestingMonths; i++) {
			if (testing.equals(month))
				return true;
			testing = testing.next();
		}
		return false;
	}

	@Override
	public boolean isValidConditions(float temperature, float rainfall) {
		return minTemp - 5 < temperature && temperature < maxTemp + 5 && minRain - 50 < rainfall
				&& rainfall < maxRain + 50;
	}

	@Override
	public boolean isValidForGrowth(float temperature, float rainfall) {
		return minTemp < temperature && temperature < maxTemp && minRain < rainfall && rainfall < maxRain;
	}

	@Override
	public ItemStack getFoodDrop() {
		return new ItemStack(ModItems.CoffeeCherries);
	}

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

	@Override
	public IFruitTreeGenerator getGenerator() {
		return COFFEETREEGEN;
	}

}
