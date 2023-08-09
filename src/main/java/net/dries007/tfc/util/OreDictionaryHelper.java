package net.dries007.tfc.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.items.ItemPowder;
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

    /**
     * Вызови это после инициализации всех остальных модулей!
     */
    public static void init() {
        MAP_BLOCK_ORE.forEach((block, oreDict) -> OreDictionary.registerOre(oreDict, block));
        MAP_ITEM_ORE.forEach((item, oreDict) -> OreDictionary.registerOre(oreDict, item));

        MAP_BLOCK_ORE.clear();
        MAP_ITEM_ORE.clear();
    }

    /**
     * Вызови это если хочешь зарегистрировать блоку его oreDict.
     */
    public static void register(Block block, String... parts) {
        for (String partBlock : parts) {
            MAP_BLOCK_ORE.put(block, partBlock);
        }
    }

    /**
     * Вызови это если хочешь зарегистрировать предмету его oreDict.
     */
    public static void register(Item item, String... parts) {
        for (String partItem : parts) {
            MAP_ITEM_ORE.put(item, partItem);
        }
    }

    public static void init_old() {

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

    private static final Converter<String, String> UPPER_UNDERSCORE_TO_LOWER_CAMEL = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
    private static final Joiner JOINER_UNDERSCORE = Joiner.on('_').skipNulls();

    public static String toString(Object... parts) {
        return UPPER_UNDERSCORE_TO_LOWER_CAMEL.convert(JOINER_UNDERSCORE.join(parts));
    }

    public static String toString(Iterable<Object> parts) {
        return UPPER_UNDERSCORE_TO_LOWER_CAMEL.convert(JOINER_UNDERSCORE.join(parts));
    }

    public static String toString(Object[] prefix, Object... parts) {
        return toString(ImmutableList.builder().add(prefix).add(parts).build());
    }

    /**
     * Checks if an ItemStack has an OreDictionary entry that matches 'name'.
     */
    public static boolean doesStackMatchOre(@Nonnull ItemStack stack, String name) {
        if (!OreDictionary.doesOreNameExist(name)) {
            TerraFirmaCraft.getLog().warn("doesStackMatchOre called with non-existing name. stack: {} name: {}", stack, name);
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
