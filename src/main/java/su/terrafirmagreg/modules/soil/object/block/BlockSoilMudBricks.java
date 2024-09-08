package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;


import gregtech.api.items.toolitem.ToolClasses;

import java.util.Random;

public class BlockSoilMudBricks extends BlockSoilMud {

  public BlockSoilMudBricks(SoilBlockVariant variant, SoilType type) {
    super(variant, type);
    getSettings()
            .sound(SoundType.STONE)
            .harvestLevel(ToolClasses.PICKAXE, 0);

  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemsSoil.MUD_BRICK.get(type);
  }
}
