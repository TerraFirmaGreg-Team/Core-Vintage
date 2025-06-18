package su.terrafirmagreg.framework.manager.registry.base.block.spi;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;

import net.minecraft.block.BlockButton;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Optional;

import git.jbredwards.fluidlogged_api.api.block.IFluidloggable;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import javax.annotation.Nullable;

@Getter
@Optional.Interface(iface = "git.jbredwards.fluidlogged_api.api.block.IFluidloggable", modid = ModIDs.FLUIDLOGGED)
public abstract class BaseBlockButton extends BlockButton implements IBlockEntry, IFluidloggable {

  protected final Settings settings;
  protected final boolean wooden;

  public BaseBlockButton() {
    this(false);
  }

  public BaseBlockButton(boolean wooden) {
    this(wooden, Settings.of(Material.CIRCUITS));
  }

  public BaseBlockButton(boolean wooden, Settings settings) {
    super(wooden);

    this.wooden = wooden;
    this.settings = settings;
  }

  @Override
  protected void playClickSound(@Nullable EntityPlayer player, World worldIn, BlockPos pos) {
    if (wooden) {
      worldIn.playSound(player, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
    } else {
      worldIn.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
    }

  }

  @Override
  protected void playReleaseSound(World worldIn, BlockPos pos) {
    if (wooden) {
      worldIn.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
    } else {
      worldIn.playSound(null, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
    }
  }


  @Optional.Method(modid = ModIDs.FLUIDLOGGED)
  @Override
  public final boolean isFluidloggable(IBlockState state, World world, BlockPos pos) {
    return isWaterloggable(state, world, pos);
  }

  @Optional.Method(modid = ModIDs.FLUIDLOGGED)
  @Override
  public final boolean isFluidValid(IBlockState state, World world, BlockPos pos, Fluid fluid) {
    return isWaterloggable(state, world, pos) && fluid == FluidRegistry.WATER;
  }

  @Optional.Method(modid = ModIDs.FLUIDLOGGED)
  @Override
  public final boolean canFluidFlow(@NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EnumFacing side) {
    return isWaterloggable(state, world, pos) && canWaterFlow(world, pos, state, side);
  }

  /**
   * Whether this block can be water-logged or not.
   */
  public boolean isWaterloggable(IBlockState state, IBlockAccess world, BlockPos pos) {
    return false;
  }

  /**
   * Whether water can flow into/out of this block.
   */
  public boolean canWaterFlow(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
    return state.getBlockFaceShape(world, pos, side) != BlockFaceShape.SOLID;
  }

}
