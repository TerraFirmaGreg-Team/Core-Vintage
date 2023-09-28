package net.dries007.tfc.api.util;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created to solve the 'dangerous alternative prefix' warning.
 */
public final class RegistryHelper {

    private RegistryHelper() {
        //
    }

    @SafeVarargs
    public static void registerTileEntities(Registry registry, Class<? extends TileEntity>... tileEntityClasses) {

        for (Class<? extends TileEntity> tileEntityClass : tileEntityClasses) {
            RegistryHelper.registerTileEntity(registry, tileEntityClass);
        }
    }

    public static void registerTileEntity(Registry registry, Class<? extends TileEntity> tileEntityClass) {

        registry.registerTileEntityRegistrationStrategy(() ->
                GameRegistry.registerTileEntity(tileEntityClass,
                        new ResourceLocation(registry.getModId(), "tile." + tileEntityClass.getSimpleName())
                ));
    }
}
