package com.speeedcraft.tfgmod.mixins.tfctech;

import ic2.api.energy.tile.IEnergySink;
import net.dries007.tfc.objects.te.TEInventory;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import org.spongepowered.asm.mixin.*;
import tfctech.TechConfig;
import tfctech.objects.storage.MachineEnergyContainer;
import tfctech.objects.tileentities.TEFridge;

@Mixin(value = TEFridge.class, remap = false)
public abstract class TEFridgeMixin extends TEInventory implements ITickable, IEnergySink {

	@Shadow
	@Final
	@Mutable
	private final MachineEnergyContainer energyContainer;

	protected TEFridgeMixin(int inventorySize) {
		super(inventorySize);
		this.energyContainer = new MachineEnergyContainer(TechConfig.DEVICES.fridgeEnergyCapacity, TechConfig.DEVICES.fridgeEnergyCapacity, 0);
	}

	/**
	 * @author SpeeeDCraft
	 * @reason Отключить возможность ложить в холодильник все.
	 */
	@Overwrite
	public ItemStack insertItem(int slot, ItemStack stack) {
		if (stack.getItem() instanceof ItemFood) {
			ItemStack output = this.inventory.insertItem(slot, stack, false);
			setAndUpdateSlots(slot);
			return output;
		}
		return stack;
	}
}
