package su.terrafirmagreg.modules.metal.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.Pair;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.api.types.variant.block.MetalBlockVariant;
import su.terrafirmagreg.modules.metal.objects.block.BlockMetalAnvil;
import su.terrafirmagreg.modules.metal.objects.block.BlockMetalCladding;
import su.terrafirmagreg.modules.metal.objects.block.BlockMetalLamp;
import su.terrafirmagreg.modules.metal.objects.block.BlockMetalPigvil;

import net.minecraft.block.Block;


import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

public final class BlocksMetal {

  public static final Map<Pair<MetalBlockVariant, MetalType>, Block> METAL_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

  public static MetalBlockVariant ANVIL;
  public static MetalBlockVariant PIGVIL;
  public static MetalBlockVariant CLADDING;
  public static MetalBlockVariant LAMP;
  public static MetalBlockVariant TRAPDOOR;

  public static void onRegister(RegistryManager registry) {

    ANVIL = MetalBlockVariant
            .builder("anvil")
            .setFactory(BlockMetalAnvil::new)
            .build();

    PIGVIL = MetalBlockVariant
            .builder("pigvil")
            .setFactory(BlockMetalPigvil::new)
            .build();

    CLADDING = MetalBlockVariant
            .builder("cladding")
            .setFactory(BlockMetalCladding::new)
            .build();

    LAMP = MetalBlockVariant
            .builder("lamp")
            .setFactory(BlockMetalLamp::new)
            .build();

    registry.blocks(METAL_BLOCKS.values());

  }

}
