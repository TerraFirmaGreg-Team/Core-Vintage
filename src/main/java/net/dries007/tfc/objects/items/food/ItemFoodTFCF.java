package net.dries007.tfc.objects.items.food;

import su.terrafirmagreg.api.util.MathUtils;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.food.FoodHeatHandler;
import net.dries007.tfc.api.capability.food.IItemFoodTFC;
import net.dries007.tfc.objects.items.ItemTFCF;
import net.dries007.tfcflorae.util.OreDictionaryHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemFoodTFCF extends ItemFood implements IItemFoodTFC {

  private static final Map<ItemTFCF, ItemFoodTFCF> MAP = new HashMap<>();
  public FoodData data;
  ArrayList<PotionEffectToHave> PotionEffects = new ArrayList<>();

  public ItemFoodTFCF(FoodData data, Object... objs) {
    super(0, 0.0F, false);
    this.setMaxDamage(0);
    this.data = data;

    for (Object obj : objs) {
      if (obj instanceof PotionEffectToHave Effect) {
        PotionEffects.add(Effect);
      } else if (obj instanceof Object[]) {
        OreDictionaryHelper.register(this, (Object[]) obj);
      } else {
        OreDictionaryHelper.register(this, obj);
      }
    }
  }

  public static ItemFoodTFCF get(ItemFoodTFCF food) {
    return MAP.get(food);
  }

  public static ItemStack get(ItemTFCF food, int amount) {
    return new ItemStack(MAP.get(food), amount);
  }

  @Override
  public ICapabilityProvider getCustomFoodHandler() {
    return new FoodHeatHandler(null, data, 1.0F, 200.0F);
  }

  @Override
  protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
    if (!PotionEffects.isEmpty()) {
      for (PotionEffectToHave Effect : PotionEffects) {
        if (MathUtils.RNG.nextInt(Effect.chance) == 0) {
          player.addPotionEffect(new PotionEffect(Effect.PotionEffect, Effect.Duration, Effect.Power));
        }
      }
    }
  }
}
