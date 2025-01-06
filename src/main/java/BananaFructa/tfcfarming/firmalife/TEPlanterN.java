package BananaFructa.tfcfarming.firmalife;

import su.terrafirmagreg.modules.core.feature.climate.Climate;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import BananaFructa.tfcfarming.Config;
import BananaFructa.tfcfarming.CropNutrients;
import BananaFructa.tfcfarming.NutrientClass;
import BananaFructa.tfcfarming.NutrientValues;
import com.eerussianguy.firmalife.te.TEPlanter;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.types.ICrop;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

public class TEPlanterN extends TEPlanter {

  NutrientValues nutrientValues = new NutrientValues(0, 0, 0);

  public TEPlanterN() {
    if (getTicksSinceUpdate() == Calendar.PLAYER_TIME.getTicks()) {resetCounter();}
  }

  @Override
  public void onCalendarUpdate(long l) {
    if (!Config.allowedDimensions.contains(this.world.provider.getDimension())) {return;}
    double tierModifier = tier >= 2 ? 0.95 : 1.05;
    long growthTicks = (long) (24000.0 * tierModifier * ConfigTFC.General.FOOD.cropGrowthTimeModifier);

    while (this.getTicksSinceUpdate() > growthTicks) {
      this.reduceCounter(growthTicks);
      int slot = Constants.RNG.nextInt(4);
      if (waterUses < 0) {
        this.resetCounter();
        return;
      }
      // Thank god this logic is in the tile entity
      if (canGrow(slot)) {
        Item cropItem = this.inventory.getStackInSlot(slot).getItem();
        if (cropItem instanceof ItemSeedsTFC itemSeedsTFC) {
          CropNutrients cropNutrients = CropNutrients.getCropNValues(itemSeedsTFC.getCrop());
          if (cropNutrients != null) {
            if (
              nutrientValues.getNutrient(cropNutrients.favouriteNutrient) >= cropNutrients.stepCost * Config.nutrientConsumptionInGreenhouse &&
              isBelowMaxTemp(cropNutrients.maximumTemperature)
            ) {
              nutrientValues.addNutrient(cropNutrients.favouriteNutrient, (int) (-cropNutrients.stepCost * Config.nutrientConsumptionInGreenhouse));
              markDirty();
            } else {
              this.resetCounter();
              return;
            }
          }
        }
        this.grow(slot);
      }
    }
  }

  public boolean isBelowMaxTemp(float maxTemp) {
    return !Config.enforceTemperature || maxTemp > Climate.getActualTemp(world, pos, 0);
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    int[] NPK = compound.getIntArray("nutrients");
    nutrientValues = new NutrientValues(NPK);
    super.readFromNBT(compound);
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    compound.setIntArray("nutrients", nutrientValues.getNPKSet());
    return super.writeToNBT(compound);
  }

  public boolean fertilize(NutrientClass nutrientClass, int value) {
    boolean result = nutrientValues.addNutrient(nutrientClass, value);
    if (result) {markDirty();}
    return result;
  }

  public boolean anyLowNutrients() {
    for (int i = 0; i < 4; i++) {
      ItemStack stack = this.inventory.getStackInSlot(i);
      if (stack.getItem() instanceof ItemSeedsTFC itemSeedsTFC) {

        CropNutrients cropNutrients = null;
        for (ICrop c : CropNutrients.MAP.keySet()) {
          if (ItemSeedsTFC.get(c) == itemSeedsTFC) {
            cropNutrients = CropNutrients.MAP.get(c);
            break;
          }
        }

        if (cropNutrients != null) {
          if (nutrientValues.getNutrient(cropNutrients.favouriteNutrient) < cropNutrients.stepCost * Config.nutrientConsumptionInGreenhouse) {return true;}
        }
      }
    }
    return false;
  }

  public NutrientValues getNutrientValues() {
    return nutrientValues;
  }
}
