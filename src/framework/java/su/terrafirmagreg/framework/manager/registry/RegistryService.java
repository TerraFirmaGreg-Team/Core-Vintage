package su.terrafirmagreg.framework.manager.registry;

import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryManager;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryService;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import com.google.common.base.Preconditions;

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

    this.map.get(registry).forEach(entry -> {

      entry.preRegister();
      registry.register((T) entry);
      entry.postRegister();
      RegistryManager.LOGGER.debug("Registry {}: {}", entry.getRegistryType().getSimpleName(), entry.getRegistryName());
    });
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @SubscribeEvent
  public <T extends IForgeRegistryEntry<T>> void onMissingMappingsEvent(RegistryEvent.MissingMappings event) {
    IForgeRegistry<T> registry = Preconditions.checkNotNull(
      event.getRegistry(), "Registry not found: %s", event.getName()
    );


  }

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRegisterBlockColor(ColorHandlerEvent.Block event) {

    this.map.register(Block.class, block -> ModelUtils.color(event.getBlockColors(), block));
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRegisterItemColor(ColorHandlerEvent.Item event) {

    this.map.register(Block.class, block -> ModelUtils.color(event.getItemColors(), block));
    this.map.register(Item.class, item -> ModelUtils.color(event.getItemColors(), item));
  }


}

