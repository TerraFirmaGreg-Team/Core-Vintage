/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.compat.top;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;
import net.dries007.tfc.compat.top.providers.AnimalProvider;
import net.dries007.tfc.compat.top.providers.BarrelProvider;

public class TOPPlugin
{
    public static void onPreInit() {
        ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;

        oneProbe.registerEntityProvider(new AnimalProvider());
        oneProbe.registerProvider(new BarrelProvider());

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
