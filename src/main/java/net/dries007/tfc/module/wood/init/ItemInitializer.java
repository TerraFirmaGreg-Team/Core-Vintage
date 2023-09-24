package net.dries007.tfc.module.wood.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.module.wood.api.variant.item.IWoodItem;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.wood.common.WoodStorage.WOOD_ITEMS;

public class ItemInitializer {

    public static void onRegister(Registry registry) {
        for (var wood : WOOD_ITEMS.values()) {
            registry.registerItem((Item) wood, wood.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
        registry.registerClientModelRegistrationStrategy(() -> {
            WOOD_ITEMS.values().forEach(IHasModel::onModelRegister);
        });
    }

    @SideOnly(Side.CLIENT)
    public static void onClientInitialization() {
        var minecraft = Minecraft.getMinecraft();
        var itemColors = minecraft.getItemColors();


        itemColors.registerItemColorHandler((s, i) -> ((IWoodItem) s.getItem()).getType().getColor(),
                WOOD_ITEMS.values()
                        .stream()
                        .map(s -> (Item) s)
                        .toArray(Item[]::new));
    }
}
