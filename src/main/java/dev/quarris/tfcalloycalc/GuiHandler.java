package dev.quarris.tfcalloycalc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerCalc(player.inventory, (TileEntityCalc) world.getTileEntity(new BlockPos(x, y, z)));
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GuiCalc(new ContainerCalc(player.inventory, (TileEntityCalc) world.getTileEntity(new BlockPos(x, y, z))));
    }
}
