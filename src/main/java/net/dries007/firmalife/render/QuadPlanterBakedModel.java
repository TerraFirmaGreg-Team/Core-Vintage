package net.dries007.firmalife.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.property.IExtendedBlockState;

import com.google.common.collect.ImmutableMap;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.firmalife.init.StatePropertiesFL;
import net.dries007.tfc.objects.blocks.BlockQuadPlanter;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.dries007.firmalife.FirmaLife.MOD_ID;
import static net.dries007.firmalife.util.ClientHelpers.bake;

@MethodsReturnNonnullByDefault
public class QuadPlanterBakedModel extends LargePlanterBakedModel implements IBakedModel {

  private static final IModel dummy = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation(MOD_ID, "block/quad_planter"));

  public QuadPlanterBakedModel() {}

  /**
   * Are you not entertained?
   */
  @Override
  public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
    if (state == null || !(state.getBlock() instanceof BlockQuadPlanter)) {return bake(dummy).getQuads(state, side, rand);}
    Map<String, String> sprites = new HashMap<>();
    sprites.put("soil", MOD_ID + (state.getValue(StatePropertiesFL.WET) ? ":blocks/potting_soil_wet" : ":blocks/potting_soil_dry"));
    if (state instanceof IExtendedBlockState extendedState) {
      sprites.put("crop1", resolveTexture(extendedState, BlockQuadPlanter.CROP_1));
      sprites.put("crop2", resolveTexture(extendedState, BlockQuadPlanter.CROP_2));
      sprites.put("crop3", resolveTexture(extendedState, BlockQuadPlanter.CROP_3));
      sprites.put("crop4", resolveTexture(extendedState, BlockQuadPlanter.CROP_4));
    }
    IModel newModel = dummy.retexture(ImmutableMap.copyOf(sprites));
    return bake(newModel).getQuads(state, side, rand);
  }
}
