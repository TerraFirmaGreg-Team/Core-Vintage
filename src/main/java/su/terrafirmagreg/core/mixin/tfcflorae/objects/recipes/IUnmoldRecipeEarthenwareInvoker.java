package su.terrafirmagreg.core.mixin.tfcflorae.objects.recipes;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.IMoldHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import tfcflorae.objects.recipes.UnmoldRecipeEarthenwareTFCF;

@Mixin(value = UnmoldRecipeEarthenwareTFCF.class, remap = false)
public interface IUnmoldRecipeEarthenwareInvoker {

  @Invoker
  ItemStack invokeGetOutputItem(IMoldHandler moldHandler);
}
