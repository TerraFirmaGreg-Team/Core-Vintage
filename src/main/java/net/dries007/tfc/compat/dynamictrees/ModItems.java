package net.dries007.tfc.compat.dynamictrees;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class ModItems {

    public static void preInit() {}

    public static void register(IForgeRegistry<Item> registry) {
        ArrayList<Item> treeItems = new ArrayList<>();
        ModTrees.tfcTrees.forEach(tree -> tree.getRegisterableItems(treeItems));
        registry.registerAll(treeItems.toArray(new Item[treeItems.size()]));
    }

}
