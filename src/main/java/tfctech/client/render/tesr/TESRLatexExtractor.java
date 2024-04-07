package tfctech.client.render.tesr;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fluids.Fluid;

import net.dries007.tfc.client.FluidSpriteCache;
import org.lwjgl.opengl.GL11;
import tfctech.objects.fluids.TechFluids;
import tfctech.objects.tileentities.TELatexExtractor;

import static net.minecraft.block.BlockHorizontal.FACING;
import static tfctech.objects.blocks.devices.BlockLatexExtractor.*;
import static tfctech.objects.tileentities.TELatexExtractor.MAX_FLUID;

public class TESRLatexExtractor extends TileEntitySpecialRenderer<TELatexExtractor> {

    @Override
    public void render(TELatexExtractor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (te.hasWorld()) {
            IBlockState state = te.getBlockState();
            Fluid flowing = TechFluids.LATEX.get();

            //Update state values according to TE

            //noinspection ConstantConditions
            state = state.withProperty(BASE, te.hasBase())
                    .withProperty(POT, te.hasPot())
                    .withProperty(CUT, te.cutState());

            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            BlockRendererDispatcher renderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

            Vec3i vec = state.getValue(FACING).getOpposite().getDirectionVec();

            GlStateManager.pushMatrix();
            GlStateManager.translate(x + vec.getX(), y, z + vec.getZ());
            GlStateManager.rotate(-90, 0, 1, 0);

            //Render the static model
            //This is done here so i can offset the model (bypass the maximum coordinates of a blockstate)
            IBakedModel ibakedmodel = renderer.getModelForState(state);
            renderer.getBlockModelRenderer().renderModelBrightness(ibakedmodel, state, 1.0F, true);

            TextureAtlasSprite sprite = FluidSpriteCache.getFlowingSprite(flowing);

            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                    GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

            int color = flowing.getColor();

            float r = ((color >> 16) & 0xFF) / 255F;
            float g = ((color >> 8) & 0xFF) / 255F;
            float b = (color & 0xFF) / 255F;
            float a = ((color >> 24) & 0xFF) / 255F;

            GlStateManager.color(r, g, b, a);

            rendererDispatcher.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

            BufferBuilder buffer = Tessellator.getInstance().getBuffer();

            double yMin = 0.1876D;
            double yMax = 0.36D;
            double yPos = (te.getFluidAmount() / (double) MAX_FLUID) * (yMax - yMin) + yMin;

            switch (state.getValue(FACING)) {
                case NORTH:
                    GlStateManager.rotate(180, 0, 1, 0);
                    GlStateManager.translate(-1, 0, -1);
                    break;
                case SOUTH:
                    break;
                case EAST:
                    GlStateManager.rotate(90, 0, 1, 0);
                    GlStateManager.translate(-1, 0, 0);
                    break;
                case WEST:
                    GlStateManager.rotate(-90, 0, 1, 0);
                    GlStateManager.translate(0, 0, -1);
                    break;
            }
            //From bark
            if (te.cutState() > 1) {
                for (double zPos = 1.075D; zPos <= 1.375D; zPos += 0.075D) {
                    buffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX_NORMAL);

                    buffer.pos(0.46625D, 0.57125D, zPos - 0.075D)
                            .tex(sprite.getMinU(), sprite.getMinV())
                            .normal(0, 1, 0)
                            .endVertex();
                    buffer.pos(0.46625D, 0.57125D, zPos)
                            .tex(sprite.getMinU(), sprite.getMaxV())
                            .normal(0, 1, 0)
                            .endVertex();
                    buffer.pos(0.54375D, 0.57125D, zPos)
                            .tex(sprite.getMaxU(), sprite.getMaxV())
                            .normal(0, 1, 0)
                            .endVertex();
                    buffer.pos(0.54375D, 0.57125D, zPos - 0.075D)
                            .tex(sprite.getMaxU(), sprite.getMinV())
                            .normal(0, 1, 0)
                            .endVertex();

                    Tessellator.getInstance().draw();
                }

                //Corner
                buffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX_NORMAL);

                buffer.pos(0.46625D, 0.57125D, 1.375D)
                        .tex(sprite.getMinU(), sprite.getMinV())
                        .normal(0, 1, 0)
                        .endVertex();
                buffer.pos(0.46625D, 0.50125D, 1.4D)
                        .tex(sprite.getMinU(), sprite.getMaxV())
                        .normal(0, 1, 0)
                        .endVertex();
                buffer.pos(0.54375D, 0.50125D, 1.4D)
                        .tex(sprite.getMaxU(), sprite.getMaxV())
                        .normal(0, 1, 0)
                        .endVertex();
                buffer.pos(0.54375D, 0.57125D, 1.375D)
                        .tex(sprite.getMaxU(), sprite.getMinV())
                        .normal(0, 1, 0)
                        .endVertex();

                Tessellator.getInstance().draw();

                //Corner, back side
                buffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX_NORMAL);

                buffer.pos(0.46625D, 0.50125D, 1.4D)
                        .tex(sprite.getMinU(), sprite.getMaxV())
                        .normal(0, 1, 0)
                        .endVertex();
                buffer.pos(0.46625D, 0.57125D, 1.375D)
                        .tex(sprite.getMinU(), sprite.getMinV())
                        .normal(0, 1, 0)
                        .endVertex();
                buffer.pos(0.54375D, 0.57125D, 1.375D)
                        .tex(sprite.getMaxU(), sprite.getMinV())
                        .normal(0, 1, 0)
                        .endVertex();
                buffer.pos(0.54375D, 0.50125D, 1.4D)
                        .tex(sprite.getMaxU(), sprite.getMaxV())
                        .normal(0, 1, 0)
                        .endVertex();

                Tessellator.getInstance().draw();

                double lastPos = 0D;
                for (double yyPos = 0.42625D; yyPos > yPos; yyPos -= 0.075D) {
                    //To pot
                    buffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX_NORMAL);

                    buffer.pos(0.46625D, yyPos + 0.075D, 1.4D)
                            .tex(sprite.getMinU(), sprite.getMinV())
                            .normal(0, 1, 0)
                            .endVertex();
                    buffer.pos(0.46625D, yyPos, 1.4D)
                            .tex(sprite.getMinU(), sprite.getMaxV())
                            .normal(0, 1, 0)
                            .endVertex();
                    buffer.pos(0.54375D, yyPos, 1.4D)
                            .tex(sprite.getMaxU(), sprite.getMaxV())
                            .normal(0, 1, 0)
                            .endVertex();
                    buffer.pos(0.54375D, yyPos + 0.075D, 1.4D)
                            .tex(sprite.getMaxU(), sprite.getMinV())
                            .normal(0, 1, 0)
                            .endVertex();

                    Tessellator.getInstance().draw();

                    //To pot, back side
                    buffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX_NORMAL);

                    buffer.pos(0.46625D, yyPos, 1.4D)
                            .tex(sprite.getMinU(), sprite.getMaxV())
                            .normal(0, 1, 0)
                            .endVertex();
                    buffer.pos(0.46625D, yyPos + 0.075D, 1.4D)
                            .tex(sprite.getMinU(), sprite.getMinV())
                            .normal(0, 1, 0)
                            .endVertex();
                    buffer.pos(0.54375D, yyPos + 0.075D, 1.4D)
                            .tex(sprite.getMaxU(), sprite.getMinV())
                            .normal(0, 1, 0)
                            .endVertex();
                    buffer.pos(0.54375D, yyPos, 1.4D)
                            .tex(sprite.getMaxU(), sprite.getMaxV())
                            .normal(0, 1, 0)
                            .endVertex();

                    Tessellator.getInstance().draw();
                    lastPos = yyPos;
                }

                //Missing last quad
                //To pot
                buffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX_NORMAL);

                buffer.pos(0.46625D, lastPos, 1.4D).tex(sprite.getMinU(), sprite.getMinV()).normal(0, 1, 0).endVertex();
                buffer.pos(0.46625D, yPos, 1.4D).tex(sprite.getMinU(), sprite.getMaxV()).normal(0, 1, 0).endVertex();
                buffer.pos(0.54375D, yPos, 1.4D).tex(sprite.getMaxU(), sprite.getMaxV()).normal(0, 1, 0).endVertex();
                buffer.pos(0.54375D, lastPos, 1.4D).tex(sprite.getMaxU(), sprite.getMinV()).normal(0, 1, 0).endVertex();

                Tessellator.getInstance().draw();

                //To pot, back side
                buffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX_NORMAL);

                buffer.pos(0.46625D, yPos, 1.4D).tex(sprite.getMinU(), sprite.getMaxV()).normal(0, 1, 0).endVertex();
                buffer.pos(0.46625D, lastPos, 1.4D).tex(sprite.getMinU(), sprite.getMinV()).normal(0, 1, 0).endVertex();
                buffer.pos(0.54375D, lastPos, 1.4D).tex(sprite.getMaxU(), sprite.getMinV()).normal(0, 1, 0).endVertex();
                buffer.pos(0.54375D, yPos, 1.4D).tex(sprite.getMaxU(), sprite.getMaxV()).normal(0, 1, 0).endVertex();

                Tessellator.getInstance().draw();

            }

            //Draws pot contents
            if (te.hasFluid()) {
                sprite = FluidSpriteCache.getStillSprite(flowing);

                buffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX_NORMAL);

                buffer.pos(0.3125D, yPos, 1.1875D).tex(sprite.getMinU(), sprite.getMinV()).normal(0, 1, 0).endVertex();
                buffer.pos(0.3125D, yPos, 1.5625D).tex(sprite.getMinU(), sprite.getMaxV()).normal(0, 1, 0).endVertex();
                buffer.pos(0.6875D, yPos, 1.5625D).tex(sprite.getMaxU(), sprite.getMaxV()).normal(0, 1, 0).endVertex();
                buffer.pos(0.6875D, yPos, 1.1875D).tex(sprite.getMaxU(), sprite.getMinV()).normal(0, 1, 0).endVertex();

                Tessellator.getInstance().draw();
            }

            GlStateManager.popMatrix();
        }
    }
}
