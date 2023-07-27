package net.dries007.tfc.mixins.gregtech;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.common.blocks.BlockOre;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@SuppressWarnings("all")
@Mixin(value = BlockOre.class, remap = false)
public class BlockOreMixin {

    @Shadow @Final
    public Material field_149764_J;

    @Inject(method = "func_180660_a", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private void getItemDropped(IBlockState state, Random rand, int fortune, CallbackInfoReturnable<Item> cir) {
        var itemStack = OreDictUnifier.get(TFGOrePrefix.oreChunk, field_149764_J);

        cir.setReturnValue(itemStack.getItem());
    }

    @Inject(method = "func_180651_a", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private void damageDropped(IBlockState state, CallbackInfoReturnable<Integer> cir) {
        var itemStack = OreDictUnifier.get(TFGOrePrefix.oreChunk, field_149764_J);

        cir.setReturnValue(itemStack.getItemDamage());
    }
}
