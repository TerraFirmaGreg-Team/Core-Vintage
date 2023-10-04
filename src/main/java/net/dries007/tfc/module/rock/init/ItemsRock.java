package net.dries007.tfc.module.rock.init;

import su.terrafirmagreg.util.registry.Registry;
import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.module.rock.StorageRock;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemsRock {

    public static void onRegister(Registry registry) {
        for (var item : StorageRock.ROCK_ITEMS.values()) {
            registry.registerItem((Item) item, item.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {
            StorageRock.ROCK_ITEMS.values().forEach(IHasModel::onModelRegister);
        });
    }
}
