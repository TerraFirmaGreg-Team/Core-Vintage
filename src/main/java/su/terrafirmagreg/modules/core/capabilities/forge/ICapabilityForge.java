package su.terrafirmagreg.modules.core.capabilities.forge;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.util.forge.ForgeStep;
import net.dries007.tfc.util.forge.ForgeSteps;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Capability for forgeable items (not to be confused with forgeable items that needs heat) Allows items to be forged in the anvil.
 */
public interface ICapabilityForge extends INBTSerializable<NBTTagCompound> {

  /**
   * Gets the current amount of work on the object
   */
  int getWork();

  /**
   * Sets the current amount of work on the object
   */
  void setWork(int work);

  /**
   * Gets the current saved recipe's registry name Returns null if no recipe name is currently saved
   */
  @Nullable
  ResourceLocation getRecipeName();

  /**
   * Sets the recipe name from an {@link AnvilRecipe}. If null, sets the recipe name to null
   */
  default void setRecipe(@Nullable AnvilRecipe recipe) {
    setRecipe(recipe != null ? recipe.getRegistryName() : null);
  }

  /**
   * Sets the recipe name from an {@link AnvilRecipe}'s registry name.
   *
   * @param recipeName a registry name of an anvil recipe
   */
  void setRecipe(@Nullable ResourceLocation recipeName);

  /**
   * Gets the last three steps, wrapped in a {@link ForgeSteps} instance. The return value is nonnull, however the individual steps might be
   */
  @Nonnull
  ForgeSteps getSteps();

  /**
   * Adds a step to the object, shuffling the last three steps down
   *
   * @param step The step to add. In general this should not be null, although it is perfectly valid for it to be
   */
  void addStep(@Nullable ForgeStep step);

  /**
   * Resets the object's {@link ICapabilityForge} components. Used if an item falls out of an anvil without getting worked Purpose is to preserve stackability
   * on items that haven't been worked yet.
   */
  void reset();

  /**
   * @return true if the item is workable
   */
  default boolean isWorkable() {
    return true;
  }

  /**
   * @return true if the item is weldable
   */
  default boolean isWeldable() {
    return true;
  }

  @SideOnly(Side.CLIENT)
  default void addTooltipInfo(@Nonnull ItemStack stack, @Nonnull List<String> text) {
    if (getWork() > 0) {
      text.add(I18n.format("tfc.tooltip.forging_in_progress"));
    }
  }
}
