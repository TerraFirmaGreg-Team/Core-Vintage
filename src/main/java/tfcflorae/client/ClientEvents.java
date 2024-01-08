package tfcflorae.client;


import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import tfcflorae.client.render.RenderBoatTFCF;
import tfcflorae.objects.blocks.entity.EntityBoatTFCF;

import static su.terrafirmagreg.Constants.MODID_TFCF;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MODID_TFCF)
public class ClientEvents {
	public static void preInit() {
		RenderingRegistry.registerEntityRenderingHandler(EntityBoatTFCF.class, RenderBoatTFCF::new);
	}
}
