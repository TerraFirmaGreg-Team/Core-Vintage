package net.dries007.tfc.module.core.api.recipes.knapping;

import net.dries007.tfc.util.SimpleCraftMatrix;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class KnappingRecipe extends IForgeRegistryEntry.Impl<KnappingRecipe> {
    private final KnappingType type;
    private final SimpleCraftMatrix matrix;

    protected KnappingRecipe(KnappingType type, boolean outsideSlotRequired, String... pattern) {
        this.matrix = new SimpleCraftMatrix(outsideSlotRequired, pattern);
        this.type = type;
    }

    public SimpleCraftMatrix getMatrix() {
        return matrix;
    }

    public abstract ItemStack getOutput();

    public KnappingType getType() {
        return this.type;
    }
}
