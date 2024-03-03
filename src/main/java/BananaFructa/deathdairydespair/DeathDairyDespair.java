package BananaFructa.deathdairydespair;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import su.terrafirmagreg.Tags;

import java.lang.reflect.Method;

import static su.terrafirmagreg.api.lib.Constants.MODID_DDD;

@Mod(modid = MODID_DDD, name = DeathDairyDespair.NAME, version = Tags.VERSION)
public class DeathDairyDespair {
	public static final String NAME = "Death, Dairy and Despair";

	private Method writePlayerData;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Config.load(event.getModConfigurationDirectory());
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);

		try {

			writePlayerData = PlayerList.class.getDeclaredMethod("writePlayerData", EntityPlayerMP.class);
			writePlayerData.setAccessible(true);

		} catch (Exception err) {
			err.printStackTrace();
		}

	}

	public void invokeWritePlayerData(PlayerList list, EntityPlayerMP player) {
		if (list == null) return;
		try {
			writePlayerData.invoke(list, player);
		} catch (Exception err) {
			err.printStackTrace();
		}
	}


	// Saves player data upon death
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof EntityPlayerMP) {
			invokeWritePlayerData(event.getEntity().world.getMinecraftServer().getPlayerList(), (EntityPlayerMP) event.getEntity());
		}
	}

}

