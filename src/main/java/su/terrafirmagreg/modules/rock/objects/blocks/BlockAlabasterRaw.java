package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.block.material.MapColor;
import net.minecraft.item.EnumDyeColor;


import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariants.RAW;

public class BlockAlabasterRaw extends BlockRockDecorative {

    private final String color;

    public BlockAlabasterRaw() {
        super(MapColor.getBlockColor(EnumDyeColor.WHITE));

        this.color = "plain";

        for (var color : EnumDyeColor.values()) {
            BlocksRock.ALABASTER_COLOR_BLOCKS.put(Pair.of(RAW, color), new BlockAlabasterRaw(color));
        }
    }

    public BlockAlabasterRaw(EnumDyeColor color) {
        super(MapColor.getBlockColor(color));

        this.color = color.getName();
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "alabaster");
        OreDictUtils.register(this, "alabaster", "raw");
    }

    @Override
    public @NotNull String getName() {
        return String.format("rock/alabaster/raw/%s", color);
    }
}
