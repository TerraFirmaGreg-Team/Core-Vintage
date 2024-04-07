package su.terrafirmagreg.api.util;

import su.terrafirmagreg.api.model.CustomModelLoader;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.base.Preconditions;

import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector4f;
import java.util.Optional;

@SuppressWarnings("unused")
public final class ModelUtils {

    // ===== StateMapper ===========================================================================================================================//

    /**
     * A {@link StateMapperBase} used to create property strings.
     */
    public static StateMapperBase PROPERTY_STRING_MAPPER = new StateMapperBase() {

        @Override
        protected @NotNull ModelResourceLocation getModelResourceLocation(@NotNull IBlockState state) {
            return new ModelResourceLocation("minecraft:air");
        }
    };

    @SideOnly(Side.CLIENT)
    public static void registerStateMapper(@NotNull Block block, IStateMapper stateMap) {
        ModelLoader.setCustomStateMapper(block, stateMap);
    }

    // ===== ItemBlock =============================================================================================================================//

    public static void registerBlockInventoryModes(String subfolder, Block... blocks) {
        for (Block block : blocks) ModelUtils.registerBlockInventoryModel(subfolder, block);
    }

    public static void registerBlockInventoryModel(String subfolder, Block block) {
        ResourceLocation registryName = block.getRegistryName();
        Preconditions.checkNotNull(registryName, "Item %s has null registry name", block);
        String modelLocation = registryName.getNamespace() + ":" + subfolder + "/" + registryName.getPath();

        ModelUtils.registerBlockInventoryModel(block, modelLocation);
    }

    public static void registerBlockInventoryModes(Block... blocks) {
        for (Block block : blocks) ModelUtils.registerBlockInventoryModel(block);
    }

    public static void registerBlockInventoryModel(Block block) {
        ResourceLocation registryName = block.getRegistryName();
        Preconditions.checkNotNull(registryName, "block %s has null registry name", block);
        ModelUtils.registerInventoryModel(Item.getItemFromBlock(block), registryName);
    }

    public static void registerBlockInventoryModel(Block block, String modelLocation) {
        ModelUtils.registerInventoryModel(Item.getItemFromBlock(block), modelLocation);

    }

    public static void registerBlockInventoryModel(Block block, ResourceLocation modelLocation) {
        ModelUtils.registerInventoryModel(Item.getItemFromBlock(block), modelLocation);

    }

    // ===== Item ================================================================================================================================//

    public static void registerInventoryModel(String subfolder, Item... items) {
        for (Item item : items) ModelUtils.registerInventoryModel(subfolder, item);
    }

    public static void registerInventoryModel(String subfolder, Item item) {
        ResourceLocation registryName = item.getRegistryName();
        Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);
        String modelLocation = registryName.getNamespace() + ":" + subfolder + "/" + registryName.getPath();

        ModelUtils.registerInventoryModel(item, modelLocation);
    }

    public static void registerInventoryModel(Item... items) {
        for (Item item : items) registerInventoryModel(item);
    }

    public static void registerInventoryModel(Item item) {
        ResourceLocation registryName = item.getRegistryName();
        Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);

        ModelUtils.registerInventoryModel(item, registryName);
    }

    public static void registerInventoryModel(Item item, String modelLocation) {
        ModelUtils.registerInventoryModel(item, new ModelResourceLocation(modelLocation, "inventory"));
    }

    public static void registerInventoryModel(Item item, ResourceLocation modelLocation) {
        ModelUtils.registerInventoryModel(item, new ModelResourceLocation(modelLocation, "inventory"));
    }

    public static void registerInventoryModel(Item item, ModelResourceLocation resourceLocation) {
        ModelUtils.registerInventoryModel(item, 0, resourceLocation);
    }

    public static void registerInventoryModel(@NotNull Item item, int metadata, @NotNull String variant) {
        ResourceLocation registryName = item.getRegistryName();
        Preconditions.checkNotNull(registryName, "Item %s has null registry name", item);

        ModelUtils.registerInventoryModel(item, metadata, new ModelResourceLocation(registryName, variant));
    }

    public static void registerInventoryModel(@NotNull Item item, int meta, @NotNull ModelResourceLocation resourceLocation) {
        ModelLoader.setCustomModelResourceLocation(item, meta, resourceLocation);
    }

    public static void registerCustomMeshDefinition(Item item, ItemMeshDefinition meshDefinition) {
        ModelLoader.setCustomMeshDefinition(item, meshDefinition);
    }

    public static void registerBackedModel(ResourceLocation resourceLocation, @NotNull IModel model) {
        CustomModelLoader.registerModel(resourceLocation, model);
    }

    // ===== Backed ================================================================================================================================//

    public static void putVertex(UnpackedBakedQuad.Builder builder, VertexFormat format, Optional<TRSRTransformation> transform, EnumFacing side,
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
                    builder.put(e, (float) side.getXOffset(), (float) side.getYOffset(), (float) side.getZOffset(), 0f);
                    break;
                default:
                    builder.put(e);
                    break;
            }
        }
    }

}
