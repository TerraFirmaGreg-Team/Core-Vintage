package su.terrafirmagreg.api.gui;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IContainerProvider<C extends Container, G extends GuiContainer> {

    C getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos);

    @SideOnly(Side.CLIENT)
    G getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos);

}
