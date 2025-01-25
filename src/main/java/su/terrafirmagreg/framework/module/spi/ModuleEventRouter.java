package su.terrafirmagreg.framework.module.spi;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.module.api.IModuleEventRoute;

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
import net.minecraftforge.common.MinecraftForge;
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
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;


public class ModuleEventRouter {

  private final Collection<IModule> loadedModules;

  @SuppressWarnings("rawtypes")
  private final Map<Class<? extends FMLStateEvent>, IModuleEventRoute.State> routes;

  public ModuleEventRouter(Collection<IModule> loadedModules) {

    this.loadedModules = loadedModules;
    this.routes = new Object2ObjectLinkedOpenHashMap<>();

    this.routes.put(FMLConstructionEvent.class,
      (IModuleEventRoute.State<FMLConstructionEvent>) (event) ->
        this.fireEvent(module -> {
          module.getLogger().info("Registering event handlers");
          module.getEventBusSubscribers().forEach(MinecraftForge.EVENT_BUS::register);

          if (module.getNetwork() != null) {
            module.getLogger().info("Registering packets");
            module.onNetworkRegister(module.getNetwork());
          }

          module.getLogger().info("Construction start");
          module.onConstruction(event);
          module.getLogger().info("Construction complete");
        })
    );
    this.routes.put(FMLPreInitializationEvent.class,
      (IModuleEventRoute.State<FMLPreInitializationEvent>) (event) ->
        this.fireEvent(module -> {

          if (module.getRegistry() != null) {
            module.getLogger().info("Registering");
            module.onRegister(module.getRegistry());
          }

          module.getLogger().info("Pre-Init start");
          module.onPreInit(event);
          module.getLogger().info("Pre-Init complete");

          if (ModUtils.isClient()) {
            if (module.getRegistry() != null) {
              module.getLogger().info("Client Registering");
              module.onClientRegister(module.getRegistry());
            }

            module.getLogger().info("Client Pre-Init start");
            module.onClientPreInit(event);
            module.getLogger().info("Client Pre-Init complete");
          }
        })
    );
    this.routes.put(FMLInitializationEvent.class,
      (IModuleEventRoute.State<FMLInitializationEvent>) (event) ->
        this.fireEvent(module -> {
          module.getLogger().info("Init start");
          module.onInit(event);
          module.getLogger().info("Init complete");

          if (ModUtils.isClient()) {
            module.getLogger().info("Client Init start");
            module.onClientInit(event);
            module.getLogger().info("Client Init complete");
          }
        })
    );
    this.routes.put(FMLPostInitializationEvent.class,
      (IModuleEventRoute.State<FMLPostInitializationEvent>) (event) ->
        this.fireEvent(module -> {
          module.getLogger().info("Post-Init start");
          module.onPostInit(event);
          module.getLogger().info("Post-Init complete");

          if (ModUtils.isClient()) {
            module.getLogger().info("Client Post-Init start");
            module.onClientPostInit(event);
            module.getLogger().info("Client Post-Init complete");
          }
        })
    );
    this.routes.put(FMLLoadCompleteEvent.class,
      (IModuleEventRoute.State<FMLLoadCompleteEvent>) (event) ->
        this.fireEvent(module -> {
          module.getLogger().info("Load-complete start");
          module.onLoadComplete(event);
          module.getLogger().info("Load-complete complete");
        })
    );
    this.routes.put(FMLServerAboutToStartEvent.class,
      (IModuleEventRoute.State<FMLServerAboutToStartEvent>) (event) ->
        this.fireEvent(module -> {
          module.getLogger().info("Server-about-to-start start");
          module.onServerAboutToStart(event);
          module.getLogger().info("Server-about-to-start complete");
        })
    );
    this.routes.put(FMLServerStartingEvent.class,
      (IModuleEventRoute.State<FMLServerStartingEvent>) (event) ->
        this.fireEvent(module -> {

          if (module.getCommand() != null) {
            module.getLogger().info("Command Registering");
            module.getCommand().registerServerCommand(event);
            module.onCommandRegister(module.getCommand());
          }

          module.getLogger().info("Server-starting start");
          module.onServerStarting(event);
          module.getLogger().info("Server-starting complete");
        })
    );
    this.routes.put(FMLServerStartedEvent.class,
      (IModuleEventRoute.State<FMLServerStartedEvent>) (event) ->
        this.fireEvent(module -> {
          module.getLogger().info("Server-started start");
          module.onServerStarted(event);
          module.getLogger().info("Server-started complete");
        })
    );
    this.routes.put(FMLServerStoppingEvent.class,
      (IModuleEventRoute.State<FMLServerStoppingEvent>) (event) ->
        this.fireEvent(module -> {
          module.getLogger().info("Server-stopping start");
          module.onServerStopping(event);
          module.getLogger().info("Server-stopping complete");
        })
    );
    this.routes.put(FMLServerStoppedEvent.class,
      (IModuleEventRoute.State<FMLServerStoppedEvent>) (event) ->
        this.fireEvent(module -> {
          module.getLogger().info("Server-stopped start");
          module.onServerStopped(event);
          module.getLogger().info("Server-stopped complete");
        })
    );
  }

  // --------------------------------------------------------------------------
  // - Internal
  // --------------------------------------------------------------------------

  protected void fireEvent(Consumer<IModule> moduleConsumer) {
    loadedModules.forEach(moduleConsumer);
  }

  public <E extends FMLStateEvent> void routeEvent(E event) {
    //noinspection unchecked
    IModuleEventRoute.State<E> route = this.routes.get(event.getClass());
    Preconditions.checkNotNull(route, "No route found for event: %s", event.getClass());

    route.routeEvent(event);
  }

  // --------------------------------------------------------------------------
  // - Registration Events
  // --------------------------------------------------------------------------

  @SubscribeEvent
  protected void onNewRegistryEvent(RegistryEvent.NewRegistry event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register NewRegistryEvent start");
      module.onNewRegister();
      module.getLogger().info("Register NewRegistryEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register BlockEvent start");
      module.getRegistry().onRegisterBlock(event);
      module.getLogger().info("Register BlockEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterItemEvent(RegistryEvent.Register<Item> event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register ItemEvent start");
      module.getRegistry().onRegisterItem(event);
      module.getLogger().info("Register ItemEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register PotionEvent start");
      module.getRegistry().onRegisterPotion(event);
      module.getLogger().info("Register PotionEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register PotionTypeEvent start");
      module.getRegistry().onRegisterPotionType(event);
      module.getLogger().info("Register PotionTypeEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register BiomeEvent start");
      module.getRegistry().onRegisterBiome(event);
      module.getLogger().info("Register BiomeEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register SoundEvent start");
      module.getRegistry().onRegisterSound(event);
      module.getLogger().info("Register SoundEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register EntityEvent start");
      module.getRegistry().onRegisterEntity(event);
      module.getLogger().info("Register EntityEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register EnchantmentEvent start");
      module.getRegistry().onRegisterEnchantment(event);
      module.getLogger().info("Register EnchantmentEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register VillagerProfessionEvent start");
      module.getRegistry().onRegisterVillagerProfession(event);
      module.getLogger().info("Register VillagerProfessionEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterRecipeEvent(RegistryEvent.Register<IRecipe> event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register RecipeEvent start");
      module.getRegistry().onRegisterRecipe(event);
      module.getLogger().info("Register RecipeEvent complete");
    });
  }

  @SubscribeEvent
  protected void onRegisterDataSerializerEntryEvent(RegistryEvent.Register<DataSerializerEntry> event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register RecipeEvent start");
      module.getRegistry().onRegisterDataSerializerEntry(event);
      module.getLogger().info("Register RecipeEvent complete");
    });
  }

//  @SubscribeEvent
//  protected void onRegisterLootTableLoadEvent(LootTableLoadEvent event) {
//    this.fireEvent(module -> {
//      module.getLogger().info("Register LootTableLoadEvent start");
//      module.getRegistry().onRegisterLootTableLoad(event);
//      module.getLogger().info("Register LootTableLoadEvent complete");
//    });
//  }

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  protected void onClientRegisterModelsEvent(ModelRegistryEvent event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register ModelsEvent start");
      module.getRegistry().onRegisterModels(event);
      module.getLogger().info("Register ModelsEvent complete");
    });
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  protected void onClientRegisterBlockColor(ColorHandlerEvent.Block event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register ColorHandlerEvent Block start");
      module.getRegistry().onRegisterBlockColor(event);
      module.getLogger().info("Register ColorHandlerEvent Block complete");
    });
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  protected void onClientRegisterItemColor(ColorHandlerEvent.Item event) {
    this.fireEvent(module -> {
      module.getLogger().info("Register ColorHandlerEvent Item start");
      module.getRegistry().onRegisterItemColor(event);
      module.getLogger().info("Register ColorHandlerEvent Item complete");
    });
  }
}
