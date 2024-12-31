package su.terrafirmagreg.old.mixin.gregtech.api.recipes.ingredients;

import net.minecraft.item.ItemStack;

import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GTRecipeItemInput.class, remap = false)
public class GTRecipeItemInputMixin {

  /**
   * Исправляет проблему, когда некоторые рецепты содержащие капабилити нагрева не перерабатываются в рецептах. Возможно стоит добавить возврат true, только
   * если у предмета есть капабилити нагрева? Стоит подумать.
   */
  @Redirect(method = "acceptsStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;areCapsCompatible(L;)Z"), remap = false)
  private boolean onAreCapsCompatible(ItemStack stack) {
    return true;
  }

}
