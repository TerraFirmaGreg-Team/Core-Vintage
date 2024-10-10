package su.terrafirmagreg.core.mixin.gregtech;

import net.minecraft.entity.item.EntityItem;

import gregtech.api.items.materialitem.MetaPrefixItem;
import gregtech.api.items.metaitem.StandardMetaItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MetaPrefixItem.class, remap = false)
public class MetaPrefixItemMixin extends StandardMetaItem {

  @Inject(method = "onEntityItemUpdate", at = @At(value = "HEAD"), remap = false, cancellable = true)
  public void onEntityItemUpdate(EntityItem itemEntity, CallbackInfoReturnable<Boolean> cir) {
    cir.setReturnValue(false);
  }
}
