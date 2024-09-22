package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockHorizontal;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.object.tile.TileSmelteryCauldron;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;


import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.Properties.HORIZONTAL;
import static su.terrafirmagreg.data.Properties.LIT;

@SuppressWarnings("deprecation")
public class BlockSmelteryCauldron extends BaseBlockHorizontal implements IProviderTile {

  public BlockSmelteryCauldron() {
    super(Settings.of(Material.IRON));

    getSettings()
            .registryKey("device/smeltery_cauldron")
            .sound(SoundType.STONE)
            .nonOpaque()
            .nonFullCube()
            .size(Size.LARGE)
            .weight(Weight.MEDIUM)
            .hardness(3.0F);

    setHarvestLevel(ToolClasses.PICKAXE, 0);
    setDefaultState(blockState.getBaseState()
            .withProperty(LIT, false)
            .withProperty(HORIZONTAL, EnumFacing.NORTH));
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState()
            .withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta % 4))
            .withProperty(LIT, meta / 4 % 2 != 0);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(HORIZONTAL).getHorizontalIndex() + (state.getValue(LIT) ? 4 : 0);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, LIT, HORIZONTAL);
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos,
          EnumFacing face) {
    return face == EnumFacing.UP ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return FULL_BLOCK_AABB;
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    var tile = TileUtils.getTile(worldIn, pos, TileSmelteryCauldron.class);
    if (tile != null) {
      tile.onBreakBlock(worldIn, pos, state);
    }
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
          EnumHand hand, EnumFacing side, float hitX,
          float hitY, float hitZ) {
    if (!player.isSneaking()) {
      if (!world.isRemote) {
        if (world.getBlockState(pos.down()).getBlock() instanceof BlockSmelteryFirebox) {
          var tile = TileUtils.getTile(world, pos, TileSmelteryCauldron.class);
          ItemStack held = player.getHeldItem(hand);
          if (held.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandler fluidHandler = tile.getCapability(
                    CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
            if (fluidHandler != null) {
              if (FluidUtil.interactWithFluidHandler(player, hand, fluidHandler)) {
                held = player.getHeldItem(hand); // Forge update item in hand
                var cap = CapabilityHeat.get(held);
                if (cap != null) {
                  cap.setTemperature(tile.getTemp());
                }
              }
            }
          } else {
            GuiHandler.openGui(world, pos, player);
          }
        } else {
          player.sendStatusMessage(new TextComponentTranslation("tooltip.tfctech.smeltery.invalid"), true);
        }
      }
      return true;
    }
    return false;
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileSmelteryCauldron();
  }

  @Override
  public Class<TileSmelteryCauldron> getTileClass() {
    return TileSmelteryCauldron.class;
  }

}
