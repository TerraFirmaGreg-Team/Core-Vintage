package net.dries007.tfc.module.food.init;

import net.dries007.tfc.module.core.api.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.util.registry.Registry;

import static net.dries007.tfc.module.food.StorageFood.FOOD_BLOCKS;

public class BlocksFood {

    public static void onRegister(Registry registry) {
        for (var block : FOOD_BLOCKS.values()) {
            var itemBlock = block.getItemBlock();
            if (itemBlock != null) registry.registerBlock((Block) block, block.getItemBlock(), block.getName());
            else registry.registerBlock((Block) block, block.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
        registry.registerClientModelRegistrationStrategy(() -> {
            FOOD_BLOCKS.values().forEach(IHasModel::onModelRegister);
        });

    }

    @SideOnly(Side.CLIENT)
    public static void onClientInitialization() {
        var itemColors = Minecraft.getMinecraft().getItemColors();
        var blockColors = Minecraft.getMinecraft().getBlockColors();

    }
}
