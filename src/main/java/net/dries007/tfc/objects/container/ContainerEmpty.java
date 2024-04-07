package net.dries007.tfc.objects.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

import org.jetbrains.annotations.Nullable;

public class ContainerEmpty extends Container {

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
    }

    @Override
    public boolean canInteractWith(@Nullable EntityPlayer player) {
        return false;
    }
}
