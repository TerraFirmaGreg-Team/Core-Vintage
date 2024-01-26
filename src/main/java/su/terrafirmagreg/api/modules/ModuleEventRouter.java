package su.terrafirmagreg.api.modules;

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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class ModuleEventRouter {

    private final Set<ModuleBase> loadedModules;
    private final Map<Class<? extends FMLStateEvent>, IFMLStateEventRoute> routes;

    public ModuleEventRouter(Set<ModuleBase> loadedModules) {

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
        this.fireEvent(module -> module.getAutoRegistry().onRegisterBlock(event));
        this.onRegisterTileEntitiesEvent();
    }

    @SubscribeEvent
    public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {
        this.fireEvent(module -> module.getAutoRegistry().onRegisterItem(event));

    }

    @SubscribeEvent
    public void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {
        this.fireEvent(module -> module.getAutoRegistry().onRegisterPotion(event));

    }

    @SubscribeEvent
    public void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
        this.fireEvent(module -> module.getAutoRegistry().onRegisterPotionType(event));

    }

    @SubscribeEvent
    public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
        this.fireEvent(module -> module.getAutoRegistry().onRegisterBiome(event));

    }

    @SubscribeEvent
    public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {
        this.fireEvent(module -> module.getAutoRegistry().onRegisterSound(event));

    }

    @SubscribeEvent
    public void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {
        this.fireEvent(module -> module.getAutoRegistry().onRegisterEntity(event));
    }

    @SubscribeEvent
    public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
        this.fireEvent(module -> module.getAutoRegistry().onRegisterEnchantment(event));
    }

    @SubscribeEvent
    public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
        this.fireEvent(module -> module.getAutoRegistry().onRegisterVillagerProfession(event));
    }


    @SubscribeEvent
    public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {
        this.fireEvent(module -> module.getAutoRegistry().onRegisterRecipes(event));
    }

    private void onRegisterTileEntitiesEvent() {
        this.fireEvent(module -> module.getAutoRegistry().onRegisterTileEntities());

    }

    // --------------------------------------------------------------------------
    // - Client
    // --------------------------------------------------------------------------

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientRegisterModelsEvent(ModelRegistryEvent event) {
        this.fireEvent(module -> module.getAutoRegistry().onClientRegisterModels(event));

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
