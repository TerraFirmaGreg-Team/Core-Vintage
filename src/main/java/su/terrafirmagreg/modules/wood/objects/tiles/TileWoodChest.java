package su.terrafirmagreg.modules.wood.objects.tiles;

import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.client.gui.GuiWoodChest;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodChest;
import su.terrafirmagreg.modules.wood.objects.containers.ContainerWoodChest;
import su.terrafirmagreg.modules.wood.objects.inventory.capability.WoodDoubleChestItemHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;


import net.dries007.tfc.api.capability.inventory.ISlotCallback;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileWoodChest extends TileEntityChest implements ISlotCallback,
    IProviderContainer<ContainerWoodChest, GuiWoodChest> {

  public static final int SIZE = 18;

  private WoodType cachedWoodType;
  private int shadowTicksSinceSync;

  {
    chestContents = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    shadowTicksSinceSync = 0;
  }

  @Nullable
  public WoodType getWood() {
    if (cachedWoodType == null) {
      if (world != null) {
        cachedWoodType = ((BlockWoodChest) world.getBlockState(pos).getBlock()).getType();
      }
    }
    return cachedWoodType;
  }

  @Override
  public int getSizeInventory() {
    return SIZE;
  }

  @Override
  protected boolean isChestAt(@NotNull BlockPos posIn) {
    if (world == null) {
      return false;
    }

    Block block = this.world.getBlockState(posIn).getBlock();
    return block instanceof BlockWoodChest blockWoodChest && blockWoodChest.getType() == getWood()
        && blockWoodChest.chestType == getChestType();
  }

  @Override
  public void update() {
    checkForAdjacentChests();
    shadowTicksSinceSync++;

    if (!world.isRemote && numPlayersUsing != 0
        && (shadowTicksSinceSync + pos.getX() + pos.getY() + pos.getZ()) % 200 == 0) {
      numPlayersUsing = 0;

      for (EntityPlayer player : world.getEntitiesWithinAABB(EntityPlayer.class,
          new AxisAlignedBB(pos.add(-5, -5, -5), pos.add(6, 6, 6)))) {
        if (player.openContainer instanceof ContainerWoodChest containerWoodChest) {
          IInventory iinventory = containerWoodChest.getLowerChestInventory();
          if (iinventory == this || iinventory instanceof InventoryLargeChest inventoryLargeChest &&
              inventoryLargeChest.isPartOfLargeChest(this)) {
            ++numPlayersUsing;
          }
        }
      }
    }

    prevLidAngle = lidAngle;

    if (numPlayersUsing > 0 && lidAngle == 0.0F && adjacentChestZNeg == null
        && adjacentChestXNeg == null) {
      double centerX = pos.getX() + 0.5D;
      double centerZ = pos.getZ() + 0.5D;

      if (adjacentChestZPos != null) {
        centerZ += 0.5D;
      }

      if (adjacentChestXPos != null) {
        centerX += 0.5D;
      }

      world.playSound(null, centerX, pos.getY() + 0.5D, centerZ, SoundEvents.BLOCK_CHEST_OPEN,
          SoundCategory.BLOCKS, 0.5F,
          world.rand.nextFloat() * 0.1F + 0.9F);
    }

    if (numPlayersUsing == 0 && lidAngle > 0.0F || numPlayersUsing > 0 && lidAngle < 1.0F) {
      float initialAngle = this.lidAngle;
      if (numPlayersUsing > 0) {
        lidAngle += 0.1F;
      } else {
        lidAngle -= 0.1F;
      }

      if (lidAngle > 1.0F) {
        lidAngle = 1.0F;
      }

      if (lidAngle < 0.5F && initialAngle >= 0.5F && adjacentChestZNeg == null
          && adjacentChestXNeg == null) {
        double centerX = pos.getX() + 0.5D;
        double centerZ = pos.getZ() + 0.5D;

        if (adjacentChestZPos != null) {
          centerZ += 0.5D;
        }

        if (adjacentChestXPos != null) {
          centerX += 0.5D;
        }

        world.playSound(null, centerX, pos.getY() + 0.5D, centerZ, SoundEvents.BLOCK_CHEST_CLOSE,
            SoundCategory.BLOCKS, 0.5F,
            world.rand.nextFloat() * 0.1F + 0.9F);
      }

      if (lidAngle < 0.0F) {
        lidAngle = 0.0F;
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  @Nullable
  public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      if (doubleChestHandler == null || doubleChestHandler.needsRefresh()) {
        doubleChestHandler = WoodDoubleChestItemHandler.get(this);
      }
      if (doubleChestHandler != null
          && doubleChestHandler != WoodDoubleChestItemHandler.NO_ADJACENT_CHESTS_INSTANCE) {
        return (T) doubleChestHandler;
      }
    }
    return super.getCapability(capability, facing);
  }

  @Override
  public boolean shouldRefresh(@NotNull World world, @NotNull BlockPos pos, IBlockState oldState,
      IBlockState newSate) {
    return oldState.getBlock() != newSate.getBlock();
  }

  @Override
  @SideOnly(Side.CLIENT)
  @NotNull
  public AxisAlignedBB getRenderBoundingBox() {
    return new AxisAlignedBB(getPos().add(-1, 0, -1), getPos().add(2, 2, 2));
  }

  @Override
  public boolean isItemValidForSlot(int index, @NotNull ItemStack stack) {
    // Blocks input from hopper
    var cap = CapabilitySize.getIItemSize(stack);
    if (cap != null) {
      return cap.getSize(stack).isSmallerThan(Size.VERY_LARGE);
    }
    return true;
  }

  @Override
  public boolean isItemValid(int slot, @NotNull ItemStack stack) {
    return isItemValidForSlot(slot, stack);
  }

  @Override
  public ContainerWoodChest getContainer(InventoryPlayer inventoryPlayer, World world,
      IBlockState state, BlockPos pos) {
    ILockableContainer chestContainer = ((BlockWoodChest) state.getBlock()).getLockableContainer(
        world, pos);
    // This is null if the chest is blocked
    if (chestContainer == null) {
      return null;
    }
    return new ContainerWoodChest(inventoryPlayer, chestContainer, inventoryPlayer.player);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public GuiWoodChest getGuiContainer(InventoryPlayer inventoryPlayer, World world,
      IBlockState state, BlockPos pos) {
    return new GuiWoodChest(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer);
  }
}
