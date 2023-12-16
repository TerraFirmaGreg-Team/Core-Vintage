package com.speeedcraft.tfgmod.mixins.tfcflorae.objects.items;

import net.minecraftforge.fluids.FluidTank;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "tfcflorae.objects.items.ceramics.ItemKaoliniteMold$FilledMoldCapability", remap = false)
public class ItemKaoliniteMoldMixin {
	@Shadow
	@Final
	private final FluidTank tank = new FluidTank(144);
}
