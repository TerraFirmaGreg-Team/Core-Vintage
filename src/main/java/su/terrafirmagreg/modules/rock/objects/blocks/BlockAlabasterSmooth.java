package su.terrafirmagreg.modules.rock.objects.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.item.EnumDyeColor;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.data.BlocksRock;

import static su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariants.SMOOTH;

public class BlockAlabasterSmooth extends BlockRockDecorative {

	private final String color;

	public BlockAlabasterSmooth() {
		super(MapColor.getBlockColor(EnumDyeColor.WHITE));

		this.color = "plain";

		for (var color : EnumDyeColor.values()) {
			BlocksRock.ALABASTER_COLOR_BLOCKS.put(Pair.of(SMOOTH, color), new BlockAlabasterSmooth(color));
		}
	}

	public BlockAlabasterSmooth(EnumDyeColor color) {
		super(MapColor.getBlockColor(color));

		this.color = color.getName();
	}


	@Override
	public void onRegisterOreDict() {
		OreDictUtils.register(this, "alabaster");
		OreDictUtils.register(this, "alabaster", "smooth");
	}

	@Override
	public @NotNull String getName() {
		return String.format("rock/alabaster/smooth/%s", color);
	}
}
