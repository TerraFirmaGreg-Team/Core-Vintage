package net.dries007.tfc.module.ceramic.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.dries007.tfc.module.ceramic.objects.items.ItemMold;
import net.dries007.tfc.module.ceramic.objects.items.ItemPottery;
import net.dries007.tfc.module.ceramic.objects.items.ItemUnfiredMold;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.ceramic.StorageCeramic.FIRED_MOLDS;
import static net.dries007.tfc.module.ceramic.StorageCeramic.UNFIRED_MOLDS;

public class ItemsCeramic {
    public static ItemPottery UNFIRED_VESSEL;
    public static ItemPottery FIRED_VESSEL;
    public static ItemPottery UNFIRED_VESSEL_GLAZED;
    public static ItemPottery FIRED_VESSEL_GLAZED;
    public static ItemPottery UNFIRED_JUG;
    public static ItemPottery FIRED_JUG;
    public static ItemPottery UNFIRED_POT;
    public static ItemPottery FIRED_POT;
    public static ItemPottery UNFIRED_BOWL;
    public static ItemPottery FIRED_BOWL;
    public static ItemPottery UNFIRED_SPINDLE;
    public static ItemPottery FIRED_SPINDLE;

    public static ItemPottery UNFIRED_FIRE_BRICK;

    public static ItemPottery UNFIRED_LARGE_VESSEL;
    public static ItemPottery UNFIRED_CRUCIBLE;
    public static ItemPottery UNFIRED_BRICK;
    public static ItemPottery UNFIRED_FLOWER_POT;

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
