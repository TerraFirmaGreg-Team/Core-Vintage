/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package su.terrafirmagreg.modules.core.feature.climate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITemperatureBlock {

  void onTemperatureUpdateTick(World world, BlockPos pos, IBlockState state);
}
