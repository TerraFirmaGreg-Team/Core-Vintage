package net.dries007.tfc.module.plant.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.module.core.api.util.IHasModel;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.plant.StoragePlant.PLANT_ITEMS;

public class ItemsPlant {

    public static void onRegister(Registry registry) {
        for (var item : PLANT_ITEMS.values()) {
            registry.registerItem((Item) item, item.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
        registry.registerClientModelRegistrationStrategy(() -> {
            PLANT_ITEMS.values().forEach(IHasModel::onModelRegister);
        });
    }

    @SideOnly(Side.CLIENT)
    public static void onClientInitialization() {
        var minecraft = Minecraft.getMinecraft();
        var itemColors = minecraft.getItemColors();
    }
}
