package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.api.base.object.inventory.api.IItemHandlerSidedCallback;
import su.terrafirmagreg.api.base.object.inventory.spi.ItemHandlerSidedWrapper;
import su.terrafirmagreg.api.base.object.tile.spi.BaseTileTickableInventory;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.framework.registry.api.provider.IProviderContainer;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.ICapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.device.ConfigDevice;
import su.terrafirmagreg.modules.device.client.gui.GuiFreezeDryer;
import su.terrafirmagreg.modules.device.object.container.ContainerFreezeDryer;
import su.terrafirmagreg.modules.device.object.inventory.InventoryFreezeDryer;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.world.classic.WorldTypeTFC;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import static su.terrafirmagreg.api.data.Properties.DirectionProp.HORIZONTAL;

public class TileFreezeDryer extends BaseTileTickableInventory
  implements IItemHandlerSidedCallback, IProviderContainer<ContainerFreezeDryer, GuiFreezeDryer> {

  public boolean overheating = false;
  public int overheatTick;
  public boolean initialized;
  @Getter
  private float localTemperature;
  private int tick;
  @Getter
  private float temperature;
  @Getter
  private double pressure;
  @Getter
  private double localPressure;
  @Getter
  private float coolant;
  @Getter
  private boolean sealed;
  @Getter
  private boolean pump;
  private int ticksSealed;

  public TileFreezeDryer() {
    super(new InventoryFreezeDryer(10));
    initialized = false;
  }

  @Override
  public void update() {
    if (!initialized) {
      initialized = true;
      localTemperature = Climate.getActualTemp(this.getPos());
      temperature = localTemperature;
      localPressure = (ConfigDevice.BLOCK.FREEZE_DRYER.seaLevelPressure + ((-(this.getPos().getY() - WorldTypeTFC.SEALEVEL)) * ConfigDevice.BLOCK.FREEZE_DRYER.pressureChange));
      pressure = localPressure;
      sealed = false;
      pump = false;
      ticksSealed = 0;
    }

    //Slow machine ticking
    if ((++tick) % 20 == 0) {
      return;
    }

    //Reset tick count
    tick = 0;

    //Get current local temperature at block pos
    localTemperature = Climate.getActualTemp(this.getPos());

    //Consume a piece of coolant
    handleCoolant();

    //Dissipate Heat
    if (coolant > ConfigDevice.BLOCK.FREEZE_DRYER.coolantConsumptionMultiplier * Math.abs(temperature - localTemperature) && pump) {
      temperature = temperature + ConfigDevice.BLOCK.FREEZE_DRYER.temperatureDissipation * (localTemperature - temperature);

      //Only consume coolant if needed.
      if (temperature >= ConfigDevice.BLOCK.FREEZE_DRYER.maxTemp) {
        coolant = coolant - ConfigDevice.BLOCK.FREEZE_DRYER.coolantConsumptionMultiplier * Math.abs(temperature - localTemperature);
        temperature = temperature - (ConfigDevice.BLOCK.FREEZE_DRYER.temperatureDissipation * temperature);
      }
    } else {
      temperature = temperature + ConfigDevice.BLOCK.FREEZE_DRYER.temperatureDissipation * (localTemperature - temperature);
    }

    //Disabled till it cools back down
    if (overheating) {
      overheatTick();
    }

    //Handle pumping action
    if (world.isBlockPowered(this.getPos()) && !overheating && pump) {

      //Increase heat
      temperature = temperature + (ConfigDevice.BLOCK.FREEZE_DRYER.heatPerPower * getPowerLevel());

      //Decrease pressure
      if (sealed) {
        pressure = pressure - (getPowerLevel() * ConfigDevice.BLOCK.FREEZE_DRYER.workPerPower * pressure) / Math.pow(
          localPressure, 2);
      }

      if (pressure < ConfigDevice.BLOCK.FREEZE_DRYER.targetPressure) {
        pressure = ConfigDevice.BLOCK.FREEZE_DRYER.targetPressure;
      }

      spawnParticles();
    }

    if (temperature >= ConfigDevice.BLOCK.FREEZE_DRYER.maxTemp) {
      overheating = true;
    }

    if (sealed && pressure <= ConfigDevice.BLOCK.FREEZE_DRYER.targetPressure) {
      if (ticksSealed < ConfigDevice.BLOCK.FREEZE_DRYER.sealedDuration) {
        ticksSealed += 1;
      }
    }

    if (sealed) {
      updateTraits();
    }

    this.markForSync();
  }

  private void handleCoolant() {
    if (inventory.getStackInSlot(9).isEmpty()) {
      return;
    }

    Item item = inventory.getStackInSlot(9).getItem();
    Block block = Block.getBlockFromItem(item);
    int coolantToAdd = 0;

    if (block == Blocks.PACKED_ICE) {
      coolantToAdd = ConfigDevice.BLOCK.FREEZE_DRYER.packedIceCoolant;
    } else if (block == BlocksTFC.SEA_ICE) {
      coolantToAdd = ConfigDevice.BLOCK.FREEZE_DRYER.seaIceCoolant;
    } else if (item == ItemsCore.ICE_SHARD.get() || block == Blocks.ICE) {
      coolantToAdd = ConfigDevice.BLOCK.FREEZE_DRYER.iceCoolant;
    } else if (block == Blocks.SNOW) {
      coolantToAdd = ConfigDevice.BLOCK.FREEZE_DRYER.snowCoolant;
    } else if (item == Items.SNOWBALL) {
      coolantToAdd = ConfigDevice.BLOCK.FREEZE_DRYER.snowBallCoolant;
    }

    if (coolantToAdd > 0 && coolant < ConfigDevice.BLOCK.FREEZE_DRYER.coolantMax - coolantToAdd) {
      coolant += coolantToAdd;
      inventory.extractItem(9, 1, false);
    }
  }

  private void overheatTick() {
    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.5,
      this.pos.getY() + 0.5, this.pos.getZ() + 0.5, 0, Math.random(), 0);
    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.5,
      this.pos.getY() + 0.5, this.pos.getZ() + 0.5, 0, Math.random(), 0);
    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.5,
      this.pos.getY() + 0.5, this.pos.getZ() + 0.5, 0, Math.random(), 0);
    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.5,
      this.pos.getY() + 0.5, this.pos.getZ() + 0.5, 0, Math.random(), 0);
    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.5,
      this.pos.getY() + 0.5, this.pos.getZ() + 0.5, 0, Math.random(), 0);
    if (temperature <= localTemperature) {
      if ((++overheatTick) % 100 != 0) {
        return;
      }
      overheatTick = 0;
      overheating = false;
    }
  }

  public int getPowerLevel() {
    EnumFacing facing = world.getBlockState(this.getPos()).getValue(HORIZONTAL);
    if (world.isBlockPowered(this.getPos())) {
      if (EnumFacing.NORTH == facing) {
        return world.getRedstonePower(getPos().offset(EnumFacing.SOUTH), EnumFacing.SOUTH);
      } else if (EnumFacing.EAST == facing) {
        return world.getRedstonePower(getPos().offset(EnumFacing.WEST), EnumFacing.WEST);
      } else if (EnumFacing.SOUTH == facing) {
        return world.getRedstonePower(getPos().offset(EnumFacing.NORTH), EnumFacing.NORTH);
      } else if (EnumFacing.WEST == facing) {
        return world.getRedstonePower(getPos().offset(EnumFacing.EAST), EnumFacing.EAST);
      }
    }
    return 0;
  }

  private void spawnParticles() {
    EnumFacing facing = world.getBlockState(this.getPos()).getValue(HORIZONTAL);
    if (world.isRemote) {
      if (EnumFacing.NORTH == facing) {
        world.spawnParticle(EnumParticleTypes.WATER_DROP, this.pos.getX() + 0.7,
          this.pos.getY() + 0.6, this.pos.getZ() + 1, 0, 0.1, 0);
      } else if (EnumFacing.EAST == facing) {
        world.spawnParticle(EnumParticleTypes.WATER_DROP, this.pos.getX(), this.pos.getY() + 0.6,
          this.pos.getZ() + 0.7, 0, 0.1, 0);
      } else if (EnumFacing.SOUTH == facing) {
        world.spawnParticle(EnumParticleTypes.WATER_DROP, this.pos.getX() + 0.3,
          this.pos.getY() + 0.6, this.pos.getZ(), 0, 0.1, 0);
      } else if (EnumFacing.WEST == facing) {
        world.spawnParticle(EnumParticleTypes.WATER_DROP, this.pos.getX() + 1,
          this.pos.getY() + 0.6, this.pos.getZ() + 0.3, 0, 0.1, 0);
      }
    }
  }

  private void updateTraits() {
    if (ticksSealed >= ConfigDevice.BLOCK.FREEZE_DRYER.sealedDuration) {
      for (int x = 0; x < inventory.getSlots() - 1; x++) {
        ItemStack stack = inventory.getStackInSlot(x);

        removeTrait(stack, FoodTrait.PRESERVING);
        applyTrait(stack, FoodTrait.DRY);
      }
    }
  }

  private void removeTrait(ItemStack stack, FoodTrait trait) {
    CapabilityFood.removeTrait(stack, trait);
  }

  private void applyTrait(ItemStack stack, FoodTrait trait) {
    ICapabilityFood food = CapabilityFood.get(stack);
    if (!stack.isEmpty() && food != null) {
      if (trait == FoodTrait.PRESERVING && (food.getTraits().contains(FoodTrait.DRY))) {
        return;
      }
    }
    CapabilityFood.applyTrait(stack, trait);
  }

  public int getPower() {
    return getPowerLevel();
  }

  public void seal() {
    sealed = true;
    applyTraits();
    this.markForSync();
  }

  private void applyTraits() {
    for (int x = 0; x < inventory.getSlots() - 1; x++) {
      ItemStack stack = inventory.getStackInSlot(x);
      applyTrait(stack, FoodTrait.PRESERVING);
    }
  }

  public void unseal() {
    sealed = false;
    pump = false;
    ticksSealed = 0;
    pressure = localPressure;
    removeTraits();
    this.markForSync();
  }

  private void removeTraits() {
    for (int x = 0; x < inventory.getSlots() - 1; x++) {
      ItemStack stack = inventory.getStackInSlot(x);
      removeTrait(stack, FoodTrait.PRESERVING);
    }
  }

  public void startPump() {
    pump = true;
    this.markForSync();
  }

  public void stopPump() {
    pump = false;
    this.markForSync();
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    super.readFromNBT(nbt);

    temperature = nbt.getFloat("Temperature");
    pressure = nbt.getDouble("Pressure");
    localPressure = nbt.getDouble("LocalPressure");
    coolant = nbt.getFloat("Coolant");
    sealed = nbt.getBoolean("Seal");
    pump = nbt.getBoolean("Pump");
    ticksSealed = nbt.getInteger("TicksSealed");
    overheatTick = nbt.getInteger("OverheatTicks");
    overheating = nbt.getBoolean("Overheating");
    initialized = nbt.getBoolean("Initialized");
  }


  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    super.writeToNBT(nbt);

    NBTUtils.setGenericNBTValue(nbt, "Temperature", temperature);
    NBTUtils.setGenericNBTValue(nbt, "Pressure", pressure);
    NBTUtils.setGenericNBTValue(nbt, "LocalPressure", localPressure);
    NBTUtils.setGenericNBTValue(nbt, "Coolant", coolant);
    NBTUtils.setGenericNBTValue(nbt, "Seal", sealed);
    NBTUtils.setGenericNBTValue(nbt, "Pump", pump);
    NBTUtils.setGenericNBTValue(nbt, "TicksSealed", ticksSealed);
    NBTUtils.setGenericNBTValue(nbt, "OverheatTicks", overheatTick);
    NBTUtils.setGenericNBTValue(nbt, "Overheating", overheating);
    NBTUtils.setGenericNBTValue(nbt, "Initialized", initialized);

    return nbt;
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
    for (int slot = 0; slot < 10; ++slot) {

      removeTraits();
      StackUtils.spawnItemStack(world, pos, inventory.getStackInSlot(slot));
    }
  }

  @Override
  public boolean canInsert(int slot, ItemStack itemStack, EnumFacing enumFacing) {

    if (sealed && slot < 9) {
      return false;
    }
    var item = itemStack.getItem();
    return ((item == ItemsCore.ICE_SHARD.get() || item == Items.SNOWBALL || item == Item.getItemFromBlock(Blocks.ICE) ||
             item == Item.getItemFromBlock(Blocks.PACKED_ICE) || item == Item.getItemFromBlock(BlocksTFC.SEA_ICE) ||
             item == Item.getItemFromBlock(Blocks.SNOW)) || slot != 9);
  }

  @Override
  public boolean canExtract(int slot, EnumFacing enumFacing) {
    return !sealed || slot >= 9;
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    ICapabilitySize sizeCap = CapabilitySize.get(stack);
    if (sizeCap != null) {
      return sizeCap.getSize(stack).isSmallerThan(Size.LARGE);
    }
    return true;
  }

  public int getSealedFor() {
    return ticksSealed;
  }

  public float getSealedTicks() {
    return ticksSealed;
  }

  @Override
  public ContainerFreezeDryer getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new ContainerFreezeDryer(inventoryPlayer, this);
  }

  @Override
  public GuiFreezeDryer getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new GuiFreezeDryer(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this);
  }


}
