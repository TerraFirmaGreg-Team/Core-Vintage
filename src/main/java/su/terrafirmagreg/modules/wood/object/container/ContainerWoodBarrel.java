package su.terrafirmagreg.modules.wood.object.container;

import su.terrafirmagreg.api.base.container.BaseContainerTile;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodBarrel;
import su.terrafirmagreg.modules.wood.object.tile.TileWoodBarrel;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import net.dries007.tfc.objects.container.IButtonHandler;
import net.dries007.tfc.objects.inventory.slot.SlotCallback;

import org.jetbrains.annotations.Nullable;

public class ContainerWoodBarrel extends BaseContainerTile<TileWoodBarrel> implements IButtonHandler {

  public ContainerWoodBarrel(InventoryPlayer playerInv, TileWoodBarrel teWoodBarrel) {
    super(playerInv, teWoodBarrel);
    
  }

  @Nullable
  public IFluidHandler getBarrelTank() {
    return tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
  }

  @Override
  public void onButtonPress(int buttonID, @Nullable NBTTagCompound extraNBT) {
    // Slot will always be 0, extraNBT will be empty
    if (!tile.getWorld().isRemote) {
      BlockWoodBarrel.toggleBarrelSeal(tile.getWorld(), tile.getPos());
    }
  }

  @Override
  protected void addContainerSlots() {
    IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
            null);

    if (inventory != null) {
      addSlotToContainer(
              new SlotCallback(inventory, TileWoodBarrel.SLOT_FLUID_CONTAINER_IN, 35, 20, tile));
      addSlotToContainer(
              new SlotCallback(inventory, TileWoodBarrel.SLOT_FLUID_CONTAINER_OUT, 35, 54, tile));
      addSlotToContainer(new SlotCallback(inventory, TileWoodBarrel.SLOT_ITEM, 89, 37, tile));
    }
  }
}
