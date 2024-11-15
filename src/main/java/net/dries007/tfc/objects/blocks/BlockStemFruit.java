package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlockDirectional;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.player.CapabilityPlayer;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.skills.SimpleSkill;
import su.terrafirmagreg.modules.core.feature.skills.SkillType;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.te.TEStemCrop;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.agriculture.Crop;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.data.Properties.DirectionProp.DIRECTIONAL;

@MethodsReturnNonnullByDefault

public class BlockStemFruit extends BaseBlockDirectional implements ICapabilitySize {

  public BlockStemFruit() {
    super(Settings.of(Material.GOURD));
    this.setDefaultState(this.blockState.getBaseState().withProperty(DIRECTIONAL, EnumFacing.NORTH));
    this.setHardness(1.0f);
  }

  @SuppressWarnings("deprecation")
  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(DIRECTIONAL, EnumFacing.byIndex(meta));
  }

  /**
   * Convert the BlockState into the correct metadata value
   */
  public int getMetaFromState(IBlockState state) {
    return state.getValue(DIRECTIONAL).getIndex();
  }

  @SuppressWarnings("deprecation")
  @Override
  public IBlockState withRotation(IBlockState state, Rotation rot) {
    return state.withProperty(DIRECTIONAL, rot.rotate(state.getValue(DIRECTIONAL)));
  }

  @SuppressWarnings("deprecation")
  @Override
  public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
    return state.withRotation(mirrorIn.toRotation(state.getValue(DIRECTIONAL)));
  }

  /**
   * Checks if this block can be placed exactly at the given position.
   */
  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return worldIn.getBlockState(pos)
                  .getBlock()
                  .isReplaceable(worldIn, pos) && worldIn.isSideSolid(pos.down(), EnumFacing.UP);
  }

  @SuppressWarnings("deprecation")
  @Override
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
                                          EntityLivingBase placer) {
    return this.getDefaultState().withProperty(DIRECTIONAL, EnumFacing.getDirectionFromEntityLiving(pos, placer));
  }

  @Override
  public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tee, ItemStack tool) {
    super.harvestBlock(world, player, pos, state, tee, tool);

    if (!world.isRemote) {
      for (EnumFacing neighbor : EnumFacing.Plane.HORIZONTAL) {
        BlockPos cropPos = pos.offset(neighbor);
        Block block = world.getBlockState(cropPos).getBlock();
        if (block instanceof BlockStemCrop crop) {
          //check the crop is pointing towards us
          TileUtils.getTile(world, cropPos, TEStemCrop.class)
                   .filter(tile -> tile.getFruitDirection() == neighbor.getOpposite())
                   .ifPresent(tile -> {
                     IBlockState cropState = world.getBlockState(cropPos);
                     int cropStage = cropState.getValue(crop.getStageProperty());
                     if (cropStage == crop.getCrop().getMaxStage()) {
                       world.setBlockState(cropPos, cropState.withProperty(crop.getStageProperty(), cropStage - 3));
                       SimpleSkill skill = CapabilityPlayer.getSkill(player, SkillType.AGRICULTURE);
                       ItemStack seedDrop = new ItemStack(ItemSeedsTFC.get(crop.getCrop()), 0);
                       if (skill != null) {
                         seedDrop.setCount(Crop.getSkillSeedBonus(skill, RANDOM));
                       }
                       if (!seedDrop.isEmpty()) {
                         ItemHandlerHelper.giveItemToPlayer(player, seedDrop);
                       }
                     }
                   });
        }
      }
    }
    world.setBlockToAir(pos);
  }

  @Override
  public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
    super.onBlockHarvested(worldIn, pos, state, player);
  }

  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, DIRECTIONAL);
  }

  @Override
  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    return willHarvest || super.removedByPlayer(state, world, pos, player, false); //delay deletion of the block until after getDrops
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    TETickCounter tile = new TETickCounter();
    tile.resetCounter();
    return tile;
  }

  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess blockAccess, BlockPos pos, IBlockState state, int fortune) {
    super.getDrops(drops, blockAccess, pos, state, fortune);
    if (blockAccess instanceof World world && world.isRemote) {return;}
    TileUtils.getTile(blockAccess, pos, TETickCounter.class).ifPresent(tile -> {
      long currentTime = Calendar.PLAYER_TIME.getTicks();
      long foodCreationDate = currentTime - tile.getTicksSinceUpdate();
      drops.forEach(stack -> {
        IFood handler = stack.getCapability(CapabilityFood.CAPABILITY, null);
        if (handler != null) {
          handler.setCreationDate(foodCreationDate);
        }
      });
    });
  }

  @Override
  public @NotNull Weight getWeight(@NotNull ItemStack stack) {
    return Weight.HEAVY;
  }

  @Override
  public @NotNull Size getSize(@NotNull ItemStack stack) {
    return Size.LARGE;
  }
}
