/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.items.ceramics;

import java.util.HashMap;
import java.util.Map;

import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.types.Metal;

public class ItemUnfiredMold extends ItemPottery
{
    private static final Map<OrePrefix, ItemUnfiredMold> MOLD_STORAGE_MAP = new HashMap<>();

    public static ItemUnfiredMold get(OrePrefix orePrefix) {
        return MOLD_STORAGE_MAP.get(orePrefix);
    }

    public final OrePrefix orePrefix;

    public ItemUnfiredMold(OrePrefix orePrefix) {
        this.orePrefix = orePrefix;

        if (MOLD_STORAGE_MAP.put(orePrefix, this) != null) {
            throw new IllegalStateException("There can only be one.");
        }
    }
}
