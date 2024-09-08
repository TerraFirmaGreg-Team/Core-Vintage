package su.terrafirmagreg.modules.metal.objects.block;

import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.api.types.variant.block.MetalBlockVariant;
import su.terrafirmagreg.modules.metal.init.BlocksMetal;
import su.terrafirmagreg.modules.metal.objects.entity.EntityMetalPigvil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.Properties.HORIZONTAL;

public class BlockMetalPigvil
    extends BlockMetalAnvil {

  public BlockMetalPigvil(MetalBlockVariant variant, MetalType type) {
    super(variant, type);

    setDefaultState(blockState.getBaseState()
        .withProperty(HORIZONTAL, EnumFacing.EAST));
  }

  public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state,
      @Nullable TileEntity tile, ItemStack stack) {
    EntityMetalPigvil pigvil = new EntityMetalPigvil(worldIn);
    pigvil.setAnvil(this);
    pigvil.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(),
        state.getValue(HORIZONTAL).getHorizontalAngle(), 0);
    worldIn.spawnEntity(pigvil);
  }

  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
      EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
      float hitX, float hitY, float hitZ) {
    worldIn.playSound(playerIn, pos, SoundEvents.ENTITY_PIG_AMBIENT, SoundCategory.BLOCKS, 1.0f,
        1.0f);
    return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
  }

  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
      EntityPlayer player) {

    return new ItemStack(BlocksMetal.PIGVIL.get(type));
  }

}
