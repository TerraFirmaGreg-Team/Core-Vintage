package su.terrafirmagreg.core.mixin.gregtech;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.common.blocks.BlockOre;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import su.terrafirmagreg.core.TerraFirmaGreg;
import su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefix;

import java.util.Random;

@Mixin(value = BlockOre.class)
public class BlockOreMixin {

    @Unique
    @Final
    public Material field_149764_J;

    @Inject(method = "getItemDropped", at = @At(value = "HEAD"), cancellable = true)
    private void getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune, CallbackInfoReturnable<Item> cir) {
        var itemStack = OreDictUnifier.get(TFGOrePrefix.oreChunk, field_149764_J);
        TerraFirmaGreg.LOGGER.info("ItemStack: " + itemStack.toString());
        cir.setReturnValue(itemStack.getItem());
    }

    @Inject(method = "damageDropped", at = @At(value = "HEAD"), cancellable = true)
    private void damageDropped(IBlockState state, CallbackInfoReturnable<Integer> cir) {
        var itemStack = OreDictUnifier.get(TFGOrePrefix.oreChunk, field_149764_J);
        TerraFirmaGreg.LOGGER.info("ItemStack: " + itemStack.toString());
        cir.setReturnValue(itemStack.getItemDamage());
    }
}
