package su.terrafirmagreg.framework.manager.packet;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.packet.api.IPacketEntry;
import su.terrafirmagreg.framework.manager.packet.api.IPacketManager;
import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import net.minecraftforge.common.MinecraftForge;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import lombok.Getter;

@Getter
public class PacketManager implements IPacketManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(PacketManager.class);

  private final IModuleEntry module;
  private final Multimap<Class<?>, IPacketEntry> mapEntry;

  private final IPacketRegistrar registrar;


  private PacketManager(IModuleEntry module) {

    this.module = module;
    this.mapEntry = LinkedHashMultimap.create();

    this.registrar = new PacketRegistrar(this);

    MinecraftForge.EVENT_BUS.register(this);
  }

  public static synchronized IPacketManager of(IModuleEntry module) {

    return MANAGER_MAP.computeIfAbsent(module, PacketManager::new);
  }


  @Override
  public LoggingHelper getLogger() {
    return LOGGER;
  }
}
