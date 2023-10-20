/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.api.types;

import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.minecraft.block.material.Material;

public interface IPlantType {
    BlockPlantTFC create(Plant plant);

    Material getPlantMaterial();
}
