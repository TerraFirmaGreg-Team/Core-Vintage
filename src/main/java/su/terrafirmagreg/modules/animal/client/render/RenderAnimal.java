package su.terrafirmagreg.modules.animal.client.render;

import su.terrafirmagreg.modules.animal.api.type.IAnimal;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;


import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")

public abstract class RenderAnimal<T extends EntityLiving> extends RenderLiving<T> {

  private final ResourceLocation youngTexture;
  private final ResourceLocation oldTexture;

  protected RenderAnimal(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn,
      @NotNull ResourceLocation youngTextures,
      @NotNull ResourceLocation oldTextures) {
    super(rendermanagerIn, modelbaseIn, shadowsizeIn);
    this.youngTexture = youngTextures;
    this.oldTexture = oldTextures;
  }

  @NotNull
  @Override
  protected ResourceLocation getEntityTexture(T entity) {
    if (entity instanceof IAnimal && ((IAnimal) entity).getAge() == IAnimal.Age.OLD) {
      return oldTexture;
    }
    return youngTexture;
  }
}
