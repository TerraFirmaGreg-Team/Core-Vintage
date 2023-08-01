/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks.plants;

import net.dries007.tfc.api.types2.plant.PlantType;
import net.dries007.tfc.api.types2.plant.PlantVariant;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.property.ITallPlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.world.classic.ChunkGenTFC.SALT_WATER;

@ParametersAreNonnullByDefault
public class BlockEmergentTallWaterPlantTFC extends BlockTallWaterPlantTFC implements ITallPlant {

	private final PlantType plantType;

	public BlockEmergentTallWaterPlantTFC(PlantVariant plantVariant, PlantType plantType) {
		super(plantVariant, plantType);

		this.plantType = plantType;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		IBlockState water = plantType.getWaterType();
		int i;
		//noinspection StatementWithEmptyBody
		for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i) ;
		if (water == SALT_WATER)
			return i < plantType.getMaxHeight() && (worldIn.isAirBlock(pos.up()) || BlocksTFC.isSaltWater(worldIn.getBlockState(pos.up()))) && canBlockStay(worldIn, pos.up(), state);
		else
			return i < plantType.getMaxHeight() && (worldIn.isAirBlock(pos.up()) || BlocksTFC.isFreshWater(worldIn.getBlockState(pos.up()))) && canBlockStay(worldIn, pos.up(), state);
	}

	public void shrink(World worldIn, BlockPos pos) {
		boolean flag = false;
		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
			if (BlocksTFC.isWater(worldIn.getBlockState(pos.offset(enumfacing)))) {
				flag = true;
			}
		}

		if (flag) worldIn.setBlockState(pos, plantType.getWaterType());
		else worldIn.setBlockToAir(pos);
		worldIn.getBlockState(pos).neighborChanged(worldIn, pos.down(), this, pos);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState soil = worldIn.getBlockState(pos.down());
		if (plantType.getWaterType() == SALT_WATER)
			return (soil.getBlock() == this || BlocksTFC.isSaltWater(worldIn.getBlockState(pos))) && this.canSustainBush(soil);
		return (soil.getBlock() == this || BlocksTFC.isFreshWater(worldIn.getBlockState(pos))) && this.canSustainBush(soil);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		this.onBlockHarvested(world, pos, state, player);

		boolean flag = false;
		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
			if (BlocksTFC.isWater(world.getBlockState(pos.offset(enumfacing)))) {
				flag = true;
			}
		}

		if (flag) return world.setBlockState(pos, plantType.getWaterType(), world.isRemote ? 11 : 3);
		else return world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
	}

	@Override
	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.canBlockStay(worldIn, pos, state)) {
			boolean flag = false;
			for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
				if (BlocksTFC.isWater(worldIn.getBlockState(pos.offset(enumfacing)))) {
					flag = true;
				}
			}

			this.dropBlockAsItem(worldIn, pos, state, 0);
			if (flag) worldIn.setBlockState(pos, plantType.getWaterType());
			else worldIn.setBlockToAir(pos);
		}
	}
}
