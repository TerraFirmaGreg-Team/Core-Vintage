package su.terrafirmagreg.modules.core.feature.ambiental.provider;

import su.terrafirmagreg.modules.core.feature.ambiental.modifiers.ModifierBase;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

// Add an example of this into TemperatureRegistry for blocks you didn't create personally
@FunctionalInterface
public interface IAmbientalBlockProvider
  extends IAmbientalBaseProvider {

  Optional<ModifierBase> getModifier(EntityPlayer player, BlockPos pos, IBlockState state);

}
