package net.dries007.tfc.util;

import com.google.common.base.CaseFormat;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.dries007.tfc.common.objects.Powder;
import net.dries007.tfc.common.objects.items.ItemPowder;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

public class OreDictionaryHelper {

    private static final Multimap<Block, String> MAP_BLOCK_ORE = HashMultimap.create();
    private static final Multimap<Item, String> MAP_ITEM_ORE = HashMultimap.create();
    private static final Multimap<ItemStack, String> MAP_ITEMSTACK_ORE = HashMultimap.create();

    /**
     * Вызови это в событии регистрации предметов, один раз, в самом конце!
     */
    public static void init() {
        MAP_BLOCK_ORE.forEach((block, oreDict) -> OreDictionary.registerOre(oreDict, block));
        MAP_ITEM_ORE.forEach((item, oreDict) -> OreDictionary.registerOre(oreDict, item));
        MAP_ITEMSTACK_ORE.forEach((item, oreDict) -> OreDictionary.registerOre(oreDict, item));

        MAP_BLOCK_ORE.clear();
        MAP_ITEM_ORE.clear();
        MAP_ITEMSTACK_ORE.clear();

        // Vanilla ore dict values
        OreDictionary.registerOre("clay", Items.CLAY_BALL);
        OreDictionary.registerOre("gemCoal", new ItemStack(Items.COAL, 1, 0));
        OreDictionary.registerOre("charcoal", new ItemStack(Items.COAL, 1, 1));
        OreDictionary.registerOre("fireStarter", new ItemStack(Items.FLINT_AND_STEEL, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("fireStarter", new ItemStack(Items.FIRE_CHARGE));
        OreDictionary.registerOre("bowl", Items.BOWL);
        OreDictionary.registerOre("blockClay", Blocks.CLAY);

        //adding oredict to dyeables for dye support. Instead of adding specific recipes color can be changed universally.
        OreDictionary.registerOre("bed", new ItemStack(Items.BED, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("carpet", new ItemStack(Blocks.CARPET, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("powderConcrete", new ItemStack(Blocks.CONCRETE_POWDER, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("terracotta", new ItemStack(Blocks.HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("terracotta", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE));

        //oredict support for TFC dyes
        OreDictionary.registerOre("dyePink", new ItemStack(ItemPowder.get(Powder.KAOLINITE)));
        OreDictionary.registerOre("dyeGray", new ItemStack(ItemPowder.get(Powder.GRAPHITE)));
        OreDictionary.registerOre("dyeRed", new ItemStack(ItemPowder.get(Powder.HEMATITE)));
        OreDictionary.registerOre("dyeBlue", new ItemStack(ItemPowder.get(Powder.LAPIS_LAZULI)));
        OreDictionary.registerOre("dyeYellow", new ItemStack(ItemPowder.get(Powder.LIMONITE)));
        OreDictionary.registerOre("dyeGreen", new ItemStack(ItemPowder.get(Powder.MALACHITE)));
        OreDictionary.registerOre("dyeBrown", new ItemStack(ItemPowder.get(Powder.FERTILIZER)));
        OreDictionary.registerOre("dyeBlack", new ItemStack(ItemPowder.get(Powder.CHARCOAL)));
        OreDictionary.registerOre("dyeBlack", new ItemStack(ItemPowder.get(Powder.COKE)));

        // Register a name without any items
        OreDictionary.getOres("infiniteFire", true);
    }

    /**
     * Вызови это если хочешь зарегистрировать блоку его oreDict.
     */
    public static void register(Block block, String... parts) {
        var oreDict = upperCaseToCamelCase(parts);
        MAP_BLOCK_ORE.put(block, oreDict);
    }

    /**
     * Вызови это если хочешь зарегистрировать предмету его oreDict,
     * используется для регистрации предметов с meta, где при ее изменении oreDict пропадает.
     */
    public static void register(ItemStack itemStack, String... parts) {
        var oreDict = upperCaseToCamelCase(parts);
        MAP_ITEMSTACK_ORE.put(itemStack, oreDict);
    }

    /**
     * Вызови это если хочешь зарегистрировать предмету его oreDict.
     */
    public static void register(Item item, String... parts) {
        var oreDict = upperCaseToCamelCase(parts);
        MAP_ITEM_ORE.put(item, oreDict);
    }

    /**
     * Конвертирует массив из строк в oreDict, передавай в массив строки содержащие только буквы,
     * регистр значения не имеет, каждый элемент массива будет отформатирован в соответствие с camelCase.
     */
    public static String upperCaseToCamelCase(String... strings) {
        if (strings.length > 1)
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, String.join("_", strings).replace("/", "_").toUpperCase());

        return strings[0];
    }

    /**
     * Checks if an ItemStack has an OreDictionary entry that matches 'name'.
     */
    public static boolean doesStackMatchOre(@Nonnull ItemStack stack, String name) {
        if (!OreDictionary.doesOreNameExist(name)) {
            // TerraFirmaCraft.getLog().warn("doesStackMatchOre called with non-existing name. stack: {} name: {}", stack, name);
            return false;
        }
        if (stack.isEmpty()) return false;
        int needle = OreDictionary.getOreID(name);
        for (int id : OreDictionary.getOreIDs(stack)) {
            if (id == needle) return true;
        }
        return false;
    }
}
