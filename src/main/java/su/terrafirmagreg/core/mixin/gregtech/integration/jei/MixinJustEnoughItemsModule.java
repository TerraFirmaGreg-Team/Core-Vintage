package su.terrafirmagreg.core.mixin.gregtech.integration.jei;

import net.minecraft.item.ItemStack;

import gregtech.api.GTValues;
import gregtech.integration.IntegrationSubmodule;
import gregtech.integration.jei.JustEnoughItemsModule;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(value = JustEnoughItemsModule.class, remap = false)
public class MixinJustEnoughItemsModule extends IntegrationSubmodule implements IModPlugin {

  @Unique
  private static final String GT_ORE_SPAWN_UID = GTValues.MODID + ":ore_spawn_location";

  @Inject(method = "register", at = @At(value = "TAIL"), remap = false)
  private void onRegister(IModRegistry registry, CallbackInfo ci) {

    // Add TFC Propicks to HEI GT ore spawn Tab
    List<Metal> tierOrdered = TFCRegistries.METALS.getValuesCollection()
                                                  .stream()
                                                  .sorted(Comparator.comparingInt(metal -> metal.getTier().ordinal()))
                                                  .collect(Collectors.toList());

    for (Metal metal : tierOrdered) {
      if (Metal.ItemType.PROPICK.hasType(metal)) {
        registry.addRecipeCatalyst(new ItemStack(ItemMetalTool.get(metal, Metal.ItemType.PROPICK)), GT_ORE_SPAWN_UID);
      }
    }
  }
}
