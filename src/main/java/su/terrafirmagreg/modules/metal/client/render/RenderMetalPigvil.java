package su.terrafirmagreg.modules.metal.client.render;

import su.terrafirmagreg.modules.metal.api.types.type.MetalTypes;
import su.terrafirmagreg.modules.metal.init.BlocksMetal;
import su.terrafirmagreg.modules.metal.objects.entity.EntityMetalPigvil;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfcthings.model.ModelPigvil;

import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.data.Reference.MODID_TFCTHINGS;

public class RenderMetalPigvil
  extends RenderLiving<EntityMetalPigvil> {

  private static final ResourceLocation PIGVIL = new ResourceLocation(MODID_TFCTHINGS,
                                                                      "textures/entity/pigvil.png");
  private static final ResourceLocation PIGVIL_BLACK = new ResourceLocation(MODID_TFCTHINGS,
                                                                            "textures/entity/pigvil_black.png");
  private static final ResourceLocation PIGVIL_RED = new ResourceLocation(MODID_TFCTHINGS,
                                                                          "textures/entity/pigvil_red.png");
  private static final ResourceLocation PIGVIL_BLUE = new ResourceLocation(MODID_TFCTHINGS,
                                                                           "textures/entity/pigvil_blue.png");
  private static final ResourceLocation PIGVIL_PURPLE = new ResourceLocation(MODID_TFCTHINGS,
                                                                             "textures/entity/pigvil_purple.png");

  public RenderMetalPigvil(RenderManager rendermanagerIn) {
    super(rendermanagerIn, new ModelPigvil(), 0.7F);

  }

  @Nullable
  @Override
  protected ResourceLocation getEntityTexture(EntityMetalPigvil entity) {
    Block anvil = entity.getAnvil();
    if (anvil.getRegistryName() == BlocksMetal.PIGVIL.get(MetalTypes.STEEL).getRegistryName()) {
      return PIGVIL;
    } else if (anvil.getRegistryName() == BlocksMetal.PIGVIL.get(MetalTypes.BLACK_STEEL)
                                                            .getRegistryName()) {
      return PIGVIL_BLACK;
    } else if (anvil.getRegistryName() == BlocksMetal.PIGVIL.get(MetalTypes.BLUE_STEEL)
                                                            .getRegistryName()) {
      return PIGVIL_BLUE;
    } else if (anvil.getRegistryName() == BlocksMetal.PIGVIL.get(MetalTypes.RED_STEEL)
                                                            .getRegistryName()) {
      return PIGVIL_RED;
    } else {
      return PIGVIL_PURPLE;
    }
  }

}
