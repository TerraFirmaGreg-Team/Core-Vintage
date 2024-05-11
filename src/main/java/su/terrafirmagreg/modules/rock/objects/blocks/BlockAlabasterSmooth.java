package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;


import static su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariants.SMOOTH;

public class BlockAlabasterSmooth extends BlockRockDecorative {

    private final String color;

    public BlockAlabasterSmooth() {
        super(Settings.of(Material.ROCK).mapColor(MapColor.getBlockColor(EnumDyeColor.WHITE)));

        this.color = "plain";

        for (var color : EnumDyeColor.values()) {
            BlocksRock.ALABASTER_COLOR_BLOCKS.put(Pair.of(SMOOTH, color), new BlockAlabasterSmooth(color));
        }
    }

    public BlockAlabasterSmooth(EnumDyeColor color) {
        super(Settings.of(Material.ROCK).mapColor(MapColor.getBlockColor(color)));

        this.color = color.getName();
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "alabaster");
        OreDictUtils.register(this, "alabaster", "smooth");
    }

    @Override
    public String getName() {
        return String.format("rock/alabaster/smooth/%s", color);
    }
}
