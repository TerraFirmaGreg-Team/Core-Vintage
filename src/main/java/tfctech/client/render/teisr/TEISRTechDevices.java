package tfctech.client.render.teisr;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;
import tfctech.objects.items.itemblocks.ItemBlockFridge;
import tfctech.objects.items.itemblocks.ItemBlockWireDrawBench;
import tfctech.objects.tileentities.TEFridge;
import tfctech.objects.tileentities.TEWireDrawBench;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TEISRTechDevices extends TileEntityItemStackRenderer
{
    private static final TEWireDrawBench teWireDrawBench = new TEWireDrawBench();
    private static final TEFridge teFridge = new TEFridge();

    @Override
    public void renderByItem(ItemStack itemStack, float partialTicks)
    {
        if (itemStack.getItem() instanceof ItemBlockWireDrawBench)
        {
            TileEntityRendererDispatcher.instance.render(teWireDrawBench, 0.0D, 0.0D, 0.0D, partialTicks);
        }
        else if (itemStack.getItem() instanceof ItemBlockFridge)
        {
            TileEntityRendererDispatcher.instance.render(teFridge, 0.0D, 0.0D, 0.0D, partialTicks);
        }
    }
}
