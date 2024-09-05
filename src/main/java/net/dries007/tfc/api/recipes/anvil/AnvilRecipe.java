package net.dries007.tfc.api.recipes.anvil;

import su.terrafirmagreg.modules.metal.objects.tile.TileMetalAnvil;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;


import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.compat.jei.IJEISimpleRecipe;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.forge.ForgeSteps;
import net.dries007.tfc.util.skills.SmithingSkill;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static su.terrafirmagreg.data.MathConstants.RNG;

/**
 * Anvil Recipe
 * <p>
 * They all take a single item input and will produce a single item output
 * todo: in 1.13+ move this to a json recipe type
 */
@Getter
public class AnvilRecipe extends IForgeRegistryEntry.Impl<AnvilRecipe> implements IJEISimpleRecipe {

    public static final NonNullList<ItemStack> EMPTY = NonNullList.create();
    private static long SEED = 0;

    protected final IIngredient<ItemStack> ingredient;
    protected final ItemStack outputItem;
    protected final ForgeRule[] rules;
    protected final Metal.Tier tier;
    protected final long workingSeed;
    protected final SmithingSkill.Type skillBonusType;

    public AnvilRecipe(ResourceLocation name, IIngredient<ItemStack> ingredient, ItemStack outputItem, Metal.Tier tier,
                       @Nullable SmithingSkill.Type skillBonusType, ForgeRule... rules) {
        this.ingredient = ingredient;
        this.outputItem = outputItem;

        this.tier = tier;
        this.skillBonusType = skillBonusType;
        this.rules = rules;
        if (rules.length == 0 || rules.length > 3)
            throw new IllegalArgumentException("Rules length must be within the closed interval [1, 3]");

        setRegistryName(name);
        workingSeed = ++SEED;
    }

    @NotNull
    public static List<AnvilRecipe> getAllFor(ItemStack stack) {
        return TFCRegistries.ANVIL.getValuesCollection()
                .stream()
                .filter(x -> x.matches(stack))
                .collect(Collectors.toList());
    }

    public boolean matches(ItemStack input) {
        return ingredient.test(input);
    }

    public boolean matches(ForgeSteps steps) {
        for (ForgeRule rule : rules) {
            if (!rule.matches(steps))
                return false;
        }
        return true;
    }

    @NotNull
    public NonNullList<ItemStack> getOutputItem(ItemStack input) {
        return matches(input) ? NonNullList.withSize(1, outputItem.copy()) : EMPTY;
    }

    public int getTarget(long worldSeed) {
        RNG.setSeed(worldSeed + workingSeed);
        return 40 + RNG.nextInt(TileMetalAnvil.WORK_MAX + -2 * 40);
    }

    @Override
    public NonNullList<IIngredient<ItemStack>> getIngredients() {
        NonNullList<IIngredient<ItemStack>> list = NonNullList.create();
        list.add(ingredient);
        list.add(IIngredient.of("hammer"));
        return list;
    }

    @Override
    public NonNullList<ItemStack> getOutputs() {
        return NonNullList.withSize(1, outputItem);
    }
}
