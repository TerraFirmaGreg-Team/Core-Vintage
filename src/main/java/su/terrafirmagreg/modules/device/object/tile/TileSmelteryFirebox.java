package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendarTickable;
import su.terrafirmagreg.modules.device.object.block.BlockSmelteryCauldron;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.objects.te.ITileFields;
import net.dries007.tfc.objects.te.TETickableInventory;
import net.dries007.tfc.util.fuel.Fuel;
import net.dries007.tfc.util.fuel.FuelManager;

import javax.annotation.Nonnull;

import static net.dries007.tfc.objects.blocks.property.ILightableBlock.LIT;

public class TileSmelteryFirebox extends TETickableInventory implements ITickable, ICalendarTickable, ITileFields {

  private float temperature;
  private float burnTemperature;
  private int burnTicks;
  private int airTicks;
  private long lastPlayerTick;

  private int reload;

  public TileSmelteryFirebox() {
    super(8);
    temperature = 0;
    burnTemperature = 0;
    burnTicks = 0;
    lastPlayerTick = Calendar.PLAYER_TIME.getTicks();
    reload = 0;
    airTicks = 0;
  }

  @Override
  public int getFieldCount() {
    return 2;
  }

  @Override
  public void setField(int index, int value) {
    switch (index) {
      case 0:
        temperature = value;
        break;
      case 1:
        burnTicks = value;
        break;
    }
  }

  @Override
  public int getField(int index) {
    switch (index) {
      case 0:
        return (int) temperature;
      case 1:
        return burnTicks;
    }
    return 0;
  }

  @Override
  public void update() {
    super.update();
    checkForCalendarUpdate();
    if (!world.isRemote) {
      IBlockState state = world.getBlockState(pos);
      if (state.getValue(LIT)) {
        burnTicks -= airTicks > 0 ? 2 : 1;
        if (--airTicks <= 0) {airTicks = 0;}
        if (burnTicks <= 0) {
          consumeFuel();
        }
        if (reload++ >= 20) {
          reload = 0;
          if (!(world.getBlockState(pos.up()).getBlock() instanceof BlockSmelteryCauldron)) {
            temperature = 0;
            world.setBlockState(pos, state.withProperty(LIT, false));
            burnTicks = 0;
            airTicks = 0;
          }
        }
      } else {
        burnTemperature = 0;
        burnTicks = 0;
        airTicks = 0;
      }
      if (temperature > 0 || burnTemperature > 0) {
        // Update temperature
        float targetTemperature = burnTemperature + airTicks;
        if (temperature != targetTemperature) {
          float delta = (float) ConfigTFC.Devices.HEAT.heatingModifier;
          temperature = CapabilityHeat.adjustTempTowards(temperature, targetTemperature, delta * (airTicks > 0 ? 2 : 1));
        }
      }
    }
  }

  public float getTemperature() {
    return temperature;
  }

  public void setTemperature(float temperature) {
    this.temperature = temperature;
  }

  public boolean onIgnite() {
    IBlockState state = world.getBlockState(pos);
    if (!state.getValue(LIT)) {
      consumeFuel();
      return state.getValue(LIT);
    }
    return false;
  }

  @Override
  public void onCalendarUpdate(long deltaPlayerTicks) {
    IBlockState state = world.getBlockState(pos);
    if (!state.getValue(LIT)) {
      return;
    }
    while (deltaPlayerTicks > 0) {
      if (burnTicks > deltaPlayerTicks) {
        burnTicks -= deltaPlayerTicks;
        float delta = (float) ConfigTFC.Devices.HEAT.heatingModifier * deltaPlayerTicks;
        temperature = CapabilityHeat.adjustTempTowards(temperature, burnTemperature, delta, delta);
        deltaPlayerTicks = 0;
      } else {
        deltaPlayerTicks -= burnTicks;
        float delta = (float) ConfigTFC.Devices.HEAT.heatingModifier * burnTicks;
        temperature = CapabilityHeat.adjustTempTowards(temperature, burnTemperature, delta, delta);
        consumeFuel();
      }
    }
  }

  @Override
  public long getLastUpdateTick() {
    return lastPlayerTick;
  }

  @Override
  public void setLastUpdateTick(long ticks) {
    lastPlayerTick = ticks;
  }

  @Override
  public int getSlotLimit(int slot) {
    return 1;
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    return FuelManager.isItemFuel(stack);
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    temperature = nbt.getFloat("temperature");
    burnTemperature = nbt.getFloat("burnTemperature");
    burnTicks = nbt.getInteger("burnTicks");
    lastPlayerTick = nbt.getLong("lastPlayerTick");
    airTicks = nbt.getInteger("airTicks");
    super.readFromNBT(nbt);
  }

  @Nonnull
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setFloat("temperature", temperature);
    nbt.setInteger("burnTicks", burnTicks);
    nbt.setFloat("burnTemperature", burnTemperature);
    nbt.setLong("lastPlayerTick", lastPlayerTick);
    nbt.setInteger("airTicks", airTicks);
    return super.writeToNBT(nbt);
  }

  public void onAirIntake(int airAmount) {
    airTicks += airAmount;
    if (airTicks > 600) {
      airTicks = 600;
    }
  }

  private void consumeFuel() {
    burnTicks = 0;
    IBlockState state = world.getBlockState(pos);
    for (int i = 0; i < 8; i++) {
      ItemStack stack = inventory.extractItem(i, 1, false);
      if (!stack.isEmpty()) {
        Fuel fuel = FuelManager.getFuel(stack);
        burnTicks = fuel.getAmount();
        burnTemperature = fuel.getTemperature();
        world.setBlockState(pos, state.withProperty(LIT, true));
        break;
      }
    }
    // Didn't find a fuel to consume
    if (burnTicks <= 0) {
      world.setBlockState(pos, state.withProperty(LIT, false));
      burnTicks = 0;
      airTicks = 0;
      burnTemperature = 0;
    }
  }
}
