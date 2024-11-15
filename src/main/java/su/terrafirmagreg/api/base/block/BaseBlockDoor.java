package su.terrafirmagreg.api.base.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.api.base.itemblock.BaseItemDoor;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

@Getter
public abstract class BaseBlockDoor extends BlockDoor implements IBlockSettings {

  protected final Settings settings;

  public BaseBlockDoor(Settings settings) {
    super(settings.getMaterial());

    this.settings = settings;

    getSettings()
      .ignoresProperties(BlockDoor.POWERED)
      .weight(Weight.HEAVY)
      .size(Size.VERY_LARGE)
      .hardness(3.0F);
  }

  @Override
  public @Nullable BaseItemDoor getItemBlock() {
    return new BaseItemDoor(this);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return state.getValue(HALF) == EnumDoorHalf.UPPER ? Items.AIR : Item.getItemFromBlock(this);
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(Item.getItemFromBlock(this));
  }

}
