package net.dries007.tfc.mixins.gregtech;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockMaterialBase;
import gregtech.common.blocks.BlockSurfaceRock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockSurfaceRock.class, remap = false)
public abstract class BlockSurfaceRockMixin extends BlockMaterialBase {

    public BlockSurfaceRockMixin(Material material) {
        super(material);
    }

    @Inject(method = "getDropStack", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private void onGetDropStack(IBlockState state, int amount, CallbackInfoReturnable<ItemStack> cir) {
        var itemStack = OreDictUnifier.get(OrePrefix.nugget, getGtMaterial(state), amount);
        var fallbackItemStack = OreDictUnifier.get(OrePrefix.dustTiny, getGtMaterial(state), amount);

        if (itemStack.getItem() == Items.AIR) {
            cir.setReturnValue(fallbackItemStack);
            return;
        }

        cir.setReturnValue(itemStack);
    }
}
