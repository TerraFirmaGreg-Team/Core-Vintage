package su.terrafirmagreg.modules.core.feature.ambiental.provider;

import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.Optional;

//Add an example of this into TemperatureRegistry for tile entities you didn't create personally
@FunctionalInterface
public interface IAmbientalProviderTile extends IAmbientalProviderBase {

  Optional<ModifierTile> getModifier(EntityPlayer player, TileEntity tile);

}
