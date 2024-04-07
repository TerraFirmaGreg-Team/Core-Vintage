package su.terrafirmagreg.modules.soil.data;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilPeat;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilPeatGrass;

import net.minecraft.block.Block;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

public final class BlocksSoil {

    public static final Map<Pair<SoilBlockVariant, SoilType>, Block> SOIL_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

    public static BlockSoilPeatGrass PEAT_GRASS;
    public static BlockSoilPeat PEAT;

    public static void onRegister(RegistryManager registry) {

        registry.registerBlocks(SOIL_BLOCKS.values());

        PEAT_GRASS = registry.registerBlock(new BlockSoilPeatGrass());
        PEAT = registry.registerBlock(new BlockSoilPeat());

    }
}
