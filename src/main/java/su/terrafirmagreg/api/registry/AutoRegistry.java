package su.terrafirmagreg.api.registry;

import lombok.Getter;
import net.darkhax.bookshelf.block.IColorfulBlock;
import net.darkhax.bookshelf.block.ITileEntityBlock;
import net.darkhax.bookshelf.item.IColorfulItem;
import net.darkhax.bookshelf.lib.Constants;
import net.darkhax.bookshelf.lib.LootBuilder;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.objects.tile.ITEBlock;

/**
 * This is used to automatically register things from the registry helper. The hope is that by
 * registering the event while the owner is active, Forge will shut up about harmless registry
 * entries being dangerous.
 */
@Getter
public class AutoRegistry implements IAutoRegister {

	/**
	 * The registry helper to register things from.
	 */
	private final Registry registry;

	public AutoRegistry(Registry registry) {

		this.registry = registry;
	}

	@Override
	public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {
		for (var block : this.registry.getBlocks()) {
			event.getRegistry().register(block);
		}

		for (var provider : this.registry.getTileProviders()) {
			if (provider instanceof Block) {
				GameRegistry.registerTileEntity(provider.getTileEntityClass(),
						((Block) provider).getRegistryName().toString().replace(":", ".").replace("_", "."));
			}
		}
	}

	@Override
	public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {
		for (var item : this.registry.getItems()) {
			event.getRegistry().register(item);
		}
	}

	@Override
	public void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {
		for (var potion : this.registry.getPotions()) {
			event.getRegistry().register(potion);
		}
	}

	@Override
	public void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
		for (var potionType : this.registry.getPotionType()) {
			event.getRegistry().register(potionType);
		}
	}

	@Override
	public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
		for (var biome : this.registry.getBiomes()) {
			event.getRegistry().register(biome);
		}
	}

	@Override
	public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {
		for (var sound : this.registry.getSounds()) {
			event.getRegistry().register(sound);
		}
	}

	@Override
	public void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {

		for (var entity : this.registry.getEntities()) {
			strategy.register(event.getRegistry());
		}

		for (var entry : this.registry.getEntities()) {

			event.getRegistry().register(entry.build());
		}
	}



	@Override
	public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
		for (final Enchantment enchant : this.registry.getEnchantments()) {

			event.getRegistry().register(enchant);
		}
	}

	@Override
	public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
		// TODO: register villager profession event
	}



	@Override
	public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {
		for (final IRecipe recipe : this.registry.getRecipes()) {

			event.getRegistry().register(recipe);
		}
	}

	@Override
	public void onRegisterTileEntitiesEvent() {

		for (ITileEntityRegistrationStrategy strategy : this.registry.getTileEntityRegistrationStrategyList()) {
			strategy.register();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientRegisterModelsEvent(ModelRegistryEvent event) {
		for (IClientModelRegistrationStrategy strategy : this.registry.getClientModelRegistrationStrategyList()) {
			strategy.register();
		}
	}


	@SubscribeEvent
	public void onTableLoaded(LootTableLoadEvent event) {

		for (final LootBuilder builder : this.registry.getLootTableEntries().get(event.getName())) {

			final LootPool pool = event.getTable().getPool(builder.getPool());

			if (pool != null) {

				pool.addEntry(builder.build());
			} else {

				Constants.LOG.info("The mod {} tried to add loot to {} but the pool was not found. {}", this.registry.getModid(), event.getName(), builder.toString());
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void modelRegistryEvent(ModelRegistryEvent event) {

		for (final Item item : this.registry.getItems()) {

			this.registry.registerInventoryModel(item);
		}

		for (final Block block : this.registry.getBlocks()) {

			this.registry.registerInventoryModel(block);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerBlockColor(ColorHandlerEvent.Block event) {

		for (final Block block : this.registry.getColoredBlocks()) {

			event.getBlockColors().registerBlockColorHandler(((IColorfulBlock) block).getColorHandler(), block);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerItemColor(ColorHandlerEvent.Item event) {

		for (final Block block : this.registry.getColoredBlocks()) {

			final IColorfulBlock colorfulBlock = (IColorfulBlock) block;

			if (colorfulBlock.getItemColorHandler() != null) {

				event.getItemColors().registerItemColorHandler(colorfulBlock.getItemColorHandler(), Item.getItemFromBlock(block));
			}
		}

		for (final Item item : this.registry.getColoredItems()) {

			event.getItemColors().registerItemColorHandler(((IColorfulItem) item).getColorHandler(), item);
		}
	}

	public void init() {}

	public void postInit() {}

	@SideOnly(Side.CLIENT)
	public void clientInit() {

		for (final ITileEntityBlock provider : this.registry.getTileProviders()) {

			final TileEntitySpecialRenderer tesr = provider.getTileRenderer();

			if (tesr != null) {

				ClientRegistry.bindTileEntitySpecialRenderer(provider.getTileEntityClass(), tesr);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void clientPostInit() {}
}
