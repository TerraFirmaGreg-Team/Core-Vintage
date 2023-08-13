package net.dries007.tfc.common.objects.container;

import net.dries007.tfc.common.objects.blocks.wood.BlockWoodWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ContainerWorkbenchTFC extends ContainerWorkbench {
    //todo: replace with proper workbench mechanics
    private final World world;
    private final BlockPos pos;
    private final BlockWoodWorkbench block;

    public ContainerWorkbenchTFC(InventoryPlayer inv, World world, BlockPos pos, BlockWoodWorkbench block) {
        super(inv, world, pos);
        this.world = world;
        this.pos = pos;
        this.block = block;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        if (world.getBlockState(pos).getBlock() != block) {
            return false;
        } else {
            return playerIn.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
        }
    }
}
