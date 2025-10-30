package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.content.provider.IProviderBlockPlacement;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import lombok.Getter;

import java.util.Random;
import java.util.function.Supplier;

@Getter
public abstract class BaseBlockDoor extends BlockDoor implements IBlockEntry, IProviderBlockPlacement {

  protected final BlockSettings settings;

  public BaseBlockDoor(BlockSettings settings) {
    super(settings.getMaterial());

    this.settings = settings;
    getSettings()
      .ignoresProperties(BlockDoor.POWERED)
      .disableStats()
      .capability(CapabilityProviderSize.of(Size.VERY_LARGE, Weight.HEAVY))
      .hardness(3.0F);

    this.fullBlock = this.settings.isOpaque();
    this.lightOpacity = this.fullBlock ? 255 : 0;
    this.translucent = this.settings.isTranslucent();
    this.useNeighborBrightness = this.settings.isUseNeighborBrightness();


  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return state.getValue(HALF) == EnumDoorHalf.UPPER ? Items.AIR : asItem();
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(asItem());
  }


  public static void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge) {
    BlockPos blockpos = pos.offset(facing.rotateY());
    BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
    int i = (worldIn.getBlockState(blockpos1).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos1.up()).isNormalCube() ? 1 : 0);
    int j = (worldIn.getBlockState(blockpos).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos.up()).isNormalCube() ? 1 : 0);
    boolean flag = worldIn.getBlockState(blockpos1).getBlock() == door || worldIn.getBlockState(blockpos1.up()).getBlock() == door;
    boolean flag1 = worldIn.getBlockState(blockpos).getBlock() == door || worldIn.getBlockState(blockpos.up()).getBlock() == door;

    if ((!flag || flag1) && j <= i) {
      if (flag1 && !flag || j < i) {
        isRightHinge = false;
      }
    } else {
      isRightHinge = true;
    }

    BlockPos blockpos2 = pos.up();
    boolean flag2 = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos2);
    IBlockState iblockstate = door.getDefaultState()
      .withProperty(BlockDoor.FACING, facing)
      .withProperty(BlockDoor.HINGE, isRightHinge ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT)
      .withProperty(BlockDoor.POWERED, flag2).withProperty(BlockDoor.OPEN, flag2);
    worldIn.setBlockState(pos, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 2);
    worldIn.setBlockState(blockpos2, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
    worldIn.notifyNeighborsOfStateChange(pos, door, false);
    worldIn.notifyNeighborsOfStateChange(blockpos2, door, false);
  }

  @Override
  public EnumActionResult onItemUse(Supplier<EnumActionResult> resultSupplier, ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (facing != EnumFacing.UP) {
      return EnumActionResult.FAIL;
    } else {
      IBlockState bottomDoorState = worldIn.getBlockState(pos);
      Block bottomDoorBlock = bottomDoorState.getBlock();

      if (!bottomDoorBlock.isReplaceable(worldIn, pos)) {
        pos = pos.offset(facing);
      }

      ItemStack itemstack = player.getHeldItem(hand);

      if (player.canPlayerEdit(pos, facing, itemstack) && this.canPlaceBlockAt(worldIn, pos)) {
        EnumFacing enumfacing = EnumFacing.fromAngle(player.rotationYaw);
        int i = enumfacing.getXOffset();
        int j = enumfacing.getZOffset();
        boolean flag = i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F || j > 0 && hitX < 0.5F;
        placeDoor(worldIn, pos, enumfacing.getOpposite(), this, flag); // only line that we change

        SoundType soundtype = bottomDoorBlock.getSoundType(bottomDoorState, worldIn, pos, player);

        worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
        itemstack.shrink(1);
        return EnumActionResult.SUCCESS;
      } else {
        return EnumActionResult.FAIL;
      }
    }
  }

}
