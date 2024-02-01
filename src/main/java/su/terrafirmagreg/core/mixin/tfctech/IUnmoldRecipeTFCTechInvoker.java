package su.terrafirmagreg.core.mixin.tfctech;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.IMoldHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import tfctech.objects.recipes.UnmoldRecipe;

@Mixin(value = UnmoldRecipe.class, remap = false)
public interface IUnmoldRecipeTFCTechInvoker {

    @Invoker
    ItemStack invokeGetOutputItem(final IMoldHandler moldHandler);
}
