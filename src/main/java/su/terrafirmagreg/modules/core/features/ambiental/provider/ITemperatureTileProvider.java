package su.terrafirmagreg.modules.core.features.ambiental.provider;

import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;


import java.util.Optional;

//Add an example of this into TemperatureRegistry for tile entities you didn't create personally
@FunctionalInterface
public interface ITemperatureTileProvider extends ITemperatureProvider {

    Optional<ModifierBase> getModifier(EntityPlayer player, TileEntity tile);

}
