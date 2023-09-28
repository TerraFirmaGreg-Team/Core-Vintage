package net.dries007.tfc.mixins.gregtech;

import gregtech.loaders.recipe.handlers.MaterialRecipeHandler;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = MaterialRecipeHandler.class, remap = false)
public class MaterialRecipeHandlerMixin {

    /**
     * Отключить 2x слиток -> генерация рецепта пластин
     */
    @Redirect(method = "processIngot", at = @At(value = "INVOKE", target = "Lgregtech/api/recipes/ModHandler;addShapedRecipe(Ljava/lang/String;Lnet/minecraft/item/ItemStack;[Ljava/lang/Object;)V", ordinal = 2), remap = false)
    private static void onProcessPlateDouble(String regName, ItemStack result, Object[] recipe) {
    }
}
