package su.terrafirmagreg.modules.soil.objects.blocks.peat;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.block.BlockBase;


public class BlockPeat extends BlockBase {

	public BlockPeat() {
		super(Material.GROUND);

		setSoundType(SoundType.GROUND);
		setHardness(0.6F);
		setHarvestLevel("shovel", 0);

//		OreDictionaryHelper.register(this, getName());
		Blocks.FIRE.setFireInfo(this, 5, 10);

		//DirtHelper.registerSoil(this.getDefaultState().getBlock(), DirtHelper.GRAVELLIKE);
	}

	@Override
	public @NotNull String getName() {
		return "soil/peat";
	}
}
