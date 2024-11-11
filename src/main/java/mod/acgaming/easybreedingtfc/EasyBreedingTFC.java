package mod.acgaming.easybreedingtfc;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import mod.acgaming.easybreedingtfc.util.EBEventHandler;

@Mod(modid = EasyBreedingTFC.MOD_ID, name = EasyBreedingTFC.NAME, version = EasyBreedingTFC.VERSION, acceptedMinecraftVersions = "[1.12.2]", dependencies = "required-after:tfc")
public class EasyBreedingTFC
{
    public static final String MOD_ID = "easybreedingtfc";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "EasyBreedingTFC";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new EBEventHandler());
    }
}