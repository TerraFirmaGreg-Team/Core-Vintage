package su.terrafirmagreg.api.module;

import su.terrafirmagreg.api.util.GameUtils;

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
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.DataSerializerEntry;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ModuleEventRouter {

  private final Collection<IModule> loadedModules;

  @SuppressWarnings("rawtypes")
  private final Map<Class<? extends FMLStateEvent>, IModuleStateEventRoute> routes;

  public ModuleEventRouter(Collection<IModule> loadedModules) {

    this.loadedModules = loadedModules;
    this.routes = new HashMap<>();

    this.routes.put(FMLConstructionEvent.class,
                    (IModuleStateEventRoute<FMLConstructionEvent>) (event) ->
                      this.fireEvent(module -> {
                        module.getLogger().debug("Registering packets");
                        module.onNetworkRegister();

                        module.getLogger().debug("Construction start");
                        module.onConstruction(event);

                        module.getLogger().debug("Construction complete");
                      })
    );
    this.routes.put(FMLPreInitializationEvent.class,
                    (IModuleStateEventRoute<FMLPreInitializationEvent>) (event) ->
                      this.fireEvent(module -> {
                        module.getLogger().debug("Pre-Init start");
                        module.onPreInit(event);

                        module.getLogger().debug("Registering");
                        module.onRegister();

                        module.getLogger().debug("Pre-Init complete");

                        if (event.getSide().isClient()) {
                          module.getLogger().debug("Client Pre-Init start");
                          module.onClientPreInit(event);

                          module.getLogger().debug("Client Registering");
                          module.onClientRegister();

                          module.getLogger().debug("Client Pre-Init complete");
                        }
                      })
    );
    this.routes.put(FMLInitializationEvent.class,
                    (IModuleStateEventRoute<FMLInitializationEvent>) (event) ->
                      this.fireEvent(module -> {
                        module.getLogger().debug("Init start");
                        module.onInit(event);
                        module.getLogger().debug("Init complete");

                        if (GameUtils.isClient()) {
                          module.getLogger().debug("Client Init start");
                          module.onClientInit(event);
                          module.getLogger().debug("Client Init complete");
                        }
                      })
    );
    this.routes.put(FMLPostInitializationEvent.class,
                    (IModuleStateEventRoute<FMLPostInitializationEvent>) (event) ->
                      this.fireEvent(module -> {
                        module.getLogger().debug("Post-Init start");
                        module.onPostInit(event);
                        module.getLogger().debug("Post-Init complete");

                        if (GameUtils.isClient()) {
                          module.getLogger().debug("Client Post-Init start");
                          module.onClientPostInit(event);
                          module.getLogger().debug("Client Post-Init complete");
                        }
                      })
    );
    this.routes.put(FMLLoadCompleteEvent.class,
                    (IModuleStateEventRoute<FMLLoadCompleteEvent>) (event) ->
                      this.fireEvent(module -> {
                        module.getLogger().debug("Load-complete start");
                        module.onLoadComplete(event);
                        module.getLogger().debug("Load-complete complete");
                      })
    );
    this.routes.put(FMLServerAboutToStartEvent.class,
                    (IModuleStateEventRoute<FMLServerAboutToStartEvent>) (event) ->
                      this.fireEvent(module -> {
                        module.getLogger().debug("Server-about-to-start start");
                        module.onServerAboutToStart(event);
                        module.getLogger().debug("Server-about-to-start complete");
                      })
    );
    this.routes.put(FMLServerStartingEvent.class,
                    (IModuleStateEventRoute<FMLServerStartingEvent>) (event) ->
                      this.fireEvent(module -> {
                        module.getLogger().debug("Server-starting start");
                        module.onServerStarting(event);
                        module.getRegistry().onRegisterCommand(event);
                        module.getLogger().debug("Server-starting complete");
                      })
    );
    this.routes.put(FMLServerStartedEvent.class,
                    (IModuleStateEventRoute<FMLServerStartedEvent>) (event) ->
                      this.fireEvent(module -> {
                        module.getLogger().debug("Server-started start");
                        module.onServerStarted(event);
                        module.getLogger().debug("Server-started complete");
                      })
    );
    this.routes.put(FMLServerStoppingEvent.class,
                    (IModuleStateEventRoute<FMLServerStoppingEvent>) (event) ->
                      this.fireEvent(module -> {
                        module.getLogger().debug("Server-stopping start");
                        module.onServerStopping(event);
                        module.getLogger().debug("Server-stopping complete");
                      })
    );
    this.routes.put(FMLServerStoppedEvent.class,
                    (IModuleStateEventRoute<FMLServerStoppedEvent>) (event) ->
                      this.fireEvent(module -> {
                        module.getLogger().debug("Server-stopped start");
                        module.onServerStopped(event);
                        module.getLogger().debug("Server-stopped complete");
                      })
    );
  }

  // --------------------------------------------------------------------------
  // - Internal
  // --------------------------------------------------------------------------

  protected void fireEvent(Consumer<IModule> moduleConsumer) {
    loadedModules.forEach(moduleConsumer);
  }

  protected <E extends FMLStateEvent> void routeFMLStateEvent(E event) {
    //noinspection unchecked
    IModuleStateEventRoute<E> route = this.routes.get(event.getClass());
    Preconditions.checkNotNull(route, "No route found for event: %s", event.getClass());

    route.routeEvent(event);
  }

  // --------------------------------------------------------------------------
  // - Registration Events
  // --------------------------------------------------------------------------

  @SubscribeEvent
  protected void onNewRegistryEvent(RegistryEvent.NewRegistry event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register NewRegistryEvent start");
      module.onNewRegister();
      module.getLogger().debug("Register NewRegistryEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register RecipesEvent start");
      module.onRecipesRegister();
      module.getLogger().debug("Register RecipesEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register BlockEvent start");
      module.getRegistry().onRegisterBlock(event);
      module.getRegistry().onRegisterTileEntities();
      module.getLogger().debug("Register BlockEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterItemEvent(RegistryEvent.Register<Item> event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register ItemEvent start");
      module.getRegistry().onRegisterItem(event);
      module.getRegistry().onRegisterOreDict();
      module.getLogger().debug("Register ItemEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register PotionEvent start");
      module.getRegistry().onRegisterPotion(event);
      module.getLogger().debug("Register PotionEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register PotionTypeEvent start");
      module.getRegistry().onRegisterPotionType(event);
      module.getLogger().debug("Register PotionTypeEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register BiomeEvent start");
      module.getRegistry().onRegisterBiome(event);
      module.getLogger().debug("Register BiomeEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register SoundEvent start");
      module.getRegistry().onRegisterSound(event);
      module.getLogger().debug("Register SoundEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register EntityEvent start");
      module.getRegistry().onRegisterEntity(event);
      module.getLogger().debug("Register EntityEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register EnchantmentEvent start");
      module.getRegistry().onRegisterEnchantment(event);
      module.getLogger().debug("Register EnchantmentEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register VillagerProfessionEvent start");
      module.getRegistry().onRegisterVillagerProfession(event);
      module.getLogger().debug("Register VillagerProfessionEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterRecipeEvent(RegistryEvent.Register<IRecipe> event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register RecipeEvent start");
      module.getRegistry().onRegisterRecipe(event);
      module.getLogger().debug("Register RecipeEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterDataSerializerEntryEvent(RegistryEvent.Register<DataSerializerEntry> event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register RecipeEvent start");
      module.getRegistry().onRegisterDataSerializerEntry(event);
      module.getLogger().debug("Register RecipeEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterLootTableLoadEvent(LootTableLoadEvent event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register LootTableLoadEvent start");
      module.getRegistry().onRegisterLootTableLoad(event);
      module.getLogger().debug("Register LootTableLoadEvent complete");
    });
  }

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  protected void onClientRegisterModelsEvent(ModelRegistryEvent event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register ModelsEvent start");
      module.getRegistry().onRegisterModels(event);
      module.getLogger().debug("Register ModelsEvent complete");
    });
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  protected void onClientRegisterBlockColor(ColorHandlerEvent.Block event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register ColorHandlerEvent Block start");
      module.getRegistry().onRegisterBlockColor(event);
      module.getLogger().debug("Register ColorHandlerEvent Block complete");
    });
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  protected void onClientRegisterItemColor(ColorHandlerEvent.Item event) {
    this.fireEvent(module -> {
      module.getLogger().debug("Register ColorHandlerEvent Item start");
      module.getRegistry().onRegisterItemColor(event);
      module.getLogger().debug("Register ColorHandlerEvent Item complete");
    });
  }
}
