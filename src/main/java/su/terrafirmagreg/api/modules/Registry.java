package su.terrafirmagreg.api.modules;

import net.darkhax.bookshelf.block.IColorfulBlock;
import net.darkhax.bookshelf.block.ITileEntityBlock;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import su.terrafirmagreg.api.util.IItemProvider;

import javax.annotation.Nonnull;

public class Registry extends RegistryHelper {

	public Registry(CreativeTabs tab) {
		enableAutoRegistration();
		setTab(tab);
	}

	public Block registerBlock(@Nonnull IItemProvider block, @Nonnull String id) {
		var itemblock = block.getItemBlock();

		if (itemblock == null) {
			return this.registerBlock(block, id);
		} else {
			return this.registerBlock((Block) block, itemblock, id);
		}
	}


	public Block registerBlock(@Nonnull Block block, @Nonnull String id) {

		block.setRegistryName(this.getModid(), id);
		block.setTranslationKey(this.getModid() + "." + id.toLowerCase().replace("_", "."));
		this.getBlocks().add(block);

		if (this.getTab() != null) {
			block.setCreativeTab(this.getTab());
		}

		if (block instanceof IColorfulBlock) {

			this.getColoredBlocks().add(block);
		}

		if (block instanceof ITileEntityBlock) {

			this.getTileProviders().add((ITileEntityBlock) block);
		}

		return block;
	}
}
