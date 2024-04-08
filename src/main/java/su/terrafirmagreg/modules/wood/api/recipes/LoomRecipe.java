package su.terrafirmagreg.modules.wood.api.recipes;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.data.RegistryWood;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;


import net.dries007.tfc.compat.jei.IJEISimpleRecipe;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

public class LoomRecipe extends IForgeRegistryEntry.Impl<LoomRecipe> implements IJEISimpleRecipe {

    private final IIngredient<ItemStack> inputItem;
    private final ItemStack outputItem;
    @Getter
    private final int stepCount;
    @Getter
    private final ResourceLocation inProgressTexture;

    protected LoomRecipe(Builder builder) {
        setRegistryName(ModUtils.getID(builder.name));
        this.inputItem = builder.inputStack;
        this.outputItem = builder.outputStack;
        this.stepCount = builder.stepCount;
        this.inProgressTexture = builder.inProgressTexture;

        if (inputItem == null || builder.inputStack.getAmount() == 0 || outputItem == null || builder.stepCount == 0) {
            throw new IllegalArgumentException("Input and output are not allowed to be empty");
        }

        if (builder.name.isEmpty()) {
            throw new RuntimeException(String.format("LoomRecipe name must contain any character: [%s]", builder.name));
        }

        //        RegistryWood.LOOM.register(this);

    }

    @Nullable
    public static LoomRecipe get(ItemStack item) {
        return RegistryWood.LOOM.getValuesCollection()
                .stream()
                .filter(x -> x.isValidInput(item))
                .findFirst()
                .orElse(null);
    }

    public int getInputCount() {
        return inputItem.getAmount();
    }

    public ItemStack getOutputItem() {
        return outputItem.copy();
    }

    @Override
    public NonNullList<IIngredient<ItemStack>> getIngredients() {
        return NonNullList.withSize(1, inputItem);
    }

    @Override
    public NonNullList<ItemStack> getOutputs() {
        return NonNullList.withSize(1, outputItem);
    }

    private boolean isValidInput(ItemStack inputItem) {
        return this.inputItem.testIgnoreCount(inputItem);
    }

    public static class Builder {

        private final String name;
        private IIngredient<ItemStack> inputStack;
        private ItemStack outputStack;
        private int stepCount;
        private ResourceLocation inProgressTexture;

        /**
         * Конструктор класса Builder.
         *
         * @param name Имя строителя.
         */
        public Builder(String name) {
            this.name = name;
        }

        /**
         * Устанавливает входной стек предметов.
         *
         * @param inputStack Входной стек предметов.
         * @return Текущий экземпляр Builder.
         */
        public Builder setInputStack(IIngredient<ItemStack> inputStack) {
            this.inputStack = inputStack;
            return this;
        }

        /**
         * Устанавливает выходной стек предметов.
         *
         * @param outputStack Выходной стек предметов.
         * @return Текущий экземпляр Builder.
         */
        public Builder setOutputStack(ItemStack outputStack) {
            this.outputStack = outputStack;
            return this;
        }

        public Builder setStepCount(int stepCount) {
            this.stepCount = stepCount;
            return this;
        }

        public Builder setInProgressTexture(String inProgressTexture) {
            this.inProgressTexture = ModUtils.getID(inProgressTexture);
            return this;
        }

        public Builder setInProgressTexture(String modId, String inProgressTexture) {
            this.inProgressTexture = new ResourceLocation(modId, inProgressTexture);
            return this;
        }

        public Builder setInProgressTexture(ResourceLocation inProgressTexture) {
            this.inProgressTexture = inProgressTexture;
            return this;
        }

        public LoomRecipe build() {
            return new LoomRecipe(this);
        }
    }

}
