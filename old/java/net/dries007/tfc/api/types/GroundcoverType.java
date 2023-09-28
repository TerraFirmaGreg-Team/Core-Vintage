package net.dries007.tfc.api.types;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum GroundcoverType implements IStringSerializable {
    BONE(Items.BONE),
    //    CLAM(),
//    DEAD_GRASS(),
//    DRIFTWOOD(),
//    FEATHER(() -> Items.FEATHER),
    FLINT(Items.FLINT),
    //    GUANO(),
//    HUMUS(),
//    MOLLUSK(),
//    MUSSEL(),
//    PINECONE(),
//    ROTTEN_FLESH(() -> Items.ROTTEN_FLESH),
//    SALT_LICK(),
//    SEAWEED(),
//    SEA_URCHIN(),
    STICK(Items.STICK);

    private final Item itemToDrop;

    GroundcoverType(Item itemToDrop) {
        this.itemToDrop = itemToDrop;
    }

    public ItemStack getItemStackToDrop() {
        return new ItemStack(itemToDrop);
    }

    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
