package su.terrafirmagreg.api.registry;

import net.darkhax.bookshelf.block.IColorfulBlock;
import net.darkhax.bookshelf.block.ITileEntityBlock;
import net.darkhax.bookshelf.item.ICustomModel;
import net.darkhax.bookshelf.registry.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.util.IItemProvider;

import javax.annotation.Nonnull;

public class Registry extends RegistryHelper {

	public Registry(CreativeTabs tab) {
		this.enableAutoRegistration();
		this.setTab(tab);
	}

	public void registerBlockWithItem(@Nonnull Block block, @Nonnull String id) {

		if (block instanceof IItemProvider itemProvider) {
			var itemblock = itemProvider.getItemBlock();
			if (itemblock != null) {
				this.registerBlock(block, itemblock, id);
			} else {
				this.registerBlock(block, id);
			}
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


	@SideOnly(Side.CLIENT)
	public void registerInventoryModel2(@Nonnull Block block) {

		if (block instanceof ICustomModel) {

			((ICustomModel) block).registerMeshModels();
		}
	}

}
