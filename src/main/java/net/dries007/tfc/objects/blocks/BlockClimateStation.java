package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.firmalife.util.GreenhouseHelpers;
import net.dries007.tfc.client.gui.overlay.IHighlightHandler;
import net.dries007.tfc.objects.te.TEClimateStation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

import static net.dries007.firmalife.init.StatePropertiesFL.STASIS;
import static net.minecraft.block.BlockHorizontal.FACING;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockClimateStation extends Block implements ICapabilitySize, IHighlightHandler {

  public final int tier;

  public BlockClimateStation(int tier) {
    super(Material.WOOD, MapColor.GREEN);
    setHardness(1.0f);
    setResistance(0.5f);
    setSoundType(SoundType.WOOD);
    setTickRandomly(true);
    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST).withProperty(STASIS, false));
    this.tier = tier;
  }

  @Override
  public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
    world.setBlockState(pos, state.withProperty(STASIS, GreenhouseHelpers.isMultiblockValid(world, pos, state, false, tier)));
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    if (!world.isRemote && hand == EnumHand.MAIN_HAND) {
      boolean visual = tier > 0;
      boolean valid = GreenhouseHelpers.isMultiblockValid(world, pos, state, visual, tier);
      world.setBlockState(pos, state.withProperty(STASIS, valid));
      if (!valid || !visual) {
        player.sendMessage(new TextComponentTranslation(valid ? "tooltip.firmalife.valid" : "tooltip.firmalife.invalid"));
      }
    }
    return true;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    switch (tier) {
      case 1:
        tooltip.add("Enables enhanced flaw detection for your greenhouse.");
        tooltip.add("Right click to show either the protected region, or the incorrect block.");
        break;
      case 2:
        tooltip.add("Enhanced climate regulation makes planters grow 10.5% faster.");
        break;
      case 3:
        tooltip.add("Enables growing grains in the greenhouse.");
        break;
      case 4:
        tooltip.add("Enables growing fruit trees in the greenhouse.");
        break;
      case 5:
        tooltip.add("Distributes steam to your spouts and sprinklers, eliminating the need to feed them with barrels.");
        break;
    }
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state) {
    for (EnumFacing d : EnumFacing.HORIZONTALS) {
      GreenhouseHelpers.setApproval(world, pos, state, d, false, false, 0);
    }
    super.breakBlock(world, pos, state);
  }

  @Override
  @SuppressWarnings("deprecation")
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta)).withProperty(STASIS, meta > 3);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(FACING).getHorizontalIndex() + (state.getValue(STASIS) ? 4 : 0);
  }

  @Override
  @SuppressWarnings("deprecation")
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
  }

  @Override
  public boolean drawHighlight(World world, BlockPos pos, EntityPlayer player, RayTraceResult result, double partialTicks) {
    double dx = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
    double dy = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
    double dz = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

    IBlockState state = world.getBlockState(pos);
    boolean stasis = false;
    if (state.getBlock() instanceof BlockClimateStation) {
      stasis = state.getValue(STASIS);
    }
    IHighlightHandler.drawBox(Block.FULL_BLOCK_AABB.offset(pos).offset(-dx, -dy, -dz).grow(0.002D), 3.0F, stasis ? 0 : 1.0F, stasis ? 1.0F : 0, 0, 0.4F);
    return true;
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FACING, STASIS);
  }

  @Override
  @Nonnull
  public Size getSize(@Nonnull ItemStack stack) {
    return Size.NORMAL;
  }

  @Override
  @Nonnull
  public Weight getWeight(@Nonnull ItemStack stack) {
    return Weight.MEDIUM;
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TEClimateStation();
  }
}
