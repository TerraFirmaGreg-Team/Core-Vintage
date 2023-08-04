package net.dries007.tfc.mixins.gregtech;

import gregtech.api.fluids.MetaFluids;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.worldgen.populator.SurfaceRockPopulator;
import gregtech.common.blocks.BlockOre;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Mixin(value = SurfaceRockPopulator.class, remap = false)
public class SurfaceGeneratorMixin {

	@Inject(method = "findUndergroundMaterials", at = @At(value = "HEAD"), remap = false, cancellable = true)
	private static void findUndergroundMaterials(Collection<IBlockState> generatedBlocks, CallbackInfoReturnable<Set<Material>> cir) {
		Set<Material> result = new HashSet<>();
		for (IBlockState blockState : generatedBlocks) {
			Material resultMaterial;
			if (blockState.getBlock() instanceof IFluidBlock || blockState.getBlock() instanceof BlockLiquid) {
				Fluid fluid = FluidRegistry.lookupFluidForBlock(blockState.getBlock());
				resultMaterial = fluid == null ? null : MetaFluids.getMaterialFromFluid(fluid);
			} else if (blockState.getBlock() instanceof BlockOre blockOre) {
				if (blockOre.material != null && blockOre.material.hasProperty(PropertyKey.ORE))
					resultMaterial = blockOre.material;
				else
					resultMaterial = null;
			} else {
				resultMaterial = null;
			}

			if (resultMaterial != null) {
				result.add(resultMaterial);
			}

		}

		cir.setReturnValue(result);
	}
}
