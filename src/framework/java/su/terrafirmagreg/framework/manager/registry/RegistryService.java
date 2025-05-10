package su.terrafirmagreg.framework.manager.registry;

import su.terrafirmagreg.api.base.IBaseSettings;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryService;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import com.google.common.base.Preconditions;

import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class RegistryService implements IRegistryService {

  private final IModule module;
  private final RegistryMap map;


  public RegistryService(RegistryManager manager) {

    this.module = manager.getModule();
    this.map = manager.getMap();

  }

  @SuppressWarnings("rawtypes")
  @SubscribeEvent
  public void onRegisterEvent(RegistryEvent.Register event) {

    IForgeRegistry<?> registry = Preconditions.checkNotNull(
      event.getRegistry(), "Registry not found: %s", event.getName()
    );

    this.register(registry);
  }

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRegisterModels(ModelRegistryEvent event) {

    this.register(Block.class, ModelUtils::register);
    this.register(Item.class, ModelUtils::register);
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRegisterBlockColor(ColorHandlerEvent.Block event) {

    this.register(Block.class, block -> ModelUtils.color(event, block));
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRegisterItemColor(ColorHandlerEvent.Item event) {

    this.register(Block.class, block -> ModelUtils.color(event, block));
    this.register(Item.class, item -> ModelUtils.color(event, item));
  }


  @SuppressWarnings("unchecked")
  public <T extends IForgeRegistryEntry<T>> void register(Class<T> registry, final Consumer<T> consumer) {

    this.map.get(registry).forEach(wrapper -> consumer.accept((T) wrapper.getEntry()));
  }


  @SuppressWarnings({"unchecked", "rawtypes"})
  public <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry) {

    this.map.get(registry).forEach(wrapper -> {
      var entry = wrapper.getEntry();
      var identifier = wrapper.getIdentifier();

      if (!identifier.equals(entry.getRegistryName())) {
        entry.setRegistryName(identifier);
      }
      registry.register((T) entry);
      RegistryManager.LOGGER.debug("Registry {}: {}", entry.getRegistryType().getSimpleName(), identifier);
      if (entry instanceof IBaseSettings settings) {

        settings.postRegister();
      }
    });
  }

}

