package net.dries007.tfc.objects.items.food;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.Nullable;

public class ItemSandwichTFCF extends ItemFoodTFCF {

  private final FoodData data;

  public ItemSandwichTFCF(FoodData data, Object... oreNameParts) {
    super(data);
    this.data = data;

    for (Object obj : oreNameParts) {
      if (obj instanceof Object[]) {
        OreDictionaryHelper.register(this, (Object[]) obj);
      } else {
        OreDictionaryHelper.register(this, obj);
      }
    }
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new ItemSandwich.SandwichHandler(nbt, data);
  }
}
