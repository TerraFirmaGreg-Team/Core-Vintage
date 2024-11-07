package su.terrafirmagreg.core.mixin.gregtech.loaders.recipe.handlers;

import net.minecraft.item.ItemStack;

import gregtech.loaders.recipe.handlers.PartsRecipeHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = PartsRecipeHandler.class, remap = false)
public class PartsRecipeHandlerMixin {

  /**
   * Disable 2x plate -> double plate recipe generation
   */
  @Redirect(method = "processPlateDouble", at = @At(value = "INVOKE", target = "Lgregtech/api/recipes/ModHandler;addShapedRecipe(Ljava/lang/String;Lnet/minecraft/item/ItemStack;[Ljava/lang/Object;)V"), remap = false)
  private static void onProcessPlateDouble(String regName, ItemStack result, Object[] recipe) {
  }
}
