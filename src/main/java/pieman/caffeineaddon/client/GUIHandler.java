package pieman.caffeineaddon.client;

import javax.annotation.Nullable;

import net.dries007.tfc.client.TFCGuiHandler.Type;
import net.dries007.tfc.client.gui.GuiContainerTFC;
import net.dries007.tfc.client.gui.GuiFirePit;
import net.dries007.tfc.client.gui.GuiLiquidTransfer;
import net.dries007.tfc.objects.container.ContainerNestBox;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.items.ceramics.ItemSmallVessel;
import net.dries007.tfc.objects.te.TEFirePit;
import net.dries007.tfc.objects.te.TENestBox;
import net.dries007.tfc.objects.te.TEQuern;
import net.dries007.tfc.util.Helpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import pieman.caffeineaddon.blocks.TEDryingMat;

public class GUIHandler implements IGuiHandler {
	public static final int DRYINGMATGUI = 0;

	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		 BlockPos pos = new BlockPos(x, y, z);
	        ItemStack stack = player.getHeldItemMainhand();
	        switch (ID){
	            case DRYINGMATGUI:
	            	TEDryingMat te = Helpers.getTE(world, pos, TEDryingMat.class);
	                return te == null ? null : new ContainerDryingMat(player.inventory, te);
	        }
	        return null;
	}


    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
        Container container = getServerGuiElement(ID, player, world, x, y, z);
        BlockPos pos = new BlockPos(x, y, z);
        switch (ID){
            case DRYINGMATGUI:
                return new GuiDryingMat(container, player.inventory, Helpers.getTE(world, pos, TEDryingMat.class), world.getBlockState(new BlockPos(x, y, z)).getBlock().getTranslationKey());
        }
        return null;
    }

}
