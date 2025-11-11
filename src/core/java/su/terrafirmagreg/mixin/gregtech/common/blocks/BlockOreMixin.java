package su.terrafirmagreg.mixin.gregtech.common.blocks;

import su.terrafirmagreg.modules.core.plugin.gregtech.unification.ore.oreprefix.OrePrefixCore;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.common.blocks.BlockOre;
import gregtech.common.blocks.properties.PropertyStoneType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

@Mixin(value = BlockOre.class)
public abstract class BlockOreMixin {

  @Unique
  @Final
  public PropertyStoneType STONE_TYPE;


  @Unique
  private Material getStoneMaterial(IBlockState state) {
    return state.getValue(STONE_TYPE).stoneMaterial;
  }


  @Inject(
    method = "getItemDropped",
    at = @At(value = "HEAD"),
    cancellable = true
  )
  private void getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune, CallbackInfoReturnable<Item> cir) {
    var itemStack = OreDictUnifier.get(OrePrefixCore.oreChunk, getStoneMaterial(state));
    cir.setReturnValue(itemStack.getItem());
  }

  @Inject(
    method = "damageDropped",
    at = @At(value = "HEAD"),
    cancellable = true
  )
  private void damageDropped(IBlockState state, CallbackInfoReturnable<Integer> cir) {
    var itemStack = OreDictUnifier.get(OrePrefixCore.oreChunk, getStoneMaterial(state));
    cir.setReturnValue(itemStack.getItemDamage());
  }

//  @Inject(
//    method = "getSubBlocks",
//    at = @At(value = "HEAD"),
//    cancellable = true
//  )
//  private void getSubBlocks(@NotNull CreativeTabs tab, @NotNull NonNullList<ItemStack> list, CallbackInfo cir) {
//    if (tab == CreativeTabs.SEARCH || tab == GregTechAPI.TAB_GREGTECH_ORES) {
//      getBlockState().getValidStates().stream()
//        .filter(state -> state.getValue(STONE_TYPE).shouldBeDroppedAsItem)
//        .forEach(state -> list.add(OreDictUnifier.get(OrePrefixCore.oreChunk, core_Vintage$getStoneMaterial(state))));
//    }
//    cir.cancel();
//  }
}
