package su.terrafirmagreg.modules.soil.init;

import net.darkhax.bookshelf.item.ICustomModel;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.modules.soil.StorageSoil;
import su.terrafirmagreg.modules.soil.objects.blocks.peat.BlockPeat;
import su.terrafirmagreg.modules.soil.objects.blocks.peat.BlockPeatGrass;

public final class BlocksSoil {

	public static BlockPeatGrass PEAT_GRASS;
	public static BlockPeat PEAT;

	public static void onRegister(Registry registry) {

//		for (var block : StorageSoil.SOIL_BLOCKS.values()) {
//			registry.registerBlockWithItem((Block) block, block.getName());
//		}

//		registry.registerBlockWithItem(PEAT_GRASS = new BlockPeatGrass(), BlockPeatGrass.NAME);
//		registry.registerBlockWithItem(PEAT = new BlockPeat(), BlockPeat.NAME);


	}

	@SideOnly(Side.CLIENT)
	public static void onClientRegister(Registry registry) {
		StorageSoil.SOIL_BLOCKS.values().forEach(ICustomModel::registerMeshModels);
	}
//
//	@SideOnly(Side.CLIENT)
//	public static void onClientInitialization() {
//		var itemColors = Minecraft.getMinecraft().getItemColors();
//		var blockColors = Minecraft.getMinecraft().getBlockColors();
//
//		IBlockColor grassColor = GrassColorHandler::computeGrassColor;
//
//
//		blockColors.registerBlockColorHandler(grassColor,
//				StorageSoil.SOIL_BLOCKS
//						.values()
//						.stream()
//						.filter(x -> x.getBlockVariant().isGrass())
//						.map(s -> (Block) s)
//						.toArray(Block[]::new));
//
//		blockColors.registerBlockColorHandler((s, w, p, i) ->
//						BlockSoilFarmland.TINT[s.getValue(BlockSoilFarmland.MOISTURE)],
//				StorageSoil.SOIL_BLOCKS
//						.values()
//						.stream()
//						.filter(x -> x.getBlockVariant() == SoilBlockVariants.FARMLAND)
//						.map(s -> (Block) s)
//						.toArray(Block[]::new));
//
//		blockColors.registerBlockColorHandler(grassColor, PEAT_GRASS);
//
//		itemColors.registerItemColorHandler((s, i) ->
//						blockColors.colorMultiplier(((ItemBlock) s.getItem()).getBlock().getDefaultState(), null, null, i),
//				StorageSoil.SOIL_BLOCKS
//						.values()
//						.stream()
//						.filter(x -> x.getBlockVariant().isGrass())
//						.map(s -> (Block) s)
//						.toArray(Block[]::new));
//
//		itemColors.registerItemColorHandler((s, i) ->
//						blockColors.colorMultiplier(((ItemBlock) s.getItem()).getBlock().getDefaultState(), null, null, i),
//				PEAT_GRASS);
//	}

}
