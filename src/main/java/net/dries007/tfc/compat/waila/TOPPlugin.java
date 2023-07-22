/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.compat.waila;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;
import net.dries007.tfc.compat.waila.interfaces.TOPBlockInterface;
import net.dries007.tfc.compat.waila.interfaces.TOPEntityInterface;
import net.dries007.tfc.compat.waila.providers.*;

public class TOPPlugin
{
    public static void onPreInit() {
        ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;

        //oneProbe.registerProvider(new StoneCategoryBlockProvider());
        //oneProbe.registerProvider(new JuiceExtractorProvider());


        /*
        new TOPBlockInterface(new BarrelProvider()),
        new TOPBlockInterface(new BerryBushProvider()),
        new TOPBlockInterface(new BlastFurnaceProvider()),
        new TOPBlockInterface(new BloomeryProvider()),
        new TOPBlockInterface(new LampProvider()),
        new TOPBlockInterface(new CropProvider()),
        new TOPBlockInterface(new CrucibleProvider()),
        new TOPBlockInterface(new FruitTreeProvider()),
        new TOPBlockInterface(new OreProvider()),
        new TOPBlockInterface(new PitKilnProvider()),
        new TOPBlockInterface(new PlacedItemProvider()),
        new TOPBlockInterface(new InfoProvider()),
        new TOPBlockInterface(new TreeProvider()),
        new TOPBlockInterface(new IngotPileProvider()),
        new TOPBlockInterface(new LogPileProvider()),
        new TOPBlockInterface(new QuernProvider())
        * */
    }
}
