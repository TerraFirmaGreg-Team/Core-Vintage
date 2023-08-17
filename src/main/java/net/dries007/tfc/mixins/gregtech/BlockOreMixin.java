package net.dries007.tfc.mixins.gregtech;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.StoneType;
import gregtech.api.unification.ore.StoneTypes;
import gregtech.api.worldgen.config.OreConfigUtils;
import gregtech.common.blocks.BlockOre;
import gregtech.common.blocks.properties.PropertyStoneType;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariants;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix;
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
        var rockType = RockType.getByName(stoneType.name.replace("tfc_", ""));

        if (rockType != null) {
            var spec = new FallingBlockManager.Specification(FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL_ROCK);
            spec.setResultingState(TFCStorage.getRockBlock(RockBlockVariants.COBBLE, rockType).getDefaultState());
            FallingBlockManager.registerFallable(this, spec);

            var hardness = rockType.getRockCategory().getHardnessModifier() + RockBlockVariants.RAW.getBaseHardness();
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
