package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;


import static su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariants.RAW;

public class BlockAlabasterRaw extends BlockRockDecorative {

    public BlockAlabasterRaw() {
        super(Settings.of(Material.ROCK));

        getSettings()
                .mapColor(MapColor.getBlockColor(EnumDyeColor.WHITE))
                .registryKey("rock/alabaster/raw/plain")
                .addOreDict("alabaster")
                .addOreDict("alabaster", "raw");

        for (var color : EnumDyeColor.values()) {
            BlocksRock.ALABASTER_COLOR_BLOCKS.put(Pair.of(RAW, color), new BlockAlabasterRaw(color));
        }
    }

    public BlockAlabasterRaw(EnumDyeColor color) {
        super(Settings.of(Material.ROCK));

        getSettings()
                .mapColor(MapColor.getBlockColor(color))
                .registryKey("rock/alabaster/raw/" + color.getName())
                .addOreDict("alabaster")
                .addOreDict("alabaster", "raw");
    }

}
