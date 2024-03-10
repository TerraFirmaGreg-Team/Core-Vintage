package su.terrafirmagreg.modules.rock.objects.blocks;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.lib.EnumColor;
import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;


public class BlockAlabaster extends BlockBase implements IItemSize {

	private final RockBlockVariant blockVariant;
	private final EnumColor color;

	public BlockAlabaster(RockBlockVariant blockVariant, EnumColor color) {
		super(Material.ROCK);

		this.blockVariant = blockVariant;
		this.color = color;

		setCreativeTab(ModuleCore.CORE_TAB);
		setSoundType(SoundType.STONE);
		setHardness(1.0F);

//        OreDictionaryHelper.register(this, "alabaster");
//        OreDictionaryHelper.register(this, "alabaster", blockVariant.toString());
//        OreDictionaryHelper.register(this, "alabaster", blockVariant.toString(), color.getName());
	}

	@Override
	public @NotNull String getName() {
		return String.format("rock/alabaster/%s/%s", blockVariant, color.getName());
	}


}
