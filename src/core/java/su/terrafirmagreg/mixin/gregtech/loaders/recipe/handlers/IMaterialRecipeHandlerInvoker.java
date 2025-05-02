package su.terrafirmagreg.mixin.gregtech.loaders.recipe.handlers;

import gregtech.api.unification.material.Material;
import gregtech.loaders.recipe.handlers.MaterialRecipeHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = MaterialRecipeHandler.class, remap = false)
public interface IMaterialRecipeHandlerInvoker {

  @Invoker
  static int invokeGetVoltageMultiplier(Material material) {
    throw new AssertionError();
  }
}
