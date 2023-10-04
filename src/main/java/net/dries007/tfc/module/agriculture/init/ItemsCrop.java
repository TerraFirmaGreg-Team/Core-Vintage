package net.dries007.tfc.module.agriculture.init;

import su.terrafirmagreg.util.registry.Registry;
import net.dries007.tfc.module.core.api.util.IHasModel;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.agriculture.StorageAgriculture.CROP_ITEMS;

public class ItemsCrop {

    public static void onRegister(Registry registry) {

        for (var item : CROP_ITEMS.values()) {
            registry.registerItem((Item) item, item.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {
            CROP_ITEMS.values().forEach(IHasModel::onModelRegister);
        });
    }
}
