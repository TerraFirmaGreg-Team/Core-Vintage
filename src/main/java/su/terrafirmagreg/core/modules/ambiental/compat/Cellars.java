package su.terrafirmagreg.core.modules.ambiental.compat;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.sharkbark.cellars.blocks.tileentity.TEIceBunker;
import org.apache.commons.lang3.reflect.FieldUtils;
import su.terrafirmagreg.core.modules.ambiental.modifier.TempModifier;

import java.util.Optional;

import static su.terrafirmagreg.core.modules.ambiental.api.ITileEntityTemperatureProvider.hasProtection;
import static su.terrafirmagreg.core.modules.ambiental.api.ITileEntityTemperatureProvider.mod;


public class Cellars {

	public static Optional<TempModifier> handleCellar(EntityPlayer player, TileEntity tile) {
		if (tile instanceof TEIceBunker iceBunker) {

			boolean isComplete = false;
			float temperature = 0.0f;

			try {
				isComplete = (boolean) FieldUtils.readField(iceBunker, "isComplete", true);
				temperature = (float) FieldUtils.readField(iceBunker, "temperature", true);
			} catch (Exception e) {
				e.printStackTrace();
			}

			float change = 0.0f;
			float potency = 0.0f;

			if (isComplete) {

				if (temperature < 10) {
					change = -2f * (12 - temperature);
					potency = -0.5f;
				}

				if (hasProtection(player)) {
					change = change * mod;
				}
			}


			return TempModifier.defined("cellar", change, potency);
		} else {
			return TempModifier.none();
		}
	}
}
