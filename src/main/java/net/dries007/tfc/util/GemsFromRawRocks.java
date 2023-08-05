package net.dries007.tfc.util;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static gregtech.api.unification.material.Materials.*;

public class GemsFromRawRocks {
    private final static List<Material> ALLOWED_MATERIALS_FOR_GEMS = Arrays.asList(
            Amethyst,
            Diamond,
            Emerald,
            GarnetRed,
            Opal,
            Ruby,
            Sapphire,
            Topaz,
            GreenSapphire,
            BlueTopaz,
            Cinnabar,
            Olivine
    );

    public static ItemStack getRandomGem()
    {
        int lower_bound = 0;
        int upper_bound = 100;

        int random_value = new Random().nextInt(upper_bound - lower_bound) + lower_bound;

        if (random_value <= 5) {
            return OreDictUnifier.get(OrePrefix.gemExquisite, ALLOWED_MATERIALS_FOR_GEMS.get(new Random().nextInt(ALLOWED_MATERIALS_FOR_GEMS.size())));
        }
        else if (random_value < 20) {
            return OreDictUnifier.get(OrePrefix.gemFlawless, ALLOWED_MATERIALS_FOR_GEMS.get(new Random().nextInt(ALLOWED_MATERIALS_FOR_GEMS.size())));
        }
        else if (random_value < 50) {
            return OreDictUnifier.get(OrePrefix.gemFlawed, ALLOWED_MATERIALS_FOR_GEMS.get(new Random().nextInt(ALLOWED_MATERIALS_FOR_GEMS.size())));
        }
        else {
            return OreDictUnifier.get(OrePrefix.gemChipped, ALLOWED_MATERIALS_FOR_GEMS.get(new Random().nextInt(ALLOWED_MATERIALS_FOR_GEMS.size())));
        }
    }
}
