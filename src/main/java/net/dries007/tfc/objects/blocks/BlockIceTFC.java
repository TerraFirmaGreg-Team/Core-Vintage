package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.core.feature.climate.ITemperatureBlock;
import su.terrafirmagreg.modules.core.feature.climate.IceMeltHandler;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;

import net.minecraft.block.BlockIce;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.Fluid;

import net.dries007.tfc.api.types.Metal.ItemType;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;

@ParametersAreNonnullByDefault
public class BlockIceTFC extends BlockIce implements ITemperatureBlock {

  private final Fluid waterFluid;
  private final float meltThreshold;

  public BlockIceTFC(Fluid waterFluid) {
    this.waterFluid = waterFluid;
    this.meltThreshold = waterFluid == FluidsCore.SALT_WATER.get() ? IceMeltHandler.SALT_WATER_MELT_THRESHOLD : IceMeltHandler.ICE_MELT_THRESHOLD;

    setHardness(0.5F);
    setLightOpacity(3);
    setSoundType(SoundType.GLASS);
    setTickRandomly(true);
  }

  /**
   * Copied from {@link BlockIce#harvestBlock(World, EntityPlayer, BlockPos, IBlockState, TileEntity, ItemStack)} with a few changes
   */
  @Override
  public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
    //noinspection ConstantConditions
    player.addStat(StatList.getBlockStats(this));
    player.addExhaustion(0.005F);

    if (canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
      java.util.List<ItemStack> items = new ArrayList<>();
      items.add(getSilkTouchDrop(state));

      ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
      for (ItemStack is : items) {
        spawnAsEntity(worldIn, pos, is);
      }
    } else {
      if (worldIn.provider.doesWaterVaporize()) {
        worldIn.setBlockToAir(pos);
        return;
      }

      int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
      harvesters.set(player);
      dropBlockAsItem(worldIn, pos, state, enchantmentLevel);
      harvesters.set(null);
      Material material = worldIn.getBlockState(pos.down()).getMaterial();

      if (material.blocksMovement() || material.isLiquid()) {
        worldIn.setBlockState(pos, waterFluid.getBlock().getDefaultState());
      }
    }
  }

  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    if (waterFluid == FluidsCore.SALT_WATER.get()) {
      return;
    }
    EntityPlayer player = harvesters.get();
    if (player != null) {
      var tool = player.getHeldItemMainhand().getItem();
      if (tool instanceof ItemMetalTool itemMetalTool && itemMetalTool.getType().equals(ItemType.ICE_SAW)) {
        drops.add(new ItemStack(ItemsCore.ICE_SHARD.get()));
      } else {
        super.getDrops(drops, world, pos, state, fortune);
      }
    }

  }

  @Override
  protected void turnIntoWater(World worldIn, BlockPos pos) {
    if (worldIn.provider.doesWaterVaporize()) {
      worldIn.setBlockToAir(pos);
    } else {
      this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
      worldIn.setBlockState(pos, waterFluid.getBlock().getDefaultState());
      worldIn.neighborChanged(pos, waterFluid.getBlock(), pos);
    }
  }

  @Override
  public void onTemperatureUpdateTick(World world, BlockPos pos, IBlockState state) {
    // Either block light (i.e. from torches) or high enough temperature
    if (world.getLightFor(EnumSkyBlock.BLOCK, pos) > 11 - getLightOpacity(state, world, pos) || Climate.getActualTemp(world, pos) > meltThreshold) {
      turnIntoWater(world, pos);
    }
  }
}
