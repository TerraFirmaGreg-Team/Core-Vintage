package net.dries007.tfc.compat.dynamictrees;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

import static net.dries007.tfc.common.objects.blocks.TFCBlocks.TREES;

public class ModItems {

    public static void preInit() {}

    public static void register(IForgeRegistry<Item> registry) {
        ArrayList<Item> treeItems = new ArrayList<>();
        TREES.forEach(tree -> tree.getRegisterableItems(treeItems));
        registry.registerAll(treeItems.toArray(new Item[treeItems.size()]));
    }

}
