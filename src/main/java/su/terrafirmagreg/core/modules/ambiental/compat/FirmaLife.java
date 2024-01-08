package su.terrafirmagreg.core.modules.ambiental.compat;

import com.eerussianguy.firmalife.te.TEOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import org.apache.commons.lang3.reflect.FieldUtils;
import su.terrafirmagreg.core.modules.ambiental.modifier.TempModifier;

import java.util.Optional;

import static su.terrafirmagreg.core.modules.ambiental.api.ITileEntityTemperatureProvider.hasProtection;
import static su.terrafirmagreg.core.modules.ambiental.api.ITileEntityTemperatureProvider.mod;


public class FirmaLife {

	public static Optional<TempModifier> handleClayOven(EntityPlayer player, TileEntity tile) {
		if (tile instanceof TEOven oven) {

			boolean isBurning = false;

			try {
				isBurning = (boolean) FieldUtils.readField(oven, "isBurning", true);
			} catch (Exception e) {
				e.printStackTrace();
			}

			float change = 0.0f;
			float potency = 1.0f;

			if (isBurning) {
				change = 8f;
				potency = 4f;

				if (hasProtection(player)) {
					change = change * mod;
				}
			}


			return TempModifier.defined("firmalife_oven", change, potency);
		} else {
			return TempModifier.none();
		}
	}
}
