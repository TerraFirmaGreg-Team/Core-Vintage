package su.terrafirmagreg.modules.wood.objects.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;

import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;

public class ContainerWoodSupplyCart extends ContainerChest {

    private final EntityWoodCart drawn;

    public ContainerWoodSupplyCart(IInventory playerInventory, IInventory chestInventory, EntityWoodCart drawn, EntityPlayer player) {
        super(playerInventory, chestInventory, player);
        this.drawn = drawn;
    }

    @Override
    public boolean canInteractWith(@NotNull EntityPlayer playerIn) {
        return super.canInteractWith(playerIn) && this.drawn.isEntityAlive() && this.drawn.getDistance(playerIn) < 8.0F;
    }

}
