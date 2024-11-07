package su.terrafirmagreg.core.mixin.gregtech.integration.jei;

import su.terrafirmagreg.core.modules.gregtech.items.tools.TFGToolItems;

import net.minecraft.item.ItemStack;

import gregtech.api.GTValues;
import gregtech.integration.IntegrationSubmodule;
import gregtech.integration.jei.JustEnoughItemsModule;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = JustEnoughItemsModule.class, remap = false)
public class JustEnoughItemsModuleMixin extends IntegrationSubmodule implements IModPlugin {

  @Unique
  private static final String GT_ORE_SPAWN_UID = GTValues.MODID + ":ore_spawn_location";

  @Inject(method = "register", at = @At(value = "TAIL"), remap = false)
  private void onRegister(IModRegistry registry, CallbackInfo ci) {

    registry.addRecipeCatalyst(new ItemStack(TFGToolItems.PROPICK.get()), GT_ORE_SPAWN_UID);

//    // Add TFC Propicks to HEI GT ore spawn Tab
//    List<Metal> tierOrdered = TFCRegistries.METALS.getValuesCollection()
//                                                  .stream()
//                                                  .sorted(Comparator.comparingInt(metal -> metal.getTier().ordinal()))
//                                                  .collect(Collectors.toList());
//
//    for (Metal metal : tierOrdered) {
//      if (Metal.ItemType.PROPICK.hasType(metal)) {
//        registry.addRecipeCatalyst(new ItemStack(TFGToolItems.PROPICK.get()), GT_ORE_SPAWN_UID);
//      }
//    }
  }
}
