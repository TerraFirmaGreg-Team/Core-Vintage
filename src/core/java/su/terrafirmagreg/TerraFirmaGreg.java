package su.terrafirmagreg;

import su.terrafirmagreg.framework.Framework;
import su.terrafirmagreg.framework.module.api.IModuleRegistrar;
import su.terrafirmagreg.modules.animal.ModuleAnimal;
import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.device.ModuleDevice;
import su.terrafirmagreg.modules.food.ModuleFood;
import su.terrafirmagreg.modules.metal.ModuleMetal;
import su.terrafirmagreg.modules.rock.ModuleRock;
import su.terrafirmagreg.modules.soil.ModuleSoil;
import su.terrafirmagreg.modules.wood.ModuleWood;
import su.terrafirmagreg.modules.world.ModuleWorld;
import su.terrafirmagreg.proxy.IProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;

import static su.terrafirmagreg.Tags.CLIENT_PROXY;
import static su.terrafirmagreg.Tags.DEPENDENCIES;
import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;
import static su.terrafirmagreg.Tags.MOD_VERSION;
import static su.terrafirmagreg.Tags.SERVER_PROXY;


@Mod(
  modid = MOD_ID,
  name = MOD_NAME,
  version = MOD_VERSION,
  dependencies = DEPENDENCIES
)
public class TerraFirmaGreg extends Framework {

  @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
  public static IProxy PROXY;

  @Mod.Instance(MOD_ID)
  public static TerraFirmaGreg INSTANCE;


  public TerraFirmaGreg() {
    super(MOD_ID, MOD_NAME);
  }

  @Override
  public void onModuleRegistrar(IModuleRegistrar registrar) {

    registrar.addModule(new ModuleCore());
    registrar.addModule(new ModuleMetal());
    registrar.addModule(new ModuleRock());
    registrar.addModule(new ModuleSoil());
    registrar.addModule(new ModuleWood());
    registrar.addModule(new ModuleDevice());
    registrar.addModule(new ModuleAnimal());
    registrar.addModule(new ModuleFood());
    registrar.addModule(new ModuleWorld());
  }

}
