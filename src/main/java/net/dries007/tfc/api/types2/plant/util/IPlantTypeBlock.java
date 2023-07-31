/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.api.types2.plant.util;

import net.dries007.tfc.api.types2.plant.PlantType;
import net.dries007.tfc.api.types2.plant.PlantVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.minecraft.item.ItemBlock;

public interface IPlantTypeBlock extends IHasModel {
	PlantVariant getPlantVariant();

	PlantType getPlantType();

	ItemBlock getItemBlock();
}
