package net.dries007.tfc.api.types;

import su.terrafirmagreg.api.data.ArmorMaterials;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.objects.blocks.metal.BlockTrapDoorMetalTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockMetalLamp;
import net.dries007.tfc.objects.items.metal.ItemAnvil;
import net.dries007.tfc.objects.items.metal.ItemIngot;
import net.dries007.tfc.objects.items.metal.ItemMetal;
import net.dries007.tfc.objects.items.metal.ItemMetalArmor;
import net.dries007.tfc.objects.items.metal.ItemMetalBucket;
import net.dries007.tfc.objects.items.metal.ItemMetalChisel;
import net.dries007.tfc.objects.items.metal.ItemMetalHoe;
import net.dries007.tfc.objects.items.metal.ItemMetalIceSaw;
import net.dries007.tfc.objects.items.metal.ItemMetalJavelin;
import net.dries007.tfc.objects.items.metal.ItemMetalMallet;
import net.dries007.tfc.objects.items.metal.ItemMetalShears;
import net.dries007.tfc.objects.items.metal.ItemMetalSheet;
import net.dries007.tfc.objects.items.metal.ItemMetalShield;
import net.dries007.tfc.objects.items.metal.ItemMetalSword;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;
import net.dries007.tfc.objects.items.metal.ItemProspectorPick;
import net.dries007.tfc.util.Helpers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiFunction;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFC;

public class Metal extends IForgeRegistryEntry.Impl<Metal> {

  @GameRegistry.ObjectHolder("tfc:unknown")
  public static final Metal UNKNOWN = Helpers.getNull();
  @GameRegistry.ObjectHolder("tfc:wrought_iron")
  public static final Metal WROUGHT_IRON = Helpers.getNull();
  @GameRegistry.ObjectHolder("tfc:gold")
  public static final Metal GOLD = Helpers.getNull();
  @GameRegistry.ObjectHolder("tfc:pig_iron")
  public static final Metal PIG_IRON = Helpers.getNull();
  @GameRegistry.ObjectHolder("tfc:bronze")
  public static final Metal BRONZE = Helpers.getNull();
  @GameRegistry.ObjectHolder("tfc:bismuth_bronze")
  public static final Metal BISMUTH_BRONZE = Helpers.getNull();
  @GameRegistry.ObjectHolder("tfc:steel")
  public static final Metal STEEL = Helpers.getNull();
  @GameRegistry.ObjectHolder("tfc:black_bronze")
  public static final Metal BLACK_BRONZE = Helpers.getNull();
  @GameRegistry.ObjectHolder("tfc:blue_steel")
  public static final Metal BLUE_STEEL = Helpers.getNull();
  @GameRegistry.ObjectHolder("tfc:red_steel")
  public static final Metal RED_STEEL = Helpers.getNull();
  @GameRegistry.ObjectHolder("tfc:black_steel")
  public static final Metal BLACK_STEEL = Helpers.getNull();

  private final Tier tier;
  private final float specificHeat;
  private final float meltTemp;
  private final boolean usable;
  private final int color;

  private final Item.ToolMaterial toolMetal;
  private final ArmorMaterials armorMetal;

  /**
   * This is a registry object that will create a number of things.
   *
   * @param name         the registry name of the object. The path must also be unique
   * @param tier         the tier of the metal
   * @param usable       is the metal usable to create basic metal items? (not tools)
   * @param specificHeat specific heat capacity. Higher = harder to heat up / cool down. Most IRL metals are between 0.3 - 0.7
   * @param meltTemp     melting point. See @link Heat for temperature scale. Similar to IRL melting point in celsius.
   * @param color        color of the metal when in fluid form. Used to auto generate a fluid texture. In future this may be used to color items as well
   * @param toolMetal    The tool material. Null if metal is not able to create tools
   */
  public Metal(@Nonnull ResourceLocation name, Tier tier, boolean usable, float specificHeat, float meltTemp, int color, @Nullable Item.ToolMaterial toolMetal, @Nullable ArmorMaterials armorMetal) {
    this.usable = usable;
    this.tier = tier;
    this.specificHeat = specificHeat;
    this.meltTemp = meltTemp;
    this.color = color;
    this.toolMetal = toolMetal;
    this.armorMetal = armorMetal;

    setRegistryName(name);
  }

  @Nullable
  public Item.ToolMaterial getToolMetal() {
    return toolMetal;
  }

  @Nullable
  public ArmorMaterials getArmorMetal() {return armorMetal;}

  public boolean isToolMetal() {
    return getToolMetal() != null;
  }

  public boolean isArmorMetal() {return getArmorMetal() != null;}

  public Tier getTier() {
    return tier;
  }

  public float getSpecificHeat() {
    return specificHeat;
  }

  public float getMeltTemp() {
    return meltTemp;
  }

  public boolean isUsable() {
    return usable;
  }

  public int getColor() {
    return color;
  }

  public String getTranslationKey() {
    //noinspection ConstantConditions
    return TFC + ".types.metal." + getRegistryName().getPath();
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public String toString() {
    return getRegistryName().getPath();
  }

  /**
   * Metals / Anvils: T0 - Stone - Work None, Weld T1 T1 - Copper - Work T1, Weld T2 T2 - Bronze / Bismuth Bronze / Black Bronze - Work T2, Weld T3 T3 - Wrought
   * Iron - Work T3, Weld T4 T4 - Steel - Work T4, Weld T5 T5 - Black Steel - Work T5, Weld T6 T6 - Red Steel / Blue Steel - Work T6, Weld T6
   * <p>
   * Devices: T0 - Stone Anvil T1 - Pit Kiln / Fire pit T2 - Forge T3 - Bloomery T4 - Blast Furnace / Crucible
   */
  public enum Tier {
    TIER_0, TIER_I, TIER_II, TIER_III, TIER_IV, TIER_V, TIER_VI;

    private static final Tier[] VALUES = values();

    @Nonnull
    public static Tier valueOf(int tier) {
      return tier < 0 || tier > VALUES.length ? TIER_I : VALUES[tier];
    }

    @Nonnull
    public Tier next() {
      return this == TIER_VI ? TIER_VI : VALUES[this.ordinal() + 1];
    }

    @Nonnull
    public Tier previous() {
      return this == TIER_0 ? TIER_0 : VALUES[this.ordinal() - 1];
    }

    public boolean isAtLeast(@Nonnull Tier requiredInclusive) {
      return this.ordinal() >= requiredInclusive.ordinal();
    }

    public boolean isAtMost(@Nonnull Tier requiredInclusive) {
      return this.ordinal() <= requiredInclusive.ordinal();
    }
  }

  public enum ItemType implements IMetalItemType {
    INGOT(false, -1, 144, ItemIngot::new, true, "XXXX", "X  X", "X  X", "X  X", "XXXX"),
    DOUBLE_INGOT(false, 288),
    SCRAP(false, 144),
    DUST(false, 144),
    NUGGET(false, 16),
    SHEET(false, 288, ItemMetalSheet::new),
    DOUBLE_SHEET(false, 576),
    ROD(false, 72),

    // TFCTech
//    BOWL_MOUNT(false, 144),
//    DRAW_PLATE(false, 144),
//    GROOVE(false, 72, ItemGroove::new),
//    INDUCTOR(false, 288),
//    TONGS(false, 144),
//    STRIP(false, 72),
//    LONG_ROD(false, 144),
//    BOLT(false, 25),
//    SCREW(false, 25),
//    SLEEVE(false, 144, true, "XXXXX", "XX XX", "X X X", "XX XX", "XXXXX"),
//    RACKWHEEL_PIECE(false, 144, true, "XXXXX", "X XXX", "X  XX", "XX  X", "XXXXX"),
//    RACKWHEEL(false, 576),
//    GEAR(false, 576, ItemGear::new),
//    WIRE(false, 72, ItemWire::new),
    //BLOWPIPE(true, 288, ItemBlowpipe::new),

    ANVIL(true, 2016, ItemAnvil::new),
    TUYERE(true, 576),
    LAMP(false, 144, (metal, itemType) -> new ItemBlockMetalLamp(metal)),
    TRAPDOOR(false, 288, (metal, itemType) -> new ItemBlock(BlockTrapDoorMetalTFC.get(metal))),

    PICK(true, 432, ItemMetalTool::new),
    PICK_HEAD(true, 432, true, "XXXXX", "X   X", " XXX ", "XXXXX"),
    SHOVEL(true, 144, ItemMetalTool::new),
    SHOVEL_HEAD(true, 144, true, "X   X", "X   X", "X   X", "X   X", "XX XX"),
    AXE(true, 432, ItemMetalTool::new),
    AXE_HEAD(true, 432, true, "X XXX", "    X", "     ", "    X", "X XXX"),
    HOE(true, 288, ItemMetalHoe::new),
    HOE_HEAD(true, 288, true, "XXXXX", "     ", "  XXX", "XXXXX"),
    CHISEL(true, 288, ItemMetalChisel::new),
    CHISEL_HEAD(true, 288, true, "X X", "X X", "X X", "X X", "X X"),
    SWORD(true, 288, (metal, itemType) -> new ItemMetalSword(metal)),
    SWORD_BLADE(true, 288, true, "XXX  ", "XX   ", "X   X", "X  XX", " XXXX"),
    MACE(true, 144, ItemMetalTool::new),
    MACE_HEAD(true, 144, true, "XX XX", "X   X", "X   X", "X   X", "XX XX"),
    SAW(true, 288, ItemMetalTool::new),
    SAW_BLADE(true, 288, true, "XXX  ", "XX   ", "X   X", "    X", "  XXX"),
    JAVELIN(true, 144, ItemMetalJavelin::new),
    JAVELIN_HEAD(true, 144, true, "XX   ", "X    ", "     ", "X   X", "XX XX"),
    HAMMER(true, 864, ItemMetalTool::new),
    HAMMER_HEAD(true, 864, true, "XXXXX", "     ", "     ", "XX XX", "XXXXX"),
    PROPICK(true, 432, ItemProspectorPick::new),
    PROPICK_HEAD(true, 432, true, "XXXXX", "    X", " XXX ", " XXXX", "XXXXX"),
    KNIFE(true, 144, ItemMetalTool::new),
    KNIFE_BLADE(true, 144, true, "XX X", "X  X", "X  X", "X  X", "X  X"),
    SCYTHE(true, 432, ItemMetalTool::new),
    SCYTHE_BLADE(true, 432, true, "XXXXX", "X    ", "    X", "  XXX", "XXXXX"),
    SHEARS(true, 288, ItemMetalShears::new),
    // Cellars
    ICE_SAW(true, 288, ItemMetalIceSaw::new),
    ICE_SAW_HEAD(true, 288, false),
    //Firmalife
    MALLET(true, 144, ItemMetalMallet::new),
    MALLET_HEAD(true, 144, true, "XXXXX", "     ", "   X ", "XXXXX", "XXXXX"),


    UNFINISHED_HELMET(true, 576),
    HELMET(true, 0, 864, ItemMetalArmor::new),
    UNFINISHED_CHESTPLATE(true, 576),
    CHESTPLATE(true, 1, 1152, ItemMetalArmor::new),
    UNFINISHED_GREAVES(true, 576),
    GREAVES(true, 2, 864, ItemMetalArmor::new),
    UNFINISHED_BOOTS(true, 288),
    BOOTS(true, 3, 576, ItemMetalArmor::new),

    SHIELD(true, 576, ItemMetalShield::new),
    BUCKET(false, 288, ItemMetalBucket::new);

    private final boolean toolItem;
    private final int armorSlot; //Which armor slot this armor should go, from 0 = Helmet to 4 = Boots
    private final int smeltAmount;
    private final boolean hasMold;
    private final BiFunction<Metal, ItemType, Item> supplier;
    private final String[] pattern;

    ItemType(boolean toolItem, int armorSlot, int smeltAmount, @Nonnull BiFunction<Metal, ItemType, Item> supplier, boolean hasMold, String... moldPattern) {
      this.toolItem = toolItem;
      this.armorSlot = armorSlot;
      this.smeltAmount = smeltAmount;
      this.supplier = supplier;
      this.hasMold = hasMold;
      this.pattern = moldPattern;
    }

    ItemType(boolean toolItem, int smeltAmount, boolean hasMold, String... moldPattern) {
      this(toolItem, -1, smeltAmount, ItemMetal::new, hasMold, moldPattern);
    }

    ItemType(boolean toolItem, int smeltAmount) {
      this(toolItem, smeltAmount, false);
    }

    ItemType(boolean toolItem, int armorSlot, int smeltAmount, @Nonnull BiFunction<Metal, ItemType, Item> supplier) {
      this(toolItem, armorSlot, smeltAmount, supplier, false);
    }

    ItemType(boolean toolItem, int smeltAmount, @Nonnull BiFunction<Metal, ItemType, Item> supplier) {
      this(toolItem, -1, smeltAmount, supplier, false);
    }

    public static Item create(Metal metal, ItemType type) {
      return type.supplier.apply(metal, type);
    }

    public boolean hasType(Metal metal) {
      if (!metal.isUsable()) {
        return this == ItemType.INGOT;
      } else if (this == ItemType.BUCKET) //only these two metals for buckets
      {
        return metal == BLUE_STEEL || metal == RED_STEEL;
      } else if (this == ItemType.ROD) // only make these for necessary metals
      {
        return metal == WROUGHT_IRON || metal == STEEL || metal == GOLD;
      } else if (this == ItemType.LAMP) // Avoid interfering with iron/steel production
      {
        return metal != PIG_IRON;
      }
      return !this.isToolItem() || metal.getToolMetal() != null;
    }

    /**
     * Used to find out if the type has a mold
     *
     * @param metal Null, if checking across all types. If present, checks if the metal is compatible with the mold type
     * @return if the type + metal combo have a valid mold
     */
    public boolean hasMold(@Nullable Metal metal) {
      if (metal == null) {
        // Query for should the mold exist during registration
        return hasMold;
      }
      if (this == ItemType.INGOT) {
        // All ingots are able to be cast in molds
        return true;
      }
      if (hasMold) {
        // All tool metals can be used in tool molds with tier at most II
        return metal.isToolMetal() && metal.getTier().isAtMost(Tier.TIER_II);
      }
      return false;
    }

    /**
     * Does this item type require a tool metal to be made
     *
     * @return true if this must be made from a tool item type
     */
    public boolean isToolItem() {
      return toolItem;
    }

    public int getArmorSlot() {
      return armorSlot;
    }

    public boolean isArmor() {return armorSlot != -1;}

    /**
     * What armor slot this ItemArmor should use? If this is not armor, return the MainHand slot
     *
     * @return which slot this item should be equipped.
     */
    public EntityEquipmentSlot getEquipmentSlot() {
      switch (armorSlot) {
        case 0:
          return EntityEquipmentSlot.HEAD;
        case 1:
          return EntityEquipmentSlot.CHEST;
        case 2:
          return EntityEquipmentSlot.LEGS;
        case 3:
          return EntityEquipmentSlot.FEET;
        default:
          return EntityEquipmentSlot.MAINHAND;
      }
    }

    public int getSmeltAmount() {
      return smeltAmount;
    }

    public String[] getPattern() {
      return pattern;
    }
  }
}
