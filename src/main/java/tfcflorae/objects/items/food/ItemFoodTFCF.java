package tfcflorae.objects.items.food;

import su.terrafirmagreg.api.base.object.item.spi.BaseItemFood;
import su.terrafirmagreg.modules.core.capabilities.food.FoodHeatHandler;
import su.terrafirmagreg.modules.core.capabilities.food.IItemFoodTFC;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.Constants;
import tfcflorae.objects.items.ItemTFCF;
import tfcflorae.util.OreDictionaryHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemFoodTFCF extends BaseItemFood implements IItemFoodTFC {

  private static final Map<ItemTFCF, ItemFoodTFCF> MAP = new HashMap<>();
  public FoodData data;
  ArrayList<PotionEffectToHave> potionEffects = new ArrayList<PotionEffectToHave>();

  public ItemFoodTFCF(FoodData data, Object... objs) {
    super(0, 0.0F, false);
    this.setMaxDamage(0);
    this.data = data;

    for (Object obj : objs) {
      if (obj instanceof PotionEffectToHave Effect) {
        potionEffects.add(Effect);
      } else if (obj instanceof Object[]) {OreDictionaryHelper.register(this, (Object[]) obj);} else {OreDictionaryHelper.register(this, obj);}
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
    if (!potionEffects.isEmpty()) {
      for (PotionEffectToHave effect : potionEffects) {
        if (Constants.RNG.nextInt(effect.chance) == 0) {player.addPotionEffect(new PotionEffect(effect.PotionEffect, effect.Duration, effect.Power));}
      }
    }
  }
}
