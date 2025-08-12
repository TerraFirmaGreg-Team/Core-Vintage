package su.terrafirmagreg.framework.manager.registry;

import su.terrafirmagreg.api.data.enums.Mods;
import su.terrafirmagreg.api.util.DataFixUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryManager;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryService;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import lombok.Getter;

@Getter
public class RegistryService implements IRegistryService {

  private final IModule module;
  private final IRegistryRegistrar registrar;
  private final RegistryMap map;


  public RegistryService(IRegistryManager manager) {

    this.module = manager.getModule();
    this.registrar = manager.getRegistrar();
    this.map = manager.getMap();

  }


  @SuppressWarnings({"unchecked", "rawtypes"})
  @SubscribeEvent
  public <T extends IForgeRegistryEntry<T>> void onRegisterEvent(RegistryEvent.Register event) {

    IForgeRegistry<T> registry = Preconditions.checkNotNull(
      event.getRegistry(), "Registry not found: %s", event.getName()
    );

    this.map.getEntry(registry.getRegistrySuperType()).forEach(entry -> {

      if (entry instanceof IRegistryEntry registryEntry) {
        registryEntry.preRegister();
        registry.register((T) registryEntry);
        registryEntry.postRegister();
        RegistryManager.LOGGER.info("Service {}: {}",
          registryEntry.getRegistryType().getSimpleName(), registryEntry.getRegistryName()
        );
      }
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
      .forEach(mapping -> {
        String mappingKey = mapping.key.toString();
        String mappingPath = mapping.key.getPath();

        this.map.getEntry(registryType).stream()
          .filter(entry -> {
            ResourceLocation registryName = entry.getRegistryName();

            if (registryName == null) {
              mapping.fail();
              return false;
            }

//            var registryNamePath = registryName.getPath();
//
//            if (!mappingPath.endsWith(registryNamePath)) {
//              mapping.warn();
//              return false;
//            }

            // Проверяем полное совпадение ключа или совпадение по variantPredicate
            return DataFixUtils.variantPredicate.test(mappingPath, registryName.getPath());
          })
          .findFirst() // Берем первый подходящий
          .ifPresent(entry -> {

            var resourceLocation = entry.getRegistryName();
            if (resourceLocation == null) {
              mapping.warn();
              return;
            }

            // Получаем объект из registry
            T registryObject = mapping.registry.getValue(resourceLocation);
            if (registryObject == null) {
              mapping.warn();
              RegistryManager.LOGGER.warn("Failed to remap {}: target object not found in registry", resourceLocation);

              return;
            }

            if (mapping.registry.getRegistrySuperType() == registryObject.getRegistryType()) {
              mapping.remap(registryObject);
              RegistryManager.LOGGER.info("Remapped {} to {}", mapping.key, resourceLocation);
              return;
            }

            mapping.warn();
            RegistryManager.LOGGER.warn("Failed to remap {}: type mismatch ({} vs {})", mapping.key, mapping.registry.getRegistrySuperType(), resourceLocation);

          });
        mapping.warn();
      });
  }

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRegisterBlockColor(ColorHandlerEvent.Block event) {

    this.map.getEntry(Block.class).forEach(entry -> ModelUtils.color(event.getBlockColors(), (Block) entry));
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRegisterItemColor(ColorHandlerEvent.Item event) {

    this.map.getEntry(Block.class).forEach(entry -> ModelUtils.color(event.getItemColors(), (Block) entry));
    this.map.getEntry(Item.class).forEach(entry -> ModelUtils.color(event.getItemColors(), (Item) entry));
  }


}

