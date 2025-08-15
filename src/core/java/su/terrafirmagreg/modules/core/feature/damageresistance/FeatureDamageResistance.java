package su.terrafirmagreg.modules.core.feature.damageresistance;

import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.framework.module.spi.StateEvent;
import su.terrafirmagreg.modules.core.feature.damageresistance.capability.CapabilityDamageResistance;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FeatureDamageResistance extends BaseFeature {

  @SubscribeEvent
  public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
    Entity entity = event.getObject();
    if (entity == null) {
      return;
    }

    ResourceLocation entityType = EntityList.getKey(entity);
    if (entityType == null) {
      return;
    }

    // Give certain entities damage resistance
    if (!CapabilityUtils.has(entity, CapabilityDamageResistance.CAPABILITY)) {

      var provider = CapabilityDamageResistance.getCustom(entityType);
      if (provider != null) {
        event.addCapability(CapabilityDamageResistance.KEY, provider);
      }
    }
  }

  @SubscribeEvent
  public static void onAttachCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event) {
    ItemStack stack = event.getObject();
    if (!StackUtils.isValid(stack)) {return;}

    ICapabilityProvider provider = CapabilityDamageResistance.getCustom(stack);
    if (provider == null) {
      return;
    }

    event.addCapability(CapabilityDamageResistance.KEY, provider);
  }

  @SubscribeEvent
  public static void onPreInit(StateEvent.PreInitialization event) {

    CapabilityDamageResistance.register();
  }

  @SubscribeEvent
  public static void onPostInit(StateEvent.PostInitialization event) {
    CapabilityDamageResistance.Handler.init();
  }
}
