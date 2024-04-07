package su.terrafirmagreg.modules.wood.objects.container;

import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

public class ContainerWoodWorkbench extends ContainerWorkbench {

    //todo: replace with proper workbench mechanics
    private final World world;
    private final BlockPos pos;
    private final BlockWoodWorkbench block;

    public ContainerWoodWorkbench(InventoryPlayer inv, World world, BlockPos pos, BlockWoodWorkbench block) {
        super(inv, world, pos);
        this.world = world;
        this.pos = pos;
        this.block = block;
    }

    @Override
    public boolean canInteractWith(@NotNull EntityPlayer playerIn) {
        if (world.getBlockState(pos).getBlock() != block) {
            return false;
        } else {
            return playerIn.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
        }
    }
}
