package su.terrafirmagreg.modules.metal.objects.recipe.anvil;

import su.terrafirmagreg.modules.metal.objects.tile.TileMetalAnvil;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.forge.ForgeSteps;
import net.dries007.tfc.util.skills.SmithingSkill;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import static su.terrafirmagreg.data.MathConstants.RNG;

@Getter
public class AnvilRecipe
  implements IAnvilRecipe {

  public static final NonNullList<ItemStack> EMPTY = NonNullList.create();
  private static long SEED = 0;

  protected final IIngredient<ItemStack> inputItem;
  protected final ItemStack outputItem;
  protected final ResourceLocation recipeName;
  protected final ForgeRule[] rules;
  protected final Metal.Tier tier;
  protected final SmithingSkill.Type skillBonusType;
  protected final long workingSeed;

  public AnvilRecipe(ResourceLocation recipeName, IIngredient<ItemStack> inputItem,
                     ItemStack outputItem, Metal.Tier tier, @Nullable SmithingSkill.Type skillBonusType,
                     ForgeRule... rules) {
    this.inputItem = inputItem;
    this.outputItem = outputItem;
    this.tier = tier;
    this.skillBonusType = skillBonusType;
    this.rules = rules;
    this.recipeName = recipeName;

    if (rules.length == 0 || rules.length > 3) {
      throw new IllegalArgumentException("Rules length must be within the closed interval [1, 3]");
    }

    workingSeed = ++SEED;
  }

  public NonNullList<ItemStack> getOutputItem(ItemStack inputItem) {
    return isValidInput(inputItem) ? NonNullList.withSize(1, outputItem.copy()) : EMPTY;

  }

  public boolean isValidInput(ItemStack inputItem) {
    return this.inputItem.test(inputItem);
  }

  @Override
  public boolean matches(ForgeSteps steps) {
    for (ForgeRule rule : rules) {
      if (!rule.matches(steps)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getTarget(long worldSeed) {
    RNG.setSeed(worldSeed + workingSeed);
    return 40 + RNG.nextInt(TileMetalAnvil.WORK_MAX + -2 * 40);
  }
}
