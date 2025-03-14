package su.terrafirmagreg.mixin.gregtech.api.capability.impl.miner;

import su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

import gregtech.api.capability.impl.miner.MinerLogic;
import gregtech.api.unification.OreDictUnifier;
import gregtech.common.blocks.BlockOre;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinerLogic.class, remap = false)
public class MinerLogicMixin {

  @Inject(method = "getRegularBlockDrops", at = @At(value = "HEAD"), remap = false, cancellable = true)
  protected void onGetRegularBlockDrops(NonNullList<ItemStack> blockDrops, WorldServer world, BlockPos blockToMine, IBlockState blockState, CallbackInfo ci) {
    if (blockState.getBlock() instanceof BlockOre blockOre) {
      blockDrops.add(OreDictUnifier.get(OrePrefixCore.oreChunk, blockOre.material));
      ci.cancel();
    }
  }
}
