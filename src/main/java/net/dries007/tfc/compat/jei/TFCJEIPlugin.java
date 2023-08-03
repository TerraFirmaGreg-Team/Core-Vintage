package net.dries007.tfc.compat.jei;

import gregtech.api.fluids.MetaFluids;
import gregtech.api.unification.material.Materials;
import gregtech.common.items.ToolItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeFoodPreservation;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeFoodTraits;
import net.dries007.tfc.api.recipes.heat.HeatRecipeMetalMelting;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.client.gui.*;
import net.dries007.tfc.compat.jei.categories.*;
import net.dries007.tfc.compat.jei.util.TFCInventoryGuiHandler;
import net.dries007.tfc.compat.jei.wrappers.*;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLoom;
import net.dries007.tfc.objects.container.ContainerInventoryCrafting;
import net.dries007.tfc.objects.items.ItemAnimalHide;
import net.dries007.tfc.objects.items.ItemAnimalHide.HideType;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.items.metal.ItemAnvil;
import net.dries007.tfc.objects.recipes.SaltingRecipe;
import net.dries007.tfc.objects.recipes.UnmoldRecipe;
import net.dries007.tfc.test.blocks.TFCBlocks;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@JEIPlugin
public final class TFCJEIPlugin implements IModPlugin {
    public static final String ALLOY_UID = TerraFirmaCraft.MOD_ID + ".alloy";
    public static final String ANVIL_UID = TerraFirmaCraft.MOD_ID + ".anvil";
    public static final String BARREL_UID = TerraFirmaCraft.MOD_ID + ".barrel";
    public static final String BLAST_FURNACE_UID = TerraFirmaCraft.MOD_ID + ".blast_furnace";
    public static final String CASTING_UID = TerraFirmaCraft.MOD_ID + ".casting";
    public static final String BLOOMERY_UID = TerraFirmaCraft.MOD_ID + ".bloomery";
    public static final String CHISEL_UID = TerraFirmaCraft.MOD_ID + ".chisel";
    public static final String HEAT_UID = TerraFirmaCraft.MOD_ID + ".heat";
    public static final String KNAP_CLAY_UID = TerraFirmaCraft.MOD_ID + ".knap.clay";
    public static final String KNAP_FIRECLAY_UID = TerraFirmaCraft.MOD_ID + ".knap.fireclay";
    public static final String KNAP_LEATHER_UID = TerraFirmaCraft.MOD_ID + ".knap.leather";
    public static final String KNAP_STONE_UID = TerraFirmaCraft.MOD_ID + ".knap.stone";
    public static final String METAL_HEAT_UID = TerraFirmaCraft.MOD_ID + ".metal_heat";
    public static final String LOOM_UID = TerraFirmaCraft.MOD_ID + ".loom";
    public static final String QUERN_UID = TerraFirmaCraft.MOD_ID + ".quern";
    public static final String ROCK_LAYER_UID = TerraFirmaCraft.MOD_ID + ".rock_layer";
    public static final String WELDING_UID = TerraFirmaCraft.MOD_ID + ".welding";
    public static final String SCRAPING_UID = TerraFirmaCraft.MOD_ID + ".scraping";
    public static final String UNMOLD_UID = TerraFirmaCraft.MOD_ID + ".unmold";

    private static IModRegistry REGISTRY;

    /**
     * Helper method to return a collection containing all possible itemstacks registered in JEI
     *
     * @return Collection of ItemStacks
     */
    public static Collection<ItemStack> getAllIngredients() {
        return REGISTRY.getIngredientRegistry().getAllIngredients(VanillaTypes.ITEM);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        // Add new JEI recipe categories
        registry.addRecipeCategories(new AlloyCategory(registry.getJeiHelpers().getGuiHelper(), ALLOY_UID));
        registry.addRecipeCategories(new AnvilCategory(registry.getJeiHelpers().getGuiHelper(), ANVIL_UID));
        registry.addRecipeCategories(new BarrelCategory(registry.getJeiHelpers().getGuiHelper(), BARREL_UID));
        registry.addRecipeCategories(new BlastFurnaceCategory(registry.getJeiHelpers().getGuiHelper(), BLAST_FURNACE_UID));
        registry.addRecipeCategories(new BloomeryCategory(registry.getJeiHelpers().getGuiHelper(), BLOOMERY_UID));
        registry.addRecipeCategories(new CastingCategory(registry.getJeiHelpers().getGuiHelper(), CASTING_UID));
        registry.addRecipeCategories(new ChiselCategory(registry.getJeiHelpers().getGuiHelper(), CHISEL_UID));
        registry.addRecipeCategories(new HeatCategory(registry.getJeiHelpers().getGuiHelper(), HEAT_UID));
        registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers().getGuiHelper(), KNAP_CLAY_UID));
        registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers().getGuiHelper(), KNAP_FIRECLAY_UID));
        registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers().getGuiHelper(), KNAP_LEATHER_UID));
        registry.addRecipeCategories(new KnappingCategory(registry.getJeiHelpers().getGuiHelper(), KNAP_STONE_UID));
        registry.addRecipeCategories(new LoomCategory(registry.getJeiHelpers().getGuiHelper(), LOOM_UID));
        registry.addRecipeCategories(new MetalHeatingCategory(registry.getJeiHelpers().getGuiHelper(), METAL_HEAT_UID));
        registry.addRecipeCategories(new QuernCategory(registry.getJeiHelpers().getGuiHelper(), QUERN_UID));
        // registry.addRecipeCategories(new RockLayerCategory(registry.getJeiHelpers().getGuiHelper(), ROCK_LAYER_UID));
        registry.addRecipeCategories(new WeldingCategory(registry.getJeiHelpers().getGuiHelper(), WELDING_UID));
        registry.addRecipeCategories(new ScrapingCategory(registry.getJeiHelpers().getGuiHelper(), SCRAPING_UID));
        registry.addRecipeCategories(new UnmoldCategory(registry.getJeiHelpers().getGuiHelper(), UNMOLD_UID));
    }

    @Override
    public void register(IModRegistry registry) {
        REGISTRY = registry;

        // Alloy Recipes
        registry.addRecipeCatalyst(new ItemStack(TFCBlocks.CRUCIBLE), ALLOY_UID);
        registry.addRecipeCatalyst(new ItemStack(ItemsTFC.FIRED_VESSEL), ALLOY_UID);

        var alloyRecipes = TFCRegistries.ALLOYS.getValuesCollection().stream()
                .map(AlloyRecipeWrapper::new)
                .collect(Collectors.toList());

        registry.addRecipes(alloyRecipes, ALLOY_UID);

        // Quern Recipes
        registry.addRecipeCatalyst(new ItemStack(TFCBlocks.QUERN), QUERN_UID);
        registry.addRecipeCatalyst(new ItemStack(ItemsTFC.HANDSTONE), QUERN_UID);

        var quernList = TFCRegistries.QUERN.getValuesCollection()
                .stream()
                .map(SimpleRecipeWrapper::new)
                .collect(Collectors.toList());

        registry.addRecipes(quernList, QUERN_UID);

        // FirePit
        registry.addRecipeCatalyst(new ItemStack(TFCBlocks.FIREPIT), HEAT_UID);
        registry.addRecipeCatalyst(new ItemStack(TFCBlocks.CHARCOAL_FORGE), HEAT_UID);

        var heatList = TFCRegistries.HEAT.getValuesCollection()
                .stream()
                .filter(r -> r.getOutputs().size() > 0 && r.getIngredients().size() > 0)
                .map(HeatRecipeWrapper::new)
                .collect(Collectors.toList());

        registry.addRecipes(heatList, HEAT_UID);

        // Anvil + Welding Recipes
        for (var anvil : ItemAnvil.getAnvilStorage()) {
            registry.addRecipeCatalyst(new ItemStack(anvil), ANVIL_UID);
            registry.addRecipeCatalyst(new ItemStack(anvil), WELDING_UID);
        }

        var anvilList = TFCRegistries.ANVIL.getValuesCollection()
                .stream()
                .map(AnvilRecipeWrapper::new)
                .collect(Collectors.toList());

        var weldList = TFCRegistries.WELDING.getValuesCollection()
                .stream()
                .map(WeldingRecipeWrapper::new)
                .collect(Collectors.toList());

        registry.addRecipes(anvilList, ANVIL_UID);
        registry.addRecipes(weldList, WELDING_UID);

        // Bloomery Recipes
        registry.addRecipeCatalyst(new ItemStack(TFCBlocks.BLOOMERY), BLOOMERY_UID);

        var bloomeryList = TFCRegistries.BLOOMERY.getValuesCollection()
                .stream()
                .map(BloomeryRecipeWrapper::new)
                .collect(Collectors.toList());

        registry.addRecipes(bloomeryList, BLOOMERY_UID);

        // Blast Furnace Recipes
        registry.addRecipeCatalyst(new ItemStack(TFCBlocks.BLAST_FURNACE), BLAST_FURNACE_UID);

        var blastList = TFCRegistries.BLAST_FURNACE.getValuesCollection()
                .stream()
                .map(BlastFurnaceRecipeWrapper::new)
                .collect(Collectors.toList());

        registry.addRecipes(blastList, BLAST_FURNACE_UID);

        // Barrel Recipes
        for (Item barrelItem : BlocksTFC.getAllBarrelItemBlocks()) {
            registry.addRecipeCatalyst(new ItemStack(barrelItem), BARREL_UID);
        }

        var barrelRecipes = TFCRegistries.BARREL.getValuesCollection()
                .stream().filter(recipe -> recipe instanceof BarrelRecipeFoodTraits || recipe instanceof BarrelRecipeFoodPreservation || recipe.getOutputStack() != ItemStack.EMPTY || recipe.getOutputFluid() != null)
                .map(BarrelRecipeWrapper::new)
                .collect(Collectors.toList());

        registry.addRecipes(barrelRecipes, BARREL_UID);

        // Loom Recipes
        for (var tree : TFCRegistries.TREES.getValuesCollection()) {
            registry.addRecipeCatalyst(new ItemStack(BlockLoom.get(tree)), LOOM_UID);
        }

        var loomRecipes = TFCRegistries.LOOM.getValuesCollection()
                .stream()
                .map(SimpleRecipeWrapper::new)
                .collect(Collectors.toList());

        registry.addRecipes(loomRecipes, LOOM_UID);

        // Metal Melting Recipes
        registry.addRecipeCatalyst(new ItemStack(TFCBlocks.CRUCIBLE), METAL_HEAT_UID);
        registry.addRecipeCatalyst(new ItemStack(ItemsTFC.FIRED_VESSEL), METAL_HEAT_UID);

        var heatMetalList = new ArrayList<>();
        getAllIngredients().forEach(stack -> {
            HeatRecipeMetalMelting recipe = (HeatRecipeMetalMelting) TFCRegistries.HEAT.getValuesCollection()
                    .stream().filter(x -> x instanceof HeatRecipeMetalMelting)
                    .filter(x -> x.isValidInput(stack, 6))
                    .findFirst().orElse(null);
            if (recipe != null) {
                FluidStack fluidStack = recipe.getOutputFluid(stack);
                // Don't add not meltable (ie: iron ore)
                if (fluidStack != null && MetaFluids.getMaterialFromFluid(fluidStack.getFluid()) == recipe.getMaterial()) {
                    MetalHeatingRecipeWrapper wrapper = new MetalHeatingRecipeWrapper(stack, recipe.getMaterial(), fluidStack.amount, recipe.getTransformTemp());
                    heatMetalList.add(wrapper);
                }
            }
        });

        registry.addRecipes(heatMetalList, METAL_HEAT_UID);

        // Unmold + Casting Recipes
        for (var oreDictStack : OreDictionary.getOres("workbench")) {
            registry.addRecipeCatalyst(oreDictStack, UNMOLD_UID);
        }

        registry.addRecipeCatalyst(new ItemStack(TFCBlocks.CRUCIBLE), CASTING_UID);
        registry.addRecipeCatalyst(new ItemStack(ItemsTFC.FIRED_VESSEL), CASTING_UID);

        var unmoldList = new ArrayList<UnmoldRecipeWrapper>();
        var castingList = new ArrayList<CastingRecipeWrapper>();

        for (var item : ForgeRegistries.RECIPES) {
            if (!(item instanceof UnmoldRecipe unmoldRecipe)) continue;

            var material = unmoldRecipe.getInputMaterial();
            var orePrefix = ((ItemMold) unmoldRecipe.getInputMold().getItem()).getOrePrefix();

            unmoldList.add(new UnmoldRecipeWrapper(material, orePrefix));
            castingList.add(new CastingRecipeWrapper(material, orePrefix));
        }

        registry.addRecipes(unmoldList, UNMOLD_UID);
        registry.addRecipes(castingList, CASTING_UID);

        // Chisel Recipes
        // TODO: Add recipe catalyst

        var chiselList = TFCRegistries.CHISEL.getValuesCollection()
                .stream()
                .map(ChiselRecipeWrapper::new)
                .collect(Collectors.toList());

        registry.addRecipes(chiselList, CHISEL_UID);

        // Scrapping Recipes
        registry.addRecipeCatalyst(ToolItems.KNIFE.get(Materials.Iron), SCRAPING_UID);

        var scrapingList = new ArrayList<>();
        for (ItemAnimalHide.HideSize size : ItemAnimalHide.HideSize.values()) {
            scrapingList.add(new ScrapingWrapper(ItemAnimalHide.get(HideType.SOAKED, size), ItemAnimalHide.get(HideType.SCRAPED, size)));
        }
        registry.addRecipes(scrapingList, SCRAPING_UID);

        // Clay Knapping
        registry.addRecipeCatalyst(new ItemStack(Items.CLAY_BALL), KNAP_CLAY_UID);

        var clayknapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                .filter(recipe -> recipe.getType() == KnappingType.CLAY)
                .map(recipe -> new KnappingRecipeWrapper(recipe, registry.getJeiHelpers().getGuiHelper()))
                .collect(Collectors.toList());


        registry.addRecipes(clayknapRecipes, KNAP_CLAY_UID);

        // Fire Clay Knapping
        registry.addRecipeCatalyst(new ItemStack(ItemsTFC.FIRE_CLAY), KNAP_FIRECLAY_UID);

        var fireclayknapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                .filter(recipe -> recipe.getType() == KnappingType.FIRE_CLAY)
                .map(recipe -> new KnappingRecipeWrapper(recipe, registry.getJeiHelpers().getGuiHelper()))
                .collect(Collectors.toList());


        registry.addRecipes(fireclayknapRecipes, KNAP_FIRECLAY_UID);

        // Leather Knapping
        registry.addRecipeCatalyst(new ItemStack(Items.LEATHER), KNAP_LEATHER_UID);

        var leatherknapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                .filter(recipe -> recipe.getType() == KnappingType.LEATHER)
                .map(recipe -> new KnappingRecipeWrapper(recipe, registry.getJeiHelpers().getGuiHelper()))
                .collect(Collectors.toList());

        registry.addRecipes(leatherknapRecipes, KNAP_LEATHER_UID);

        // Stone Knapping Recipes
        for (var rock : RockType.values()) {
            registry.addRecipeCatalyst(new ItemStack(TFCStorage.getRockItem(rock)), KNAP_STONE_UID);
        }

        var stoneknapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                .filter(recipe -> recipe.getType() == KnappingType.STONE)
                .map(recipe -> new KnappingRecipeWrapper(recipe, registry.getJeiHelpers().getGuiHelper()))
                .collect(Collectors.toList());

        registry.addRecipes(stoneknapRecipes, KNAP_STONE_UID);

        // Wraps all rock layers
        //List<RockLayerWrapper> rockLayerList = TFCRegistries.ROCKS.getValuesCollection()
        //    .stream()
        //    .map(RockLayerWrapper::new)
        //    .collect(Collectors.toList());

        //registry.addRecipes(rockLayerList, ROCK_LAYER_UID);

        // Click areas
        registry.addRecipeClickArea(GuiKnapping.class, 97, 44, 22, 15, KNAP_CLAY_UID, KNAP_FIRECLAY_UID, KNAP_LEATHER_UID, KNAP_STONE_UID);
        registry.addRecipeClickArea(GuiAnvilTFC.class, 26, 24, 9, 14, ANVIL_UID, WELDING_UID);
        registry.addRecipeClickArea(GuiBarrel.class, 92, 21, 9, 14, BARREL_UID);
        registry.addRecipeClickArea(GuiCrucible.class, 139, 100, 10, 15, ALLOY_UID);
        registry.addRecipeClickArea(GuiCrucible.class, 82, 100, 10, 15, METAL_HEAT_UID);
        registry.addRecipeClickArea(GuiFirePit.class, 79, 37, 18, 10, HEAT_UID);

        // Fix inventory tab overlap see https://github.com/TerraFirmaCraft/TerraFirmaCraft/issues/646
        registry.addAdvancedGuiHandlers(new TFCInventoryGuiHandler<>(GuiInventory.class));
        registry.addAdvancedGuiHandlers(new TFCInventoryGuiHandler<>(GuiCalendar.class));
        registry.addAdvancedGuiHandlers(new TFCInventoryGuiHandler<>(GuiNutrition.class));
        registry.addAdvancedGuiHandlers(new TFCInventoryGuiHandler<>(GuiSkills.class));

        // Add JEI descriptions for basic mechanics

        registry.addIngredientInfo(new ItemStack(TFCBlocks.PIT_KILN, 1), VanillaTypes.ITEM, new TextComponentTranslation("jei.description.tfc.pit_kiln").getFormattedText());
        registry.addIngredientInfo(new ItemStack(TFCBlocks.PLACED_ITEM, 1), VanillaTypes.ITEM, new TextComponentTranslation("jei.description.tfc.placed_item").getFormattedText());
        registry.addIngredientInfo(new ItemStack(Items.COAL, 1, 1), VanillaTypes.ITEM, new TextComponentTranslation("jei.description.tfc.charcoal_pit").getFormattedText());


        //        TFCRegistries.ROCK_CATEGORIES.forEach(category -> registry.addRecipeCatalyst(new ItemStack(ItemRockKnife.get(category)), SCRAPING_UID));

        // Custom handlers
        registry.handleRecipes(SaltingRecipe.class, SaltingRecipeWrapper::new, VanillaRecipeCategoryUid.CRAFTING);

        // ContainerInventoryCrafting - Add ability to transfer recipe items
        IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
        transferRegistry.addRecipeTransferHandler(ContainerInventoryCrafting.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
    }
}
