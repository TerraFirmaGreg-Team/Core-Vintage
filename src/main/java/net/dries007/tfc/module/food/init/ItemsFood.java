package net.dries007.tfc.module.food.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.config.ConfigTFC;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.food.StorageFood.FOOD_ITEMS;

public class ItemsFood {


    public static void onRegister(Registry registry) {
        for (var item : FOOD_ITEMS.values()) {
            registry.registerItem((Item) item, item.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
        registry.registerClientModelRegistrationStrategy(() -> {
            FOOD_ITEMS.values().forEach(IHasModel::onModelRegister);
        });
    }

    @SideOnly(Side.CLIENT)
    public static void onClientInitialization() {
        var minecraft = Minecraft.getMinecraft();
        var itemColors = minecraft.getItemColors();


        itemColors.registerItemColorHandler((s, i) -> {
            var food = s.getCapability(CapabilityFood.CAPABILITY, null);
            if (food != null) {
                return food.isRotten() ? ConfigTFC.Client.DISPLAY.rottenFoodOverlayColor : 0xFFFFFF;
            }
            return 0xFFFFFF;
        }, ForgeRegistries.ITEMS.getValuesCollection().stream().filter(x -> x instanceof ItemFood).toArray(Item[]::new));
    }
}
