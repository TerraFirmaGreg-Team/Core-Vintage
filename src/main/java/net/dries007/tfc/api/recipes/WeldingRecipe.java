package net.dries007.tfc.api.recipes;

import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.player.IPlayerData;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.compat.jei.util.IJEISimpleRecipe;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.core.init.RegistryCore;
import net.dries007.tfc.util.skills.SkillType;
import net.dries007.tfc.util.skills.SmithingSkill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class WeldingRecipe extends IForgeRegistryEntry.Impl<WeldingRecipe> implements IJEISimpleRecipe {
    private final int minTier;
    private final IIngredient<ItemStack> input1;
    private final IIngredient<ItemStack> input2;
    private final ItemStack output;
    private final SmithingSkill.Type skillType;

    public WeldingRecipe(ResourceLocation name, IIngredient<ItemStack> input1, IIngredient<ItemStack> input2, ItemStack output, int minTier) {
        this(name, input1, input2, output, minTier, null);
    }

    public WeldingRecipe(ResourceLocation name, IIngredient<ItemStack> input1, IIngredient<ItemStack> input2, ItemStack output, int minTier, @Nullable SmithingSkill.Type skillType) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.minTier = minTier;
        this.skillType = skillType;

        setRegistryName(name);
    }

    public static WeldingRecipe get(ItemStack stack1, ItemStack stack2, int tier) {
        return RegistryCore.WELDING.getValuesCollection().stream().filter(x -> x.matches(stack1, stack2, tier)).findFirst().orElse(null);
    }

    public int getTier() {
        return minTier;
    }

    @Nonnull
    public ItemStack getOutput(@Nullable EntityPlayer player) {
        ItemStack stack = output.copy();
        if (player != null) {
            IPlayerData cap = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
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

    public boolean matches(ItemStack input1, ItemStack input2, int tier) {
        // Need to check both orientations
        return Helpers.isAtLeast(tier, minTier) && ((this.input1.test(input1) && this.input2.test(input2)) || (this.input1.test(input2) && this.input2.test(input1)));
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
