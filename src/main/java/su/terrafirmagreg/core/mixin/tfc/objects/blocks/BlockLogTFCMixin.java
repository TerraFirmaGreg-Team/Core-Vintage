package su.terrafirmagreg.core.mixin.tfc.objects.blocks;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockLogTFC.class, remap = false)
public abstract class BlockLogTFCMixin extends BlockLog implements IItemSize {

  /**
   * @author SpeeeDCraft
   * @reason Disable tree cap from TFC
   */
  @Inject(method = "removedByPlayer", at = @At(value = "HEAD"), remap = false, cancellable = true)
  public void removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest, CallbackInfoReturnable<Boolean> cir) {
    cir.setReturnValue(super.removedByPlayer(state, world, pos, player, willHarvest));
  }

}
