package net.dries007.tfc.compat.jei;

import su.terrafirmagreg.data.Constants;
import su.terrafirmagreg.data.enums.EnumHideSize;
import su.terrafirmagreg.modules.device.client.gui.GuiCrucible;
import su.terrafirmagreg.modules.device.client.gui.GuiFirePit;
import su.terrafirmagreg.modules.device.client.gui.GuiSmelteryCauldron;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.metal.client.gui.GuiGlassworking;
import su.terrafirmagreg.modules.metal.client.gui.GuiMetalAnvil;
import su.terrafirmagreg.modules.rock.api.types.category.RockCategory;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.init.ItemsRock;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.client.gui.GuiWoodBarrel;
import su.terrafirmagreg.modules.wood.init.BlocksWood;
import su.terrafirmagreg.modules.wood.init.RegistryWood;
import su.terrafirmagreg.modules.world.classic.objects.generator.vein.VeinRegistry;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeFoodPreservation;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipeFoodTraits;
import net.dries007.tfc.api.recipes.heat.HeatRecipeMetalMelting;
import net.dries007.tfc.api.recipes.knapping.KnappingTypes;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.gui.GuiCalendar;
import net.dries007.tfc.client.gui.GuiKnapping;
import net.dries007.tfc.client.gui.GuiNutrition;
import net.dries007.tfc.client.gui.GuiSkills;
import net.dries007.tfc.compat.jei.categories.AlloyCategory;
import net.dries007.tfc.compat.jei.categories.BarrelCategory;
import net.dries007.tfc.compat.jei.categories.BlastFurnaceCategory;
import net.dries007.tfc.compat.jei.categories.BloomeryCategory;
import net.dries007.tfc.compat.jei.categories.CastingCategory;
import net.dries007.tfc.compat.jei.categories.ChiselCategory;
import net.dries007.tfc.compat.jei.categories.GlassworkingCategory;
import net.dries007.tfc.compat.jei.categories.HeatCategory;
import net.dries007.tfc.compat.jei.categories.KnappingCategory;
import net.dries007.tfc.compat.jei.categories.LoomCategory;
import net.dries007.tfc.compat.jei.categories.MetalHeatingCategory;
import net.dries007.tfc.compat.jei.categories.RockLayerCategory;
import net.dries007.tfc.compat.jei.categories.ScrapingCategory;
import net.dries007.tfc.compat.jei.categories.SmelteryCategory;
import net.dries007.tfc.compat.jei.categories.VeinCategory;
import net.dries007.tfc.compat.jei.categories.WeldingCategory;
import net.dries007.tfc.compat.jei.wrappers.AlloyRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.AnvilRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.BarrelRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.BlastFurnaceRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.BloomeryRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.CastingRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.ChiselRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.GlassworkingRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.HeatRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.KnappingRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.MetalHeatingRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.RockLayerWrapper;
import net.dries007.tfc.compat.jei.wrappers.SaltingRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.ScrapingWrapper;
import net.dries007.tfc.compat.jei.wrappers.SimpleRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.SmelteryRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.UnmoldRecipeWrapper;
import net.dries007.tfc.compat.jei.wrappers.VeinWrapper;
import net.dries007.tfc.compat.jei.wrappers.WeldingRecipeWrapper;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.container.ContainerInventoryCrafting;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.items.ItemAnimalHide;
import net.dries007.tfc.objects.items.ItemAnimalHide.HideType;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.TechItems;
import net.dries007.tfc.objects.items.glassworking.ItemBlowpipe;
import net.dries007.tfc.objects.items.metal.ItemMetalChisel;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;
import net.dries007.tfc.objects.items.tools.ItemRockKnife;
import net.dries007.tfc.objects.recipes.SaltingRecipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@JEIPlugin
public final class TFCJEIPlugin implements IModPlugin {

  public static final String ALLOY_UID = Constants.MODID_TFC + ".alloy";
  public static final String ANVIL_UID = Constants.MODID_TFC + ".anvil";
  public static final String BARREL_UID = Constants.MODID_TFC + ".barrel";
  public static final String BLAST_FURNACE_UID = Constants.MODID_TFC + ".blast_furnace";
  public static final String BLOOMERY_UID = Constants.MODID_TFC + ".bloomery";
  public static final String CASTING_UID = Constants.MODID_TFC + ".casting";
  public static final String CHISEL_UID = Constants.MODID_TFC + ".chisel";
  public static final String HEAT_UID = Constants.MODID_TFC + ".heat";
  public static final String KNAP_CLAY_UID = Constants.MODID_TFC + ".knap.clay";
  public static final String KNAP_FIRECLAY_UID = Constants.MODID_TFC + ".knap.fireclay";
  public static final String KNAP_LEATHER_UID = Constants.MODID_TFC + ".knap.leather";
  public static final String KNAP_STONE_UID = Constants.MODID_TFC + ".knap.stone";
  public static final String METAL_HEAT_UID = Constants.MODID_TFC + ".metal_heat";
  public static final String LOOM_UID = Constants.MODID_TFC + ".loom";
  public static final String ROCK_LAYER_UID = Constants.MODID_TFC + ".rock_layer";
  public static final String VEIN_UID = Constants.MODID_TFC + ".vein";
  public static final String WELDING_UID = Constants.MODID_TFC + ".welding";
  public static final String SCRAPING_UID = Constants.MODID_TFC + ".scraping";
  public static final String SMELTERY_UID = Constants.MODID_TFC + ".smeltery";
  public static final String GLASSWORKING_UID = Constants.MODID_TFC + ".glassworking";

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
    registry.addRecipeCategories(new RockLayerCategory(registry.getJeiHelpers().getGuiHelper(), ROCK_LAYER_UID));
    registry.addRecipeCategories(new VeinCategory(registry.getJeiHelpers().getGuiHelper(), VEIN_UID));
    registry.addRecipeCategories(new WeldingCategory(registry.getJeiHelpers().getGuiHelper(), WELDING_UID));
    registry.addRecipeCategories(new ScrapingCategory(registry.getJeiHelpers().getGuiHelper(), SCRAPING_UID));
    registry.addRecipeCategories(new SmelteryCategory(registry.getJeiHelpers().getGuiHelper(), SMELTERY_UID));
    registry.addRecipeCategories(new GlassworkingCategory(registry.getJeiHelpers().getGuiHelper(), GLASSWORKING_UID));
  }

  @Override
  public void register(IModRegistry registry) {
    REGISTRY = registry;

    //Wraps all heating recipes, if they return ingredient(1 or more) -> itemstacks(1 or more)
    List<HeatRecipeWrapper> heatList = TFCRegistries.HEAT.getValuesCollection()
                                                         .stream()
                                                         .filter(r -> !r.getOutputs().isEmpty() && !r.getIngredients().isEmpty())
                                                         .map(HeatRecipeWrapper::new)
                                                         .collect(Collectors.toList());

    registry.addRecipes(heatList, HEAT_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.FIRE_PIT), HEAT_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.CHARCOAL_FORGE), HEAT_UID);

    // Glassworking (blowpipe)
    List<GlassworkingRecipeWrapper> glassList = TFCRegistries.GLASSWORKING.getValuesCollection()
                                                                          .stream()
                                                                          .map(x -> new GlassworkingRecipeWrapper(x, registry.getJeiHelpers()
                                                                                                                             .getGuiHelper()))
                                                                          .collect(Collectors.toList());

    registry.addRecipes(glassList, GLASSWORKING_UID);
    TFCRegistries.METALS.getValuesCollection().forEach(metal -> {
      ItemBlowpipe blowpipe = ItemBlowpipe.get(metal);
      if (blowpipe != null) {
        registry.addRecipeCatalyst(new ItemStack(blowpipe), GLASSWORKING_UID);
      }
    });

    // Smeltery
    List<SmelteryRecipeWrapper> smelteryList = TFCRegistries.SMELTERY.getValuesCollection()
                                                                     .stream()
                                                                     .map(SmelteryRecipeWrapper::new)
                                                                     .collect(Collectors.toList());

    registry.addRecipes(smelteryList, SMELTERY_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.SMELTERY_CAULDRON), SMELTERY_UID);

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
    List<SimpleRecipeWrapper> loomRecipes = RegistryWood.LOOM.getValuesCollection()
                                                             .stream()
                                                             .map(SimpleRecipeWrapper::new)
                                                             .collect(Collectors.toList());

    registry.addRecipes(loomRecipes, LOOM_UID);
    for (var wood : WoodType.getTypes()) {
      registry.addRecipeCatalyst(new ItemStack(BlocksWood.LOOM.get(wood)), LOOM_UID);
    }

    // Alloy Recipes
    List<AlloyRecipeWrapper> alloyRecipes = TFCRegistries.ALLOYS.getValuesCollection().stream()
                                                                .map(AlloyRecipeWrapper::new)
                                                                .collect(Collectors.toList());

    registry.addRecipes(alloyRecipes, ALLOY_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.CRUCIBLE), ALLOY_UID);
    registry.addRecipeCatalyst(new ItemStack(ItemsTFC.FIRED_VESSEL), ALLOY_UID);

    // Clay Knapping
    List<KnappingRecipeWrapper> clayknapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                        .filter(recipe -> recipe.getType() == KnappingTypes.CLAY)
                                                                        .map(recipe -> new KnappingRecipeWrapper(recipe, registry.getJeiHelpers()
                                                                                                                                 .getGuiHelper()))
                                                                        .collect(Collectors.toList());

    registry.addRecipes(clayknapRecipes, KNAP_CLAY_UID);
    for (ItemStack stack : OreDictionary.getOres("ingotClay")) {
      registry.addRecipeCatalyst(stack, KNAP_CLAY_UID);
    }

    // Fire Clay Knapping
    List<KnappingRecipeWrapper> fireclayknapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                            .filter(recipe -> recipe.getType() == KnappingTypes.FIRE_CLAY)
                                                                            .map(recipe -> new KnappingRecipeWrapper(recipe, registry.getJeiHelpers()
                                                                                                                                     .getGuiHelper()))
                                                                            .collect(Collectors.toList());

    registry.addRecipes(fireclayknapRecipes, KNAP_FIRECLAY_UID);
    for (ItemStack stack : OreDictionary.getOres("fireClay")) {
      registry.addRecipeCatalyst(stack, KNAP_FIRECLAY_UID);
    }

    // Leather Knapping
    List<KnappingRecipeWrapper> leatherknapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                           .filter(recipe -> recipe.getType() == KnappingTypes.LEATHER)
                                                                           .map(recipe -> new KnappingRecipeWrapper(recipe, registry.getJeiHelpers()
                                                                                                                                    .getGuiHelper()))
                                                                           .collect(Collectors.toList());

    registry.addRecipes(leatherknapRecipes, KNAP_LEATHER_UID);
    for (ItemStack stack : OreDictionary.getOres("leather")) {
      registry.addRecipeCatalyst(stack, KNAP_LEATHER_UID);
    }

    // Leather Knapping Recipes
    List<KnappingRecipeWrapper> stoneknapRecipes = TFCRegistries.KNAPPING.getValuesCollection().stream()
                                                                         .filter(recipe -> recipe.getType() == KnappingTypes.STONE)
                                                                         .flatMap(recipe -> RockType.getTypes()
                                                                                                    .stream()
                                                                                                    .map(rock -> new KnappingRecipeWrapper.Stone(recipe, registry.getJeiHelpers()
                                                                                                                                                                 .getGuiHelper(), rock)))
                                                                         .collect(Collectors.toList());

    registry.addRecipes(stoneknapRecipes, KNAP_STONE_UID);
    RockType.getTypes().forEach(rock ->
                                  registry.addRecipeCatalyst(new ItemStack(ItemsRock.LOOSE.get(rock)), KNAP_STONE_UID)
    );

    //Wraps all barrel recipes
    List<BarrelRecipeWrapper> barrelRecipes = TFCRegistries.BARREL.getValuesCollection()
                                                                  .stream()
                                                                  .filter(recipe -> recipe instanceof BarrelRecipeFoodTraits
                                                                                    || recipe instanceof BarrelRecipeFoodPreservation ||
                                                                                    recipe.getOutputStack() != ItemStack.EMPTY
                                                                                    || recipe.getOutputFluid() != null)
                                                                  .map(BarrelRecipeWrapper::new)
                                                                  .collect(Collectors.toList());

    registry.addRecipes(barrelRecipes, BARREL_UID);
    for (var barrelItem : BlocksWood.BARREL.getMap().values()) {
      registry.addRecipeCatalyst(new ItemStack(barrelItem), BARREL_UID);
    }

    //Wraps all bloomery recipes
    List<BloomeryRecipeWrapper> bloomeryList = TFCRegistries.BLOOMERY.getValuesCollection()
                                                                     .stream()
                                                                     .map(BloomeryRecipeWrapper::new)
                                                                     .collect(Collectors.toList());

    registry.addRecipes(bloomeryList, BLOOMERY_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.BLOOMERY), BLOOMERY_UID);

    //Wraps all blast furnace recipes
    List<BlastFurnaceRecipeWrapper> blastList = TFCRegistries.BLAST_FURNACE.getValuesCollection()
                                                                           .stream()
                                                                           .map(BlastFurnaceRecipeWrapper::new)
                                                                           .collect(Collectors.toList());

    registry.addRecipes(blastList, BLAST_FURNACE_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.BLAST_FURNACE), BLAST_FURNACE_UID);

    //Wraps all metal melting recipes
    List<MetalHeatingRecipeWrapper> heatMetalList = new ArrayList<>();
    getAllIngredients().forEach(stack -> {
      HeatRecipeMetalMelting recipe = (HeatRecipeMetalMelting) TFCRegistries.HEAT.getValuesCollection()
                                                                                 .stream()
                                                                                 .filter(x -> x instanceof HeatRecipeMetalMelting)
                                                                                 .filter(x -> x.isValidInput(stack, Metal.Tier.TIER_VI))
                                                                                 .findFirst()
                                                                                 .orElse(null);
      if (recipe != null) {
        FluidStack fluidStack = recipe.getOutputFluid(stack);
        // Don't add not meltable (ie: iron ore)
        if (fluidStack != null && FluidsTFC.getMetalFromFluid(fluidStack.getFluid()) == recipe.getMetal()) {
          MetalHeatingRecipeWrapper wrapper = new MetalHeatingRecipeWrapper(stack, recipe.getMetal(), fluidStack.amount,
                                                                            recipe.getTransformTemp());
          heatMetalList.add(wrapper);
        }
      }
    });
    registry.addRecipes(heatMetalList, METAL_HEAT_UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.CRUCIBLE), METAL_HEAT_UID);
    registry.addRecipeCatalyst(new ItemStack(ItemsTFC.FIRED_VESSEL), METAL_HEAT_UID);

    //Wraps all chisel recipes
    List<ChiselRecipeWrapper> chiselList = TFCRegistries.CHISEL.getValuesCollection()
                                                               .stream()
                                                               .map(ChiselRecipeWrapper::new)
                                                               .collect(Collectors.toList());

    registry.addRecipes(chiselList, CHISEL_UID);

    //Wraps all rock layers
    List<RockLayerWrapper> rockLayerList = RockType.getTypes()
                                                   .stream()
                                                   .map(RockLayerWrapper::new)
                                                   .collect(Collectors.toList());

    registry.addRecipes(rockLayerList, ROCK_LAYER_UID);

    //Wraps all veins
    List<VeinWrapper> veinList = VeinRegistry.INSTANCE.getVeins().values()
                                                      .stream()
                                                      .map(VeinWrapper::new)
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
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.CRUCIBLE), CASTING_UID);
    registry.addRecipeCatalyst(new ItemStack(ItemsTFC.FIRED_VESSEL), CASTING_UID);

    //Click areas
    registry.addRecipeClickArea(GuiKnapping.class, 97, 44, 22, 15, KNAP_CLAY_UID, KNAP_FIRECLAY_UID, KNAP_LEATHER_UID, KNAP_STONE_UID);
    registry.addRecipeClickArea(GuiMetalAnvil.class, 26, 24, 9, 14, ANVIL_UID, WELDING_UID);
    registry.addRecipeClickArea(GuiWoodBarrel.class, 92, 21, 9, 14, BARREL_UID);
    registry.addRecipeClickArea(GuiCrucible.class, 139, 100, 10, 15, ALLOY_UID);
    registry.addRecipeClickArea(GuiCrucible.class, 82, 100, 10, 15, METAL_HEAT_UID);
    registry.addRecipeClickArea(GuiFirePit.class, 79, 37, 18, 10, HEAT_UID);
    registry.addRecipeClickArea(GuiSmelteryCauldron.class, 52, 58, 72, 15, SMELTERY_UID);
    registry.addRecipeClickArea(GuiGlassworking.class, 132, 27, 9, 14, GLASSWORKING_UID);

    // Fix inventory tab overlap see https://github.com/TerraFirmaCraft/TerraFirmaCraft/issues/646
    registry.addAdvancedGuiHandlers(new TFCInventoryGuiHandler<>(GuiInventory.class));
    registry.addAdvancedGuiHandlers(new TFCInventoryGuiHandler<>(GuiCalendar.class));
    registry.addAdvancedGuiHandlers(new TFCInventoryGuiHandler<>(GuiNutrition.class));
    registry.addAdvancedGuiHandlers(new TFCInventoryGuiHandler<>(GuiSkills.class));

    //Add JEI descriptions for basic mechanics

    registry.addIngredientInfo(new ItemStack(BlocksDevice.PIT_KILN, 1), VanillaTypes.ITEM,
                               new TextComponentTranslation("jei.description.tfc.pit_kiln").getFormattedText());
    registry.addIngredientInfo(new ItemStack(BlocksTFC.PLACED_ITEM, 1), VanillaTypes.ITEM,
                               new TextComponentTranslation("jei.description.tfc.placed_item").getFormattedText());
    registry.addIngredientInfo(new ItemStack(Items.COAL, 1, 1), VanillaTypes.ITEM,
                               new TextComponentTranslation("jei.description.tfc.charcoal_pit").getFormattedText());
    registry.addIngredientInfo(new ItemStack(TechItems.IRON_GROOVE), VanillaTypes.ITEM, "jei.information.tfctech.groove");
    registry.addIngredientInfo(new FluidStack(FluidsTFC.LATEX.get(), 1000), VanillaTypes.FLUID, "jei.information.tfctech.latex");
    registry.addIngredientInfo(new ItemStack(BlocksDevice.FRIDGE), VanillaTypes.ITEM, "jei.information.tfctech.fridge");
    registry.addIngredientInfo(new ItemStack(BlocksDevice.INDUCTION_CRUCIBLE), VanillaTypes.ITEM, "jei.information.tfctech.crucible");
    registry.addIngredientInfo(new ItemStack(BlocksDevice.ELECTRIC_FORGE), VanillaTypes.ITEM, "jei.information.tfctech.forge");

    List<ScrapingWrapper> scrapingList = new ArrayList<>();
    for (EnumHideSize size : EnumHideSize.values()) {
      scrapingList.add(new ScrapingWrapper(ItemAnimalHide.get(HideType.SOAKED, size), ItemAnimalHide.get(HideType.SCRAPED, size)));
    }
    registry.addRecipes(scrapingList, SCRAPING_UID);
    RockCategory.getCategories().forEach(category -> registry.addRecipeCatalyst(new ItemStack(ItemRockKnife.get(category)), SCRAPING_UID));

    //Custom handlers
    registry.handleRecipes(SaltingRecipe.class, SaltingRecipeWrapper::new, VanillaRecipeCategoryUid.CRAFTING);

    //ContainerInventoryCrafting - Add ability to transfer recipe items
    IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
    transferRegistry.addRecipeTransferHandler(ContainerInventoryCrafting.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
  }
}
