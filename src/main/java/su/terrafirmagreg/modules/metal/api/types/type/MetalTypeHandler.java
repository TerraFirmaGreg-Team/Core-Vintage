package su.terrafirmagreg.modules.metal.api.types.type;

import gregtech.api.unification.material.Materials;

public class MetalTypeHandler {
	public static void init() {
		MetalTypes.COPPER = new MetalType("copper", Materials.Copper);
		MetalTypes.BISMUTH_BRONZE = new MetalType("bismuth_bronze", Materials.BismuthBronze);
		MetalTypes.BRONZE = new MetalType("bronze", Materials.Bronze);
		MetalTypes.BLACK_BRONZE = new MetalType("black_bronze", Materials.BlackBronze);
		MetalTypes.BLACK_STEEL = new MetalType("black_steel", Materials.BlackSteel);
		MetalTypes.STEEL = new MetalType("steel", Materials.Steel);
		MetalTypes.RED_STEEL = new MetalType("red_steel", Materials.RedSteel);
		MetalTypes.BLUE_STEEL = new MetalType("blue_steel", Materials.BlueSteel);
		MetalTypes.WROUGHT_IRON = new MetalType("wrought_iron", Materials.WroughtIron);
	}
}
