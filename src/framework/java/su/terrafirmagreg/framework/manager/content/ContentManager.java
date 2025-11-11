package su.terrafirmagreg.framework.manager.content;

import su.terrafirmagreg.api.data.enums.Mods;
import su.terrafirmagreg.api.util.DataFixUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.framework.FrameworkLogger;
import su.terrafirmagreg.framework.manager.content.api.IContentEntry;
import su.terrafirmagreg.framework.manager.content.api.IContentManager;
import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import lombok.Getter;

@Getter
@SuppressWarnings("rawtypes")
public class ContentManager implements IContentManager {

  public static final FrameworkLogger LOGGER = FrameworkLogger.of(ContentManager.class);

  private final IModuleEntry module;
  private final Multimap<Class<?>, IContentEntry<?, ?>> mapEntry;

  private final IContentRegistrar registrar;

  private ContentManager(IModuleEntry module) {

    this.module = module;
    this.mapEntry = LinkedHashMultimap.create();

    this.registrar = new ContentRegistrar(this);

    MinecraftForge.EVENT_BUS.register(this);
  }

  public static IContentManager of(IModuleEntry module) {

    return MANAGER_MAP.computeIfAbsent(module, ContentManager::new);
  }

  @Override
  public FrameworkLogger getLogger() {
    return LOGGER;
  }


  @SuppressWarnings({"unchecked", "rawtypes"})
  @SubscribeEvent
  public <T extends IForgeRegistryEntry<T>> void onRegisterEvent(RegistryEvent.Register event) {

    IForgeRegistry<T> registry = Preconditions.checkNotNull(
      event.getRegistry(), "Registry not found: %s", event.getName()
    );
    final Class<T> registryType = registry.getRegistrySuperType();
    final var map = this.getMapEntry();
    if (!map.containsKey(registryType)) {
      return;
    }
    map.get(registryType)
      .forEach(entry -> {

        entry.apply();
        registry.register((T) entry.setRegistryName(entry.getIdentifier()));
        entry.postRegister();
        ContentManager.LOGGER.debug("Registry {}: {}",
          entry.getRegistryType().getSimpleName(), entry.getRegistryName()
        );
      });

  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @SubscribeEvent
  public <T extends IForgeRegistryEntry<T>> void onMissingMappingsEvent(RegistryEvent.MissingMappings event) {
    ImmutableList<Mapping<T>> mappings = event.getAllMappings();
    IForgeRegistry<T> registry = Preconditions.checkNotNull(
      event.getRegistry(), "Registry not found: %s", event.getName()
    );

    Class<T> registryType = registry.getRegistrySuperType();

    mappings.stream()
      .filter(mapping -> Mods.contains(mapping.key.getNamespace()))
      .filter(mapping -> this.getMapEntry().containsKey(registryType))
      .forEach(mapping -> {
        String mappingPath = mapping.key.getPath();

        this.getMapEntry().get(registryType).stream()
          .filter(entry -> {
            ResourceLocation registryName = entry.getRegistryName();
            if (registryName == null) {
              return false;
            }
            // Используем обновленный variantPredicate для сравнения путей
            return DataFixUtils.variantPredicate.test(mappingPath, registryName.getPath());
          }).forEach(entry -> {
            // Нашли совпадение, выполняем ремаппинг
            ResourceLocation registryName = entry.getRegistryName();
            T registryObject = mapping.registry.getValue(registryName);
            var registrySuperType = mapping.registry.getRegistrySuperType();
            if (registryObject == null) {
              return;
            }

            if (registrySuperType == registryObject.getRegistryType()) {
              mapping.remap(registryObject);
              ContentManager.LOGGER.info("Remapped {} to {} (type: {})", mapping.key, registryName, registrySuperType.getSimpleName());
            } else {
              mapping.fail();
            }
          });
      });
  }

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRegisterBlockColor(ColorHandlerEvent.Block event) {

    this.getMapEntry().get(Block.class).forEach(entry -> ModelUtils.color(event.getBlockColors(), (Block) entry.asEntry()));
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRegisterItemColor(ColorHandlerEvent.Item event) {

    this.getMapEntry().get(Block.class).forEach(entry -> ModelUtils.color(event.getItemColors(), (Block) entry.asEntry()));
    this.getMapEntry().get(Item.class).forEach(entry -> ModelUtils.color(event.getItemColors(), (Item) entry.asEntry()));
  }

}
