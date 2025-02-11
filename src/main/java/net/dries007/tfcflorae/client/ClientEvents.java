package net.dries007.tfcflorae.client;


import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

import net.dries007.tfc.client.render.RenderBoatTFCF;
import net.dries007.tfc.objects.blocks.entity.EntityBoatTFCF;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCF;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TFCF)
public class ClientEvents {

  public static void preInit() {
    RenderingRegistry.registerEntityRenderingHandler(EntityBoatTFCF.class, RenderBoatTFCF::new);
  }
}
