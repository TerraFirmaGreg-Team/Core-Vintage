package net.dries007.tfc.api.recipes;

import su.terrafirmagreg.modules.core.capabilities.player.CapabilityPlayer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.compat.jei.IJEISimpleRecipe;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.skills.SkillType;
import net.dries007.tfc.util.skills.SmithingSkill;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Welding Recipe This takes two items and produces a single item out
 * todo: in 1.13+ move this to a json recipe type
 */

public class WeldingRecipe extends IForgeRegistryEntry.Impl<WeldingRecipe> implements IJEISimpleRecipe {

  private final Metal.Tier minTier;
  private final IIngredient<ItemStack> input1;
  private final IIngredient<ItemStack> input2;
  private final ItemStack output;
  private final SmithingSkill.Type skillType;

  public WeldingRecipe(ResourceLocation name, IIngredient<ItemStack> input1, IIngredient<ItemStack> input2, ItemStack output, Metal.Tier minTier) {
    this(name, input1, input2, output, minTier, null);
  }

  public WeldingRecipe(ResourceLocation name, IIngredient<ItemStack> input1, IIngredient<ItemStack> input2, ItemStack output, Metal.Tier minTier,
                       @Nullable SmithingSkill.Type skillType) {
    this.input1 = input1;
    this.input2 = input2;
    this.output = output;
    this.minTier = minTier;
    this.skillType = skillType;

    setRegistryName(name);
  }

  public static WeldingRecipe get(ItemStack stack1, ItemStack stack2, Metal.Tier tier) {
    return TFCRegistries.WELDING.getValuesCollection()
                                .stream()
                                .filter(x -> x.matches(stack1, stack2, tier))
                                .findFirst()
                                .orElse(null);
  }

  public boolean matches(ItemStack input1, ItemStack input2, Metal.Tier tier) {
    // Need to check both orientations
    return tier.isAtLeast(minTier) &&
           ((this.input1.test(input1) && this.input2.test(input2)) || (this.input1.test(input2) && this.input2.test(input1)));
  }

  @NotNull
  public Metal.Tier getTier() {
    return minTier;
  }

  @NotNull
  public ItemStack getOutput(@Nullable EntityPlayer player) {
    ItemStack stack = output.copy();
    if (player != null) {
      var cap = CapabilityPlayer.get(player);
      if (cap != null) {
        SmithingSkill skill = cap.getSkill(SkillType.SMITHING);
        if (skill != null && skillType != null) {
          skill.addSkill(skillType, 1);
          SmithingSkill.applySkillBonus(skill, stack, skillType);
        }
      }
    }
    return stack;
  }

  @Override
  public NonNullList<IIngredient<ItemStack>> getIngredients() {
    NonNullList<IIngredient<ItemStack>> list = NonNullList.create();
    list.add(input1);
    list.add(input2);
    list.add(IIngredient.of("dustFlux"));
    return list;
  }

  @Override
  public NonNullList<ItemStack> getOutputs() {
    return NonNullList.withSize(1, output);
  }
}
