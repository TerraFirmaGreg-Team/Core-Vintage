package net.dries007.tfc.compat.jei;

import su.terrafirmagreg.api.data.enums.Mods;
import su.terrafirmagreg.modules.device.client.gui.GuiCrucible;
import su.terrafirmagreg.modules.device.client.gui.GuiFirePit;
import su.terrafirmagreg.modules.device.init.BlocksDevice;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import gregtech.api.unification.material.Materials;
import gregtech.common.items.ToolItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeFoodPreservation;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeFoodTraits;
import net.dries007.tfc.api.recipes.heat.HeatRecipeMetalMelting;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.client.gui.GuiAnvilTFC;
import net.dries007.tfc.client.gui.GuiBarrel;
import net.dries007.tfc.client.gui.GuiCalendar;
import net.dries007.tfc.client.gui.GuiKnapping;
import net.dries007.tfc.client.gui.GuiNutrition;
import net.dries007.tfc.client.gui.GuiSkills;
import net.dries007.tfc.compat.jei.categories.AlloyCategory;
import net.dries007.tfc.compat.jei.categories.AnvilCategory;
import net.dries007.tfc.compat.jei.categories.BarrelCategory;
import net.dries007.tfc.compat.jei.categories.BlastFurnaceCategory;
import net.dries007.tfc.compat.jei.categories.BloomeryCategory;
import net.dries007.tfc.compat.jei.categories.CastingCategory;
import net.dries007.tfc.compat.jei.categories.ChiselCategory;
import net.dries007.tfc.compat.jei.categories.HeatCategory;
import net.dries007.tfc.compat.jei.categories.KnappingCategory;
import net.dries007.tfc.compat.jei.categories.LoomCategory;
import net.dries007.tfc.compat.jei.categories.MetalHeatingCategory;
import net.dries007.tfc.compat.jei.categories.QuernCategory;
import net.dries007.tfc.compat.jei.categories.RockLayerCategory;
import net.dries007.tfc.compat.jei.categories.ScrapingCategory;
import net.dries007.tfc.compat.jei.categories.VeinCategory;
import net.dries007.tfc.compat.jei.categories.WeldingCategory;
import net.dries007.tfc.compat.jei.wrappers.AlloyRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.AnvilRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.BarrelRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.BlastFurnaceRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.BloomeryRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.CastingRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.ChiselRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.HeatRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.KnappingRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.MetalHeatingRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.RockLayerWrapper;
import net.dries007.tfc.compat.jei.wrappers.SaltingRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.ScrapingWrapper;
import net.dries007.tfc.compat.jei.wrappers.SimpleRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.UnmoldRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.VeinWrapper;
import net.dries007.tfc.compat.jei.wrappers.WeldingRecipeWrapper;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.stone.BlockOreTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLoom;
import net.dries007.tfc.objects.container.ContainerInventoryCrafting;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.items.ItemAnimalHide;
import net.dries007.tfc.objects.items.ItemAnimalHide.HideType;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.metal.ItemAnvil;
import net.dries007.tfc.objects.items.metal.ItemMetalChisel;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.dries007.tfc.objects.items.rock.ItemRockKnife;
import net.dries007.tfc.objects.recipes.SaltingRecipe;
import net.dries007.tfc.world.classic.worldgen.vein.VeinRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@JEIPlugin
public final class TFCJEIPlugin implements IModPlugin {

  public static final String ALLOY_UID = Mods.ModIDs.TFC + ".alloy";
  public static final String ANVIL_UID = Mods.ModIDs.TFC + ".anvil";
  public static final String BARREL_UID = Mods.ModIDs.TFC + ".barrel";
  public static final String BLAST_FURNACE_UID = Mods.ModIDs.TFC + ".blast_furnace";
  public static final String BLOOMERY_UID = Mods.ModIDs.TFC + ".bloomery";
  public static final String CASTING_UID = Mods.ModIDs.TFC + ".casting";
  public static final String CHISEL_UID = Mods.ModIDs.TFC + ".chisel";
  public static final String HEAT_UID = Mods.ModIDs.TFC + ".heat";
  public static final String KNAP_CLAY_UID = Mods.ModIDs.TFC + ".knap.clay";
  public static final String KNAP_FIRECLAY_UID = Mods.ModIDs.TFC + ".knap.fireclay";
  public static final String KNAP_LEATHER_UID = Mods.ModIDs.TFC + ".knap.leather";
  public static final String KNAP_STONE_UID = Mods.ModIDs.TFC + ".knap.stone";
  public static final String METAL_HEAT_UID = Mods.ModIDs.TFC + ".metal_heat";
  public static final String LOOM_UID = Mods.ModIDs.TFC + ".loom";
  public static final String QUERN_UID = Mods.ModIDs.TFC + ".quern";
  public static final String ROCK_LAYER_UID = Mods.ModIDs.TFC + ".rock_layer";
  public static final String VEIN_UID = Mods.ModIDs.TFC + ".vein";
  public static final String WELDING_UID = Mods.ModIDs.TFC + ".welding";
  public static final String SCRAPING_UID = Mods.ModIDs.TFC + ".scraping";

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
    //Add new JEI recipe categories
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
    registry.addRecipeCategories(new RockLayerCategory(registry.getJeiHelpers().getGuiHelper(), ROCK_LAYER_UID));
    registry.addRecipeCategories(new VeinCategory(registry.getJeiHelpers().getGuiHelper(), VEIN_UID));
    registry.addRecipeCategories(new WeldingCategory(registry.getJeiHelpers().getGuiHelper(), WELDING_UID));
    registry.addRecipeCategories(new ScrapingCategory(registry.getJeiHelpers().getGuiHelper(), SCRAPING_UID));
  }

  @Override
  public void register(IModRegistry registry) {
    REGISTRY = registry;

    //Wraps all quern recipes
    List<SimpleRecipeWrapper> quernList = TFCRegistries.QUERN.getValuesCollection()
      .stream()
      .map(SimpleRecipeWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(quernList, QUERN_UID); //Register recipes to quern category
    registry.addRecipeCatalyst(new ItemStack(BlocksTFC.QUERN), QUERN_UID); //Register BlockQuern as the device that do quern recipes

    //Wraps all heating recipes, if they return ingredient(1 or more) -> itemstacks(1 or more)
    List<HeatRecipeWrapper> heatList = TFCRegistries.HEAT.getValuesCollection()
      .stream()
      .filter(r -> r.getOutputs().size() > 0 && r.getIngredients().size() > 0)
      .map(HeatRecipeWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(heatList, HEAT_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.FIRE_PIT.get()), HEAT_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.CHARCOAL_FORGE.get()), HEAT_UID);

    //Wraps all anvil recipes
    List<AnvilRecipeWrapper> anvilList = TFCRegistries.ANVIL.getValuesCollection()
      .stream()
      .map(AnvilRecipeWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(anvilList, ANVIL_UID);

    //Wraps all welding recipes
    List<WeldingRecipeWrapper> weldList = TFCRegistries.WELDING.getValuesCollection()
      .stream()
      .map(WeldingRecipeWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(weldList, WELDING_UID);

    //Wraps all loom recipes
    List<SimpleRecipeWrapper> loomRecipes = TFCRegistries.LOOM.getValuesCollection()
      .stream()
      .map(SimpleRecipeWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(loomRecipes, LOOM_UID);
    for (Tree tree : TFCRegistries.TREES.getValuesCollection()) {
      registry.addRecipeCatalyst(new ItemStack(BlockLoom.get(tree)), LOOM_UID);
    }

    // Alloy Recipes
    List<AlloyRecipeWrapper> alloyRecipes = TFCRegistries.ALLOYS.getValuesCollection().stream()
      .map(AlloyRecipeWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(alloyRecipes, ALLOY_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.CRUCIBLE.get()), ALLOY_UID);
    registry.addRecipeCatalyst(new ItemStack(ItemsTFC.FIRED_VESSEL), ALLOY_UID);

    // Clay Knapping
    List<KnappingRecipeWrapper> clayknapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
      .filter(recipe -> recipe.getType() == KnappingType.CLAY)
      .map(recipe -> new KnappingRecipeWrapper(recipe, registry.getJeiHelpers()
        .getGuiHelper()))
      .collect(Collectors.toList());

    registry.addRecipes(clayknapRecipes, KNAP_CLAY_UID);
    for (ItemStack stack : OreDictionary.getOres("clay")) {
      registry.addRecipeCatalyst(stack, KNAP_CLAY_UID);
    }

    // Fire Clay Knapping
    List<KnappingRecipeWrapper> fireclayknapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
      .filter(recipe -> recipe.getType() == KnappingType.FIRE_CLAY)
      .map(recipe -> new KnappingRecipeWrapper(recipe, registry.getJeiHelpers()
        .getGuiHelper()))
      .collect(Collectors.toList());

    registry.addRecipes(fireclayknapRecipes, KNAP_FIRECLAY_UID);
    for (ItemStack stack : OreDictionary.getOres("fireClay")) {
      registry.addRecipeCatalyst(stack, KNAP_FIRECLAY_UID);
    }

    // Leather Knapping
    List<KnappingRecipeWrapper> leatherknapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
      .filter(recipe -> recipe.getType() == KnappingType.LEATHER)
      .map(recipe -> new KnappingRecipeWrapper(recipe, registry.getJeiHelpers()
        .getGuiHelper()))
      .collect(Collectors.toList());

    registry.addRecipes(leatherknapRecipes, KNAP_LEATHER_UID);
    for (ItemStack stack : OreDictionary.getOres("leather")) {
      registry.addRecipeCatalyst(stack, KNAP_LEATHER_UID);
    }

    // Leather Knapping Recipes
    List<KnappingRecipeWrapper> stoneknapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
      .filter(recipe -> recipe.getType() == KnappingType.STONE)
      .flatMap(recipe -> TFCRegistries.ROCKS.getValuesCollection()
        .stream()
        .map(rock -> new KnappingRecipeWrapper.Stone(recipe, registry.getJeiHelpers()
          .getGuiHelper(), rock)))
      .collect(Collectors.toList());

    registry.addRecipes(stoneknapRecipes, KNAP_STONE_UID);
    for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
      registry.addRecipeCatalyst(new ItemStack(ItemRock.get(rock)), KNAP_STONE_UID);
    }

    //Wraps all barrel recipes
    List<BarrelRecipeWrapper> barrelRecipes = TFCRegistries.BARREL.getValuesCollection()
      .stream().filter(recipe -> recipe instanceof BarrelRecipeFoodTraits
                                 || recipe instanceof BarrelRecipeFoodPreservation
                                 || recipe.getOutputStack() != ItemStack.EMPTY
                                 || recipe.getOutputFluid() != null)
      .map(BarrelRecipeWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(barrelRecipes, BARREL_UID);
    for (Item barrelItem : BlocksTFC.getAllBarrelItemBlocks()) {
      registry.addRecipeCatalyst(new ItemStack(barrelItem), BARREL_UID);
    }

    //Wraps all bloomery recipes
    List<BloomeryRecipeWrapper> bloomeryList = TFCRegistries.BLOOMERY.getValuesCollection()
      .stream()
      .map(BloomeryRecipeWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(bloomeryList, BLOOMERY_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.BLOOMERY.get()), BLOOMERY_UID);

    //Wraps all blast furnace recipes
    List<BlastFurnaceRecipeWrapper> blastList = TFCRegistries.BLAST_FURNACE.getValuesCollection()
      .stream()
      .map(BlastFurnaceRecipeWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(blastList, BLAST_FURNACE_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.BLAST_FURNACE.get()), BLAST_FURNACE_UID);

    //Wraps all metal melting recipes
    List<MetalHeatingRecipeWrapper> heatMetalList = new ArrayList<>();
    getAllIngredients().forEach(stack -> {
      HeatRecipeMetalMelting recipe = (HeatRecipeMetalMelting) TFCRegistries.HEAT.getValuesCollection()
        .stream().filter(x -> x instanceof HeatRecipeMetalMelting)
        .filter(x -> x.isValidInput(stack, Metal.Tier.TIER_VI))
        .findFirst().orElse(null);
      if (recipe != null) {
        FluidStack fluidStack = recipe.getOutputFluid(stack);
        // Don't add not meltable (ie: iron ore)
        if (fluidStack != null && FluidsTFC.getMetalFromFluid(fluidStack.getFluid()) == recipe.getMetal()) {
          MetalHeatingRecipeWrapper wrapper = new MetalHeatingRecipeWrapper(stack, recipe.getMetal(), fluidStack.amount, recipe.getTransformTemp());
          heatMetalList.add(wrapper);
        }
      }
    });
    registry.addRecipes(heatMetalList, METAL_HEAT_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.CRUCIBLE.get()), METAL_HEAT_UID);
    registry.addRecipeCatalyst(new ItemStack(ItemsTFC.FIRED_VESSEL), METAL_HEAT_UID);

    //Wraps all chisel recipes
    List<ChiselRecipeWrapper> chiselList = TFCRegistries.CHISEL.getValuesCollection()
      .stream()
      .map(ChiselRecipeWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(chiselList, CHISEL_UID);

    //Wraps all rock layers
    List<RockLayerWrapper> rockLayerList = TFCRegistries.ROCKS.getValuesCollection()
      .stream()
      .map(RockLayerWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(rockLayerList, ROCK_LAYER_UID);

    // TODO Hide TFC Ores in HEI
    IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
    for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
      for (Ore ore : TFCRegistries.ORES.getValuesCollection()) {
        blacklist.addIngredientToBlacklist(new ItemStack(BlockOreTFC.get(ore, rock)));
      }
    }

    //Wraps all veins
    List<VeinWrapper> veinList = VeinRegistry.INSTANCE.getVeins().values()
      .stream().map(VeinWrapper::new)
      .collect(Collectors.toList());

    registry.addRecipes(veinList, VEIN_UID);

    // Register metal related stuff (put everything here for performance + sorted registration)
    List<UnmoldRecipeWrapper> unmoldList = new ArrayList<>();
    List<CastingRecipeWrapper> castingList = new ArrayList<>();
    List<Metal> tierOrdered = TFCRegistries.METALS.getValuesCollection()
      .stream()
      .sorted(Comparator.comparingInt(metal -> metal.getTier().ordinal()))
      .collect(Collectors.toList());
    for (Metal metal : tierOrdered) {
      if (Metal.ItemType.ANVIL.hasType(metal)) {
        registry.addRecipeCatalyst(new ItemStack(ItemAnvil.get(metal, Metal.ItemType.ANVIL)), ANVIL_UID);
        registry.addRecipeCatalyst(new ItemStack(ItemAnvil.get(metal, Metal.ItemType.ANVIL)), WELDING_UID);
      }
      if (Metal.ItemType.CHISEL.hasType(metal)) {
        registry.addRecipeCatalyst(new ItemStack(ItemMetalChisel.get(metal, Metal.ItemType.CHISEL)), CHISEL_UID);
      }
      if (Metal.ItemType.PROPICK.hasType(metal)) {
        registry.addRecipeCatalyst(new ItemStack(ItemMetalTool.get(metal, Metal.ItemType.PROPICK)), ROCK_LAYER_UID);
        registry.addRecipeCatalyst(new ItemStack(ItemMetalTool.get(metal, Metal.ItemType.PROPICK)), VEIN_UID);
      }
      if (Metal.ItemType.KNIFE.hasType(metal)) {
        registry.addRecipeCatalyst(new ItemStack(ItemMetalTool.get(metal, Metal.ItemType.KNIFE)), SCRAPING_UID);
      }
      for (Metal.ItemType type : Metal.ItemType.values()) {
        if (type.hasMold(metal)) {
          unmoldList.add(new UnmoldRecipeWrapper(metal, type));
          castingList.add(new CastingRecipeWrapper(metal, type));
        }
      }
    }
    registry.addRecipes(unmoldList, VanillaRecipeCategoryUid.CRAFTING);
    registry.addRecipes(castingList, CASTING_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.CRUCIBLE.get()), CASTING_UID);
    registry.addRecipeCatalyst(new ItemStack(ItemsTFC.FIRED_VESSEL), CASTING_UID);

    //Click areas
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

    //Add JEI descriptions for basic mechanics

    registry.addIngredientInfo(new ItemStack(BlocksDevice.PIT_KILN.get()), VanillaTypes.ITEM, new TextComponentTranslation("jei.description.tfc.pit_kiln").getFormattedText());
    registry.addIngredientInfo(new ItemStack(BlocksTFC.PLACED_ITEM), VanillaTypes.ITEM, new TextComponentTranslation("jei.description.tfc.placed_item").getFormattedText());
    registry.addIngredientInfo(new ItemStack(Items.COAL, 1, 1), VanillaTypes.ITEM, new TextComponentTranslation("jei.description.tfc.charcoal_pit").getFormattedText());

    List<ScrapingWrapper> scrapingList = new ArrayList<>();
    for (ItemAnimalHide.HideSize size : ItemAnimalHide.HideSize.values()) {
      scrapingList.add(new ScrapingWrapper(ItemAnimalHide.get(HideType.SOAKED, size), ItemAnimalHide.get(HideType.SCRAPED, size)));
    }
    registry.addRecipes(scrapingList, SCRAPING_UID);
    TFCRegistries.ROCK_CATEGORIES.forEach(category -> registry.addRecipeCatalyst(new ItemStack(ItemRockKnife.get(category)), SCRAPING_UID));
    // Add GT Knife to Knapping Tab
    registry.addRecipeCatalyst(ToolItems.KNIFE.get(Materials.Iron), SCRAPING_UID);

    //Custom handlers
    registry.handleRecipes(SaltingRecipe.class, SaltingRecipeWrapper::new, VanillaRecipeCategoryUid.CRAFTING);

    //ContainerInventoryCrafting - Add ability to transfer recipe items
    IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
    transferRegistry.addRecipeTransferHandler(ContainerInventoryCrafting.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
  }
}
