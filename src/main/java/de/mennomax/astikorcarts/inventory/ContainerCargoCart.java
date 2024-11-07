package de.mennomax.astikorcarts.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;

import de.mennomax.astikorcarts.entity.AbstractDrawn;

public class ContainerCargoCart extends ContainerChest
{
    private final AbstractDrawn drawn;

    public ContainerCargoCart(IInventory playerInventory, IInventory chestInventory, AbstractDrawn drawn, EntityPlayer player)
    {
        super(playerInventory, chestInventory, player);
        this.drawn = drawn;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return super.canInteractWith(playerIn) && this.drawn.isEntityAlive() && this.drawn.getDistance(playerIn) < 8.0F;
    }

}