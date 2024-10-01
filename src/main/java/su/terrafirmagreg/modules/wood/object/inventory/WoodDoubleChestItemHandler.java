package su.terrafirmagreg.modules.wood.object.inventory;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.wood.object.tile.TileWoodChest;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.VanillaDoubleChestItemHandler;

import net.dries007.tfc.api.capability.inventory.ISlotCallback;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("WeakerAccess")
public class WoodDoubleChestItemHandler extends VanillaDoubleChestItemHandler {

  public WoodDoubleChestItemHandler(@Nullable TileEntityChest mainChest, @Nullable TileEntityChest other, boolean mainChestIsUpper) {
    super(mainChest, other, mainChestIsUpper);
  }

  @Nullable
  public static VanillaDoubleChestItemHandler get(TileEntityChest chest) {
    World world = chest.getWorld();
    BlockPos pos = chest.getPos();
    //noinspection ConstantConditions
    if (world == null || pos == null || !world.isBlockLoaded(pos)) {
      return null; // Still loading
    }

    Block blockType = chest.getBlockType();

    EnumFacing[] horizontals = EnumFacing.HORIZONTALS;
    for (int i = horizontals.length - 1; i >= 0; i--)   // Use reverse order so we can return early
    {
      EnumFacing enumfacing = horizontals[i];
      BlockPos blockPos = pos.offset(enumfacing);
      Block block = world.getBlockState(blockPos).getBlock();

      if (block == blockType) {
        var tile = TileUtils.getTile(world, blockPos, TileEntityChest.class);
        tile.map(tileEntityChest -> new WoodDoubleChestItemHandler(chest, tileEntityChest, enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH));
      }
    }
    return VanillaDoubleChestItemHandler.NO_ADJACENT_CHESTS_INSTANCE; //All alone
  }

  @Override
  public int getSlots() {
    return TileWoodChest.SIZE * 2;
  }

  @Override
  public ItemStack getStackInSlot(int slot) {
    boolean accessingUpperChest = slot < TileWoodChest.SIZE;
    int targetSlot = accessingUpperChest ? slot : slot - TileWoodChest.SIZE;
    TileEntityChest chest = getChest(accessingUpperChest);
    return chest != null ? chest.getStackInSlot(targetSlot) : ItemStack.EMPTY;
  }

  @Override
  public void setStackInSlot(int slot, ItemStack stack) {
    boolean accessingUpperChest = slot < TileWoodChest.SIZE;
    int targetSlot = accessingUpperChest ? slot : slot - TileWoodChest.SIZE;
    TileEntityChest chest = getChest(accessingUpperChest);
    if (chest != null) {
      IItemHandler singleHandler = chest.getSingleChestHandler();
      if (singleHandler instanceof IItemHandlerModifiable iItemHandlerModifiable) {
        iItemHandlerModifiable.setStackInSlot(targetSlot, stack);
      }
    }

    chest = getChest(!accessingUpperChest);
    if (chest != null) {
      chest.markDirty();
    }
  }

  @Override
  public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
    boolean accessingUpperChest = slot < TileWoodChest.SIZE;
    int targetSlot = accessingUpperChest ? slot : slot - TileWoodChest.SIZE;
    TileEntityChest chest = getChest(accessingUpperChest);
    if (chest == null) {
      return stack;
    }
    if (chest instanceof ISlotCallback && !((ISlotCallback) chest).isItemValid(slot, stack)) {
      return stack;
    }

    int starting = stack.getCount();
    ItemStack ret = chest.getSingleChestHandler().insertItem(targetSlot, stack, simulate);
    if (ret.getCount() != starting && !simulate) {
      chest = getChest(!accessingUpperChest);
      if (chest != null) {
        chest.markDirty();
      }
    }

    return ret;
  }

  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
    boolean accessingUpperChest = slot < TileWoodChest.SIZE;
    int targetSlot = accessingUpperChest ? slot : slot - TileWoodChest.SIZE;
    TileEntityChest chest = getChest(accessingUpperChest);
    if (chest == null) {
      return ItemStack.EMPTY;
    }

    ItemStack ret = chest.getSingleChestHandler().extractItem(targetSlot, amount, simulate);
    if (!ret.isEmpty() && !simulate) {
      chest = getChest(!accessingUpperChest);
      if (chest != null) {
        chest.markDirty();
      }
    }

    return ret;
  }

  @Override
  public int getSlotLimit(int slot) {
    boolean accessingUpperChest = slot < TileWoodChest.SIZE;
    //noinspection ConstantConditions
    return getChest(accessingUpperChest).getInventoryStackLimit();
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    boolean accessingUpperChest = slot < TileWoodChest.SIZE;
    int targetSlot = accessingUpperChest ? slot : slot - TileWoodChest.SIZE;
    TileEntityChest chest = getChest(accessingUpperChest);
    if (chest != null) {
      return chest.getSingleChestHandler().isItemValid(targetSlot, stack);
    }
    return true;
  }
}
