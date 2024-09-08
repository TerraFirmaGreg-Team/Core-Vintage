package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager;
import su.terrafirmagreg.modules.soil.api.spi.IDirt;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import gregtech.api.items.toolitem.ToolClasses;

import lombok.Getter;

import java.util.Random;

@Getter
@SuppressWarnings("deprecation")
public class BlockSoilGrassPath extends BlockGrassPath implements ISoilBlock {

  protected final Settings settings;
  protected final SoilBlockVariant variant;
  protected final SoilType type;

  public BlockSoilGrassPath(SoilBlockVariant variant, SoilType type) {

    this.variant = variant;
    this.type = type;
    this.useNeighborBrightness = true;
    this.settings = Settings.of(Material.GROUND);

    getSettings()
            .registryKey(variant.getRegistryKey(type))
            .sound(SoundType.PLANT)
            .hardness(2.0F)
            .nonCube()
            .harvestLevel(ToolClasses.SHOVEL, 0)
            .oreDict(variant);
  }

  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (fromPos.getY() == pos.getY() + 1) {
      IBlockState up = world.getBlockState(fromPos);
      if (up.isSideSolid(world, fromPos, EnumFacing.DOWN)
              && FallingBlockManager.getSpecification(up) == null) {
        IDirt.turnToDirt(world, pos);
      }
    }
  }

  @Override
  public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
    BlockPos upPos = pos.up();
    IBlockState up = world.getBlockState(upPos);
    if (up.isSideSolid(world, upPos, EnumFacing.DOWN)
            && FallingBlockManager.getSpecification(up) == null) {
      IDirt.turnToDirt(world, pos);
    }
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemsSoil.PILE.get(type);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    switch (side) {
      case UP:
        return true;
      case NORTH:
      case SOUTH:
      case WEST:
      case EAST:
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();
        if (iblockstate.isOpaqueCube()) {
          return false;
        }
        if (block instanceof BlockFarmland || block instanceof BlockGrassPath) {
          return false;
        }
      default:
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
  }
}
