//package su.tfg.api.module;
//
//import net.minecraft.block.Block;
//import net.minecraft.enchantment.Enchantment;
//import net.minecraft.item.Item;
//import net.minecraft.item.crafting.IRecipe;
//import net.minecraft.potion.Potion;
//import net.minecraft.potion.PotionType;
//import net.minecraft.util.SoundEvent;
//import net.minecraft.world.biome.Biome;
//import net.minecraftforge.client.event.ModelRegistryEvent;
//import net.minecraftforge.event.RegistryEvent;
//import net.minecraftforge.fml.common.event.*;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.registry.EntityEntry;
//import net.minecraftforge.fml.common.registry.VillagerRegistry;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Consumer;
//
//public class ModuleEventRouter {
//
//    private final List<ModuleBase> moduleList;
//    private final Map<Class<? extends FMLStateEvent>, IFMLStateEventRoute> routes;
//
//    public ModuleEventRouter(List<ModuleBase> moduleList) {
//
//        this.moduleList = moduleList;
//        this.routes = new HashMap<>();
//
//        this.routes.put(
//                FMLConstructionEvent.class,
//                (IFMLStateEventRoute<FMLConstructionEvent>) (event) ->
//                        this.fireEvent(moduleBase -> moduleBase.onConstruction(event))
//        );
//        this.routes.put(
//                FMLPreInitializationEvent.class,
//                (IFMLStateEventRoute<FMLPreInitializationEvent>) (event) -> {
//                    this.fireEvent(moduleBase -> moduleBase.onPreInitialization(event));
//
//                    if (event.getSide() == Side.CLIENT) {
//                        this.fireEvent(moduleBase -> moduleBase.onClientPreInitialization(event));
//                    }
//                }
//        );
//        this.routes.put(
//                FMLInitializationEvent.class,
//                (IFMLStateEventRoute<FMLInitializationEvent>) (event) -> {
//                    this.fireEvent(moduleBase -> moduleBase.onInitialization(event));
//
//                    if (event.getSide() == Side.CLIENT) {
//                        this.fireEvent(moduleBase -> moduleBase.onClientInitialization(event));
//                    }
//                }
//        );
//        this.routes.put(
//                FMLPostInitializationEvent.class,
//                (IFMLStateEventRoute<FMLPostInitializationEvent>) (event) -> {
//                    this.fireEvent(moduleBase -> moduleBase.onPostInitialization(event));
//
//                    if (event.getSide() == Side.CLIENT) {
//                        this.fireEvent(moduleBase -> moduleBase.onClientPostInitialization(event));
//                    }
//                }
//        );
//        this.routes.put(
//                FMLLoadCompleteEvent.class,
//                (IFMLStateEventRoute<FMLLoadCompleteEvent>) (event) ->
//                        this.fireEvent(moduleBase -> moduleBase.onLoadComplete(event))
//        );
//        this.routes.put(
//                FMLServerAboutToStartEvent.class,
//                (IFMLStateEventRoute<FMLServerAboutToStartEvent>) (event) ->
//                        this.fireEvent(moduleBase -> moduleBase.onServerAboutToStart(event))
//        );
//        this.routes.put(
//                FMLServerStartingEvent.class,
//                (IFMLStateEventRoute<FMLServerStartingEvent>) (event) ->
//                        this.fireEvent(moduleBase -> moduleBase.onServerStarting(event))
//        );
//        this.routes.put(
//                FMLServerStartedEvent.class,
//                (IFMLStateEventRoute<FMLServerStartedEvent>) (event) ->
//                        this.fireEvent(moduleBase -> moduleBase.onServerStarted(event))
//        );
//        this.routes.put(
//                FMLServerStoppingEvent.class,
//                (IFMLStateEventRoute<FMLServerStoppingEvent>) (event) ->
//                        this.fireEvent(moduleBase -> moduleBase.onServerStopping(event))
//        );
//        this.routes.put(
//                FMLServerStoppedEvent.class,
//                (IFMLStateEventRoute<FMLServerStoppedEvent>) (event) ->
//                        this.fireEvent(moduleBase -> moduleBase.onServerStopped(event))
//        );
//    }
//
//    /* package */ <E extends FMLStateEvent> void routeFMLStateEvent(E event) {
//
//        //noinspection unchecked
//        IFMLStateEventRoute<E> route = this.routes.get(event.getClass());
//
//        if (route == null) {
//            throw new IllegalArgumentException("No route found for event: " + event.getClass());
//        }
//
//        route.routeEvent(event);
//    }
//
//    // --------------------------------------------------------------------------
//    // - Registration Events
//    // --------------------------------------------------------------------------
//
//    @SubscribeEvent
//    public void onRegisterBlockEvent(RegistryEvent.Register<Block> event) {
//        this.fireEvent(module -> module.onRegisterBlock(event));
//        this.onRegisterTileEntitiesEvent();
//    }
//
//    @SubscribeEvent
//    public void onRegisterItemEvent(RegistryEvent.Register<Item> event) {
//        this.fireEvent(module -> module.onRegisterItem(event));
//
//    }
//
//    @SubscribeEvent
//    public void onRegisterPotionEvent(RegistryEvent.Register<Potion> event) {
//        this.fireEvent(module -> module.onRegisterPotion(event));
//
//    }
//
//    @SubscribeEvent
//    public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
//        this.fireEvent(module -> module.onRegisterBiome(event));
//
//    }
//
//    @SubscribeEvent
//    public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event) {
//        this.fireEvent(module -> module.onRegisterSound(event));
//
//    }
//
//    @SubscribeEvent
//    public void onRegisterPotionTypeEvent(RegistryEvent.Register<PotionType> event) {
//        this.fireEvent(module -> module.onRegisterPotionType(event));
//
//    }
//
//    @SubscribeEvent
//    public void onRegisterEnchantmentEvent(RegistryEvent.Register<Enchantment> event) {
//        this.fireEvent(module -> module.onRegisterEnchantment(event));
//
//    }
//
//    @SubscribeEvent
//    public void onRegisterVillagerProfessionEvent(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
//        this.fireEvent(module -> module.onRegisterVillagerProfession(event));
//
//    }
//
//    @SubscribeEvent
//    public void onRegisterEntityEvent(RegistryEvent.Register<EntityEntry> event) {
//        this.fireEvent(module -> module.onRegisterEntity(event));
//
//    }
//
//    @SubscribeEvent
//    public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {
//
//        this.fireEvent(module -> module.onRegisterRecipes(event));
//    }
//
//    private void onRegisterTileEntitiesEvent() {
//        this.fireEvent(ModuleBase::onRegisterTileEntities);
//
//    }
//
//    // --------------------------------------------------------------------------
//    // - Client
//    // --------------------------------------------------------------------------
//
//    @SubscribeEvent
//    @SideOnly(Side.CLIENT)
//    public void onClientRegisterModelsEvent(ModelRegistryEvent event) {
//        this.fireEvent(module -> module.onClientRegisterModels(event));
//
//    }
//
//    // --------------------------------------------------------------------------
//    // - Internal
//    // --------------------------------------------------------------------------
//
//    private void fireEvent(Consumer<ModuleBase> moduleConsumer) {
//        for (ModuleBase module : this.moduleList) {
//            moduleConsumer.accept(module);
//        }
//    }
//
//}
