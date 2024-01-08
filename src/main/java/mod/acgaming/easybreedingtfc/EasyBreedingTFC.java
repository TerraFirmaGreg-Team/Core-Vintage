package mod.acgaming.easybreedingtfc;

import mod.acgaming.easybreedingtfc.util.EBEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import su.terrafirmagreg.Tags;

import static su.terrafirmagreg.Constants.MODID_EASYBREEDINGTFC;

@Mod(modid = MODID_EASYBREEDINGTFC, name = EasyBreedingTFC.NAME, version = Tags.VERSION, dependencies = "required-after:tfc")
public class EasyBreedingTFC {
	public static final String NAME = "EasyBreedingTFC";

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new EBEventHandler());
	}
}
