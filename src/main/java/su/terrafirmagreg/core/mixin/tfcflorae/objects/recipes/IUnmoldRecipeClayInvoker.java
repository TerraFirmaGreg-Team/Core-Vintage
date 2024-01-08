package su.terrafirmagreg.core.mixin.tfcflorae.objects.recipes;

import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.objects.recipes.UnmoldRecipe;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = UnmoldRecipe.class, remap = false)
public interface IUnmoldRecipeClayInvoker {
	@Invoker
	ItemStack invokeGetOutputItem(IMoldHandler moldHandler);
}
