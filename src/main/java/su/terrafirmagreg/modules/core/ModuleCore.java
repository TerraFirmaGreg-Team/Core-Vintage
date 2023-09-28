package su.terrafirmagreg.modules.core;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.network.IPacketService;
import com.codetaylor.mc.athenaeum.registry.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.modules.core.api.util.BaseCreativeTab;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleCore extends ModuleBase {

    public static final BaseCreativeTab MISC_TAB = new BaseCreativeTab("misc", "tfc:wand");
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleCore.class.getSimpleName());

    public static IPacketService PACKET_SERVICE;

    public ModuleCore() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, MISC_TAB));
        this.enableAutoRegistry();

        PACKET_SERVICE = this.enableNetwork();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onRegister(Registry registry) {

        BlocksCore.onRegister(registry);
        ItemsCore.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlocksCore.onClientRegister(registry);
        ItemsCore.onClientRegister(registry);
    }
}
