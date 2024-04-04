package su.terrafirmagreg.modules.device.data;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.device.client.render.RenderSlingMetal;
import su.terrafirmagreg.modules.device.client.render.RenderSlingStone;
import su.terrafirmagreg.modules.device.client.render.RenderSlingUnknownMetal;
import su.terrafirmagreg.modules.device.objects.entity.EntitySlingStone;
import su.terrafirmagreg.modules.device.objects.entity.EntitySlingStoneMetal;
import su.terrafirmagreg.modules.device.objects.entity.EntitySlingStoneMetalLight;
import su.terrafirmagreg.modules.device.objects.entity.EntityUnknownProjectile;

public final class EntitiesDevice {

	public static void onRegister(RegistryManager registry) {
		registry.registerEntity("slingstone",
				EntityEntryBuilder.create()
				                  .entity(EntitySlingStone.class)
				                  .tracker(64, 1, true)
		);

		registry.registerEntity("slingstonemetal",
				EntityEntryBuilder.create()
				                  .entity(EntitySlingStoneMetal.class)
				                  .tracker(64, 1, true)
		);

		registry.registerEntity("slingstonemetallight",
				EntityEntryBuilder.create()
				                  .entity(EntitySlingStoneMetalLight.class)
				                  .tracker(64, 1, true)
		);

		registry.registerEntity("unknownprojectile",
				EntityEntryBuilder.create()
				                  .entity(EntityUnknownProjectile.class)
				                  .tracker(64, 1, true)
		);
	}

	@SideOnly(Side.CLIENT)
	public static void onClientRegister(RegistryManager registry) {
		RenderingRegistry.registerEntityRenderingHandler(EntitySlingStone.class, RenderSlingStone::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySlingStoneMetal.class, RenderSlingMetal::new);
		RenderingRegistry.registerEntityRenderingHandler(EntitySlingStoneMetalLight.class, RenderSlingMetal::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityUnknownProjectile.class, RenderSlingUnknownMetal::new);

	}
}
