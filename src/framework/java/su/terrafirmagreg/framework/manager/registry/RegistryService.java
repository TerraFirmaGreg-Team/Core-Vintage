package su.terrafirmagreg.framework.manager.registry;

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

import com.google.common.base.Preconditions;

import lombok.Getter;

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

    this.map.register(registry);
  }

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRegisterModels(ModelRegistryEvent event) {

    this.map.register(Block.class, ModelUtils::register);
    this.map.register(Item.class, ModelUtils::register);
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRegisterBlockColor(ColorHandlerEvent.Block event) {

    this.map.register(Block.class, block -> ModelUtils.color(event, block));
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRegisterItemColor(ColorHandlerEvent.Item event) {

    this.map.register(Block.class, block -> ModelUtils.color(event, block));

    this.map.register(Item.class, item -> ModelUtils.color(event, item));
  }


}

