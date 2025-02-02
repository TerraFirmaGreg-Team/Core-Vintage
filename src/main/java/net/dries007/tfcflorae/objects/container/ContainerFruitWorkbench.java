package net.dries007.tfcflorae.objects.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfcflorae.objects.blocks.wood.fruitwood.BlockFruitWorkbench;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ContainerFruitWorkbench extends ContainerWorkbench {

  //todo: replace with proper workbench mechanics
  private final World world;
  private final BlockPos pos;
  private final BlockFruitWorkbench block;

  public ContainerFruitWorkbench(InventoryPlayer inv, World world, BlockPos pos, BlockFruitWorkbench block) {
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
