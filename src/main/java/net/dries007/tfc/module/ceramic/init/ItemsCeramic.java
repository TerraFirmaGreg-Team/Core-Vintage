package net.dries007.tfc.module.ceramic.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.dries007.tfc.module.ceramic.common.items.ItemMold;
import net.dries007.tfc.module.ceramic.common.items.ItemUnfiredMold;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.ceramic.StorageCeramic.FIRED_MOLDS;
import static net.dries007.tfc.module.ceramic.StorageCeramic.UNFIRED_MOLDS;

public class ItemsCeramic {

    public static void onRegister(Registry registry) {

        //==== Molds =================================================================================================//

        for (var orePrefix : OrePrefix.values()) {
            var orePrefixExtension = (IOrePrefixExtension) orePrefix;
            if (orePrefixExtension.getHasMold()) {
                if (UNFIRED_MOLDS.put(orePrefix, new ItemUnfiredMold(orePrefix)) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s", orePrefix));

                if (FIRED_MOLDS.put(orePrefix, new ItemMold(orePrefix)) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s", orePrefix));
            }
        }

        for (var item : UNFIRED_MOLDS.values()) {
            registry.registerItem(item, "ceramics/unfired/mold/" + item.orePrefix.name.toLowerCase());
        }

        for (var item : FIRED_MOLDS.values()) {
            registry.registerItem(item, "ceramics/fired/mold/" + item.orePrefix.name.toLowerCase());
        }

    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {

        });
    }
}
