package su.terrafirmagreg.core.mixin.firmalife;

import net.minecraft.item.ItemStack;

import com.eerussianguy.firmalife.recipe.UnmoldRecipeFL;
import net.dries007.tfc.api.capability.IMoldHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = UnmoldRecipeFL.class, remap = false)
public interface IUnmoldRecipeFLInvoker {

    @Invoker
    ItemStack invokeGetOutputItem(IMoldHandler moldHandler);
}
