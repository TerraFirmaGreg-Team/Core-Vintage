package net.dries007.tfc.objects.blocks.plants.property;

import su.terrafirmagreg.data.enums.EnumPlantPart;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface ITallPlant {

  AxisAlignedBB PLANT_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
  AxisAlignedBB SHORTER_PLANT_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.5D, 0.875D);

  default AxisAlignedBB getTallBoundingBax(int age, IBlockState state, IBlockAccess source, BlockPos pos) {
    if (getPlantPart(source, pos) == EnumPlantPart.LOWER || getPlantPart(source, pos) == EnumPlantPart.MIDDLE) {
      return PLANT_AABB.offset(state.getOffset(source, pos));
    }
    return switch (age) {
      case 0, 1 -> SHORTER_PLANT_AABB.offset(state.getOffset(source, pos));
      default -> PLANT_AABB.offset(state.getOffset(source, pos));
    };

  }

  default EnumPlantPart getPlantPart(IBlockAccess world, BlockPos pos) {
    if (world.getBlockState(pos.down()).getBlock() != this && world.getBlockState(pos.up()).getBlock() == this) {
      return EnumPlantPart.LOWER;
    }
    if (world.getBlockState(pos.down()).getBlock() == this && world.getBlockState(pos.up()).getBlock() == this) {
      return EnumPlantPart.MIDDLE;
    }
    if (world.getBlockState(pos.down()).getBlock() == this && world.getBlockState(pos.up()).getBlock() != this) {
      return EnumPlantPart.UPPER;
    }
    return EnumPlantPart.SINGLE;
  }

}
