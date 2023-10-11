package net.dries007.tfc.module.core;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.dries007.tfc.api.types.GroundcoverType;
import net.dries007.tfc.module.core.api.util.EnumColor;
import net.dries007.tfc.module.core.objects.blocks.BlockGroundcover;
import net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariant;
import net.dries007.tfc.module.rock.objects.blocks.BlockAlabaster;
import net.minecraft.block.Block;
import su.terrafirmagreg.util.util.Pair;

import javax.annotation.Nonnull;
import java.util.Map;

public class StorageCore {

    //==== Block =====================================================================================================//


    public static final Map<Pair<RockBlockVariant, EnumColor>, BlockAlabaster> ALABASTER_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<GroundcoverType, BlockGroundcover> GROUNDCOVER_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();


    @Nonnull
    public static Block getAlabasterBlock(@Nonnull RockBlockVariant variant, @Nonnull EnumColor color) {
        var block = (Block) ALABASTER_BLOCKS.get(new Pair<>(variant, color));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s, %s", color, variant));
    }

    @Nonnull
    public static Block getGroundcoverBlock(@Nonnull GroundcoverType type) {
        var block = (Block) GROUNDCOVER_BLOCKS.get(type);
        if (block != null) return block;
        throw new RuntimeException(String.format("Block is null: %s", type));
    }


}
