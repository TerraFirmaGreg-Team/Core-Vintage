package su.terrafirmagreg.api.registry;

import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.module.ModuleManager;
import su.terrafirmagreg.api.spi.block.IColorfulBlock;
import su.terrafirmagreg.api.spi.item.IColorfulItem;
import su.terrafirmagreg.api.spi.item.ICustomMesh;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.OreDictUtils;

/**
 * This is used to automatically register things from the registry helper. The hope is that by
 * registering the event while the owner is active, Forge will shut up about harmless registry
 * entries being dangerous.
 */
@Getter
public class Registry {

	/**
	 * The registry helper to register things from.
	 */
	private final RegistryManager registryManager;

	public Registry(RegistryManager registryManager) {
		this.registryManager = registryManager;

	}


	public void onRegisterBlock(RegistryEvent.Register<Block> event) {
		for (var block : this.registryManager.getBlocks()) {
			event.getRegistry().register(block);
		}
	}


	public void onRegisterItem(RegistryEvent.Register<Item> event) {
		for (var item : this.registryManager.getItems()) {
			event.getRegistry().register(item);
		}
		OreDictUtils.init();
	}


	public void onRegisterPotion(RegistryEvent.Register<Potion> event) {
		for (var potion : this.registryManager.getPotions()) {
			event.getRegistry().register(potion);
		}
	}


	public void onRegisterPotionType(RegistryEvent.Register<PotionType> event) {
		for (var potionType : this.registryManager.getPotionType()) {
			event.getRegistry().register(potionType);
		}
	}


	public void onRegisterBiome(RegistryEvent.Register<Biome> event) {
		for (var biome : this.registryManager.getBiomes()) {
			event.getRegistry().register(biome);
		}
	}


	public void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {
		for (var sound : this.registryManager.getSounds()) {
			event.getRegistry().register(sound);
		}
	}


	public void onRegisterEntity(RegistryEvent.Register<EntityEntry> event) {
		for (var entry : this.registryManager.getEntities()) {
			event.getRegistry().register(entry);
		}
	}


	public void onRegisterEnchantment(RegistryEvent.Register<Enchantment> event) {
		for (var enchant : this.registryManager.getEnchantments()) {
			event.getRegistry().register(enchant);
		}
	}


	public void onRegisterVillagerProfession(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
		// TODO: register villager profession event
	}


	public void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
		for (var recipe : this.registryManager.getRecipes()) {
			event.getRegistry().register(recipe);
		}
	}


	public void onRegisterTileEntities() {
		for (var provider : this.registryManager.getTileProviders()) {
			GameRegistry.registerTileEntity(
					provider.getTileEntityClass(),
					ModUtils.getID("tile." + provider.getTileEntityClass().getSimpleName())
			);
		}
	}


	public void onRegisterLootTableLoad(LootTableLoadEvent event) {
		for (var builder : this.registryManager.getLootTableEntries().get(event.getName())) {
			var pool = event.getTable().getPool(builder.getPool());
			if (pool != null) pool.addEntry(builder.build());
			else {
				ModuleManager.LOGGER.info(
						"The mod {} tried to add loot to {} but the pool was not found. {}",
						this.registryManager.getModID(), event.getName(), builder.toString());
			}
		}
	}


	// --------------------------------------------------------------------------
	// - Client
	// --------------------------------------------------------------------------


	@SideOnly(Side.CLIENT)
	public void onRegisterModels(ModelRegistryEvent event) {

		for (var model : this.registryManager.getCustomModel()) {
			model.onModelRegister();
		}

		for (var model : this.registryManager.getCustomStateMapper()) {
			model.onStateMapperRegister();
		}

		for (var item : this.registryManager.getCustomMeshes()) {
			ModelLoader.setCustomMeshDefinition(item, ((ICustomMesh) item).getCustomMesh());
		}
	}

	@SideOnly(Side.CLIENT)
	public void onRegisterTileEntitySpecialRenderer() {
		for (var provider : this.registryManager.getTileProviders()) {
			final TileEntitySpecialRenderer tesr = provider.getTileRenderer();

			if (tesr != null) ClientRegistry.bindTileEntitySpecialRenderer(provider.getTileEntityClass(), tesr);
		}
	}

	@SideOnly(Side.CLIENT)
	public void onRegisterBlockColor(ColorHandlerEvent.Block event) {
		for (var block : this.registryManager.getColoredBlocks()) {
			if (block instanceof IColorfulBlock colorfulBlock) {
				event.getBlockColors().registerBlockColorHandler(colorfulBlock.getColorHandler(), block);
			}

		}
	}

	@SideOnly(Side.CLIENT)
	public void onRegisterItemColor(ColorHandlerEvent.Item event) {
		for (var block : this.registryManager.getColoredBlocks()) {
			if (block instanceof IColorfulBlock colorfulBlock) {
				if (colorfulBlock.getItemColorHandler() != null) {
					event.getItemColors().registerItemColorHandler(colorfulBlock.getItemColorHandler(), Item.getItemFromBlock(block));
				}
			}
		}

		for (var item : this.registryManager.getColoredItems()) {
			if (item instanceof IColorfulItem colorfulItem) {
				event.getItemColors().registerItemColorHandler(colorfulItem.getColorHandler(), item);
			}
		}
	}
}
