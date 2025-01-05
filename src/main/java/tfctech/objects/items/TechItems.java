package tfctech.objects.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import com.google.common.collect.ImmutableList;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.ToolMaterialsTFC;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemPottery;
import net.dries007.tfc.util.OreDictionaryHelper;

import su.terrafirmagreg.api.data.enums.Mods;

import tfctech.objects.blocks.TechBlocks;
import tfctech.objects.items.ceramics.ItemFluidBowl;
import tfctech.objects.items.ceramics.ItemTechMold;
import tfctech.objects.items.glassworking.ItemBlowpipe;
import tfctech.objects.items.glassworking.ItemGlassMolder;
import tfctech.objects.items.metal.ItemGroove;
import tfctech.objects.items.metal.ItemTechMetal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

import static net.dries007.tfc.objects.CreativeTabsTFC.CT_METAL;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_MISC;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_POTTERY;
import static net.dries007.tfc.util.Helpers.getNull;
import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCTECH;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = TFCTECH)
@GameRegistry.ObjectHolder(TFCTECH)
public final class TechItems {

  @GameRegistry.ObjectHolder("metal/iron_groove")
  public static final ItemGroove IRON_GROOVE = getNull();
  @GameRegistry.ObjectHolder("metal/iron_bowl_mount")
  public static final ItemTechMetal IRON_BOWL_MOUNT = getNull();
  @GameRegistry.ObjectHolder("ceramics/fluid_bowl")
  public static final ItemFluidBowl FLUID_BOWL = getNull();
  @GameRegistry.ObjectHolder("latex/vulcanizing_agents")
  public static final ItemMiscTech VULCANIZING_AGENTS = getNull();
  @GameRegistry.ObjectHolder("latex/rubber_mix")
  public static final ItemMiscHeatable RUBBER_MIX = getNull();
  @GameRegistry.ObjectHolder("latex/rubber")
  public static final ItemMiscHeatable RUBBER = getNull();

  // Glassworking
  @GameRegistry.ObjectHolder("ceramics/unfired/glass_block")
  public static final ItemPottery UNFIRED_MOLD_BLOCK = getNull();
  @GameRegistry.ObjectHolder("ceramics/mold/glass_block")
  public static final ItemGlassMolder MOLD_BLOCK = getNull();
  @GameRegistry.ObjectHolder("ceramics/unfired/glass_pane")
  public static final ItemPottery UNFIRED_MOLD_PANE = getNull();
  @GameRegistry.ObjectHolder("ceramics/mold/glass_pane")
  public static final ItemGlassMolder MOLD_PANE = getNull();
  @GameRegistry.ObjectHolder("powder/lime")
  public static final ItemMiscTech LIME = getNull();
  @GameRegistry.ObjectHolder("powder/potash")
  public static final ItemMiscTech POTASH = getNull();
  @GameRegistry.ObjectHolder("pot_potash")
  public static final ItemPottery POTASH_POT = getNull();
  @GameRegistry.ObjectHolder("powder/ash")
  public static final ItemMiscTech ASH = getNull();
  @GameRegistry.ObjectHolder("pot_ash")
  public static final ItemPottery ASH_POT = getNull();


  @GameRegistry.ObjectHolder("metal/iron_draw_plate")
  public static final ItemTechMetal IRON_DRAW_PLATE = getNull();
  @GameRegistry.ObjectHolder("metal/steel_draw_plate")
  public static final ItemTechMetal STEEL_DRAW_PLATE = getNull();
  @GameRegistry.ObjectHolder("metal/black_steel_draw_plate")
  public static final ItemTechMetal BLACK_STEEL_DRAW_PLATE = getNull();
  @GameRegistry.ObjectHolder("metal/iron_tongs")
  public static final ItemTechMetal IRON_TONGS = getNull();
  @GameRegistry.ObjectHolder("wiredraw/leather_belt")
  public static final ItemMiscTech LEATHER_BELT = getNull();
  @GameRegistry.ObjectHolder("wiredraw/winch")
  public static final ItemMiscTech WINCH = getNull();

  @GameRegistry.ObjectHolder("metal/copper_inductor")
  public static final ItemTechMetal COPPER_INDUCTOR = getNull();

  @GameRegistry.ObjectHolder("metal/tin_sleeve")
  public static final ItemTechMetal TIN_SLEEVE = getNull();
  @GameRegistry.ObjectHolder("metal/brass_sleeve")
  public static final ItemTechMetal BRASS_SLEEVE = getNull();
  @GameRegistry.ObjectHolder("metal/steel_sleeve")
  public static final ItemTechMetal STEEL_SLEEVE = getNull();

  @GameRegistry.ObjectHolder("ceramics/unfired/rackwheel_piece")
  public static final ItemPottery UNFIRED_RACKWHEEL_PIECE = getNull();
  @GameRegistry.ObjectHolder("ceramics/mold/rackwheel_piece")
  public static final ItemTechMold MOLD_RACKWHEEL_PIECE = getNull();

  @GameRegistry.ObjectHolder("ceramics/unfired/sleeve")
  public static final ItemPottery UNFIRED_SLEEVE = getNull();
  @GameRegistry.ObjectHolder("ceramics/mold/sleeve")
  public static final ItemTechMold MOLD_SLEEVE = getNull();


  private static ImmutableList<Item> allSimpleItems;
  private static ImmutableList<Item> allMetalItems;
  private static ImmutableList<Item> allCeramicMoldItems;

  public static ImmutableList<Item> getAllSimpleItems() {
    return allSimpleItems;
  }

  public static ImmutableList<Item> getAllMetalItems() {
    return allMetalItems;
  }

  public static ImmutableList<Item> getAllCeramicMoldItems() {
    return allCeramicMoldItems;
  }

  @SubscribeEvent
  public static void registerItems(RegistryEvent.Register<Item> event) {
    IForgeRegistry<Item> r = event.getRegistry();
    ImmutableList.Builder<Item> simpleItems = ImmutableList.builder();

    simpleItems.add(register(r, "pot_ash", new ItemPottery(), CT_MISC));
    simpleItems.add(register(r, "pot_potash", new ItemPottery() {
      @Nonnull
      @Override
      public ItemStack getContainerItem(@Nonnull ItemStack itemStack) {
        return new ItemStack(ItemsTFC.FIRED_POT);
      }

      @Override
      public boolean hasContainerItem(@Nonnull ItemStack stack) {
        return true;
      }
    }, CT_MISC));
    simpleItems.add(register(r, "powder/potash", new ItemMiscTech(Size.SMALL, Weight.LIGHT, "dustPotash"), CT_MISC));
    simpleItems.add(register(r, "powder/lime", new ItemMiscHeatable(Size.SMALL, Weight.LIGHT, 0.22f, 2000f, "dustLime"), CT_MISC));

    simpleItems.add(register(r, "latex/vulcanizing_agents", new ItemMiscTech(Size.SMALL, Weight.LIGHT), CT_MISC));
    simpleItems.add(register(r, "latex/rubber_mix", new ItemMiscHeatable(Size.SMALL, Weight.LIGHT, 0.8f, 800f), CT_MISC));
    simpleItems.add(register(r, "latex/rubber", new ItemMiscHeatable(Size.SMALL, Weight.LIGHT, 0.8f, 800f, "rubber"), CT_MISC));

    simpleItems.add(register(r, "wiredraw/leather_belt", new ItemMiscTech(Size.NORMAL, Weight.LIGHT), CT_MISC));
    simpleItems.add(register(r, "wiredraw/winch", new ItemMiscTech(Size.NORMAL, Weight.MEDIUM), CT_MISC));

    //Unfired is simple
    simpleItems.add(register(r, "ceramics/unfired/sleeve", new ItemPottery(), CT_MISC));
    simpleItems.add(register(r, "ceramics/unfired/rackwheel_piece", new ItemPottery(), CT_MISC));
    simpleItems.add(register(r, "ceramics/unfired/glass_block", new ItemPottery(), CT_MISC));
    simpleItems.add(register(r, "ceramics/unfired/glass_pane", new ItemPottery(), CT_MISC));

    allSimpleItems = simpleItems.build();

    ImmutableList.Builder<Item> ceramicItems = ImmutableList.builder();
    ceramicItems.add(register(r, "ceramics/mold/glass_block", new ItemGlassMolder(ItemGlassMolder.BLOCK_TANK), CT_MISC));
    ceramicItems.add(register(r, "ceramics/mold/glass_pane", new ItemGlassMolder(ItemGlassMolder.PANE_TANK), CT_MISC));
    ceramicItems.add(register(r, "ceramics/mold/rackwheel_piece", new ItemTechMold(ItemTechMetal.ItemType.RACKWHEEL_PIECE), CT_MISC));
    // This one is special since we only have 3 sleeves: tin, brass and steel
    // In 1.15, refactor the mod to properly use recipes instead of this ugly code
    ItemTechMold sleeveMold = new ItemTechMold(ItemTechMetal.ItemType.SLEEVE) {
      @Override
      public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FilledMoldCapability(nbt) {
          @Override
          public int fill(FluidStack resource, boolean doFill) {
            if (resource != null) {
              Metal metal = FluidsTFC.getMetalFromFluid(resource.getFluid());
              if (isValidMetal(metal)) {
                return super.fill(resource, doFill);
              }
            }
            return 0;
          }

          private boolean isValidMetal(@Nullable Metal metal) {
            if (metal != null) {
              //noinspection ConstantConditions
              return "tin".equals(metal.getRegistryName().getPath()) ||
                     "brass".equals(metal.getRegistryName().getPath()) ||
                     "steel".equals(metal.getRegistryName().getPath());
            }
            return false;
          }
        };
      }
    };
    ceramicItems.add(register(r, "ceramics/mold/sleeve", sleeveMold, CT_MISC));
    allCeramicMoldItems = ceramicItems.build();

    ImmutableList.Builder<Item> metalItems = ImmutableList.builder();

    metalItems.add(register(r, "metal/iron_groove", ItemTechMetal.ItemType.create(Metal.WROUGHT_IRON, ItemTechMetal.ItemType.GROOVE), CT_METAL));
    metalItems.add(register(r, "metal/iron_bowl_mount", ItemTechMetal.ItemType.create(Metal.WROUGHT_IRON, ItemTechMetal.ItemType.BOWL_MOUNT), CT_METAL));

    metalItems.add(register(r, "metal/iron_draw_plate", ItemTechMetal.ItemType.create(Metal.WROUGHT_IRON, ItemTechMetal.ItemType.DRAW_PLATE)
                                                                              .setMaxDamage(ToolMaterialsTFC.WROUGHT_IRON.getMaxUses())
                                                                              .setMaxStackSize(1), CT_METAL));
    metalItems.add(register(r, "metal/steel_draw_plate", ItemTechMetal.ItemType.create(TFCRegistries.METALS.getValue(new ResourceLocation(Mods.Names.TFC, "steel")), ItemTechMetal.ItemType.DRAW_PLATE)
                                                                               .setMaxDamage(ToolMaterialsTFC.STEEL.getMaxUses())
                                                                               .setMaxStackSize(1), CT_METAL));
    metalItems.add(register(r, "metal/black_steel_draw_plate", ItemTechMetal.ItemType.create(TFCRegistries.METALS.getValue(new ResourceLocation(Mods.Names.TFC, "black_steel")), ItemTechMetal.ItemType.DRAW_PLATE)
                                                                                     .setMaxDamage(ToolMaterialsTFC.BLACK_STEEL.getMaxUses())
                                                                                     .setMaxStackSize(1), CT_METAL));
    metalItems.add(register(r, "metal/iron_tongs", ItemTechMetal.ItemType.create(Metal.WROUGHT_IRON, ItemTechMetal.ItemType.TONGS)
                                                                         .setMaxStackSize(1), CT_MISC));

    metalItems.add(register(r, "metal/copper_inductor", ItemTechMetal.ItemType.create(TFCRegistries.METALS.getValue(new ResourceLocation(Mods.Names.TFC, "copper")), ItemTechMetal.ItemType.INDUCTOR), CT_METAL));

    metalItems.add(register(r, "metal/tin_sleeve", ItemTechMetal.ItemType.create(TFCRegistries.METALS.getValue(new ResourceLocation(Mods.Names.TFC, "tin")), ItemTechMetal.ItemType.SLEEVE), CT_METAL));
    metalItems.add(register(r, "metal/brass_sleeve", ItemTechMetal.ItemType.create(TFCRegistries.METALS.getValue(new ResourceLocation(Mods.Names.TFC, "brass")), ItemTechMetal.ItemType.SLEEVE), CT_METAL));
    metalItems.add(register(r, "metal/steel_sleeve", ItemTechMetal.ItemType.create(TFCRegistries.METALS.getValue(new ResourceLocation(Mods.Names.TFC, "steel")), ItemTechMetal.ItemType.SLEEVE), CT_METAL));

    for (Metal metal : TFCRegistries.METALS.getValuesCollection()) {
      if (ObfuscationReflectionHelper.getPrivateValue(Metal.class, metal, "usable").equals(false)) {continue;}
      //noinspection ConstantConditions
      metalItems.add(register(r, "metal/" + metal.getRegistryName().getPath().toLowerCase()
                                 + "_strip", ItemTechMetal.ItemType.create(metal, ItemTechMetal.ItemType.STRIP), CT_METAL));
      metalItems.add(register(r, "metal/" + metal.getRegistryName().getPath().toLowerCase()
                                 + "_rackwheel_piece", ItemTechMetal.ItemType.create(metal, ItemTechMetal.ItemType.RACKWHEEL_PIECE), CT_METAL));
      metalItems.add(register(r, "metal/" + metal.getRegistryName().getPath().toLowerCase()
                                 + "_rackwheel", ItemTechMetal.ItemType.create(metal, ItemTechMetal.ItemType.RACKWHEEL), CT_METAL));
      metalItems.add(register(r, "metal/" + metal.getRegistryName().getPath().toLowerCase()
                                 + "_gear", ItemTechMetal.ItemType.create(metal, ItemTechMetal.ItemType.GEAR), CT_METAL));
      metalItems.add(register(r, "metal/" + metal.getRegistryName().getPath().toLowerCase()
                                 + "_wire", ItemTechMetal.ItemType.create(metal, ItemTechMetal.ItemType.WIRE), CT_METAL));
      metalItems.add(register(r, "metal/" + metal.getRegistryName().getPath().toLowerCase()
                                 + "_long_rod", ItemTechMetal.ItemType.create(metal, ItemTechMetal.ItemType.LONG_ROD), CT_METAL));
      metalItems.add(register(r, "metal/" + metal.getRegistryName().getPath().toLowerCase()
                                 + "_rod", ItemTechMetal.ItemType.create(metal, ItemTechMetal.ItemType.ROD), CT_METAL));
      metalItems.add(register(r, "metal/" + metal.getRegistryName().getPath().toLowerCase()
                                 + "_bolt", ItemTechMetal.ItemType.create(metal, ItemTechMetal.ItemType.BOLT), CT_METAL));
      metalItems.add(register(r, "metal/" + metal.getRegistryName().getPath().toLowerCase()
                                 + "_screw", ItemTechMetal.ItemType.create(metal, ItemTechMetal.ItemType.SCREW), CT_METAL));
      if (metal.getTier().isAtLeast(Metal.Tier.TIER_III) && metal.getToolMetal() != null) {
        metalItems.add(register(r, "metal/" + metal.getRegistryName().getPath().toLowerCase() + "_blowpipe", new ItemBlowpipe(metal), CT_METAL));
      }
    }

    allMetalItems = metalItems.build();

    register(r, "ceramics/fluid_bowl", new ItemFluidBowl(), CT_POTTERY);

    TechBlocks.getAllInventoryItemBlocks().forEach(x -> registerItemBlock(r, x));
    TechBlocks.getAllTEISRBlocks().forEach(x -> registerItemBlock(r, x));

    //Register oredict for metal item components
    for (Item metalItem : allMetalItems) {
      if (metalItem instanceof ItemTechMetal) {
        ItemTechMetal techMetal = (ItemTechMetal) metalItem;
        if (techMetal.getType() == ItemTechMetal.ItemType.ROD) {
          OreDictionary.registerOre(OreDictionaryHelper.toString("stick", techMetal.getMetal(ItemStack.EMPTY)), new ItemStack(metalItem, 1, 0));
        } else if (techMetal.getType() == ItemTechMetal.ItemType.LONG_ROD) {
          OreDictionary.registerOre(OreDictionaryHelper.toString("stick", "long", techMetal.getMetal(ItemStack.EMPTY)), new ItemStack(metalItem, 1, 0));
        } else {
          OreDictionary.registerOre(OreDictionaryHelper.toString(techMetal.getType(), techMetal.getMetal(ItemStack.EMPTY)), new ItemStack(metalItem, 1, 0));
        }
      } else {
        Metal metal = ((IMetalItem) metalItem).getMetal(ItemStack.EMPTY);
        OreDictionary.registerOre(OreDictionaryHelper.toString("blowpipe"), new ItemStack(metalItem, 1, 0));
      }
    }

    for (Item item : allSimpleItems) {
      if (item instanceof ItemMiscTech && ((ItemMiscTech) item).getOreDictionary() != null) {
        OreDictionary.registerOre(((ItemMiscTech) item).getOreDictionary(), item);
      }
    }
    // This is probably safe since block registers first than items across all mods
    registerTFCOreDict();
  }

  /**
   * Register new ore dictionaries to TFC
   */
  private static void registerTFCOreDict() {
    BlocksTFC.getAllBlockRockVariants().forEach(x -> {
      if (x.getType() == Rock.Type.SAND && isSilica(x)) {
        OreDictionary.registerOre("sandSilica", x);
      }
    });
  }

  private static boolean isSilica(BlockRockVariant block) {
    //noinspection ConstantConditions
    String rockName = block.getRock().getRegistryName().getPath().toLowerCase();
    return rockName.equalsIgnoreCase("chert") ||
           rockName.equalsIgnoreCase("granite") ||
           rockName.equalsIgnoreCase("quartzite") ||
           rockName.equalsIgnoreCase("rhyolite") ||
           rockName.equalsIgnoreCase("phyllite");
  }

  private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
    ItemBlock itemBlock = producer.apply(block);
    //noinspection ConstantConditions
    itemBlock.setRegistryName(block.getRegistryName());
    return itemBlock;
  }

  @SuppressWarnings("ConstantConditions")
  private static void registerItemBlock(IForgeRegistry<Item> r, ItemBlock item) {
    item.setRegistryName(item.getBlock().getRegistryName());
    item.setCreativeTab(item.getBlock().getCreativeTab());
    r.register(item);
  }

  private static <T extends Item> T register(IForgeRegistry<Item> r, String name, T item, CreativeTabs ct) {
    item.setRegistryName(TFCTECH, name);
    item.setTranslationKey(TFCTECH + "." + name.replace('/', '.'));
    item.setCreativeTab(ct);
    r.register(item);
    return item;
  }
}
