package su.terrafirmagreg.modules.rock.objects.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import su.terrafirmagreg.api.spi.block.BlockBase;


public abstract class BlockRockDecorative extends BlockBase {


	public BlockRockDecorative(MapColor blockMapColorIn) {
		super(Material.ROCK, blockMapColorIn);


		setSoundType(SoundType.STONE);
		setHardness(1.0F);
	}
}
