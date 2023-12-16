package com.speeedcraft.tfgmod.mixins.tfctech;

import net.minecraftforge.fluids.FluidTank;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import tfctech.objects.items.ceramics.ItemTechMold;

@Mixin(value = ItemTechMold.FilledMoldCapability.class, remap = false)
public class ItemTechMoldMixin {
	@Shadow
	@Final
	private final FluidTank tank = new FluidTank(144);
}