package net.dries007.tfc.objects.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.objects.entity.projectile.EntityThrownJavelin;

import static su.terrafirmagreg.data.Constants.MODID_TFC;

public class EntitiesTFC {

  private static int id = 1; // don't use id 0, it's easier to debug if something goes wrong

  public static void preInit() {
    register("thrown_javelin", EntityThrownJavelin.class);
  }

  private static void register(String name, Class<? extends Entity> cls) {
    EntityRegistry.registerModEntity(new ResourceLocation(MODID_TFC, name), cls, name, id++, TerraFirmaCraft.getInstance(), 160, 20, true);
  }

}
