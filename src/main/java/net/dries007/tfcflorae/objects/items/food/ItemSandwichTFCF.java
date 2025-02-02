package net.dries007.tfcflorae.objects.items.food;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.objects.items.food.ItemSandwich;
import net.dries007.tfcflorae.util.OreDictionaryHelper;

import javax.annotation.Nullable;

public class ItemSandwichTFCF extends ItemFoodTFCF {

  private final FoodData data;

  public ItemSandwichTFCF(FoodData data, Object... oreNameParts) {
    super(data);
    this.data = data;

    for (Object obj : oreNameParts) {
      if (obj instanceof Object[]) {OreDictionaryHelper.register(this, (Object[]) obj);} else {OreDictionaryHelper.register(this, obj);}
    }
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new ItemSandwich.SandwichHandler(nbt, data);
  }
}
