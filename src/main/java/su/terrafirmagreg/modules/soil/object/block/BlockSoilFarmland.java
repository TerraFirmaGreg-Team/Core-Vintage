package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.registry.provider.IProviderBlockColor;
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
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import gregtech.api.items.toolitem.ToolClasses;

import lombok.Getter;

import java.util.Random;

@Getter
@SuppressWarnings("deprecation")
public class BlockSoilFarmland extends BlockFarmland implements ISoilBlock, IProviderBlockColor {

  public static final int[] TINT = new int[]{
          0xffffffff,
          0xffe7e7e7,
          0xffd7d7d7,
          0xffc7c7c7,
          0xffb7b7b7,
          0xffa7a7a7,
          0xff979797,
          0xff878787,
  };
  public static final AxisAlignedBB FLIPPED_AABB = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);

  protected final Settings settings;
  protected final SoilBlockVariant variant;
  protected final SoilType type;

  public BlockSoilFarmland(SoilBlockVariant variant, SoilType type) {

    this.variant = variant;
    this.type = type;
    this.settings = Settings.of(Material.GROUND);

    getSettings()
            .registryKey(variant.getRegistryKey(type))
            .ignoresProperties(MOISTURE)
            .sound(SoundType.GROUND)
            .useNeighborBrightness()
            .hardness(2.0F)
            .harvestLevel(ToolClasses.SHOVEL, 0)
            .fallable(this, variant.getSpecification())
            .oreDict(variant)
            .oreDict(variant, type);

    setDefaultState(blockState.getBaseState()
            .withProperty(MOISTURE, 1)); // 1 is default so it doesn't instantly turn back to dirt
  }

  @Override
  public boolean isSideSolid(IBlockState baseState, IBlockAccess world, BlockPos pos, EnumFacing side) {
    return (side != EnumFacing.DOWN && side != EnumFacing.UP);
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(ItemsSoil.PILE.get(type));
  }


  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block,
          BlockPos fromPos) {
    if (fromPos.getY() == pos.getY() + 1) {
      IBlockState up = world.getBlockState(fromPos);
      if (up.isSideSolid(world, fromPos, EnumFacing.DOWN) && FallingBlockManager.getSpecification(up) == null) {
        IDirt.turnToDirt(world, pos);
      }
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess,
          BlockPos pos, EnumFacing side) {
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

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemsSoil.PILE.get(type);
  }

  @Override
  public IBlockColor getBlockColor() {
    return (s, w, p, i) -> BlockSoilFarmland.TINT[s.getValue(MOISTURE)];
  }

}
