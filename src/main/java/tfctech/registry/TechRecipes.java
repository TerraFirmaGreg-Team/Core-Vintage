package tfctech.registry;

import su.terrafirmagreg.api.data.enums.Mods;
import su.terrafirmagreg.modules.core.init.FluidsCore;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import net.dries007.tfc.api.recipes.WeldingRecipe;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.metal.ItemMetal;
import net.dries007.tfc.objects.recipes.ShapelessDamageRecipe;
import net.dries007.tfc.util.OreDictionaryHelper;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import net.dries007.tfc.util.forge.ForgeRule;
import tfctech.TechConfig;
import tfctech.api.recipes.GlassworkingRecipe;
import tfctech.api.recipes.SmelteryRecipe;
import tfctech.api.recipes.WireDrawingRecipe;
import tfctech.objects.items.TechItems;
import tfctech.objects.items.glassworking.ItemBlowpipe;
import tfctech.objects.items.metal.ItemTechMetal;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCTECH;

@SuppressWarnings({"ConstantConditions", "unused"})
@Mod.EventBusSubscriber(modid = TFCTECH)
public final class TechRecipes {

  @SubscribeEvent
  public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event) {
    IForgeRegistryModifiable<IRecipe> modRegistry = (IForgeRegistryModifiable<IRecipe>) event.getRegistry();
    if (TechConfig.TWEAKS.removeGlassRecipes) {
      modRegistry.remove(new ResourceLocation("minecraft:glass_pane"));

      List<ItemStack> removeList = new ArrayList<>();
      FurnaceRecipes.instance().getSmeltingList().keySet().forEach(stack -> {
        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(stack);
        if (OreDictionaryHelper.doesStackMatchOre(result, "blockGlass")) {
          removeList.add(result);
        }
      });
      removeList.forEach(stack -> FurnaceRecipes.instance().getSmeltingList().remove(stack));
    }
  }

  @SubscribeEvent
  public static void onRegisterBarrelRecipeEvent(RegistryEvent.Register<BarrelRecipe> event) {
    event.getRegistry().registerAll(
      new BarrelRecipe(IIngredient.of(FluidsCore.LATEX.get(), 100), IIngredient.of(new ItemStack(TechItems.VULCANIZING_AGENTS)), null, new ItemStack(TechItems.RUBBER_MIX, 6),
        8 * ICalendar.TICKS_IN_HOUR).setRegistryName("rubber_mix")
    );
  }

  @SubscribeEvent
  public static void onRegisterHeatRecipeEvent(RegistryEvent.Register<HeatRecipe> event) {
    event.getRegistry().registerAll(
      new HeatRecipeSimple(IIngredient.of(new ItemStack(TechItems.RUBBER_MIX)), new ItemStack(TechItems.RUBBER), 600f, Metal.Tier.TIER_I).setRegistryName("rubber"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(TechItems.UNFIRED_RACKWHEEL_PIECE)), new ItemStack(TechItems.MOLD_RACKWHEEL_PIECE), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_mold_rackwheel"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(TechItems.UNFIRED_SLEEVE)), new ItemStack(TechItems.MOLD_SLEEVE), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_mold_sleeve"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(TechItems.ASH_POT)), new ItemStack(TechItems.POTASH_POT), 500f, Metal.Tier.TIER_I).setRegistryName("potash_pot"),
      new HeatRecipeSimple(IIngredient.of("rockFlux"), new ItemStack(TechItems.LIME, 2), 600f, Metal.Tier.TIER_I).setRegistryName("lime"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(TechItems.UNFIRED_MOLD_PANE)), new ItemStack(TechItems.MOLD_PANE), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_mold_pane"),
      new HeatRecipeSimple(IIngredient.of(new ItemStack(TechItems.UNFIRED_MOLD_BLOCK)), new ItemStack(TechItems.MOLD_BLOCK), 1599f, Metal.Tier.TIER_I).setRegistryName("fired_mold_block")
    );

    IForgeRegistryModifiable<HeatRecipe> modRegistry = (IForgeRegistryModifiable<HeatRecipe>) event.getRegistry();
    if (TechConfig.TWEAKS.removeGlassRecipes) {
      modRegistry.remove(new ResourceLocation(Mods.Names.TFC, "glass"));
      modRegistry.remove(new ResourceLocation(Mods.Names.TFC, "glass_shard"));
    }

  }

  @SubscribeEvent
  public static void onRegisterAnvilRecipeEvent(RegistryEvent.Register<AnvilRecipe> event) {
    IForgeRegistry<AnvilRecipe> r = event.getRegistry();
    r.register(new AnvilRecipe(new ResourceLocation(TFCTECH, "iron_groove"), IIngredient.of(ItemTechMetal.get(Metal.WROUGHT_IRON, ItemTechMetal.ItemType.STRIP)), new ItemStack(TechItems.IRON_GROOVE), Metal.Tier.TIER_III, null, ForgeRule.HIT_LAST, ForgeRule.BEND_SECOND_LAST, ForgeRule.BEND_THIRD_LAST));
    r.register(new AnvilRecipe(new ResourceLocation(TFCTECH, "iron_bowl_mount"), IIngredient.of(ItemMetal.get(Metal.WROUGHT_IRON, Metal.ItemType.INGOT)), new ItemStack(TechItems.IRON_BOWL_MOUNT), Metal.Tier.TIER_III, null, ForgeRule.BEND_LAST, ForgeRule.DRAW_SECOND_LAST, ForgeRule.BEND_NOT_LAST));

    r.register(new AnvilRecipe(new ResourceLocation(TFCTECH, "iron_tongs"), IIngredient.of(ItemMetal.get(Metal.WROUGHT_IRON, Metal.ItemType.INGOT)), new ItemStack(TechItems.IRON_TONGS), Metal.Tier.TIER_III, null, ForgeRule.HIT_LAST, ForgeRule.DRAW_SECOND_LAST, ForgeRule.BEND_THIRD_LAST));
    r.register(new AnvilRecipe(new ResourceLocation(TFCTECH, "iron_draw_plate"), IIngredient.of(ItemMetal.get(Metal.WROUGHT_IRON, Metal.ItemType.INGOT)), new ItemStack(TechItems.IRON_DRAW_PLATE), Metal.Tier.TIER_III, null, ForgeRule.PUNCH_LAST, ForgeRule.PUNCH_SECOND_LAST, ForgeRule.HIT_ANY));
    r.register(new AnvilRecipe(new ResourceLocation(TFCTECH, "steel_draw_plate"), IIngredient.of(ItemMetal.get(TFCRegistries.METALS.getValue(new ResourceLocation(Mods.Names.TFC, "steel")), Metal.ItemType.INGOT)), new ItemStack(TechItems.STEEL_DRAW_PLATE), Metal.Tier.TIER_IV, null, ForgeRule.PUNCH_LAST, ForgeRule.PUNCH_SECOND_LAST, ForgeRule.HIT_ANY));
    r.register(new AnvilRecipe(new ResourceLocation(TFCTECH, "black_steel_draw_plate"), IIngredient.of(ItemMetal.get(TFCRegistries.METALS.getValue(new ResourceLocation(Mods.Names.TFC, "black_steel")), Metal.ItemType.INGOT)), new ItemStack(TechItems.BLACK_STEEL_DRAW_PLATE), Metal.Tier.TIER_V, null, ForgeRule.PUNCH_LAST, ForgeRule.PUNCH_SECOND_LAST, ForgeRule.HIT_ANY));

    for (Metal metal : TFCRegistries.METALS.getValuesCollection()) {
      if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false)) {continue;}
      IIngredient<ItemStack> ingredient;
      //Register all wires
      Item item = ItemTechMetal.get(metal, ItemTechMetal.ItemType.STRIP);
      if (item != null) {
        ingredient = IIngredient.of(new ItemStack(item));
        Item outputItem = ItemTechMetal.get(metal, ItemTechMetal.ItemType.WIRE);
        if (outputItem != null) {
          ItemStack output = new ItemStack(outputItem, 1, 4); //using meta as stage selector
          if (!output.isEmpty()) {
            //noinspection ConstantConditions
            r.register(new AnvilRecipe(new ResourceLocation(TFCTECH, (metal.getRegistryName().getPath()).toLowerCase()
                                                                     + "_wire"), ingredient, output, metal.getTier(), null, ForgeRule.DRAW_LAST, ForgeRule.DRAW_NOT_LAST));
          }
        }
      }

      //Register all long rods
      ingredient = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.INGOT)));
      item = ItemTechMetal.get(metal, ItemTechMetal.ItemType.LONG_ROD);
      if (item != null) {
        ItemStack output = new ItemStack(item);
        if (!output.isEmpty()) {
          //noinspection ConstantConditions
          r.register(new AnvilRecipe(new ResourceLocation(TFCTECH, (metal.getRegistryName().getPath()).toLowerCase()
                                                                   + "_long_rod"), ingredient, output, metal.getTier(), null, ForgeRule.HIT_LAST, ForgeRule.HIT_SECOND_LAST, ForgeRule.HIT_THIRD_LAST));
        }
      }

      //Register all blowpipes
      ingredient = IIngredient.of(new ItemStack(ItemMetal.get(metal, Metal.ItemType.SHEET)));
      item = ItemBlowpipe.get(metal);
      if (item != null) {
        ItemStack output = new ItemStack(item);
        if (!output.isEmpty()) {
          //noinspection ConstantConditions
          r.register(new AnvilRecipe(new ResourceLocation(TFCTECH, (metal.getRegistryName().getPath()).toLowerCase()
                                                                   + "_blowpipe"), ingredient, output, metal.getTier(), null, ForgeRule.BEND_LAST, ForgeRule.BEND_SECOND_LAST));
        }
      }
    }
  }

  @SubscribeEvent
  public static void onRegisterKnappingRecipeEvent(RegistryEvent.Register<KnappingRecipe> event) {
    IForgeRegistry<KnappingRecipe> r = event.getRegistry();

    r.registerAll(
      new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(TechItems.UNFIRED_SLEEVE), "XXXXX", "XX XX", "X X X", "XX XX", "XXXXX").setRegistryName("clay_sleeve"),
      new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(TechItems.UNFIRED_RACKWHEEL_PIECE), "XXXXX", "X XXX", "X  XX", "XX  X", "XXXXX").setRegistryName("clay_rackwheel_piece"),
      new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(TechItems.UNFIRED_MOLD_PANE), "XXXXX", "X   X", "X   X", "X   X", "XXXXX").setRegistryName("clay_mold_pane"),
      new KnappingRecipeSimple(KnappingType.CLAY, true, new ItemStack(TechItems.UNFIRED_MOLD_BLOCK), "X   X", "X   X", "X   X", "X   X", " XXX ").setRegistryName("clay_mold_block")
    );
  }

  @SubscribeEvent
  public static void onRegisterGlassworkingRecipeEvent(RegistryEvent.Register<GlassworkingRecipe> event) {
    IForgeRegistry<GlassworkingRecipe> r = event.getRegistry();

    r.registerAll(
      new GlassworkingRecipe(new ItemStack(Items.GLASS_BOTTLE),
        " X X ", " X X ", "X   X", "X   X", " XXX ").setRegistryName(TFCTECH, "glass_bottle")
    );
  }

  @SubscribeEvent
  public static void onRegisterSmelteryRecipeEvent(RegistryEvent.Register<SmelteryRecipe> event) {
    IForgeRegistry<SmelteryRecipe> r = event.getRegistry();
    r.registerAll(
      new SmelteryRecipe.Builder()
        .addInput(IIngredient.of("dustPotash")).addInput(IIngredient.of("sandSilica")).addInput(IIngredient.of("dustLime"))
        .setOutput(new FluidStack(FluidsCore.GLASS.get(), 1000), 800).build()
        .setRegistryName(new ResourceLocation(TFCTECH, "glass")),
      new SmelteryRecipe.Builder()
        .addInput(IIngredient.of("blockGlass"))
        .setOutput(new FluidStack(FluidsCore.GLASS.get(), 1000), 800).build()
        .setRegistryName(new ResourceLocation(TFCTECH, "glass_block")),
      new SmelteryRecipe.Builder()
        .addInput(IIngredient.of("paneGlass"))
        .setOutput(new FluidStack(FluidsCore.GLASS.get(), 375), 800).build()
        .setRegistryName(new ResourceLocation(TFCTECH, "glass_pane")),
      new SmelteryRecipe.Builder()
        .addInput(IIngredient.of(ItemsTFC.GLASS_SHARD))
        .setOutput(new FluidStack(FluidsCore.GLASS.get(), 500), 800).build()
        .setRegistryName(new ResourceLocation(TFCTECH, "glass_shard")),
      new SmelteryRecipe.Builder()
        .addInput(IIngredient.of(Items.GLASS_BOTTLE))
        .setOutput(new FluidStack(FluidsCore.GLASS.get(), 250), 800).build()
        .setRegistryName(new ResourceLocation(TFCTECH, "glass_bottle"))
    );
  }

  @SubscribeEvent
  public static void onRegisterWireDrawingRecipeEvent(RegistryEvent.Register<WireDrawingRecipe> event) {
    IForgeRegistry<WireDrawingRecipe> r = event.getRegistry();
    //Register all wires
    for (Metal metal : TFCRegistries.METALS.getValuesCollection()) {
      if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false)) {continue;}
      for (int i = 4; i > 0; i--) {
        IIngredient<ItemStack> ingredient = IIngredient.of(new ItemStack(ItemTechMetal.get(metal, ItemTechMetal.ItemType.WIRE), 1, i));
        ItemStack output = new ItemStack(ItemTechMetal.get(metal, ItemTechMetal.ItemType.WIRE), 1, i - 1);
        Metal.Tier tier = metal.getTier();
        int color = metal.getColor();
        if (!output.isEmpty()) {
          //noinspection ConstantConditions
          r.register(new WireDrawingRecipe(new ResourceLocation(TFCTECH, (metal.getRegistryName().getPath()).toLowerCase() + "_wire_"
                                                                         + i), ingredient, tier, output, color));
        }
      }
    }
  }

  @SubscribeEvent
  public static void onRegisterWeldingRecipeEvent(RegistryEvent.Register<WeldingRecipe> event) {
    IForgeRegistry<WeldingRecipe> r = event.getRegistry();
    // Register all gear (rackwheel + sleeve) welding recipes
    // Tier I-II = Tin sleeve
    // Tier III-IV = Brass sleeve
    // Tier V-VI = Steel sleeve
    for (Metal metal : TFCRegistries.METALS.getValuesCollection()) {
      if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false)) {continue;}
      IIngredient<ItemStack> ingredient1 = IIngredient.of(new ItemStack(ItemTechMetal.get(metal, ItemTechMetal.ItemType.RACKWHEEL)));
      IIngredient<ItemStack> ingredient2;
      if (metal.getTier().isAtMost(Metal.Tier.TIER_II)) {
        ingredient2 = IIngredient.of(new ItemStack(TechItems.TIN_SLEEVE));
      } else if (metal.getTier().isAtMost(Metal.Tier.TIER_IV)) {
        ingredient2 = IIngredient.of(new ItemStack(TechItems.BRASS_SLEEVE));
      } else {
        ingredient2 = IIngredient.of(new ItemStack(TechItems.STEEL_SLEEVE));
      }
      ItemStack output = new ItemStack(ItemTechMetal.get(metal, ItemTechMetal.ItemType.GEAR));
      if (!output.isEmpty()) {
        //noinspection ConstantConditions
        r.register(new WeldingRecipe(new ResourceLocation(TFCTECH, (metal.getRegistryName().getPath()).toLowerCase()
                                                                   + "_gear"), ingredient1, ingredient2, output, metal.getTier(), null));
      }
    }
  }

  @SubscribeEvent
  public static void onRegisterCraftingRecipeEvent(RegistryEvent.Register<IRecipe> event) {
    IForgeRegistry<IRecipe> r = event.getRegistry();
    //Register all strips
    List<ItemStack> allChisels = new ArrayList<>();
    for (Metal metal : TFCRegistries.METALS.getValuesCollection()) {
      if (!metal.isToolMetal()) {continue;}
      allChisels.add(new ItemStack(ItemMetal.get(metal, Metal.ItemType.CHISEL), 1, OreDictionary.WILDCARD_VALUE));
    }
    Ingredient chisel = Ingredient.fromStacks(allChisels.toArray(new ItemStack[0]));

    ResourceLocation groupStrip = new ResourceLocation(TFCTECH, "strip");
    ResourceLocation groupRod = new ResourceLocation(TFCTECH, "rod");
    ResourceLocation groupBolt = new ResourceLocation(TFCTECH, "bolt");
    ResourceLocation groupScrew = new ResourceLocation(TFCTECH, "screw");
    for (Metal metal : TFCRegistries.METALS.getValuesCollection()) {
      if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false)) {continue;}

      /*
       * Strips
       */
      Ingredient ingredient = Ingredient.fromStacks(new ItemStack(ItemMetal.get(metal, Metal.ItemType.SHEET)));
      ItemStack output = new ItemStack(ItemTechMetal.get(metal, ItemTechMetal.ItemType.STRIP), 4);
      if (!output.isEmpty()) {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(chisel);
        list.add(ingredient);
        //noinspection ConstantConditions
        r.register(new ShapelessDamageRecipe(groupStrip, list, output, 1).setRegistryName(TFCTECH, metal.getRegistryName().getPath().toLowerCase() + "_strip"));
      }

      /*
       * Rods
       */
      ingredient = Ingredient.fromStacks(new ItemStack(ItemTechMetal.get(metal, ItemTechMetal.ItemType.LONG_ROD)));
      output = new ItemStack(ItemTechMetal.get(metal, ItemTechMetal.ItemType.ROD), 2);
      if (!output.isEmpty()) {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(chisel);
        list.add(ingredient);
        //noinspection ConstantConditions
        r.register(new ShapelessDamageRecipe(groupRod, list, output, 1).setRegistryName(TFCTECH, metal.getRegistryName().getPath().toLowerCase() + "_rod"));
      }

      /*
       * Bolts
       */
      ingredient = Ingredient.fromStacks(new ItemStack(ItemTechMetal.get(metal, ItemTechMetal.ItemType.ROD)));
      output = new ItemStack(ItemTechMetal.get(metal, ItemTechMetal.ItemType.BOLT), 2);
      if (!output.isEmpty()) {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(chisel);
        list.add(ingredient);
        //noinspection ConstantConditions
        r.register(new ShapelessDamageRecipe(groupBolt, list, output, 1).setRegistryName(TFCTECH, metal.getRegistryName().getPath().toLowerCase() + "_bolt"));
      }

      /*
       * Screws
       */
      ingredient = Ingredient.fromStacks(new ItemStack(ItemTechMetal.get(metal, ItemTechMetal.ItemType.BOLT)));
      output = new ItemStack(ItemTechMetal.get(metal, ItemTechMetal.ItemType.SCREW));
      if (!output.isEmpty()) {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(chisel);
        list.add(ingredient);
        //noinspection ConstantConditions
        r.register(new ShapelessDamageRecipe(groupScrew, list, output, 1).setRegistryName(TFCTECH, metal.getRegistryName().getPath().toLowerCase() + "_screw"));
      }

      /*
       * Rackwheels
       */
      ingredient = Ingredient.fromStacks(new ItemStack(ItemTechMetal.get(metal, ItemTechMetal.ItemType.RACKWHEEL_PIECE)));
      output = new ItemStack(ItemTechMetal.get(metal, ItemTechMetal.ItemType.RACKWHEEL));
      if (!output.isEmpty()) {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(ingredient);
        list.add(ingredient);
        list.add(ingredient);
        list.add(ingredient);
        //noinspection ConstantConditions
        r.register(new ShapedRecipes("rackwheel", 2, 2, list, output).setRegistryName(TFCTECH, metal.getRegistryName().getPath().toLowerCase() + "_rackwheel"));
      }
    }
  }
}
