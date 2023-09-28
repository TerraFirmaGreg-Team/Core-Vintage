package net.dries007.tfc.module.animal.client.render;

import net.dries007.tfc.module.animal.api.type.IAnimal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("WeakerAccess")
@ParametersAreNonnullByDefault
public abstract class RenderAnimal<T extends EntityLiving> extends RenderLiving<T> {
    private final ResourceLocation youngTexture;
    private final ResourceLocation oldTexture;

    protected RenderAnimal(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn, @Nonnull ResourceLocation youngTextures, @Nonnull ResourceLocation oldTextures) {
        super(rendermanagerIn, modelbaseIn, shadowsizeIn);
        this.youngTexture = youngTextures;
        this.oldTexture = oldTextures;
    }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        if (entity instanceof IAnimal && ((IAnimal) entity).getAge() == IAnimal.Age.OLD) {
            return oldTexture;
        }
        return youngTexture;
    }
}
