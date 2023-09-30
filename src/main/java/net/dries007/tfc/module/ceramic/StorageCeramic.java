package net.dries007.tfc.module.ceramic;

import gregtech.api.unification.ore.OrePrefix;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.dries007.tfc.module.ceramic.common.items.ItemMold;
import net.dries007.tfc.module.ceramic.common.items.ItemUnfiredMold;

import java.util.Map;

public class StorageCeramic {

    public static final Map<OrePrefix, ItemMold> FIRED_MOLDS = new Object2ObjectLinkedOpenHashMap<>();
    public static final Map<OrePrefix, ItemUnfiredMold> UNFIRED_MOLDS = new Object2ObjectLinkedOpenHashMap<>();


}
