package su.terrafirmagreg.api.module;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class ModuleEventRouter {

    private final Set<ModuleBase> loadedModules;
    private final Map<Class<? extends FMLStateEvent>, IFMLStateEventRoute> routes;

    public ModuleEventRouter(Set<ModuleBase> loadedModules) {

        System.out.println("Loaded modules: " + loadedModules);

        this.loadedModules = loadedModules;
        this.routes = new HashMap<>();

        this.routes.put(FMLConstructionEvent.class,
                (IFMLStateEventRoute<FMLConstructionEvent>) (event) -> {
                    this.fireEvent(module -> {
                        module.getLogger().debug("Registering packets");
                        module.onNetworkRegister(); //TODO: Pre-Init?

                        module.getLogger().debug("Construction start");
                        module.onConstruction(event);
                        module.getLogger().debug("Construction complete");
                    });
                }
        );
        this.routes.put(FMLPreInitializationEvent.class,
                (IFMLStateEventRoute<FMLPreInitializationEvent>) (event) ->
                        this.fireEvent(module -> {
                            module.getLogger().debug("Registering");
                            module.onRegister();

                            module.getLogger().debug("Pre-Init start");
                            module.onPreInit(event);
                            module.getLogger().debug("Pre-Init complete");

                            if (event.getSide().isClient()) {
                                module.getLogger().debug("Client Registering");
                                module.onClientRegister();

                                module.getLogger().debug("Client Pre-Init start");
                                module.onClientPreInit(event);
                                module.getLogger().debug("Client Pre-Init complete");
                            }
                        })
        );
        this.routes.put(FMLInitializationEvent.class,
                (IFMLStateEventRoute<FMLInitializationEvent>) (event) ->
                        this.fireEvent(module -> {
                            module.getLogger().debug("Init start");
                            module.onInit(event);
                            module.getLogger().debug("Init complete");

                            if (event.getSide().isClient()) {
                                module.getLogger().debug("Client Init start");
                                module.onClientInit(event);
                                module.getLogger().debug("Client Init complete");
                            }
                        })
        );
        this.routes.put(FMLPostInitializationEvent.class,
                (IFMLStateEventRoute<FMLPostInitializationEvent>) (event) ->
                        this.fireEvent(module -> {
                            module.getLogger().debug("Post-onInit start");
                            module.onPostInit(event);
                            module.getLogger().debug("Post-onInit complete");

                            if (event.getSide() == Side.CLIENT) {
                                module.getLogger().debug("Client Post-onInit start");
                                module.onClientPostInit(event);
                                module.getLogger().debug("Client Post-onInit complete");
                            }
                        })
        );
        this.routes.put(FMLLoadCompleteEvent.class,
                (IFMLStateEventRoute<FMLLoadCompleteEvent>) (event) ->
                        this.fireEvent(module -> {
                            module.getLogger().debug("Load-complete start");
                            module.onLoadComplete(event);
                            module.getLogger().debug("Load-complete complete");
                        })
        );
        this.routes.put(FMLServerAboutToStartEvent.class,
                (IFMLStateEventRoute<FMLServerAboutToStartEvent>) (event) ->
                        this.fireEvent(module -> {
                            module.getLogger().debug("Server-about-to-start start");
                            module.onServerAboutToStart(event);
                            module.getLogger().debug("Server-about-to-start complete");
                        })
        );
        this.routes.put(FMLServerStartingEvent.class,
                (IFMLStateEventRoute<FMLServerStartingEvent>) (event) ->
                        this.fireEvent(module -> {
                            module.getLogger().debug("Server-starting start");
                            module.onServerStarting(event);
                            module.getLogger().debug("Server-starting complete");
                        })
        );
        this.routes.put(FMLServerStartedEvent.class,
                (IFMLStateEventRoute<FMLServerStartedEvent>) (event) ->
                        this.fireEvent(module -> {
                            module.getLogger().debug("Server-started start");
                            module.onServerStarted(event);
                            module.getLogger().debug("Server-started complete");
                        })
        );
        this.routes.put(FMLServerStoppingEvent.class,
                (IFMLStateEventRoute<FMLServerStoppingEvent>) (event) ->
                        this.fireEvent(module -> {
                            module.getLogger().debug("Server-stopping start");
                            module.onServerStopping(event);
                            module.getLogger().debug("Server-stopping complete");
                        })
        );
        this.routes.put(FMLServerStoppedEvent.class,
                (IFMLStateEventRoute<FMLServerStoppedEvent>) (event) ->
                        this.fireEvent(module -> {
                            module.getLogger().debug("Server-topped start");
                            module.onServerStopped(event);
                            module.getLogger().debug("Server-topped complete");
                        })
        );
    }

    public <E extends FMLStateEvent> void routeFMLStateEvent(E event) {
        //noinspection unchecked
        IFMLStateEventRoute<E> route = this.routes.get(event.getClass());

        if (route == null) throw new IllegalArgumentException("No route found for event: " + event.getClass());
        route.routeEvent(event);
    }

    // --------------------------------------------------------------------------
    // - Registration Events
    // --------------------------------------------------------------------------

    @SubscribeEvent
    public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {
        this.fireEvent(module -> {
            module.getLogger().debug("Register BlockEvent start");
            module.getAutoRegistry().onRegisterBlock(event);
            module.getAutoRegistry().onRegisterTileEntities();
            module.getLogger().debug("Register BlockEvent complete");
        });
    }

    @SubscribeEvent
    public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {
        this.fireEvent(module -> {
            module.getLogger().debug("Register ItemEvent start");
            module.getAutoRegistry().onRegisterItem(event);
            module.getLogger().debug("Register ItemEvent complete");
        });
    }

    @SubscribeEvent
    public void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {
        this.fireEvent(module -> {
            module.getLogger().debug("Register PotionEvent start");
            module.getAutoRegistry().onRegisterPotion(event);
            module.getLogger().debug("Register PotionEvent complete");
        });
    }

    @SubscribeEvent
    public void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
        this.fireEvent(module -> {
            module.getLogger().debug("Register PotionTypeEvent start");
            module.getAutoRegistry().onRegisterPotionType(event);
            module.getLogger().debug("Register PotionTypeEvent complete");
        });
    }

    @SubscribeEvent
    public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
        this.fireEvent(module -> {
            module.getLogger().debug("Register BiomeEvent start");
            module.getAutoRegistry().onRegisterBiome(event);
            module.getLogger().debug("Register BiomeEvent complete");
        });
    }

    @SubscribeEvent
    public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {
        this.fireEvent(module -> {
            module.getLogger().debug("Register SoundEvent start");
            module.getAutoRegistry().onRegisterSound(event);
            module.getLogger().debug("Register SoundEvent complete");
        });
    }

    @SubscribeEvent
    public void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {
        this.fireEvent(module -> {
            module.getLogger().debug("Register EntityEvent start");
            module.getAutoRegistry().onRegisterEntity(event);
            module.getLogger().debug("Register EntityEvent complete");
        });
    }

    @SubscribeEvent
    public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
        this.fireEvent(module -> {
            module.getLogger().debug("Register EnchantmentEvent start");
            module.getAutoRegistry().onRegisterEnchantment(event);
            module.getLogger().debug("Register EnchantmentEvent complete");
        });
    }

    @SubscribeEvent
    public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
        this.fireEvent(module -> {
            module.getLogger().debug("Register VillagerProfessionEvent start");
            module.getAutoRegistry().onRegisterVillagerProfession(event);
            module.getLogger().debug("Register VillagerProfessionEvent complete");
        });
    }


    @SubscribeEvent
    public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {
        this.fireEvent(module -> {
            module.getLogger().debug("Register RecipesEvent( start");
            module.getAutoRegistry().onRegisterRecipes(event);
            module.getLogger().debug("Register RecipesEvent( complete");
        });
    }

    // --------------------------------------------------------------------------
    // - Client
    // --------------------------------------------------------------------------

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientRegisterModelsEvent(ModelRegistryEvent event) {
        this.fireEvent(module -> {
            module.getLogger().debug("Register ModelsEvent start");
            module.getAutoRegistry().onClientRegisterModels(event);
            module.getLogger().debug("Register ModelsEvent complete");
        });
    }

    // --------------------------------------------------------------------------
    // - Internal
    // --------------------------------------------------------------------------

    private void fireEvent(Consumer<ModuleBase> moduleConsumer) {
        for (ModuleBase module : this.loadedModules) {
            moduleConsumer.accept(module);
        }
    }

//    private void fireEvent(Consumer<ModuleBase> moduleConsumer) {
//        for (ModuleBase module : loadedModules) {
//            currentContainer = containers.get(getContainerID(module));
//            moduleConsumer.accept(module);
//        }
//    }

}
