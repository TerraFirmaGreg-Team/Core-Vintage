package net.dries007.tfc.module.core;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.util.OreDictionaryHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleCorePost
    extends ModuleBase {

  public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleCorePost.class.getSimpleName());

  public ModuleCorePost() {

    super(0, MOD_ID);

    this.setRegistry(new Registry(MOD_ID, null));
    this.enableAutoRegistry();
  }

  @Override
  public void onRegister(Registry registry) {
      OreDictionaryHelper.init();
  }

}
