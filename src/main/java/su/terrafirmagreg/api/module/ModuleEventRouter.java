package su.terrafirmagreg.api.module;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.util.GameUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ModuleEventRouter {

	private final List<ModuleBase> loadedModules;
	private final Map<Class<? extends FMLStateEvent>, IFMLStateEventRoute> routes;

	public ModuleEventRouter(List<ModuleBase> loadedModules) {

		this.loadedModules = loadedModules;
		this.routes = new HashMap<>();

		this.routes.put(FMLConstructionEvent.class,
				(IFMLStateEventRoute<FMLConstructionEvent>) (event) ->
						this.fireEvent(module -> {
							module.getLogger().info("Construction start");
							module.onConstruction(event);
							module.getLogger().info("Construction complete");
						})
		);
		this.routes.put(FMLPreInitializationEvent.class,
				(IFMLStateEventRoute<FMLPreInitializationEvent>) (event) ->
						this.fireEvent(module -> {
							module.getLogger().info("Registering packets");
							module.onNetworkRegister();

							module.getLogger().info("Registering");
							module.onRegister();

							module.getLogger().info("Pre-Init start");
							module.onPreInit(event);
							module.getLogger().info("Pre-Init complete");

							if (event.getSide().isClient()) {
								module.getLogger().info("Client Registering");
								module.onClientRegister();

								module.getLogger().info("Client Pre-Init start");
								module.onClientPreInit(event);
								module.getLogger().info("Client Pre-Init complete");
							}
						})
		);
		this.routes.put(FMLInitializationEvent.class,
				(IFMLStateEventRoute<FMLInitializationEvent>) (event) ->
						this.fireEvent(module -> {
							module.getLogger().info("Init start");
							module.onInit(event);
							module.getLogger().info("Init complete");

							if (GameUtils.isClient()) {
								module.getLogger().info("Client Init start");
								module.onClientInit(event);
								module.getLogger().info("Client Init complete");
							}
						})
		);
		this.routes.put(FMLPostInitializationEvent.class,
				(IFMLStateEventRoute<FMLPostInitializationEvent>) (event) ->
						this.fireEvent(module -> {
							module.getLogger().info("Post-Init start");
							module.onPostInit(event);
							module.getLogger().info("Post-Init complete");

							if (GameUtils.isClient()) {
								module.getLogger().info("Client Post-Init start");
								module.onClientPostInit(event);
								module.getLogger().info("Client Post-Init complete");
							}
						})
		);
		this.routes.put(FMLLoadCompleteEvent.class,
				(IFMLStateEventRoute<FMLLoadCompleteEvent>) (event) ->
						this.fireEvent(module -> {
							module.getLogger().info("Load-complete start");
							module.onLoadComplete(event);
							module.getLogger().info("Load-complete complete");
						})
		);
		this.routes.put(FMLServerAboutToStartEvent.class,
				(IFMLStateEventRoute<FMLServerAboutToStartEvent>) (event) ->
						this.fireEvent(module -> {
							module.getLogger().info("Server-about-to-start start");
							module.onServerAboutToStart(event);
							module.getLogger().info("Server-about-to-start complete");
						})
		);
		this.routes.put(FMLServerStartingEvent.class,
				(IFMLStateEventRoute<FMLServerStartingEvent>) (event) ->
						this.fireEvent(module -> {
							module.getLogger().info("Server-starting start");
							module.onServerStarting(event);
							module.getLogger().info("Server-starting complete");
						})
		);
		this.routes.put(FMLServerStartedEvent.class,
				(IFMLStateEventRoute<FMLServerStartedEvent>) (event) ->
						this.fireEvent(module -> {
							module.getLogger().info("Server-started start");
							module.onServerStarted(event);
							module.getLogger().info("Server-started complete");
						})
		);
		this.routes.put(FMLServerStoppingEvent.class,
				(IFMLStateEventRoute<FMLServerStoppingEvent>) (event) ->
						this.fireEvent(module -> {
							module.getLogger().info("Server-stopping start");
							module.onServerStopping(event);
							module.getLogger().info("Server-stopping complete");
						})
		);
		this.routes.put(FMLServerStoppedEvent.class,
				(IFMLStateEventRoute<FMLServerStoppedEvent>) (event) ->
						this.fireEvent(module -> {
							module.getLogger().info("Server-stopped start");
							module.onServerStopped(event);
							module.getLogger().info("Server-stopped complete");
						})
		);
	}

	// --------------------------------------------------------------------------
	// - Registration Events
	// --------------------------------------------------------------------------

	@SubscribeEvent
	@SuppressWarnings("unused")
	protected void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register BlockEvent start");
			module.getRegistry().onRegisterBlock(event);
			module.getRegistry().onRegisterTileEntities();
			module.getLogger().info("Register BlockEvent complete");
		});
	}

	@SubscribeEvent
	@SuppressWarnings("unused")
	protected void onRegisterItemEvent(RegistryEvent.Register<Item> event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register ItemEvent start");
			module.getRegistry().onRegisterItem(event);
			module.getLogger().info("Register ItemEvent complete");
		});
	}

	@SubscribeEvent
	@SuppressWarnings("unused")
	protected void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register PotionEvent start");
			module.getRegistry().onRegisterPotion(event);
			module.getLogger().info("Register PotionEvent complete");
		});
	}

	@SubscribeEvent
	@SuppressWarnings("unused")
	protected void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register PotionTypeEvent start");
			module.getRegistry().onRegisterPotionType(event);
			module.getLogger().info("Register PotionTypeEvent complete");
		});
	}

	@SubscribeEvent
	@SuppressWarnings("unused")
	protected void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register BiomeEvent start");
			module.getRegistry().onRegisterBiome(event);
			module.getLogger().info("Register BiomeEvent complete");
		});
	}

	@SubscribeEvent
	@SuppressWarnings("unused")
	protected void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register SoundEvent start");
			module.getRegistry().onRegisterSound(event);
			module.getLogger().info("Register SoundEvent complete");
		});
	}

	@SubscribeEvent
	@SuppressWarnings("unused")
	protected void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register EntityEvent start");
			module.getRegistry().onRegisterEntity(event);
			module.getLogger().info("Register EntityEvent complete");
		});
	}

	@SubscribeEvent
	@SuppressWarnings("unused")
	protected void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register EnchantmentEvent start");
			module.getRegistry().onRegisterEnchantment(event);
			module.getLogger().info("Register EnchantmentEvent complete");
		});
	}

	@SubscribeEvent
	@SuppressWarnings("unused")
	protected void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register VillagerProfessionEvent start");
			module.getRegistry().onRegisterVillagerProfession(event);
			module.getLogger().info("Register VillagerProfessionEvent complete");
		});
	}

	@SubscribeEvent
	@SuppressWarnings("unused")
	protected void onRegisterEnchantmentEvent(LootTableLoadEvent event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register LootTableLoadEvent start");
			module.getRegistry().onRegisterLootTableLoad(event);
			module.getLogger().info("Register LootTableLoadEvent complete");
		});
	}


	@SubscribeEvent
	@SuppressWarnings("unused")
	protected void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register RecipesEvent start");
			module.getRegistry().onRegisterRecipes(event);
			module.getLogger().info("Register RecipesEvent complete");
		});
	}

	// --------------------------------------------------------------------------
	// - Client
	// --------------------------------------------------------------------------

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unused")
	protected void onClientRegisterModelsEvent(ModelRegistryEvent event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register ModelsEvent start");
			module.getRegistry().onRegisterModels(event);
			module.getRegistry().onRegisterTileEntitySpecialRenderer();
			module.getLogger().info("Register ModelsEvent complete");
		});
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unused")
	protected void onClientRegisterBlockColor(ColorHandlerEvent.Block event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register ModelsEvent start");
			module.getRegistry().onRegisterBlockColor(event);
			module.getLogger().info("Register ModelsEvent complete");
		});
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unused")
	protected void onClientRegisterItemColor(ColorHandlerEvent.Item event) {
		this.fireEvent(module -> {
			module.getLogger().info("Register ModelsEvent start");
			module.getRegistry().onRegisterItemColor(event);
			module.getLogger().info("Register ModelsEvent complete");
		});
	}

	// --------------------------------------------------------------------------
	// - Internal
	// --------------------------------------------------------------------------

	protected void fireEvent(Consumer<ModuleBase> moduleConsumer) {
		for (ModuleBase module : this.loadedModules) {
			moduleConsumer.accept(module);
		}
	}

	protected <E extends FMLStateEvent> void routeFMLStateEvent(E event) {
		//noinspection unchecked
		IFMLStateEventRoute<E> route = this.routes.get(event.getClass());

		if (route == null) throw new IllegalArgumentException("No route found for event: " + event.getClass());
		route.routeEvent(event);
	}
}
