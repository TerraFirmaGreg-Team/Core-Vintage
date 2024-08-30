package su.terrafirmagreg.api.registry.provider;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.Nullable;

// Configure automatic model registration
public interface IProviderModel {

    @SideOnly(Side.CLIENT)
    default void onModelRegister() {

    }

    default @Nullable ResourceLocation getResourceLocation() {
        return null;
    }
}
