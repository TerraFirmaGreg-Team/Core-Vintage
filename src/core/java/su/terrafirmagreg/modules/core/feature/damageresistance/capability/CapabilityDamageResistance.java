package su.terrafirmagreg.modules.core.feature.damageresistance.capability;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.data.ingredient.IIngredient;

import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class CapabilityDamageResistance {

  public static final ResourceLocation KEY = ModUtils.resource("damage_resistance_capability");

  @CapabilityInject(ICapabilityDamageResistance.class)
  public static final Capability<ICapabilityDamageResistance> CAPABILITY = ModUtils.getNull();

  public static void register() {
    CapabilityManager.INSTANCE.register(ICapabilityDamageResistance.class, new CapabilityStorageDamageResistance(), CapabilityProviderDamageResistance::new);
  }

  @Nullable
  public static ICapabilityProvider getCustom(ItemStack stack) {
    for (var entry : Handler.CUSTOM_ITEMS.entrySet()) {
      if (entry.getKey().testIgnoreCount(stack)) {
        return entry.getValue().get();
      }
    }
    if (stack.getItem() instanceof ItemArmor) {
      return new CapabilityProviderDamageResistance();
    }
    return null;
  }

  @Nullable
  public static ICapabilityProvider getCustom(ResourceLocation entityTypeName) {
    if (Handler.CUSTOM_ENTITY.containsKey(entityTypeName)) {
      return Handler.CUSTOM_ENTITY.get(entityTypeName).get();
    }
    return null;
  }

  public static class Handler {

    //Used inside CT, set custom IDamageResistance for armor items outside TFC
    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>();

    // Map entities -> Capability to damage resistance
    public static final Map<ResourceLocation, Supplier<ICapabilityProvider>> CUSTOM_ENTITY = new HashMap<>();

    public static void init() {
      CUSTOM_ITEMS.put(IIngredient.of(Items.LEATHER_HELMET),
        () -> new CapabilityProviderDamageResistance(3.0F, 6.0F, 4.5F));

      CUSTOM_ITEMS.put(IIngredient.of(Items.LEATHER_CHESTPLATE),
        () -> new CapabilityProviderDamageResistance(3.0F, 6.0F, 4.5F));

      CUSTOM_ITEMS.put(IIngredient.of(Items.LEATHER_LEGGINGS),
        () -> new CapabilityProviderDamageResistance(3.0F, 6.0F, 4.5F));

      CUSTOM_ITEMS.put(IIngredient.of(Items.LEATHER_BOOTS),
        () -> new CapabilityProviderDamageResistance(3.0F, 6.0F, 4.5F));

      CUSTOM_ENTITY.put(ModUtils.resource("minecraft", "skeleton"),
        () -> new CapabilityProviderDamageResistance(0, 1000000000, 50));

      CUSTOM_ENTITY.put(ModUtils.resource("minecraft", "wither_skeleton"),
        () -> new CapabilityProviderDamageResistance(0, 1000000000, 50));

      CUSTOM_ENTITY.put(ModUtils.resource("minecraft", "stray"),
        () -> new CapabilityProviderDamageResistance(0, 1000000000, 50));

      CUSTOM_ENTITY.put(ModUtils.resource("minecraft", "creeper"),
        () -> new CapabilityProviderDamageResistance(50, 0, 15));

      CUSTOM_ENTITY.put(ModUtils.resource("minecraft", "enderman"),
        () -> new CapabilityProviderDamageResistance(10, 10, 10));

      CUSTOM_ENTITY.put(ModUtils.resource("minecraft", "zombie"),
        () -> new CapabilityProviderDamageResistance(50, 15, 0));

      CUSTOM_ENTITY.put(ModUtils.resource("minecraft", "husk"),
        () -> new CapabilityProviderDamageResistance(50, 15, 0));

      CUSTOM_ENTITY.put(ModUtils.resource("minecraft", "zombie_villager"),
        () -> new CapabilityProviderDamageResistance(50, 15, 0));

    }

  }
}
