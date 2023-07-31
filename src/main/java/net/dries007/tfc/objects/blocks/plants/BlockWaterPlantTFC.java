/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks.plants;

import net.dries007.tfc.api.types2.plant.PlantType;
import net.dries007.tfc.api.types2.plant.PlantVariant;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.world.classic.ChunkGenTFC.SALT_WATER;

// todo: either pull some trickery to make this look like water or simply wait until 1.13 and implement ILiquidContainer
@ParametersAreNonnullByDefault
public class BlockWaterPlantTFC extends BlockPlantTFC {

	private final PlantType plantType;
	private final PlantVariant plantVariant;

	public BlockWaterPlantTFC(PlantVariant plantVariant, PlantType plantType) {
		super(plantVariant, plantType);

		this.plantType = plantType;
		this.plantVariant = plantVariant;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState soil = worldIn.getBlockState(pos.down());
		if (plantType.getWaterType() == SALT_WATER)
			return BlocksTFC.isSaltWater(worldIn.getBlockState(pos)) && this.canSustainBush(soil);
		return BlocksTFC.isFreshWater(worldIn.getBlockState(pos)) && this.canSustainBush(soil);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		this.onBlockHarvested(world, pos, state, player);
		return world.setBlockState(pos, plantType.getWaterType(), world.isRemote ? 11 : 3);
	}

	@Override
	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.canBlockStay(worldIn, pos, state)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockState(pos, plantType.getWaterType());
		}
	}
}
