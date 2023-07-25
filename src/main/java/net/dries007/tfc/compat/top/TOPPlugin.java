/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.compat.top;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;
import net.dries007.tfc.compat.top.providers.*;

public class TOPPlugin
{
    public static void onPreInit() {
        ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;

        oneProbe.registerEntityProvider(new AnimalProvider());
        oneProbe.registerProvider(new BarrelProvider());
        oneProbe.registerProvider(new BerryBushProvider());
        oneProbe.registerProvider(new BlastFurnaceProvider());
        oneProbe.registerProvider(new BloomeryProvider());
        oneProbe.registerProvider(new CropProvider());
        oneProbe.registerProvider(new CrucibleProvider());
        oneProbe.registerProvider(new FruitTreeProvider());
        oneProbe.registerProvider(new LampProvider());
        oneProbe.registerProvider(new LogPileProvider());
        oneProbe.registerProvider(new PitKilnProvider());
        oneProbe.registerProvider(new QuernProvider());
        oneProbe.registerProvider(new PlacedItemProvider());
        oneProbe.registerProvider(new TreeProvider());

        oneProbe.registerProvider(new TorchProvider());
        oneProbe.registerProvider(new SoilTypeProvider());
        oneProbe.registerProvider(new RockBlockProvider());
    }
}
