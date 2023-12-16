package com.lumintorious.ambiental.compat;

import com.eerussianguy.firmalife.te.TEOven;
import com.lumintorious.ambiental.modifier.TempModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Optional;

import static com.lumintorious.ambiental.api.ITileEntityTemperatureProvider.hasProtection;
import static com.lumintorious.ambiental.api.ITileEntityTemperatureProvider.mod;


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
