package net.dries007.tfc.module.soil.init;

import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.module.soil.StorageSoil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.util.registry.Registry;

public class ItemsSoil {

    public static void onRegister(Registry registry) {
        for (var item : StorageSoil.SOIL_ITEMS.values()) {
            registry.registerItem((Item) item, item.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
        registry.registerClientModelRegistrationStrategy(() -> {
            StorageSoil.SOIL_ITEMS.values().forEach(IHasModel::onModelRegister);
        });
    }

    @SideOnly(Side.CLIENT)
    public static void onClientInitialization() {
        var minecraft = Minecraft.getMinecraft();
        var itemColors = minecraft.getItemColors();


    }
}
