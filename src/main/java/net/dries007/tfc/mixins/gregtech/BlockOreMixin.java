package net.dries007.tfc.mixins.gregtech;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.StoneType;
import gregtech.common.blocks.BlockOre;
import gregtech.common.blocks.properties.PropertyStoneType;
import net.dries007.tfc.module.core.submodule.rock.api.type.RockType;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix;
import net.dries007.tfc.module.core.submodule.rock.common.RockStorage;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

import static net.dries007.tfc.module.core.submodule.rock.api.variant.block.RockBlockVariants.COBBLE;
import static net.dries007.tfc.module.core.submodule.rock.api.variant.block.RockBlockVariants.RAW;

@SuppressWarnings("all")
@Mixin(value = BlockOre.class, remap = false)
public abstract class BlockOreMixin extends Block {

    @Shadow
    @Final
    public PropertyStoneType STONE_TYPE;

    @Shadow
    @Final
    public Material field_149764_J;

    public BlockOreMixin(net.minecraft.block.material.Material materialIn) {
        super(materialIn);
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
    private void onConstructor(Material material, StoneType[] allowedValues, CallbackInfo ci) {
        var stoneType = (StoneType) getBlockState().getBaseState().getValue(STONE_TYPE);
        var type = RockType.getByName(stoneType.name.replace("tfc_", ""));

        if (type != null) {
            var spec = new FallingBlockManager.Specification(FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL_ROCK);
            spec.setResultingState(RockStorage.getRockBlock(COBBLE, type).getDefaultState());
            FallingBlockManager.registerFallable(this, spec);

            var hardness = type.getCategory().getHardnessModifier() + RAW.getBaseHardness();
            setHardness(hardness * 2F);
        }
    }

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
