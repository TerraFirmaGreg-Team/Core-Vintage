package net.dries007.tfc.mixins.minecraft;

import su.terrafirmagreg.tfc.TerraFirmaCraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.world.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.client.gui.GuiScreen.isShiftKeyDown;

@SuppressWarnings("all")
@Mixin(value = GuiCreateWorld.class)
public class GuiCreateWorldMixin {

    @Shadow
    private int selectedIndex = TerraFirmaCraft.WORLD_TYPE_TFC.getId();

    @Inject(method = "canSelectCurWorldType", at = @At(value = "HEAD"), cancellable = true)
    private void onCanSelectCurWorldType(CallbackInfoReturnable<Boolean> cir) {
        if (isShiftKeyDown()) {
            selectedIndex = WorldType.DEBUG_ALL_BLOCK_STATES.getId();
        } else {
            selectedIndex = TerraFirmaCraft.WORLD_TYPE_TFC.getId();
        }
        cir.setReturnValue(true);
    }
}
