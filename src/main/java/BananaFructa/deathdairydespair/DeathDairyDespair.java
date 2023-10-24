package BananaFructa.deathdairydespair;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Method;

@Mod(modid = DeathDairyDespair.MODID, name = DeathDairyDespair.NAME, version = DeathDairyDespair.VERSION)
public class DeathDairyDespair
{
    public static final String MODID = "deathdairydespair";
    public static final String NAME = "Death, Dairy and Despair";
    public static final String VERSION = "0.1";

    private Method writePlayerData;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.load(event.getModConfigurationDirectory());
        TFCCommonEventHandlerWrapper.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        try {

            writePlayerData = PlayerList.class.getDeclaredMethod("func_72391_b",EntityPlayerMP.class);
            writePlayerData.setAccessible(true);

        } catch (Exception err) {
            err.printStackTrace();
        }

    }

    public void invokeWritePlayerData (PlayerList list,EntityPlayerMP player) {
        if (list == null) return;
        try {
            writePlayerData.invoke(list,player);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }


    // Saves player data upon death
    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityPlayerMP) {
            System.out.println("A");
            invokeWritePlayerData(event.getEntity().world.getMinecraftServer().getPlayerList(),(EntityPlayerMP)event.getEntity());
        }
    }

}

