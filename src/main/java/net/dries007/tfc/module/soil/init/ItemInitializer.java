package net.dries007.tfc.module.soil.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.module.soil.api.variant.item.SoilItemVariantHandler;
import net.dries007.tfc.module.wood.api.variant.item.IWoodItem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.core.common.objects.blocks.TFCBlocks.PEAT_GRASS;
import static net.dries007.tfc.module.soil.common.SoilStorage.SOIL_BLOCKS;
import static net.dries007.tfc.module.soil.common.SoilStorage.SOIL_ITEMS;
import static net.dries007.tfc.module.wood.common.WoodStorage.WOOD_ITEMS;

public class ItemInitializer {

    public static void onRegister(Registry registry) {
        for (var wood : SOIL_ITEMS.values()) {
            registry.registerItem((Item) wood, wood.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
        registry.registerClientModelRegistrationStrategy(() -> {
            SOIL_ITEMS.values().forEach(IHasModel::onModelRegister);
        });
    }

    @SideOnly(Side.CLIENT)
    public static void onClientInitialization() {
        var minecraft = Minecraft.getMinecraft();
        var itemColors = minecraft.getItemColors();





    }
}
