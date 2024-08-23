package su.terrafirmagreg.modules.metal.api.types.type;

import static gregtech.api.unification.material.Materials.*;

public class MetalTypeHandler {

    public static void init() {

        MetalTypes.BISMUTH = new MetalType.Builder("bismuth")
                .material(Bismuth)
                .heat(0.14f, 270)
                .tier(1)
                .build();

        MetalTypes.BISMUTH_BRONZE = new MetalType.Builder("bismuth_bronze")
                .material(BismuthBronze)
                .heat(0.35f, 985)
                .tier(2)
                .build();

        MetalTypes.BLACK_BRONZE = new MetalType.Builder("black_bronze")
                .material(BlackBronze)
                .heat(0.35f, 1070)
                .tier(2)
                .build();

        MetalTypes.BRASS = new MetalType.Builder("brass")
                .material(Brass)
                .heat(0.35f, 930)
                .tier(1)
                .build();

        MetalTypes.BRONZE = new MetalType.Builder("bronze")
                .material(Bronze)
                .heat(0.35f, 950)
                .tier(2)
                .build();

        MetalTypes.COPPER = new MetalType.Builder("copper")
                .material(Copper)
                .heat(0.35f, 1080)
                .tier(1)
                .build();

        MetalTypes.GOLD = new MetalType.Builder("gold")
                .material(Gold)
                .heat(0.6f, 1060)
                .tier(1)
                .build();

        MetalTypes.LEAD = new MetalType.Builder("lead")
                .material(Lead)
                .heat(0.22f, 328)
                .tier(1)
                .build();

        MetalTypes.NICKEL = new MetalType.Builder("nickel")
                .material(Nickel)
                .heat(0.48f, 1453)
                .tier(1)
                .build();

        MetalTypes.ROSE_GOLD = new MetalType.Builder("rose_gold")
                .material(RoseGold)
                .heat(0.35f, 960)
                .tier(1)
                .build();

        MetalTypes.SILVER = new MetalType.Builder("silver")
                .material(Silver)
                .heat(0.48f, 961)
                .tier(1)
                .build();

        MetalTypes.TIN = new MetalType.Builder("tin")
                .material(Tin)
                .heat(0.14f, 230)
                .tier(1)
                .build();

        MetalTypes.ZINC = new MetalType.Builder("zinc")
                .material(Zinc)
                .heat(0.21f, 420)
                .tier(1)
                .build();

        MetalTypes.STERLING_SILVER = new MetalType.Builder("sterling_silver")
                .material(SterlingSilver)
                .heat(0.35f, 900)
                .tier(1)
                .build();

        MetalTypes.WROUGHT_IRON = new MetalType.Builder("wrought_iron")
                .material(WroughtIron)
                .heat(0.35f, 1535)
                .tier(3)
                .build();

        MetalTypes.PIG_IRON = new MetalType.Builder("pig_iron")
                .material(PigIron)
                .heat(0.35f, 1535)
                .tier(3)
                .build();

        MetalTypes.STEEL = new MetalType.Builder("steel")
                .material(Steel)
                .heat(0.35f, 1540)
                .tier(4)
                .build();

        MetalTypes.PLATINUM = new MetalType.Builder("platinum")
                .material(Platinum)
                .heat(0.35f, 1730)
                .tier(5)
                .build();

        MetalTypes.BLACK_STEEL = new MetalType.Builder("black_steel")
                .material(BlackSteel)
                .heat(0.35f, 1485)
                .tier(5)
                .build();

        MetalTypes.BLUE_STEEL = new MetalType.Builder("blue_steel")
                .material(BlueSteel)
                .heat(0.35f, 1540)
                .tier(6)
                .build();

        MetalTypes.RED_STEEL = new MetalType.Builder("red_steel")
                .material(RedSteel)
                .heat(0.35f, 1540)
                .tier(6)
                .build();

        MetalTypes.WEAK_STEEL = new MetalType.Builder("red_steel")
                .material(WeakSteel)
                .heat(0.35f, 1540)
                .tier(4)
                .build();

        MetalTypes.WEAK_BLUE_STEEL = new MetalType.Builder("red_steel")
                .material(WeakBlueSteel)
                .heat(0.35f, 1540)
                .tier(5)
                .build();

        MetalTypes.WEAK_RED_STEEL = new MetalType.Builder("red_steel")
                .material(WeakRedSteel)
                .heat(0.35f, 1540)
                .tier(5)
                .build();

        MetalTypes.HIGH_CARBON_STEEL = new MetalType.Builder("red_steel")
                .material(HighCarbonSteel)
                .heat(0.35f, 1540)
                .tier(3)
                .build();

        MetalTypes.HIGH_CARBON_BLUE_STEEL = new MetalType.Builder("red_steel")
                .material(HighCarbonBlueSteel)
                .heat(0.35f, 1540)
                .tier(5)
                .build();

        MetalTypes.HIGH_CARBON_RED_STEEL = new MetalType.Builder("red_steel")
                .material(HighCarbonRedSteel)
                .heat(0.35f, 1540)
                .tier(5)
                .build();

        MetalTypes.HIGH_CARBON_BLACK_STEEL = new MetalType.Builder("red_steel")
                .material(HighCarbonBlackSteel)
                .heat(0.35f, 1540)
                .tier(4)
                .build();

        MetalTypes.UNKNOWN = new MetalType.Builder("red_steel")
                .material(Unknown)
                .heat(0.5f, 1250)
                .tier(1)
                .build();

    }
}
