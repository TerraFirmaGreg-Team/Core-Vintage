package su.terrafirmagreg.modules.soil.content.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlock;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.ISoilEntry;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

@Getter
public class BlockSoilMud extends BaseBlock implements ISoilEntry {

  protected static final AxisAlignedBB MUD_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6D, 1.0D);

  protected final SoilType type;

  public BlockSoilMud(SoilType type) {
    super(BlockSettings.of()
      .addOreDict("mud")
      .material(Material.GROUND)
      .sound(SoundType.GROUND)
      .harvestLevel(ToolClasses.SHOVEL, 0)
      .hardness(2.0F)
    );

    this.type = type;
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
