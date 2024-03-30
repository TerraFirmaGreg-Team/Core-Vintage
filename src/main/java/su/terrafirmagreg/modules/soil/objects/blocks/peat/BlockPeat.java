package su.terrafirmagreg.modules.soil.objects.blocks.peat;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.OreDictUtils;


public class BlockPeat extends BlockBase {

	public BlockPeat() {
		super(Material.GROUND);

		setSoundType(SoundType.GROUND);
		setHardness(0.6F);
		setHarvestLevel("shovel", 0);


		BlockUtils.setFireInfo(this, 5, 10);
		//DirtHelper.registerSoil(this.getDefaultState().get(), DirtHelper.GRAVELLIKE);
	}

	@Override
	public @NotNull String getName() {
		return "soil/peat";
	}

	@Override
	public void onRegisterOreDict() {
		OreDictUtils.register(this, "peat");
	}
}
