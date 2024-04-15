//package su.terrafirmagreg.modules.arboriculture.api.types.variant.tree;
//
//import net.dries007.tfc.objects.items.food.ItemFoodTFC;
//import net.dries007.tfc.util.agriculture.Food;
//import su.terrafirmagreg.modules.wood.api.types.type.WoodTypes;
//import tfcflorae.objects.items.ItemsTFCF;
//
//import static net.dries007.tfc.types.DefaultTrees.*;
//
//public class WoodTreeVariantHandler {
//
//	public static void configureModules() {
//		WoodTreeVariants.ACACIA = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("acacia", 30f, 210f, 19f, 31f)
//				.setWoodType(WoodTypes.ACACIA)
//				.setHeight(12)
//				.setMinGrowthTime(11)
//				.setDensity(0.1f, 0.6f)
//				.setGenerator(GEN_ACACIA)
//				.setParamMap(0.10f, 14f, 6, 6, 0.90f)
//				.setCellKit("acacia")
//				.build();
//
//		WoodTreeVariants.ASH = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("ash", 60f, 140f, -6f, 12f)
//				.setWoodType(WoodTypes.ASH)
//				.setBushes()
//				.setGenerator(GEN_NORMAL_2)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.25f, 12f, 4, 3, 1.00f)
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.ASPEN = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("aspen", 10f, 80f, -10f, 16f)
//				.setWoodType(WoodTypes.ASPEN)
//				.setMinGrowthTime(8)
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_ASPEN)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 8, 2, 1.50f)
//				.setGrowthLogicKit("conifer")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.BIRCH = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("birch", 20f, 180f, -15f, 7f)
//				.setWoodType(WoodTypes.BIRCH)
//				.setRadius(1)
//				.setGenerator(GEN_TALL_2)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.25f, 12f, 5, 5, 1.15f)
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.BLACKWOOD = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("blackwood", 0f, 120f, 4f, 33f)
//				.setWoodType(WoodTypes.BLACKWOOD)
//				.setHeight(12)
//				.setMinGrowthTime(8)
//				.setGenerator(GEN_MEDIUM)
//				.setParamMap(0.20f, 13f, 3, 4, 0.90f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("darkoak")
//				.build();
//
//		WoodTreeVariants.CHESTNUT = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("chestnut", 160f, 320f, 11f, 35f)
//				.setWoodType(WoodTypes.CHESTNUT)
//				.setBushes()
//				.setGenerator(GEN_NORMAL)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.20f, 10f, 3, 3, 1.00f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.DOUGLAS_FIR = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("douglas_fir", 280f, 480f, -2f, 14f)
//				.setWoodType(WoodTypes.DOUGLAS_FIR)
//				.setDominance(5.2f)
//				.setHeight(16)
//				.setDensity(0.25f, 2f)
//				.setGenerator(GEN_TALL)
//				.setParamMap(0.15f, 20f, 5, 3, 1.15f)
//				.setGrowthLogicKit("conifer")
//				.setCellKit("conifer")
//				.setBushes()
//				.setConifer()
//				.build();
//
//		WoodTreeVariants.HICKORY = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("hickory", 80f, 250f, 7f, 29f)
//				.setWoodType(WoodTypes.HICKORY)
//				.setMinGrowthTime(10)
//				.setBushes()
//				.setGenerator(GEN_TALL_2)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.20f, 14f, 5, 3, 0.80f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.MAPLE = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("maple", 140f, 360f, 3f, 20f)
//				.setWoodType(WoodTypes.MAPLE)
//				.setDominance(6.3f)
//				.setRadius(1)
//				.setGenerator(GEN_MEDIUM_2)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.15f, 15f, 6, 3, 0.95f)
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.OAK = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("oak", 180f, 430f, -8f, 12f)
//				.setWoodType(WoodTypes.OAK)
//				.setHeight(16)
//				.setMinGrowthTime(10)
//				.setBushes()
//				.setGenerator(GEN_TALL_2)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.PALM = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("palm", 280f, 500f, 16f, 35f)
//				.setWoodType(WoodTypes.PALM)
//				.setDecayDist(6)
//				.setGenerator(GEN_TROPICAL)
//				.setParamMap(0.05f, 16f, 5, 4, 1.10f)
//				.setGrowthLogicKit("jungle")
//				.setCellKit("palm")
//				.build();
//
//		WoodTreeVariants.PINE = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("pine", 60f, 250f, -15f, 7f)
//				.setWoodType(WoodTypes.PINE)
//				.setDensity(0.1f, 0.8f)
//				.setRadius(1)
//				.setGenerator(GEN_CONIFER)
//				.setParamMap(0.20f, 18f, 6, 2, 1.20f)
//				.setGrowthLogicKit("conifer")
//				.setCellKit("conifer")
//				.setConifer()
//				.build();
//
//		WoodTreeVariants.ROSEWOOD = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("rosewood", 10f, 190f, 8f, 18f)
//				.setWoodType(WoodTypes.ROSEWOOD)
//				.setHeight(12)
//				.setMinGrowthTime(8)
//				.setGenerator(GEN_MEDIUM)
//				.setParamMap(0.35f, 15f, 7, 3, 1.00f)
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.SEQUOIA = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("sequoia", 250f, 420f, -5f, 12f)
//				.setWoodType(WoodTypes.SEQUOIA)
//				.setRadius(3)
//				.setHeight(24)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setDensity(0.4f, 0.9f)
//				.setGenerator(GEN_SEQUOIA)
//				.setParamMap(0.20f, 36f, 9, 4, 0.70f)
//				.setGrowthLogicKit("conifer")
//				.setCellKit("conifer")
//				.setConifer()
//				.setBushes()
//				.setThick()
//				.build();
//
//		WoodTreeVariants.SPRUCE = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("spruce", 120f, 380f, -11f, 6f)
//				.setWoodType(WoodTypes.SPRUCE)
//				.setDensity(0.1f, 0.8f)
//				.setRadius(1)
//				.setGenerator(GEN_CONIFER)
//				.setParamMap(0.15f, 12f, 6, 3, 1.10f)
//				.setGrowthLogicKit("conifer")
//				.setCellKit("conifer")
//				.setConifer()
//				.build();
//
//		WoodTreeVariants.SYCAMORE = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("sycamore", 120f, 290f, 17f, 33f)
//				.setWoodType(WoodTypes.SYCAMORE)
//				.setMinGrowthTime(8)
//				.setDensity(0.25f, 2f)
//				.setGenerator(GEN_MEDIUM_2)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.20f, 10f, 4, 3, 0.90f)
//				.setCellKit("deciduous")
//				.setBushes()
//				.build();
//
//		WoodTreeVariants.WHITE_CEDAR = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("white_cedar", 10f, 240f, -8f, 17f)
//				.setWoodType(WoodTypes.WHITE_CEDAR)
//				.setHeight(16)
//				.setBushes()
//				.setGenerator(GEN_TALL_SINGLE)
//				.setFruit(() -> ItemsTFCF.JUNIPER, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.15f, 20f, 6, 2, 1.10f)
//				.setGrowthLogicKit("conifer")
//				.setCellKit("deciduous")
//				.setConifer()
//				.build();
//
//		WoodTreeVariants.WILLOW = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("willow", 230f, 400f, 15f, 32f)
//				.setWoodType(WoodTypes.WILLOW)
//				.setMinGrowthTime(11)
//				.setDensity(0.7f, 2f)
//				.setRadius(1)
//				.setGenerator(GEN_WILLOW)
//				.setParamMap(0.55f, 15f, 2, 2, 1.40f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.setBushes()
//				.build();
//
//		WoodTreeVariants.KAPOK = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant.Builder("kapok", 210f, 500f, 15f, 35f)
//				.setWoodType(WoodTypes.KAPOK)
//				.setDominance(8.5f)
//				.setRadius(3)
//				.setHeight(24)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setDensity(0.6f, 2f)
//				.setGenerator(GEN_KAPOK_COMPOSITE)
//				.setParamMap(0.10f, 30f, 7, 4, 0.85f)
//				.setGrowthLogicKit("jungle")
//				.setCellKit("deciduous")
//				.setBushes()
//				.setThick()
//				.build();
//
//		//TODO NEW
//		WoodTreeVariants.AFRICAN_PADAUK = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("african_padauk", 275f, 500f, 22f, 50f)
//				.setWoodType(WoodTypes.AFRICAN_PADAUK)
//				.setDensity(0.5f, 2f)
//				.setMinGrowthTime(18)
//				.setDecayDist(6)
//				.setRadius(1)
//				.setBushes()
//				.setGenerator(GEN_AFRICAN_PADAUK)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.ANGELIM = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("angelim", 320f, 500f, 22f, 50f)
//				.setWoodType(WoodTypes.ANGELIM)
//				.setRadius(1)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setDensity(0.5f, 2f)
//				.setBushes()
//				.setGenerator(GEN_ANGELIM)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.BOX = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("box", 180f, 400f, -8f, 15f)
//				.setWoodType(WoodTypes.BOX)
//				.setMinGrowthTime(8)
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_TALL_TFC)
//				.setBushes()
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.BRAZILWOOD = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("brazilwood", 290f, 550f, 14f, 37f)
//				.setWoodType(WoodTypes.BRAZILWOOD)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_BRAZILWOOD)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.COCOBOLO = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("cocobolo", 255f, 500f, 20f, 50f)
//				.setWoodType(WoodTypes.COCOBOLO)
//				.setRadius(1)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setDensity(0.5f, 2f)
//				.setGenerator(GEN_COCOBOLO)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.EBONY = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("ebony", 180f, 320f, 19f, 38f)
//				.setWoodType(WoodTypes.EBONY)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setGenerator(GEN_EBONY)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.FEVER = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("fever", 70f, 220f, 19f, 50f)
//				.setWoodType(WoodTypes.FEVER)
//				.setMinGrowthTime(10)
//				.setBushes()
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_FEVER)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.FRUITWOOD = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("fruitwood", 180f, 550f, 11f, 30f)
//				.setWoodType(WoodTypes.FRUITWOOD)
//				.setDominance(0)
//				.setMinGrowthTime(9)
//				.setBushes()
//				.setDensity(0f, 0f)
//				.setGenerator(GEN_FRUITWOOD)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.GREENHEART = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("greenheart", 310f, 500f, 23f, 50f)
//				.setWoodType(WoodTypes.GREENHEART)
//				.setRadius(1)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setBushes()
//				.setDensity(0.5f, 2f)
//				.setGenerator(GEN_GREENHEART)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.HOLLY = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("holly", 140f, 400f, -4f, 16f)
//				.setWoodType(WoodTypes.HOLLY)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_HOLLY)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.IROKO = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("iroko", 300f, 500f, 21f, 50f)
//				.setWoodType(WoodTypes.IROKO)
//				.setRadius(1)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setBushes()
//				.setDensity(0.5f, 2f)
//				.setGenerator(GEN_IROKO)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.IRONWOOD = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("ironwood", 30f, 210f, 11f, 36f)
//				.setWoodType(WoodTypes.IRONWOOD)
//				.setDecayDist(6)
//				.setMinGrowthTime(11)
//				.setBushes()
//				.setDensity(0.1f, 0.6f)
//				.setGenerator(GEN_IRONWOOD)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.KAURI = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("kauri", 330f, 500f, 23f, 50f)
//				.setWoodType(WoodTypes.KAURI)
//				.setRadius(1)
//				.setMinGrowthTime(10)
//				.setBushes()
//				.setDensity(0.5f, 2f)
//				.setGenerator(GEN_KAURI)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.LIMBA = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("limba", 290f, 550f, 14f, 37f)
//				.setWoodType(WoodTypes.LIMBA)
//				.setMinGrowthTime(9)
//				.setBushes()
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_LIMBA)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.LOGWOOD = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("logwood", 180f, 430f, 12f, 35f)
//				.setWoodType(WoodTypes.LOGWOOD)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_LOGWOOD)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.MAHOE = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("mahoe", 180f, 350f, 13f, 32f)
//				.setWoodType(WoodTypes.MAHOE)
//				.setHeight(16)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setGenerator(GEN_MAHOE)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.MANGROVE = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("mangrove", 200f, 500f, 15f, 40f)
//				.setWoodType(WoodTypes.MANGROVE)
//				.setDominance(0f)
//				.setDensity(0f, 0f)
//				.setRadius(1)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setGenerator(GEN_MANGROVE)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.MARBLEWOOD = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("marblewood", 180f, 500f, 16f, 35f)
//				.setWoodType(WoodTypes.MARBLEWOOD)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setBushes()
//				.setDensity(0.2f, 2f)
//				.setGenerator(GEN_MARBLEWOOD)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.MESSMATE = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("messmate", 120f, 270f, 2f, 27f)
//				.setWoodType(WoodTypes.MESSMATE)
//				.setMinGrowthTime(10)
//				.setBushes()
//				.setDensity(0.2f, 2f)
//				.setGenerator(GEN_MESSMATE)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.MOUNTAIN_ASH = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("mountain_ash", 80f, 270f, 9f, 33f)
//				.setWoodType(WoodTypes.MOUNTAIN_ASH)
//				.setMinGrowthTime(10)
//				.setBushes()
//				.setDensity(0.4f, 2f)
//				.setGenerator(GEN_MOUNTAIN_ASH)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.PURPLEHEART = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("purpleheart", 310f, 500f, 22f, 50f)
//				.setWoodType(WoodTypes.PURPLEHEART)
//				.setRadius(1)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setBushes()
//				.setDensity(0.5f, 2f)
//				.setGenerator(GEN_PURPLEHEART)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.RUBBER_FIG = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("rubber_fig", 210f, 550f, 16f, 35f)
//				.setWoodType(WoodTypes.RUBBER_FIG)
//				.setDecayDist(6)
//				.setMinGrowthTime(16)
//				.setBushes()
//				.setDensity(0.2f, 1f)
//				.setGenerator(GEN_RUBBER_FIG)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.TEAK = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("teak", 180f, 430f, 17f, 35f)
//				.setWoodType(WoodTypes.TEAK)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_TEAK)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.WENGE = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("wenge", 255f, 500f, 20f, 50f)
//				.setWoodType(WoodTypes.WENGE)
//				.setRadius(1)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setDensity(0.5f, 2f)
//				.setGenerator(GEN_WENGE)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.YELLOW_MERANTI = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("yellow_meranti", 260f, 500f, 21f, 50f)
//				.setWoodType(WoodTypes.YELLOW_MERANTI)
//				.setRadius(1)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setBushes()
//				.setDensity(0.5f, 2f)
//				.setGenerator(GEN_YELLOW_MERANTI)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.ZEBRAWOOD = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("zebrawood", 280f, 500f, 23f, 50f)
//				.setWoodType(WoodTypes.ZEBRAWOOD)
//				.setRadius(1)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setBushes()
//				.setDensity(0.5f, 2f)
//				.setGenerator(GEN_ZEBRAWOOD)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.BALD_CYPRESS = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("bald_cypress", 180f, 500f, 10f, 38f)
//				.setWoodType(WoodTypes.BALD_CYPRESS)
//				.setDominance(0f)
//				.setDensity(0f, 0f)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setConifer()
//				.setGenerator(GEN_BALD_CYPRESS)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.CYPRESS = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("cypress", 140f, 350f, 4f, 33f)
//				.setWoodType(WoodTypes.CYPRESS)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setConifer()
//				.setGenerator(GEN_CYPRESS)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.HEMLOCK = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("hemlock", 140f, 400f, -9f, 10f)
//				.setWoodType(WoodTypes.HEMLOCK)
//				.setMinGrowthTime(8)
//				.setConifer()
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_HEMLOCK)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.NORDMANN_FIR = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("nordmann_fir", 100f, 380f, -16f, 7f)
//				.setWoodType(WoodTypes.NORDMANN_FIR)
//				.setMinGrowthTime(8)
//				.setConifer()
//				.setDensity(0.1f, 0.9f)
//				.setGenerator(GEN_NORDMANN_FIR)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.NORWAY_SPRUCE = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("norway_spruce", 100f, 380f, -20f, 5f)
//				.setWoodType(WoodTypes.NORWAY_SPRUCE)
//				.setMinGrowthTime(8)
//				.setConifer()
//				.setDensity(0.1f, 0.9f)
//				.setGenerator(GEN_NORWAY_SPRUCE)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.REDWOOD = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("redwood", 160f, 400f, 0f, 17f)
//				.setWoodType(WoodTypes.REDWOOD)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setConifer()
//				.setBushes()
//				.setDensity(0.4f, 2f)
//				.setGenerator(GEN_REDWOOD)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.BAOBAB = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("baobab", 10f, 150f, 23f, 40f)
//				.setWoodType(WoodTypes.BAOBAB)
//				.setDecayDist(6)
//				.setMinGrowthTime(20)
//				.setDensity(0.1f, 0.3f)
//				.setGenerator(GEN_BAOBAB)
//				.setFruit(() -> ItemsTFCF.BAOBAB_FRUIT, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.EUCALYPTUS = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("eucalyptus", 120f, 300f, 18f, 39f)
//				.setWoodType(WoodTypes.EUCALYPTUS)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setDensity(0.35f, 2f)
//				.setGenerator(GEN_EUCALYPTUS)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.HAWTHORN = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("hawthorn", 180f, 400f, -8f, 14f)
//				.setWoodType(WoodTypes.HAWTHORN)
//				.setMinGrowthTime(8)
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_HAWTHORN)
//				.setFruit(() -> ItemsTFCF.HAWTHORN, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.MACLURA = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("maclura", 140f, 400f, -1f, 17f)
//				.setWoodType(WoodTypes.MACLURA)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_MACLURA)
//				.setFruit(() -> ItemsTFCF.OSAGE_ORANGE, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.MAHOGANY = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("mahogany", 220f, 500f, 23f, 42f)
//				.setWoodType(WoodTypes.MAHOGANY)
//				.setRadius(1)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setBushes()
//				.setDensity(0.5f, 2f)
//				.setGenerator(GEN_MAHOGANY)
//				.setFruit(() -> ItemsTFCF.SKY_FRUIT, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.PINK_IVORY = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("pink_ivory", 210f, 500f, 18f, 31f)
//				.setWoodType(WoodTypes.PINK_IVORY)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setBushes()
//				.setDensity(0.2f, 2f)
//				.setGenerator(GEN_PINK_IVORY)
//				.setFruit(() -> ItemsTFCF.PINK_IVORY_DRUPE, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.RED_CEDAR = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("red_cedar", 10f, 240f, -8f, 17f)
//				.setWoodType(WoodTypes.RED_CEDAR)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setBushes()
//				.setConifer()
//				.setBushes()
//				.setDensity(0.4f, 2f)
//				.setGenerator(GEN_RED_CEDAR)
//				.setFruit(() -> ItemsTFCF.JUNIPER, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.ROWAN = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("rowan", 180f, 400f, -15f, 8f)
//				.setWoodType(WoodTypes.ROWAN)
//				.setMinGrowthTime(8)
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_ROWAN)
//				.setFruit(() -> ItemsTFCF.ROWAN_BERRY, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.SYZYGIUM = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("syzygium", 140f, 360f, 13f, 35f)
//				.setWoodType(WoodTypes.SYZYGIUM)
//				.setDecayDist(6)
//				.setMinGrowthTime(16)
//				.setBushes()
//				.setDensity(0.2f, 1f)
//				.setGenerator(GEN_SYZYGIUM)
//				.setFruit(() -> ItemsTFCF.RIBERRY, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.YEW = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("yew", 180f, 350f, -15f, 11f)
//				.setWoodType(WoodTypes.YEW)
//				.setMinGrowthTime(10)
//				.setBushes()
//				.setGenerator(GEN_YEW)
//				.setFruit(() -> ItemsTFCF.YEW_BERRY, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.JACARANDA = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("jacaranda", 180f, 300f, 10f, 34f)
//				.setWoodType(WoodTypes.JACARANDA)
//				.setMinGrowthTime(8)
//				.setDensity(0.25f, 2f)
//				.setGenerator(GEN_JACARANDA)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.JUNIPER = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("juniper", 80f, 350f, -8f, 20f)
//				.setWoodType(WoodTypes.JUNIPER)
//				.setMinGrowthTime(8)
//				.setConifer()
//				.setDensity(0.25f, 0.75f)
//				.setGenerator(GEN_JUNIPER)
//				.setFruit(() -> ItemsTFCF.JUNIPER, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.IPE = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("ipe", 150f, 350f, 15f, 32f)
//				.setWoodType(WoodTypes.IPE)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setBushes()
//				.setDensity(0.2f, 2f)
//				.setGenerator(GEN_IPE)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.PINK_CHERRY = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("pink_cherry", 180f, 300f, 0f, 20f)
//				.setWoodType(WoodTypes.PINK_CHERRY)
//				.setMinGrowthTime(8)
//				.setDensity(0.25f, 2f)
//				.setGenerator(GEN_PINK_CHERRY)
//				.setFruit(() -> ItemFoodTFC.get(Food.CHERRY), 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.WHITE_CHERRY = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("white_cherry", 180f, 300f, 0f, 20f)
//				.setWoodType(WoodTypes.WHITE_CHERRY)
//				.setMinGrowthTime(8)
//				.setDensity(0.25f, 2f)
//				.setGenerator(GEN_WHITE_CHERRY)
//				.setFruit(() -> ItemFoodTFC.get(Food.CHERRY), 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.SWEETGUM = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("sweetgum", 140f, 360f, -2f, 18f)
//				.setWoodType(WoodTypes.SWEETGUM)
//				.setDecayDist(6)
//				.setMinGrowthTime(16)
//				.setBushes()
//				.setDensity(0.2f, 1f)
//				.setGenerator(GEN_SWEETGUM)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.LARCH = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("larch", 60f, 400f, -12f, 15f)
//				.setWoodType(WoodTypes.LARCH)
//				.setMinGrowthTime(8)
//				.setConifer()
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_LARCH)
//				.setFruit(() -> ItemsTFCF.PINECONE, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.ALDER = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("alder", 60f, 400f, -4f, 13f)
//				.setWoodType(WoodTypes.ALDER)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setDensity(0.25f, 2f)
//				.setGenerator(GEN_ALDER)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.BEECH = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("beech", 220f, 300f, -15f, 9f)
//				.setWoodType(WoodTypes.BEECH)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_BEECH)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.BLACK_WALNUT = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("black_walnut", 180f, 300f, -10f, 16f)
//				.setWoodType(WoodTypes.BLACK_WALNUT)
//				.setMinGrowthTime(9)
//				.setBushes()
//				.setGenerator(GEN_BLACK_WALNUT)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.BUTTERNUT = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("butternut", 180f, 320f, -8f, 17f)
//				.setWoodType(WoodTypes.BUTTERNUT)
//				.setMinGrowthTime(9)
//				.setBushes()
//				.setGenerator(GEN_BUTTERNUT)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.EUROPEAN_OAK = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("european_oak", 140f, 430f, -8f, 15f)
//				.setWoodType(WoodTypes.EUROPEAN_OAK)
//				.setMinGrowthTime(10)
//				.setBushes()
//				.setGenerator(GEN_EUROPEAN_OAK)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.GINKGO = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("ginkgo", 240f, 550f, 6f, 20f)
//				.setWoodType(WoodTypes.GINKGO)
//				.setMinGrowthTime(8)
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_GINKGO)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.HAZEL = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("hazel", 60f, 400f, -10f, 14f)
//				.setWoodType(WoodTypes.HAZEL)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_HAZEL)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.HORNBEAM = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("hornbeam", 140f, 430f, -10f, 12f)
//				.setWoodType(WoodTypes.HORNBEAM)
//				.setMinGrowthTime(10)
//				.setBushes()
//				.setGenerator(GEN_HORNBEAM)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.LOCUST = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("locust", 120f, 290f, -6f, 15f)
//				.setWoodType(WoodTypes.LOCUST)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setGenerator(GEN_LOCUST)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.POPLAR = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("poplar", 140f, 400f, -7f, 14f)
//				.setWoodType(WoodTypes.POPLAR)
//				.setMinGrowthTime(8)
//				.setDensity(0.25f, 1f)
//				.setGenerator(GEN_POPLAR)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.RED_ELM = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("red_elm", 60f, 290f, 2f, 20f)
//				.setWoodType(WoodTypes.RED_ELM)
//				.setDecayDist(6)
//				.setMinGrowthTime(18)
//				.setBushes()
//				.setDensity(0.4f, 2f)
//				.setGenerator(GEN_RED_ELM)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.WALNUT = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("walnut", 180f, 300f, -10f, 16f)
//				.setWoodType(WoodTypes.WALNUT)
//				.setMinGrowthTime(9)
//				.setBushes()
//				.setGenerator(GEN_WALNUT)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.WHITE_ELM = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("white_elm", 60f, 290f, 2f, 20f)
//				.setWoodType(WoodTypes.WHITE_ELM)
//				.setMinGrowthTime(8)
//				.setBushes()
//				.setGenerator(GEN_WHITE_ELM)
//				.setFruit(null, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.WHITEBEAM = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("whitebeam", 140f, 430f, -10f, 12f)
//				.setWoodType(WoodTypes.WHITEBEAM)
//				.setMinGrowthTime(10)
//				.setBushes()
//				.setGenerator(GEN_WHITEBEAM)
//				.setFruit(() -> ItemsTFCF.ROWAN_BERRY, 0.33f)
//				.setStages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//
//		WoodTreeVariants.HEVEA = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("hevea", 140f, 350f, 7f, 27f)
//				.setWoodType(WoodTypes.HEVEA)
//				.setDensity(0.1f, 0.6f)
//				.setRadius(2)
//				.setMinGrowthTime(10)
//				.setGenerator(GEN_TALL)
//				.setParamMap(0.20f, 13f, 3, 7, 1.25f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.CINNAMON = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("cinnamon", 28, 35, 280, 400)
//				.setWoodType(WoodTypes.CINNAMON)
//				.setDensity(0f, 1f)
//				.setDominance(0)
//				.setRadius(4)
//				.setHeight(15)
//				.setDecayDist(4)
//				.setMinGrowthTime(15)
//				.setGenerator(GEN_SEQUOIA)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.CASSIA_CINNAMON = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("cassia_cinnamon", 20, 35, 250, 400)
//				.setWoodType(WoodTypes.CINNAMON)
//				.setDensity(0.1f, 1f)
//				.setDominance(2)
//				.setRadius(4)
//				.setHeight(15)
//				.setDecayDist(6)
//				.setMinGrowthTime(15)
//				.setGenerator(GEN_CASSIA_CINNAMON)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//
//		WoodTreeVariants.CEYLON_CINNAMON = new su.terrafirmagreg.modules.arboriculture.api.types.variant.tree.WoodTreeVariant
//				.Builder("ceylon_cinnamon", 20, 35, 250, 400)
//				.setWoodType(WoodTypes.CINNAMON)
//				.setDensity(0.1f, 1f)
//				.setDominance(2)
//				.setRadius(4)
//				.setHeight(15)
//				.setDecayDist(6)
//				.setMinGrowthTime(15)
//				.setGenerator(GEN_CEYLON_CINNAMON)
//				.setParamMap(0.30f, 16f, 3, 3, 0.85f)
//				.setGrowthLogicKit("darkoak")
//				.setCellKit("deciduous")
//				.build();
//	}
//}
