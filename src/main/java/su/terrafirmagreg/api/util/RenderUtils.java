package su.terrafirmagreg.api.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexTransformer;
import net.minecraftforge.common.model.TRSRTransformation;


import org.jetbrains.annotations.NotNull;

import lombok.experimental.UtilityClass;

import javax.vecmath.Vector4f;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@UtilityClass
@SuppressWarnings("unused")
public final class RenderUtils {

  public static void putVertex(UnpackedBakedQuad.Builder builder, VertexFormat format,
      Optional<TRSRTransformation> transform, EnumFacing side,
      float x, float y, float z, float u, float v, float r, float g, float b, float a) {
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

  public static IBakedModel getBakedModel(IBlockState blockState) {

    return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes()
        .getModelForState(blockState);
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
}
