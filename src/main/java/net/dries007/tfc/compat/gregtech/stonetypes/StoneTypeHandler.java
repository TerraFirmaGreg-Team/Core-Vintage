package net.dries007.tfc.compat.gregtech.stonetypes;

import gregtech.api.unification.ore.StoneType;
import gregtech.integration.jei.basic.OreByProduct;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariants;
import net.minecraft.block.SoundType;

import static net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix.oreChunk;

public class StoneTypeHandler {
    private static int counter = 16;

    public static void init() {

        OreByProduct.addOreByProductPrefix(oreChunk);

        for (var rockType : RockType.getRockTypes()) {
            new StoneType(
                    counter++, "tfc_" + rockType.toString(), SoundType.STONE, rockType.getOrePrefix(), rockType.getMaterial(),
                    () -> TFCStorage.getRockBlock(RockBlockVariants.RAW, rockType).getDefaultState(),
                    state -> state.getBlock() == TFCStorage.getRockBlock(RockBlockVariants.RAW, rockType),
                    false
            );
        }
    }
}
