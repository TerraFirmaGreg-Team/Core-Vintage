package su.terrafirmagreg.core.mixin.tfcflorae.objects.blocks;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.api.capability.size.IItemSize;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfcflorae.objects.blocks.wood.BlockLogTFCF;

@Mixin(value = BlockLogTFCF.class, remap = false)
public abstract class BlockLogTFCFMixin extends BlockLog implements IItemSize {

    @Inject(method = "removedByPlayer", at = @At(value = "HEAD"), remap = false, cancellable = true)
    public void removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.removedByPlayer(state, world, pos, player, willHarvest));
    }
}
