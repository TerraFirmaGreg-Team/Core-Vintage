package tfctech.client.render.tesr;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import tfctech.client.render.models.ModelWireDrawBench;
import tfctech.objects.tileentities.TEWireDrawBench;

import static su.terrafirmagreg.old.api.data.Reference.TFCTECH;

public class TESRWireDrawBench extends TileEntitySpecialRenderer<TEWireDrawBench>
{
    private static final ResourceLocation BENCH_TEXTURES = new ResourceLocation(TFCTECH, "textures/models/wiredraw_bench.png");
    private final ModelWireDrawBench model = new ModelWireDrawBench();


    @Override
    public void render(TEWireDrawBench te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        if (te.hasWorld())
        {
            GlStateManager.pushMatrix(); // start
            GlStateManager.translate(x, y, z); // position

            bindTexture(BENCH_TEXTURES); // texture

            model.setRotation(te.getRotation());
            model.setWire(te.getWireColor());
            model.setDrawplateMetal(te.getDrawPlateMetal());
            if (te.hasWire())
            {
                model.setProgress(te.getProgress(), te.getLastProgress(), partialTicks);
            }
            else
            {
                model.setProgress(100, 100, 1.0F);
            }

            model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

            GlStateManager.popMatrix(); // end
        }
        else
        {
            //For itemstacks rendering

            GlStateManager.pushMatrix(); // start
            GlStateManager.translate(1, -0.4F, 0);
            GlStateManager.rotate(-35, 0, 1, 0);

            bindTexture(BENCH_TEXTURES); // texture

            model.setRotation(EnumFacing.EAST);
            model.setWire(0x00000000);
            model.setDrawplateMetal(null);
            model.setProgress(100, 100, 1.0F);

            model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

            GlStateManager.popMatrix(); // end
        }
    }

    @Override
    public boolean isGlobalRenderer(TEWireDrawBench te)
    {
        return true;
    }
}
