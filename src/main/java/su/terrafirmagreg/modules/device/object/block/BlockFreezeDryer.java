package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.modules.device.object.tile.TileFreezeDryer;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.sharkbark.cellars.Main;
import net.dries007.sharkbark.cellars.init.ModBlocks;
import net.dries007.sharkbark.cellars.init.ModItems;
import net.dries007.sharkbark.cellars.util.IHasModel;
import net.dries007.sharkbark.cellars.util.Reference;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.util.Helpers;

import javax.annotation.Nullable;

public class BlockFreezeDryer extends BlockContainer implements IHasModel {

  public static final PropertyDirection FACING;

  static {
    FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
  }


  public BlockFreezeDryer(String name, Material material) {
    super(Material.ROCK);
    setTranslationKey(name);
    setRegistryName(name);
    setCreativeTab(CreativeTabsTFC.CT_MISC);
    setHardness(2F);
    ModBlocks.BLOCKS.add(this);
    ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(name));
    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {

    return false;
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return false;
  }

  public EnumFacing getFacing(IBlockState state) {
    return state.getValue(FACING);
  }

  @Override
  public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.CUTOUT;
  }

  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    this.setDefaultFacing(worldIn, pos, state);
  }

  private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
    if (!worldIn.isRemote) {
      IBlockState iblockstate = worldIn.getBlockState(pos.north());
      IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
      IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
      IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
      EnumFacing enumfacing = state.getValue(FACING);

      if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
        enumfacing = EnumFacing.SOUTH;
      } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
        enumfacing = EnumFacing.NORTH;
      } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
        enumfacing = EnumFacing.EAST;
      } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
        enumfacing = EnumFacing.WEST;
      }

      worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
    }
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FACING);
  }

  @Override
  public IBlockState withRotation(IBlockState state, Rotation rot) {
    return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    EnumFacing enumfacing = EnumFacing.byHorizontalIndex(meta);

    if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
      enumfacing = EnumFacing.NORTH;
    }

    return this.getDefaultState().withProperty(FACING, enumfacing);
  }


  /**
   * Convert the BlockState into the correct metadata value
   */
  public int getMetaFromState(IBlockState state) {
    return state.getValue(FACING).getIndex();
  }

  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing playerFacing, float hitX, float hitY, float hitZ) {

    if (!worldIn.isRemote) {
      player.openGui(Main.INSTANCE, Reference.GUI_FREEZE_DRYER, worldIn, pos.getX(), pos.getY(), pos.getZ());
    }

    return true;

  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

    if (stack.hasDisplayName()) {
      TileEntity entity = worldIn.getTileEntity(pos);

      if (entity instanceof TileFreezeDryer) {
        //((TECellarShelf)entity).setCustomName(stack.getDisplayName());
      }
    }

  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileFreezeDryer tile = Helpers.getTE(worldIn, pos, TileFreezeDryer.class);
    if (tile != null) {
      tile.onBreakBlock(worldIn, pos, state);
    }
    super.breakBlock(worldIn, pos, state);
  }


  @Nullable
  @Override
  public TileEntity createNewTileEntity(World world, int i) {
    if (!world.isRemote) {
      System.out.println("Client : Creating TileEntity");
    } else {
      System.out.println("Server : Creating TileEntity");
    }
    return new TileFreezeDryer();
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;

  }


  @Override
  public void registerModels() {
    Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
  }
}
