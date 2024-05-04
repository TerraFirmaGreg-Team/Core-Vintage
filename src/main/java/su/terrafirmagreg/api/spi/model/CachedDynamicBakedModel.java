package su.terrafirmagreg.api.spi.model;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import javax.vecmath.Matrix4f;
import java.util.List;
import java.util.Map;

/**
 * This model class allows for the quads of a model to change based on various in world context information. For example this can allow for certain textures in a model to be
 * changed based on an upgrade or other change. Variants of the model are automatically stored in a cache to allow for less memory use and object construction when rendering block
 * models.
 * <p>
 * This model works by passing item context to {@link #getCacheKey(ItemStack, World, EntityLivingBase)} and block context to {@link #getCacheKey(IBlockState, EnumFacing)} to get a
 * cache key for the context. This key is then sent to {@link #generateBlockModel(String)} to build the new model instance. Both item and block models share the same cache.
 */
public abstract class CachedDynamicBakedModel implements IBakedModel {

    /**
     * A cache of baked models. This is used to prevent re-baking the same model.
     */
    private final Map<String, IBakedModel> cache = Maps.newHashMap();

    /**
     * The original baked model.
     */
    @Getter
    private final IBakedModel bakedOriginal;

    /**
     * The raw model data.
     */
    @Getter
    private final IModel raw;

    public CachedDynamicBakedModel(IBakedModel standard, IModel tableModel) {

        this.bakedOriginal = standard;
        this.raw = tableModel;
    }

    /**
     * Generates a cache key from a block context. This method should always return the same result for the same input. The result is used by {@link #generateBlockModel(String)} to
     * create a new model version so the result should also be descriptive.
     *
     * @param state The block state. If you use an extended block state this can contain arbitrary objects from
     *              {@link Block#getExtendedState(IBlockState, net.minecraft.world.IBlockAccess, net.minecraft.util.math.BlockPos)}.
     * @param side  The side of the block.
     * @return A cache key which contains information based on the context.
     */
    public abstract String getCacheKey(IBlockState state, EnumFacing side);

    /**
     * Generates a cache key from an item context. This method should always return the same result for the same input. The result is used by {@link #generateBlockModel(String)} to
     * create a new model version so the result should also be descriptive.
     *
     * @param stack  The ItemStack. This is the actual item, and has nbt.
     * @param world  The world instance.
     * @param entity The entity. This is almost always null, but sometimes refers to the holder.
     * @return A cache key which contains information based on the context.
     */
    public abstract String getCacheKey(ItemStack stack, World world, EntityLivingBase entity);

    /**
     * Generates a new baked model from a cache key.
     *
     * @param key The cache key for the model. This is generated by {@link #getCacheKey(IBlockState, EnumFacing)} for block models and
     *            {@link #getCacheKey(ItemStack, World, EntityLivingBase)} for items.
     * @return The baked model for the key.
     */
    public abstract IBakedModel generateBlockModel(String key);

    @Override
    public @NotNull List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {

        return this.getModel(this.getCacheKey(state, side)).getQuads(state, side, rand);
    }

    public IBakedModel getModel(String key) {

        // Check if the cache already has this model.
        if (this.cache.containsKey(key)) {

            // Grab the model from the cache.
            final IBakedModel cachedModel = this.cache.get(key);

            // If model is not null, return it's quads and be done.
            if (cachedModel != null) {

                return cachedModel;
            }
        }

        // No cached copy exists, so make a new one.
        final IBakedModel newModel = this.generateBlockModel(key);

        // If the cache key is not null, cache the newly made model.
        if (key != null) {

            this.cache.put(key, newModel);
        }

        return newModel;
    }

    @Override
    public boolean isAmbientOcclusion() {

        return this.bakedOriginal.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {

        return this.bakedOriginal.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {

        return this.bakedOriginal.isBuiltInRenderer();
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleTexture() {

        return this.bakedOriginal.getParticleTexture();
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull ItemCameraTransforms getItemCameraTransforms() {

        return this.bakedOriginal.getItemCameraTransforms();
    }

    @Override
    public @NotNull Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.@NotNull TransformType transformType) {

        final Pair<? extends IBakedModel, Matrix4f> pair = this.bakedOriginal.handlePerspective(transformType);
        return Pair.of(this, pair.getRight());
    }

    @Override
    public @NotNull ItemOverrideList getOverrides() {

        return ItemOverrideListRetexturable.INSTANCE;
    }

    /**
     * This class handles getting an IBakedModel for an item context. It is {@link CachedDynamicBakedModel#getCacheKey(ItemStack, World, EntityLivingBase)} works.
     */
    private static class ItemOverrideListRetexturable extends ItemOverrideList {

        public static final ItemOverrideList INSTANCE = new ItemOverrideListRetexturable();

        private ItemOverrideListRetexturable() {

            super(ImmutableList.of());
        }

        @Override
        public @NotNull IBakedModel handleItemState(@NotNull IBakedModel originalModel, @NotNull ItemStack stack, World world,
                                                    EntityLivingBase entity) {

            if (originalModel instanceof CachedDynamicBakedModel rextexturable) {

                return rextexturable.getModel(rextexturable.getCacheKey(stack, world, entity));
            }

            return originalModel;
        }
    }
}
