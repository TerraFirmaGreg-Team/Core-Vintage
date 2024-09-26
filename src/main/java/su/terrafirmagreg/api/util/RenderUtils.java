package su.terrafirmagreg.api.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexTransformer;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.ImmutableMap;
import org.lwjgl.opengl.GL11;

import org.jetbrains.annotations.NotNull;

import lombok.experimental.UtilityClass;

import javax.vecmath.Vector4f;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@UtilityClass
@SuppressWarnings("unused")
public final class RenderUtils {

  public static void putVertex(UnpackedBakedQuad.Builder builder, VertexFormat format, Optional<TRSRTransformation> transform, EnumFacing side, float x, float y,
                               float z, float u, float v, float r, float g, float b, float a) {
    Vector4f vec = new Vector4f();
    for (int e = 0; e < format.getElementCount(); e++) {
      switch (format.getElement(e).getUsage()) {
        case POSITION:
          if (transform.isPresent()) {
            vec.x = x;
            vec.y = y;
            vec.z = z;
            vec.w = 1;
            transform.get().getMatrix().transform(vec);
            builder.put(e, vec.x, vec.y, vec.z, vec.w);
          } else {
            builder.put(e, x, y, z, 1);
          }
          break;
        case COLOR:
          builder.put(e, r, g, b, a);
          break;
        case UV:
          if (u != -1 && v != -1) {
            if (format.getElement(e).getIndex() == 0) {
              builder.put(e, u, v, 0f, 1f);
              break;
            }
          }
        case NORMAL:
          builder.put(e, (float) side.getXOffset(), (float) side.getYOffset(),
                      (float) side.getZOffset(), 0f);
          break;
        default:
          builder.put(e);
          break;
      }
    }
  }

  /**
   * Transforms a quad.
   * <p>
   * <a href="https://github.com/Vazkii/Botania/blob/master/src/main/java/vazkii/botania/client/model/GunModel.java">...</a>
   *
   * @param quad      the quad
   * @param transform the transform
   * @return the transformed quad
   */
  public static BakedQuad transform(BakedQuad quad, final TRSRTransformation transform) {

    UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(DefaultVertexFormats.ITEM);

    final IVertexConsumer consumer = new VertexTransformer(builder) {

      @Override
      public void put(int element, float @NotNull ... data) {

        VertexFormatElement formatElement = DefaultVertexFormats.ITEM.getElement(element);

        switch (formatElement.getUsage()) {

          case POSITION: {
            float[] newData = new float[4];
            Vector4f vec = new Vector4f(data);
            transform.getMatrix().transform(vec);
            vec.get(newData);
            this.parent.put(element, newData);
            break;
          }

          default: {
            this.parent.put(element, data);
            break;
          }
        }
      }
    };

    quad.pipe(consumer);

    return builder.build();
  }

  public static List<BakedQuad> getQuads(ItemStack stack, long rand) {

    Item item = stack.getItem();

    if (item instanceof ItemBlock itemBlock) {
      Block block = itemBlock.getBlock();
      IBlockState blockState = block.getStateFromMeta(stack.getMetadata());
      IBakedModel bakedModel = getBakedModel(blockState);
      List<BakedQuad> result = new ArrayList<>();

      for (EnumFacing facing : EnumFacing.values()) {
        result.addAll(bakedModel.getQuads(blockState, facing, rand));
      }

      return result;

    } else {
      IBakedModel bakedModel = getBakedModel(stack);
      return bakedModel.getQuads(null, null, rand);
    }
  }

  public static IBakedModel getBakedModel(IBlockState blockState) {
    return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes()
                    .getModelForState(blockState);
  }

  public static IBakedModel getBakedModel(ItemStack stack) {

    Item item = stack.getItem();

    if (item instanceof ItemBlock itemBlock) {
      Block block = itemBlock.getBlock();
      IBlockState blockState = block.getStateFromMeta(stack.getMetadata());
      return getBakedModel(blockState);

    } else {
      return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
    }
  }

  public static TextureAtlasSprite getTopTextureFromBlock(Block block, int meta) {
    IBlockState state = block.getStateFromMeta(meta);
    return getTopTextureFromBlockstate(state);
  }

  public static TextureAtlasSprite getTopTextureFromBlockstate(IBlockState state) {
    final var mc = Minecraft.getMinecraft();
    IBakedModel model = mc.getBlockRendererDispatcher()
                          .getBlockModelShapes()
                          .getModelForState(state);
    if (model != mc.getBlockRendererDispatcher()
                   .getBlockModelShapes()
                   .getModelManager()
                   .getMissingModel()) {
      List<BakedQuad> quads = model.getQuads(state, EnumFacing.UP, 0);
      return !quads.isEmpty() ? quads.get(0).getSprite() : mc.getBlockRendererDispatcher()
                                                             .getBlockModelShapes()
                                                             .getTexture(state);
    }
    return mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
  }

  public static TextureAtlasSprite getTextureFromBlock(Block block, int meta) {
    IBlockState state = block.getStateFromMeta(meta);
    return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
  }

  public static TextureAtlasSprite getTextureFromBlockstate(IBlockState state) {
    return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
  }

  public static ImmutableMap<TransformType, TRSRTransformation> getTransforms(IBakedModel model) {
    ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation> builder = ImmutableMap.builder();
    for (ItemCameraTransforms.TransformType type : ItemCameraTransforms.TransformType.values()) {
      TRSRTransformation transformation = new TRSRTransformation(model.handlePerspective(type).getRight());
      if (!transformation.equals(TRSRTransformation.identity())) {
        builder.put(type, TRSRTransformation.blockCenterToCorner(transformation));
      }
    }
    return builder.build();
  }

  // Code based on code from The Betweenlands
  public static void renderInvalidArea(World world, BlockPos blockPos, int yOffset) {
    if (StreamSupport.stream(Minecraft.getMinecraft().player.getHeldEquipment().spliterator(), false)
                     .anyMatch(stack -> !stack.isEmpty() && stack.getItem() == Items.LEAD)) {

      renderUsedArea(world, blockPos, yOffset, 0.55F, 0.15F);
    }
  }

  public static void renderUsedArea(World world, BlockPos blockPos, int yOffset, float invalidAplha, float validAplha) {
    GlStateManager.pushMatrix();

    // Setup OpenGL state
    setupOpenGLState();

    GlStateManager.enableDepth();
    GlStateManager.glLineWidth(2F);

    // Render boxes
    GlStateManager.doPolygonOffset(-0.1F, -10.0F);
    GlStateManager.enablePolygonOffset();
    renderBoxes(world, blockPos, yOffset, invalidAplha, validAplha);
    GlStateManager.disablePolygonOffset();

    // Restore OpenGL state
    restoreOpenGLState();

    GlStateManager.popMatrix();
  }

  private static void setupOpenGLState() {
    GlStateManager.disableLighting();
    GlStateManager.disableTexture2D();
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0f);
    GlStateManager.color(1, 1, 1, 1);
    GL11.glEnable(GL11.GL_LINE_SMOOTH);
  }

  private static void renderBoxes(World world, BlockPos blockPos, int yOffset, float invalidAplha, float validAplha) {
    final int BOX_SIZE = 3;
    final var renderManager = Minecraft.getMinecraft().getRenderManager();

    for (int x = -BOX_SIZE; x <= BOX_SIZE; x++) {
      for (int y = yOffset; y <= 1 + yOffset; y++) {
        for (int z = -BOX_SIZE; z <= BOX_SIZE; z++) {
          BlockPos pos = blockPos.add(x, y, z);
          if ((x <= 1 && x >= -1) && (z <= 1 && z >= -1)) {
            continue;
          }
          if (pos.getY() >= 0) {
            IBlockState state = world.getBlockState(pos);
            if (!state.getBlock().isReplaceable(world, pos)) {

              GlStateManager.color(1, 0, 0, invalidAplha);
              drawBoundingBoxOutline(new AxisAlignedBB(pos).offset(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ));
              drawBoundingBox(state.getBoundingBox(world, pos).offset(pos)
                                   .offset(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ));
            } else {
              GlStateManager.color(0, 1, 0, validAplha);
              drawBoundingBoxOutline(new AxisAlignedBB(pos).offset(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ));
            }
          }
        }
      }
    }
  }

  private static void restoreOpenGLState() {
    GL11.glDisable(GL11.GL_LINE_SMOOTH);
    GlStateManager.color(1, 1, 1, 1);
    GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
    GlStateManager.depthMask(true);
    GlStateManager.enableTexture2D();
    GlStateManager.enableDepth();
    GlStateManager.disableBlend();
    GlStateManager.enableLighting();
  }

  @SideOnly(Side.CLIENT)
  public static void drawBoundingBoxOutline(AxisAlignedBB par1AxisAlignedBB) {
    GL11.glBegin(GL11.GL_LINE_STRIP);
    GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
    GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
    GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
    GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
    GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
    GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
    GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
    GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
    GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
    GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
    GL11.glEnd();
    GL11.glBegin(GL11.GL_LINES);
    GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
    GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
    GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
    GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
    GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
    GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
    GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
    GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
    GL11.glEnd();
  }

  @SideOnly(Side.CLIENT)
  public static void drawBoundingBox(AxisAlignedBB axisalignedbb) {
    GL11.glBegin(GL11.GL_QUADS);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
    GL11.glVertex3d(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
    GL11.glEnd();
  }
}
