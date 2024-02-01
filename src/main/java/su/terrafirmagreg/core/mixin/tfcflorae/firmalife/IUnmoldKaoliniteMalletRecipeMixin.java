package su.terrafirmagreg.core.mixin.tfcflorae.firmalife;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.IMoldHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import tfcflorae.compat.firmalife.recipes.UnmoldKaoliniteMalletRecipe;

@Mixin(value = UnmoldKaoliniteMalletRecipe.class, remap = false)
public interface IUnmoldKaoliniteMalletRecipeMixin {

    @Invoker
    ItemStack invokeGetOutputItem(IMoldHandler moldHandler);
}
