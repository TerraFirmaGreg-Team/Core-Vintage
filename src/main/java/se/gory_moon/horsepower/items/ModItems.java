package se.gory_moon.horsepower.items;

import su.terrafirmagreg.api.lib.Constants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;


import com.google.common.collect.Maps;
import se.gory_moon.horsepower.Configs;
import se.gory_moon.horsepower.HorsePowerMod;
import se.gory_moon.horsepower.blocks.ModBlocks;
import se.gory_moon.horsepower.recipes.ShapedChoppingRecipe;
import se.gory_moon.horsepower.recipes.ShapelessChoppingRecipe;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@GameRegistry.ObjectHolder(Constants.MODID_HORSEPOWER)
@Mod.EventBusSubscriber(modid = Constants.MODID_HORSEPOWER)
public class ModItems {

    public static final Item FLOUR = new Item().setRegistryName(se.gory_moon.horsepower.lib.Constants.FLOUR_ITEM)
            .setTranslationKey(se.gory_moon.horsepower.lib.Constants.FLOUR_ITEM)
            .setCreativeTab(HorsePowerMod.creativeTab);
    public static final Item DOUGH = new Item().setRegistryName(se.gory_moon.horsepower.lib.Constants.DOUGH_ITEM)
            .setTranslationKey(se.gory_moon.horsepower.lib.Constants.DOUGH_ITEM)
            .setCreativeTab(HorsePowerMod.creativeTab);
    private static final List<ResourceLocation> recipesToRemove = new LinkedList<>();
    private static Set<Map.Entry<ResourceLocation, IRecipe>> recipes;

    public static void registerRecipes() {
        if (Configs.general.enableDough) {
            GameRegistry.addSmelting(DOUGH, new ItemStack(Items.BREAD), 0F);
            OreDictionary.registerOre("foodDough", DOUGH);
        }
        if (Configs.general.enableFlour)
            OreDictionary.registerOre("foodFlour", FLOUR);
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> ev) throws NoSuchFieldException, IllegalAccessException {
        recipes = ev.getRegistry().getEntries();
        ResourceLocation loc = new ResourceLocation("horsepower:chopper");
        ev.getRegistry()
                .register(new ShapedChoppingRecipe(loc, OreDictionary.getOres("logWood"), new ItemStack(ModBlocks.BLOCK_CHOPPER), "LSL", "SFS", "SWS",
                        'S', "stickWood", 'L', Ingredient.fromStacks(new ItemStack(Items.LEAD)), 'F', "saw", 'W', "logWood").setRegistryName(loc));
        if (Configs.general.enableHandChoppingBlock) {
            loc = new ResourceLocation("horsepower:manual_chopping");
            ev.getRegistry()
                    .register(new ShapelessChoppingRecipe(loc, OreDictionary.getOres("logWood"), new ItemStack(ModBlocks.BLOCK_MANUAL_CHOPPER, 2),
                            "logWood", "saw").setRegistryName(loc));
        }

        if (Configs.general.removeVanillaRecipes)
            removeRecipes();
    }

    public static ResourceLocation findMatchingRecipe(InventoryCrafting craftMatrix) {
        for (Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {
            if (recipe.getValue().matches(craftMatrix, null)) {
                return recipe.getKey();
            }
        }

        return null;
    }

    private static void removeRecipes() {
        Container dummyContainer = new Container() {

            @Override
            public void onCraftMatrixChanged(IInventory par1IInventory) {
            }

            @Override
            public boolean canInteractWith(EntityPlayer entityplayer) {
                return true;
            }
        };
        InventoryCrafting crafting = new InventoryCrafting(dummyContainer, 3, 3);

        removeRecipe(findMatchingRecipe(setRecipe(crafting, "###", '#', Items.WHEAT)));
        removeRecipe(findMatchingRecipe(setRecipe(crafting, "#", '#', Items.REEDS)));
        removeRecipe(findMatchingRecipe(setRecipe(crafting, "#", '#', new ItemStack(Blocks.BONE_BLOCK))));
        removeRecipe(findMatchingRecipe(setRecipe(crafting, "#", '#', Items.BONE)));
        removeRecipe(findMatchingRecipe(setRecipe(crafting, "#", '#', Items.BEETROOT)));
        removeRecipe(findMatchingRecipe(
                setRecipe(crafting, "#", '#', new ItemStack(Blocks.YELLOW_FLOWER, 1, BlockFlower.EnumFlowerType.DANDELION.getMeta()))));
        removeRecipe(
                findMatchingRecipe(setRecipe(crafting, "#", '#', new ItemStack(Blocks.RED_FLOWER, 1, BlockFlower.EnumFlowerType.POPPY.getMeta()))));
        removeRecipe(findMatchingRecipe(
                setRecipe(crafting, "#", '#', new ItemStack(Blocks.RED_FLOWER, 1, BlockFlower.EnumFlowerType.BLUE_ORCHID.getMeta()))));
        removeRecipe(
                findMatchingRecipe(setRecipe(crafting, "#", '#', new ItemStack(Blocks.RED_FLOWER, 1, BlockFlower.EnumFlowerType.ALLIUM.getMeta()))));
        removeRecipe(findMatchingRecipe(
                setRecipe(crafting, "#", '#', new ItemStack(Blocks.RED_FLOWER, 1, BlockFlower.EnumFlowerType.HOUSTONIA.getMeta()))));
        removeRecipe(findMatchingRecipe(
                setRecipe(crafting, "#", '#', new ItemStack(Blocks.RED_FLOWER, 1, BlockFlower.EnumFlowerType.RED_TULIP.getMeta()))));
        removeRecipe(findMatchingRecipe(
                setRecipe(crafting, "#", '#', new ItemStack(Blocks.RED_FLOWER, 1, BlockFlower.EnumFlowerType.ORANGE_TULIP.getMeta()))));
        removeRecipe(findMatchingRecipe(
                setRecipe(crafting, "#", '#', new ItemStack(Blocks.RED_FLOWER, 1, BlockFlower.EnumFlowerType.WHITE_TULIP.getMeta()))));
        removeRecipe(findMatchingRecipe(
                setRecipe(crafting, "#", '#', new ItemStack(Blocks.RED_FLOWER, 1, BlockFlower.EnumFlowerType.PINK_TULIP.getMeta()))));
        removeRecipe(findMatchingRecipe(
                setRecipe(crafting, "#", '#', new ItemStack(Blocks.RED_FLOWER, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta()))));
        removeRecipe(findMatchingRecipe(
                setRecipe(crafting, "#", '#', new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockDoublePlant.EnumPlantType.SUNFLOWER.getMeta()))));
        removeRecipe(findMatchingRecipe(
                setRecipe(crafting, "#", '#', new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockDoublePlant.EnumPlantType.SYRINGA.getMeta()))));
        removeRecipe(findMatchingRecipe(
                setRecipe(crafting, "#", '#', new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockDoublePlant.EnumPlantType.ROSE.getMeta()))));
        removeRecipe(findMatchingRecipe(
                setRecipe(crafting, "#", '#', new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockDoublePlant.EnumPlantType.PAEONIA.getMeta()))));

        recipesToRemove.forEach(recipe -> RegistryManager.ACTIVE.getRegistry(GameData.RECIPES).remove(recipe));
    }

    private static InventoryCrafting setRecipe(InventoryCrafting crafting, Object... recipeComponents) {
        crafting.clear();
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (recipeComponents[i] instanceof String[]) {
            String[] astring = (String[]) recipeComponents[i++];

            for (String s2 : astring) {
                ++k;
                j = s2.length();
                s = s + s2;
            }
        } else {
            while (recipeComponents[i] instanceof String) {
                String s1 = (String) recipeComponents[i++];
                ++k;
                j = s1.length();
                s = s + s1;
            }
        }

        Map<Character, ItemStack> map;

        for (map = Maps.newHashMap(); i < recipeComponents.length; i += 2) {
            Character character = (Character) recipeComponents[i];
            ItemStack itemstack = ItemStack.EMPTY;

            if (recipeComponents[i + 1] instanceof Item) {
                itemstack = new ItemStack((Item) recipeComponents[i + 1]);
            } else if (recipeComponents[i + 1] instanceof Block) {
                itemstack = new ItemStack((Block) recipeComponents[i + 1], 1, 32767);
            } else if (recipeComponents[i + 1] instanceof ItemStack) {
                itemstack = (ItemStack) recipeComponents[i + 1];
            }

            map.put(character, itemstack);
        }

        for (int l = 0; l < j * k; ++l) {
            char c0 = s.charAt(l);

            if (map.containsKey(Character.valueOf(c0))) {
                crafting.setInventorySlotContents(l, map.get(Character.valueOf(c0)).copy());
            }
        }

        return crafting;
    }

    private static void removeRecipesWithResult(ItemStack resultItem) {

        for (Map.Entry<ResourceLocation, IRecipe> recipe : recipes) {
            ItemStack recipeResult = recipe.getValue().getRecipeOutput();
            if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
                recipesToRemove.add(recipe.getKey());
            }
        }
    }

    private static void removeRecipe(ResourceLocation recipe) {
        if (recipe != null)
            recipesToRemove.add(recipe);
    }

    @Mod.EventBusSubscriber(modid = Constants.MODID_HORSEPOWER)
    public static class RegistrationHandler {

        public static final Set<Item> ITEMS = new HashSet<>();

        /**
         * Register this mod's {@link Item}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {

            final Item[] items = { FLOUR, DOUGH };

            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final Item item : items) {
                if (!Configs.general.enableFlour && item == FLOUR)
                    continue;
                if (!Configs.general.enableDough && item == DOUGH)
                    continue;

                registry.register(item);
                ITEMS.add(item);
            }
        }
    }
}
