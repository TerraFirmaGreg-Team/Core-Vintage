package su.terrafirmagreg.modules.soil;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.util.Pair;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.item.ISoilItem;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;
import su.terrafirmagreg.modules.soil.objects.blocks.peat.BlockPeat;
import su.terrafirmagreg.modules.soil.objects.blocks.peat.BlockPeatGrass;

import java.util.Collection;
import java.util.Map;

import static su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariants.*;

public class StorageSoil {

    public static final Map<Pair<SoilBlockVariant, SoilType>, ISoilBlock> SOIL_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<Pair<SoilItemVariant, SoilType>, ISoilItem> SOIL_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

    @NotNull
    public static Block getBlock(@NotNull SoilBlockVariant variant, @NotNull SoilType type) {
        var block = (Block) SOIL_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block soil is null: %s, %s", variant, type));
    }

    @NotNull
    public static ISoilBlock getIBlock(@NotNull SoilBlockVariant variant, @NotNull SoilType type) {
        var block = SOIL_BLOCKS.get(new Pair<>(variant, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block soil is null: %s, %s", variant, type));
    }

    @NotNull
    public static Item getItem(@NotNull SoilItemVariant variant, @NotNull SoilType type) {
        var item = (Item) SOIL_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item soil is null: %s, %s", variant, type));
    }

    @NotNull
    public static ISoilItem getIItem(@NotNull SoilItemVariant variant, @NotNull SoilType type) {
        var item = SOIL_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item soil is null: %s, %s", variant, type));
    }

    public static boolean isGrass(IBlockState current) {
        if (current.getBlock() instanceof BlockPeatGrass) return true;
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var variant = soilTypeBlock.getBlockVariant();
            return variant == GRASS || variant == DRY_GRASS ||
                    variant == CLAY_GRASS;
        }
        return false;
    }

    public static boolean isSoil(IBlockState current) {
        if (current.getBlock() instanceof BlockPeat) return true;
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock) {
            var variant = soilTypeBlock.getBlockVariant();
            return variant == GRASS || variant == DRY_GRASS ||
                    variant == DIRT || variant == CLAY ||
                    variant == CLAY_GRASS;
        }
        return false;
    }
}
