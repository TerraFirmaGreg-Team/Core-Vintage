package su.terrafirmagreg.modules.soil.api.types.type;

public class SoilTypeHandler {

	public static void init() {

		SoilTypes.SILT = new SoilType
				.Builder("silt")
				.build();

		SoilTypes.LOAM = new SoilType
				.Builder("loam")
				.build();

		SoilTypes.SANDY_LOAM = new SoilType
				.Builder("sandy_loam")
				.build();

		SoilTypes.SILTY_LOAM = new SoilType
				.Builder("silty_loam")
				.build();

		SoilTypes.HUMUS = new SoilType
				.Builder("humus")
				.build();
	}
}
