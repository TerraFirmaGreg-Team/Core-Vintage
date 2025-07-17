package su.terrafirmagreg.framework.manager.registry;

import su.terrafirmagreg.api.util.DataFixUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryManager;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryService;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
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

    this.map.getEntry(registry.getRegistrySuperType()).forEach(wrapper -> {
      var entry = wrapper.getEntry();
      var name = wrapper.getName();
      var identifier = ModUtils.resource(module.getIdentifier(), name);

      if (entry instanceof IRegistryEntry registryEntry) {

        registryEntry.preRegister();
        if (!identifier.equals(registryEntry.getRegistryName())) {
          registryEntry.setRegistryName(identifier);
        }
        registry.register((T) registryEntry);
        registryEntry.postRegister();
        RegistryManager.LOGGER.info("Service {}: {}", registryEntry.getRegistryType().getSimpleName(), registryEntry.getRegistryName());
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

    mappings.stream()
      .filter(mapping -> DataFixUtils.MOD_ID_SET.contains(mapping.key.getNamespace()))
      .forEach(mapping -> {
        String mappingKey = mapping.key.toString();
        String mappingPath = mapping.key.getPath();

        this.map.getEntry(registry.getRegistrySuperType()).forEach(wrapper -> {
          var entry = wrapper.getEntry();
          var identifier = wrapper.getName();
          if (mappingPath.endsWith(wrapper.getName())) {

            if (entry == null) {
              mapping.warn();
              RegistryManager.LOGGER.error("Service: Failed to map {}", identifier);
              return;
            }

            mapping.remap((T) entry);
            RegistryManager.LOGGER.info("Mapped {} to {}", mappingKey, entry.getRegistryName());
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

    this.map.getEntry(Block.class).forEach(wrapper -> ModelUtils.color(event.getBlockColors(), (Block) wrapper.getEntry()));
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRegisterItemColor(ColorHandlerEvent.Item event) {

    this.map.getEntry(Block.class).forEach(wrapper -> ModelUtils.color(event.getItemColors(), (Block) wrapper.getEntry()));
    this.map.getEntry(Item.class).forEach(wrapper -> ModelUtils.color(event.getItemColors(), (Item) wrapper.getEntry()));
  }


}

