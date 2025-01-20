package su.terrafirmagreg.modules.core.feature.ambiental.handler;

import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalRegistry;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierBlock;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderBlock;
import su.terrafirmagreg.modules.core.init.FluidsCore;

import net.minecraft.init.Blocks;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fluids.FluidRegistry;

import gregtech.common.blocks.MetaBlocks;

public final class ModifierHandlerBlock {


  public static final AmbientalRegistry<IAmbientalProviderBlock> BLOCK = new AmbientalRegistry<>();

  static {
    BLOCK.register((player, pos, state) ->
      ModifierBlock.defined("torch", 3f, 0f)
        .filter(mod -> state.getBlock() == Blocks.TORCH)
    );

    BLOCK.register((player, pos, state) ->
      ModifierBlock.defined("fire", 3f, 0f)
        .filter(mod -> state.getBlock() == Blocks.FIRE)
    );

    BLOCK.register((player, pos, state) ->
      ModifierBlock.defined("lava", 3f, 0f)
        .filter(mod -> state.getBlock() == Blocks.LAVA)
    );

    BLOCK.register((player, pos, state) ->
      ModifierBlock.defined("flowing_lava", 3f, 0f)
        .filter(mod -> state.getBlock() == Blocks.FLOWING_LAVA)
    );

    BLOCK.register((player, pos, state) ->
      ModifierBlock.defined("snow_layer", -1.5f, 0.2f)
        .filter(mod -> state.getBlock() == Blocks.SNOW_LAYER)
    );

    BLOCK.register((player, pos, state) ->
      ModifierBlock.defined("snow", -0.5f, 0.2f)
        .filter(mod -> state.getBlock() == Blocks.SNOW &&
                       player.world.getLightFor(EnumSkyBlock.SKY, pos) == 15
        )
    );

    BLOCK.register((player, pos, state) ->
      ModifierBlock.defined("in_hot_water", 5f, 6f)
        .filter(mod -> player.isInWater())
        .filter(mod -> state.getBlock() == FluidsCore.HOT_WATER.get().getBlock())
    );

    BLOCK.register((player, pos, state) ->
      ModifierBlock.defined("in_ocean_water", -8f, 6f)
        .filter(mod -> player.isInWater())
        .filter(mod -> state.getBlock() == FluidsCore.SALT_WATER.get().getBlock() &&
                       player.world.getBiome(pos).getTempCategory() == Biome.TempCategory.OCEAN
        )
    );

    BLOCK.register((player, pos, state) ->
      ModifierBlock.defined("in_water", -5f, 6f)
        .filter(mod -> player.isInWater())
        .filter(mod -> state.getBlock() == FluidRegistry.getFluid("water").getBlock())
    );

    BLOCK.register((player, pos, state) ->
      ModifierBlock.defined("in_lava", 10f, 5f)
        .filter(mod -> player.isInWater())
        .filter(mod -> state.getBlock() == FluidRegistry.getFluid("lava").getBlock())
    );

    // GTCEu
    BLOCK.register((player, pos, state) ->
      ModifierBlock.defined("wire_coil", 3f, 3f)
        .filter(mod -> state.getBlock() == MetaBlocks.WIRE_COIL)
    );

  }

}
