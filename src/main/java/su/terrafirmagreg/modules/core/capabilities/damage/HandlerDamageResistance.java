package su.terrafirmagreg.modules.core.capabilities.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HandlerDamageResistance {

  //Used inside CT, set custom IDamageResistance for armor items outside TFC
  public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>();

  // Map entities -> Capability to damage resistance
  public static final Map<ResourceLocation, Supplier<ICapabilityProvider>> CUSTOM_ENTITY = new HashMap<>();

  public static void init() {

    CUSTOM_ENTITY.put(new ResourceLocation("minecraft", "skeleton"),
            () -> new ProviderDamageResistance(0, 1000000000, 50));

    CUSTOM_ENTITY.put(new ResourceLocation("minecraft", "wither_skeleton"),
            () -> new ProviderDamageResistance(0, 1000000000, 50));

    CUSTOM_ENTITY.put(new ResourceLocation("minecraft", "stray"),
            () -> new ProviderDamageResistance(0, 1000000000, 50));

    CUSTOM_ENTITY.put(new ResourceLocation("minecraft", "creeper"),
            () -> new ProviderDamageResistance(50, 0, 15));

    CUSTOM_ENTITY.put(new ResourceLocation("minecraft", "enderman"),
            () -> new ProviderDamageResistance(10, 10, 10));

    CUSTOM_ENTITY.put(new ResourceLocation("minecraft", "zombie"),
            () -> new ProviderDamageResistance(50, 15, 0));

    CUSTOM_ENTITY.put(new ResourceLocation("minecraft", "husk"),
            () -> new ProviderDamageResistance(50, 15, 0));

    CUSTOM_ENTITY.put(new ResourceLocation("minecraft", "zombie_villager"),
            () -> new ProviderDamageResistance(50, 15, 0));

  }

  @Nullable
  public static ICapabilityProvider getCustom(ItemStack stack) {
    for (var entry : CUSTOM_ITEMS.entrySet()) {
      if (entry.getKey().testIgnoreCount(stack)) {
        return entry.getValue().get();
      }
    }
    if (stack.getItem() instanceof ItemArmor) {
      return new ProviderDamageResistance();
    }
    return null;
  }

  @Nullable
  public static ICapabilityProvider getCustom(Entity entity) {

    return CUSTOM_ENTITY.get(EntityList.getKey(entity)).get();
  }
}
