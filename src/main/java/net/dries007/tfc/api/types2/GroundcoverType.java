package net.dries007.tfc.api.types2;

import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IStringSerializable;

public enum GroundcoverType implements IStringSerializable {
    BONE(() -> Items.BONE),
    CLAM(),
    DEAD_GRASS(),
    DRIFTWOOD(),
    FEATHER(() -> Items.FEATHER),
    FLINT(() -> Items.FLINT),
    GUANO(),
    HUMUS(),
    MOLLUSK(),
    MUSSEL(),
    PINECONE(),
    ROTTEN_FLESH(() -> Items.ROTTEN_FLESH),
    SALT_LICK(),
    SEAWEED(),
    SEA_URCHIN(),
    STICK(() -> Items.STICK);

    @Nullable private final Supplier<? extends Item> vanillaItem; // The vanilla item this corresponds to

    GroundcoverType(@Nullable Supplier<? extends Item> vanillaItem) {
        this.vanillaItem = vanillaItem;
    }

    GroundcoverType() {
        this.vanillaItem = null;
    }

    @Nullable public Function<Block, ItemBlock> createBlockItem() {
        return vanillaItem == null ? ItemBlock::new : null;
    }


    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
