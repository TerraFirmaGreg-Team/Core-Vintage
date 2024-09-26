package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.api.base.tile.BaseTileTickableInventory;
import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.device.client.gui.GuiCellarShelf;
import su.terrafirmagreg.modules.device.object.container.ContainerCellarShelf;
import su.terrafirmagreg.modules.device.object.inventory.InventoryCellarShelf;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.inventory.IItemHandlerSidedCallback;
import net.dries007.tfc.api.capability.inventory.ItemHandlerSidedWrapper;
import pieman.caffeineaddon.ModConfig;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

public class TileCellarShelf extends BaseTileTickableInventory
  implements IItemHandlerSidedCallback, IProviderContainer<ContainerCellarShelf, GuiCellarShelf> {

  @Getter
  public float temperature = -1;
  //private NonNullList<ItemStack> chestContents = NonNullList.<ItemStack>withSize(14, ItemStack.EMPTY);
  private int cellarTick = -240;    //Because a bunker may be not in the same chunk
  //public int isOpen = 0;
  private int updateTickCounter = 120;
  private int lastUpdate = -1;

  public TileCellarShelf() {
    super(new InventoryCellarShelf(14));

  }

  @Override
  public void update() {
    if (world.isRemote) {
      return;
    }

    if (updateTickCounter % 100 == 0 || lastUpdate == 240) {
      handleItemTicking();
    }
    updateTickCounter++;

    if (lastUpdate >= 0) {
      lastUpdate--;
    }
    if (lastUpdate == -1 && temperature > -1000) {
      temperature = -1000;
    }
  }

  private void handleItemTicking() {
    if (cellarTick >= 0) {
      if (cellarTick > 0) {
        cellarTick--;

        //Syncing
        if (cellarTick == 0) {
          BlockUtils.notifyBlockUpdate(world, pos, 2);
        }
      }
      //Updates shelf contents.
      updateTraits();
    } else {
      cellarTick++;

      //Syncing syncing
      if (cellarTick == 0) {
        BlockUtils.notifyBlockUpdate(world, pos, 2);
      }
    }
  }

  private void updateTraits() {
    for (int x = 0; x < inventory.getSlots(); x++) {
      ItemStack stack = inventory.getStackInSlot(x);
      NBTTagCompound nbt;
      if (stack.hasTagCompound()) {
        nbt = stack.getTagCompound();
      } else {
        nbt = new NBTTagCompound();
      }

      String string = getTrait(stack, nbt);

      if (temperature > ModConfig.coolMaxThreshold || temperature <= -1000) {
        removeTrait(stack, nbt);
      } else if ((temperature <= ModConfig.frozenMaxThreshold && temperature > -1000)
                 && string.compareTo("freezing") != 0) {
        removeTrait(stack, nbt);
        applyTrait(stack, nbt, "freezing", FoodTrait.FREEZING);
      } else if (
        (temperature <= ModConfig.icyMaxThreshold && temperature > ModConfig.frozenMaxThreshold)
        && string.compareTo("icy") != 0) {
        removeTrait(stack, nbt);
        applyTrait(stack, nbt, "icy", FoodTrait.ICY);
      } else if (
        (temperature <= ModConfig.coolMaxThreshold && temperature > ModConfig.icyMaxThreshold)
        && string.compareTo("cool") != 0) {
        removeTrait(stack, nbt);
        applyTrait(stack, nbt, "cool", FoodTrait.COOL);
      }
    }
  }

  private String getTrait(ItemStack stack, NBTTagCompound nbt) {
    String string = nbt.getString("CellarAddonTemperature");
    if (string.compareTo("cool") == 0) {
      return "cool";
    }
    if (string.compareTo("icy") == 0) {
      return "icy";
    }
    if (string.compareTo("freezing") == 0) {
      return "freezing";
    }
    return "";
  }

  private void removeTrait(ItemStack stack, NBTTagCompound nbt) {
    String string = nbt.getString("CellarAddonTemperature");
    if (string.compareTo("cool") == 0) {
      CapabilityFood.removeTrait(stack, FoodTrait.COOL);
    }
    if (string.compareTo("icy") == 0) {
      CapabilityFood.removeTrait(stack, FoodTrait.ICY);
    }
    if (string.compareTo("freezing") == 0) {
      CapabilityFood.removeTrait(stack, FoodTrait.FREEZING);
    }
    nbt.removeTag("CellarAddonTemperature");
    stack.setTagCompound(nbt);
  }

  private void applyTrait(ItemStack stack, NBTTagCompound nbt, String string, FoodTrait trait) {
    nbt.setString("CellarAddonTemperature", string);
    CapabilityFood.applyTrait(stack, trait);
    stack.setTagCompound(nbt);
  }

  public void updateShelf(float temp) {
    cellarTick = 100;
    temperature = temp;
    lastUpdate = 240;
    //Syncing syncing diving diving
    BlockUtils.notifyBlockUpdate(world, pos, 2);
  }

  @Nullable
  @Override
  public SPacketUpdateTileEntity getUpdatePacket() {
    NBTTagCompound nbt = new NBTTagCompound();
    writeToNBT(nbt);
    writeSyncData(nbt);
    return new SPacketUpdateTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), 1, nbt);
  }

  private void writeSyncData(NBTTagCompound nbt) {
    float temp = (lastUpdate < 0) ? -1000 : temperature;
    NBTUtils.setGenericNBTValue(nbt, "Temperature", temp);
    NBTUtils.setGenericNBTValue(nbt, "Items", super.serializeNBT());
  }

  @Override
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
    readFromNBT(packet.getNbtCompound());
    readSyncData(packet.getNbtCompound());
  }

  private void readSyncData(NBTTagCompound tagCompound) {
    temperature = tagCompound.getFloat("Temperature");
    super.deserializeNBT(tagCompound.getCompoundTag("Items"));
  }

  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return (T) new ItemHandlerSidedWrapper(this, inventory, facing);
    }
    return super.getCapability(capability, facing);
  }

  @Override
  public void onBreakBlock(World world, BlockPos pos, IBlockState state) {
    for (int i = 0; i < 14; ++i) {
      ItemStack stack = inventory.getStackInSlot(i);
      NBTTagCompound nbt;
      if (stack.hasTagCompound()) {
        nbt = stack.getTagCompound();
      } else {
        nbt = new NBTTagCompound();
      }

      removeTrait(stack, nbt);
      stack.setTagCompound(null);
      StackUtils.spawnItemStack(world, pos, stack);
    }
  }

  @Override
  public boolean canInsert(int i, ItemStack itemStack, EnumFacing enumFacing) {
    return true;
  }

  @Override
  public boolean canExtract(int i, EnumFacing enumFacing) {
    return true;
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    ICapabilitySize sizeCap = CapabilitySize.getIItemSize(stack);
    if (sizeCap != null) {
      return sizeCap.getSize(stack).isSmallerThan(Size.LARGE);
    }
    return true;
  }

  @Override
  public ContainerCellarShelf getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new ContainerCellarShelf(inventoryPlayer, this);
  }

  @Override
  public GuiCellarShelf getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new GuiCellarShelf(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this, state);
  }


}

