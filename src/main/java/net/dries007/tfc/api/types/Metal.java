package net.dries007.tfc.api.types;

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
import net.dries007.tfc.objects.items.metal.ItemMetalShears;
import net.dries007.tfc.objects.items.metal.ItemMetalSheet;
import net.dries007.tfc.objects.items.metal.ItemMetalShield;
import net.dries007.tfc.objects.items.metal.ItemMetalSword;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;
import net.dries007.tfc.objects.items.metal.ItemProspectorPick;
import net.dries007.tfc.util.Helpers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.function.BiFunction;

import static su.terrafirmagreg.api.data.Constants.MODID_TFC;

public class Metal extends IForgeRegistryEntry.Impl<Metal> {

    @GameRegistry.ObjectHolder(MODID_TFC + ":unknown")
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

    @Getter
    private final Tier tier;
    @Getter
    private final float specificHeat;
    @Getter
    private final float meltTemp;
    @Getter
    private final boolean usable;
    @Getter
    private final int color;

    private final Item.ToolMaterial toolMetal;
    private final IArmorMaterialTFC armorMetal;

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
    public Metal(@NotNull ResourceLocation name, Tier tier, boolean usable, float specificHeat, float meltTemp, int color, @Nullable Item.ToolMaterial toolMetal,
                 @Nullable IArmorMaterialTFC armorMetal) {
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
    public IArmorMaterialTFC getArmorMetal() {return armorMetal;}

    public boolean isToolMetal() {
        return getToolMetal() != null;
    }

    public boolean isArmorMetal() {return getArmorMetal() != null;}

    public String getTranslationKey() {
        //noinspection ConstantConditions
        return MODID_TFC + ".types.metal." + getRegistryName().getPath();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public String toString() {
        return getRegistryName().getPath();
    }

    /**
     * Metals / Anvils: T0 - Stone - Work None, Weld T1 T1 - Copper - Work T1, Weld T2 T2 - Bronze / Bismuth Bronze / Black Bronze - Work T2, Weld T3 T3 - Wrought Iron - Work T3,
     * Weld T4 T4 - Steel - Work T4, Weld T5 T5 - Black Steel - Work T5, Weld T6 T6 - Red Steel / Blue Steel - Work T6, Weld T6
     * <p>
     * Devices: T0 - Stone Anvil T1 - Pit Kiln / Fire pit T2 - Forge T3 - Bloomery T4 - Blast Furnace / Crucible
     */
    public enum Tier {
        TIER_0,
        TIER_I,
        TIER_II,
        TIER_III,
        TIER_IV,
        TIER_V,
        TIER_VI;

        private static final Tier[] VALUES = values();

        @NotNull
        public static Tier valueOf(int tier) {
            return tier < 0 || tier > VALUES.length ? TIER_I : VALUES[tier];
        }

        @NotNull
        public Tier next() {
            return this == TIER_VI ? TIER_VI : VALUES[this.ordinal() + 1];
        }

        @NotNull
        public Tier previous() {
            return this == TIER_0 ? TIER_0 : VALUES[this.ordinal() - 1];
        }

        public boolean isAtLeast(@NotNull Tier requiredInclusive) {
            return this.ordinal() >= requiredInclusive.ordinal();
        }

        public boolean isAtMost(@NotNull Tier requiredInclusive) {
            return this.ordinal() <= requiredInclusive.ordinal();
        }
    }

    public enum ItemType {
        INGOT(false, -1, 100, ItemIngot::new, true, "XXXX", "X  X", "X  X", "X  X", "XXXX"),
        DOUBLE_INGOT(false, 200),
        SCRAP(false, 100),
        DUST(false, 100),
        NUGGET(false, 10),
        SHEET(false, 200, ItemMetalSheet::new),
        DOUBLE_SHEET(false, 400),
        ROD(false, 50),

        ANVIL(true, 1400, ItemAnvil::new),
        TUYERE(true, 400),
        LAMP(false, 100, ItemBlockMetalLamp::new),
        TRAPDOOR(false, 200, (metal, itemType) -> new ItemBlock(BlockTrapDoorMetalTFC.get(metal))),

        PICK(true, 100, ItemMetalTool::new),
        PICK_HEAD(true, 100, true, "XXXXX", "X   X", " XXX ", "XXXXX"),
        SHOVEL(true, 100, ItemMetalTool::new),
        SHOVEL_HEAD(true, 100, true, "X   X", "X   X", "X   X", "X   X", "XX XX"),
        AXE(true, 100, ItemMetalTool::new),
        AXE_HEAD(true, 100, true, "X XXX", "    X", "     ", "    X", "X XXX"),
        HOE(true, 100, ItemMetalHoe::new),
        HOE_HEAD(true, 100, true, "XXXXX", "     ", "  XXX", "XXXXX"),
        CHISEL(true, 100, ItemMetalChisel::new),
        CHISEL_HEAD(true, 100, true, "X X", "X X", "X X", "X X", "X X"),
        SWORD(true, 100, ItemMetalSword::new),
        SWORD_BLADE(true, 100, true, "XXX  ", "XX   ", "X   X", "X  XX", " XXXX"),
        MACE(true, 100, ItemMetalTool::new),
        MACE_HEAD(true, 100, true, "XX XX", "X   X", "X   X", "X   X", "XX XX"),
        SAW(true, 100, ItemMetalTool::new),
        SAW_BLADE(true, 100, true, "XXX  ", "XX   ", "X   X", "    X", "  XXX"),
        JAVELIN(true, 100, ItemMetalJavelin::new),
        JAVELIN_HEAD(true, 100, true, "XX   ", "X    ", "     ", "X   X", "XX XX"),
        HAMMER(true, 100, ItemMetalTool::new),
        HAMMER_HEAD(true, 100, true, "XXXXX", "     ", "     ", "XX XX", "XXXXX"),
        PROPICK(true, 100, ItemProspectorPick::new),
        PROPICK_HEAD(true, 100, true, "XXXXX", "    X", " XXX ", " XXXX", "XXXXX"),
        KNIFE(true, 100, ItemMetalTool::new),
        KNIFE_BLADE(true, 100, true, "XX X", "X  X", "X  X", "X  X", "X  X"),
        SCYTHE(true, 100, ItemMetalTool::new),
        SCYTHE_BLADE(true, 100, true, "XXXXX", "X    ", "    X", "  XXX", "XXXXX"),

        ICE_SAW(true, 100, ItemMetalIceSaw::new),
        ICE_SAW_HEAD(true, 100, false),

        SHEARS(true, 200, ItemMetalShears::new),

        UNFINISHED_HELMET(true, 400),
        HELMET(true, 0, 600, ItemMetalArmor::new),
        UNFINISHED_CHESTPLATE(true, 400),
        CHESTPLATE(true, 1, 800, ItemMetalArmor::new),
        UNFINISHED_GREAVES(true, 400),
        GREAVES(true, 2, 600, ItemMetalArmor::new),
        UNFINISHED_BOOTS(true, 200),
        BOOTS(true, 3, 400, ItemMetalArmor::new),

        SHIELD(true, 400, ItemMetalShield::new),
        BUCKET(false, 200, ItemMetalBucket::new);

        @Getter
        private final boolean toolItem; //true if this must be made from a tool item type
        @Getter
        private final int armorSlot; //Which armor slot this armor should go, from 0 = Helmet to 4 = Boots
        @Getter
        private final int smeltAmount;
        private final boolean hasMold;
        private final BiFunction<Metal, ItemType, Item> supplier;
        @Getter
        private final String[] pattern;

        ItemType(boolean toolItem, int armorSlot, int smeltAmount, @NotNull BiFunction<Metal, ItemType, Item> supplier, boolean hasMold, String... moldPattern) {
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

        ItemType(boolean toolItem, int armorSlot, int smeltAmount, @NotNull BiFunction<Metal, ItemType, Item> supplier) {
            this(toolItem, armorSlot, smeltAmount, supplier, false);
        }

        ItemType(boolean toolItem, int smeltAmount, @NotNull BiFunction<Metal, ItemType, Item> supplier) {
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

        public boolean isArmor() {return armorSlot != -1;}

        /**
         * What armor slot this ItemArmor should use? If this is not armor, return the MainHand slot
         *
         * @return which slot this item should be equipped.
         */
        public EntityEquipmentSlot getEquipmentSlot() {
            return switch (armorSlot) {
                case 0 -> EntityEquipmentSlot.HEAD;
                case 1 -> EntityEquipmentSlot.CHEST;
                case 2 -> EntityEquipmentSlot.LEGS;
                case 3 -> EntityEquipmentSlot.FEET;
                default -> EntityEquipmentSlot.MAINHAND;
            };
        }

    }
}
