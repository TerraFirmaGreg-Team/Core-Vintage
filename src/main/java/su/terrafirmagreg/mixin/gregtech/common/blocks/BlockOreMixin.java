package su.terrafirmagreg.mixin.gregtech.common.blocks;

import su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.common.blocks.BlockOre;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

@Mixin(value = BlockOre.class, remap = false)
public class BlockOreMixin {

  @Shadow
  @Final
  public Material field_149764_J;

  private Material getMaterial() {
    return field_149764_J;
  }


  @Inject(method = "getItemDropped", at = @At(value = "HEAD"), cancellable = true)
  private void getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune, CallbackInfoReturnable<Item> cir) {
    var itemStack = OreDictUnifier.get(OrePrefixHandler.oreChunk, getMaterial());
    cir.setReturnValue(itemStack.getItem());
  }

  @Inject(method = "damageDropped", at = @At(value = "HEAD"), cancellable = true)
  private void damageDropped(IBlockState state, CallbackInfoReturnable<Integer> cir) {
    var itemStack = OreDictUnifier.get(OrePrefixHandler.oreChunk, getMaterial());
    cir.setReturnValue(itemStack.getItemDamage());
  }
}
