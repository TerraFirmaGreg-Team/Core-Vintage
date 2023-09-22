package net.dries007.tfc.module.rock.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.module.rock.api.variant.item.RockItemVariantHandler;
import net.dries007.tfc.module.soil.api.variant.item.SoilItemVariantHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.rock.common.RockStorage.ROCK_ITEMS;
import static net.dries007.tfc.module.soil.common.SoilStorage.SOIL_ITEMS;

public class ItemInitializer {

    public static void onRegister(Registry registry) {

        for (var item : ROCK_ITEMS.values()) {
            registry.registerItem((Item) item, item.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {
            ROCK_ITEMS.values().forEach(IHasModel::onModelRegister);
        });
    }
}
