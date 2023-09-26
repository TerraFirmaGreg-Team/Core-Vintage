package net.dries007.tfc.module.animal;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.module.animal.init.BlocksAnimal;
import net.dries007.tfc.module.animal.init.EntitiesAnimal;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;
import static net.dries007.tfc.module.core.ModuleCore.MISC_TAB;

public class ModuleAnimal extends ModuleBase {

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleAnimal.class.getSimpleName());

    public ModuleAnimal() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, MISC_TAB));
        this.enableAutoRegistry();

        //PACKET_SERVICE = this.enableNetwork();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onRegister(Registry registry) {

        BlocksAnimal.onRegister(registry);
        EntitiesAnimal.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlocksAnimal.onClientRegister(registry);
        EntitiesAnimal.onClientRegister();
    }

    @Override
    public void onClientInitializationEvent(FMLInitializationEvent event) {
        super.onClientInitializationEvent(event);

    }
}
