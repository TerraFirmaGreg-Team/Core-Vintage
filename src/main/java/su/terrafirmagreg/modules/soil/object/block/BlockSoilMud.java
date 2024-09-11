package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

public class BlockSoilMud extends BlockSoil {

  protected static final AxisAlignedBB MUD_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6D, 1.0D);

  public BlockSoilMud(SoilBlockVariant variant, SoilType type) {
    super(variant, type);
  }


  public static Collection<BlockSoilMud> create(Map<SoilType, BlockSoilMud> map_block) {
    SoilBlockVariant.getVariants().forEach(variant -> {
      SoilType.getTypes().forEach(type -> {

        var block = new BlockSoilMud(variant, type);
        if (map_block.put(type, block) != null) {
          throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", variant, type));
        }
      });
    });
    return map_block.values();
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {

    if (fortune > 3) {
      fortune = 3;
    }

    if (rand.nextInt(10 - fortune * 3) == 0) {
      return ItemsSoil.MUD_BALL.get(type);
    }
    return super.getItemDropped(state, rand, fortune);
  }


  @Nullable
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return MUD_AABB;
  }

  @Override
  public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    entityIn.motionX *= 0.7D;
    entityIn.motionZ *= 0.7D;
  }


}
