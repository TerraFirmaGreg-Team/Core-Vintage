package net.dries007.tfc.module.wood.init;

import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.module.wood.api.types.variant.item.IWoodItem;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.util.registry.Registry;

import static net.dries007.tfc.module.wood.StorageWood.TREE_ITEMS;
import static net.dries007.tfc.module.wood.StorageWood.WOOD_ITEMS;

public class ItemsWood {

    public static void onRegister(Registry registry) {
        for (var item : WOOD_ITEMS.values()) registry.registerItem((Item) item, item.getName());
        for (var item : TREE_ITEMS) registry.registerItem(item);
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
