package su.terrafirmagreg.modules.core.feature.ambiental.handler;

import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalRegistry;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierBase;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderEquipment;

import gregtech.common.items.MetaItems;

public final class ModifierHandlerEquipment {

  public static final AmbientalRegistry<IAmbientalProviderEquipment> EQUIPMENT = new AmbientalRegistry<>();

  static {
    EQUIPMENT.register((player, stack) ->
      ModifierBase.defined("nanoHelmet", 3f, -0.25f)
        .filter(mod -> stack.getItem() == MetaItems.NANO_HELMET.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierBase.defined("nanoChestplate", 3f, -0.25f)
        .filter(mod -> stack.getItem() == MetaItems.NANO_CHESTPLATE.getStackForm().getItem()
                       || stack.getItem() == MetaItems.NANO_CHESTPLATE_ADVANCED.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierBase.defined("nanoLeggings", 3f, -0.25f)
        .filter(mod -> stack.getItem() == MetaItems.NANO_LEGGINGS.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierBase.defined("nanoBoots", 3f, -0.25f)
        .filter(mod -> stack.getItem() == MetaItems.NANO_BOOTS.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierBase.defined("quantumHelmet", 3f, -0.25f)
        .filter(mod -> stack.getItem() == MetaItems.QUANTUM_HELMET.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierBase.defined("quantumChestplate", 3f, -0.25f)
        .filter(mod -> stack.getItem() == MetaItems.QUANTUM_CHESTPLATE.getStackForm().getItem()
                       || stack.getItem() == MetaItems.QUANTUM_CHESTPLATE_ADVANCED.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierBase.defined("quantumLeggings", 3f, -0.25f)
        .filter(mod -> stack.getItem() == MetaItems.QUANTUM_LEGGINGS.getStackForm().getItem())
    );

    EQUIPMENT.register((player, stack) ->
      ModifierBase.defined("quantumBoots", 3f, -0.25f)
        .filter(mod -> stack.getItem() == MetaItems.QUANTUM_BOOTS.getStackForm().getItem())
    );
  }

}
