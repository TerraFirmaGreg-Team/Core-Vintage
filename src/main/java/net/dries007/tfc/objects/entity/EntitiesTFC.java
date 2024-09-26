package net.dries007.tfc.objects.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.objects.entity.animal.EntityAlpacaTFC;
import net.dries007.tfc.objects.entity.animal.EntityBlackBearTFC;
import net.dries007.tfc.objects.entity.animal.EntityBoarTFC;
import net.dries007.tfc.objects.entity.animal.EntityCamelTFC;
import net.dries007.tfc.objects.entity.animal.EntityChickenTFC;
import net.dries007.tfc.objects.entity.animal.EntityCougarTFC;
import net.dries007.tfc.objects.entity.animal.EntityCowTFC;
import net.dries007.tfc.objects.entity.animal.EntityCoyoteTFC;
import net.dries007.tfc.objects.entity.animal.EntityDeerTFC;
import net.dries007.tfc.objects.entity.animal.EntityDireWolfTFC;
import net.dries007.tfc.objects.entity.animal.EntityDonkeyTFC;
import net.dries007.tfc.objects.entity.animal.EntityDuckTFC;
import net.dries007.tfc.objects.entity.animal.EntityGazelleTFC;
import net.dries007.tfc.objects.entity.animal.EntityGoatTFC;
import net.dries007.tfc.objects.entity.animal.EntityGrizzlyBearTFC;
import net.dries007.tfc.objects.entity.animal.EntityGrouseTFC;
import net.dries007.tfc.objects.entity.animal.EntityHareTFC;
import net.dries007.tfc.objects.entity.animal.EntityHorseTFC;
import net.dries007.tfc.objects.entity.animal.EntityHyenaTFC;
import net.dries007.tfc.objects.entity.animal.EntityJackalTFC;
import net.dries007.tfc.objects.entity.animal.EntityLionTFC;
import net.dries007.tfc.objects.entity.animal.EntityLlamaTFC;
import net.dries007.tfc.objects.entity.animal.EntityMongooseTFC;
import net.dries007.tfc.objects.entity.animal.EntityMuleTFC;
import net.dries007.tfc.objects.entity.animal.EntityMuskOxTFC;
import net.dries007.tfc.objects.entity.animal.EntityOcelotTFC;
import net.dries007.tfc.objects.entity.animal.EntityPantherTFC;
import net.dries007.tfc.objects.entity.animal.EntityParrotTFC;
import net.dries007.tfc.objects.entity.animal.EntityPheasantTFC;
import net.dries007.tfc.objects.entity.animal.EntityPigTFC;
import net.dries007.tfc.objects.entity.animal.EntityPolarBearTFC;
import net.dries007.tfc.objects.entity.animal.EntityQuailTFC;
import net.dries007.tfc.objects.entity.animal.EntityRabbitTFC;
import net.dries007.tfc.objects.entity.animal.EntitySaberToothTFC;
import net.dries007.tfc.objects.entity.animal.EntitySheepTFC;
import net.dries007.tfc.objects.entity.animal.EntityTurkeyTFC;
import net.dries007.tfc.objects.entity.animal.EntityWildebeestTFC;
import net.dries007.tfc.objects.entity.animal.EntityWolfTFC;
import net.dries007.tfc.objects.entity.animal.EntityYakTFC;
import net.dries007.tfc.objects.entity.animal.EntityZebuTFC;
import net.dries007.tfc.objects.entity.projectile.EntityThrownJavelin;

import static su.terrafirmagreg.data.Constants.MODID_TFC;

public class EntitiesTFC {

  private static int id = 1; // don't use id 0, it's easier to debug if something goes wrong

  public static void preInit() {
    register("thrown_javelin", EntityThrownJavelin.class);
    registerLiving("sheeptfc", EntitySheepTFC.class, 0xFFFFFF, 0xFF6347);
    registerLiving("cowtfc", EntityCowTFC.class, 0xA52A2A, 0xFFFFFF);
    registerLiving("grizzlybeartfc", EntityGrizzlyBearTFC.class, 0xB22222, 0xDEB887);
    registerLiving("chickentfc", EntityChickenTFC.class, 0x557755, 0xFFF91F);
    registerLiving("pheasanttfc", EntityPheasantTFC.class, 0x5577FF, 0xFFFA90);
    registerLiving("deertfc", EntityDeerTFC.class, 0x55FF55, 0x5FFAAF);
    registerLiving("pigtfc", EntityPigTFC.class, 0xAA7722, 0xFFEBCD);
    registerLiving("wolftfc", EntityWolfTFC.class, 0xB0ACAC, 0x796555);
    registerLiving("rabbittfc", EntityRabbitTFC.class, 0x885040, 0x462612);
    registerLiving("horsetfc", EntityHorseTFC.class, 0xA5886B, 0xABA400);
    registerLiving("donkeytfc", EntityDonkeyTFC.class, 0x493C32, 0x756659);
    registerLiving("muletfc", EntityMuleTFC.class, 0x180200, 0x482D1A);
    registerLiving("polarbeartfc", EntityPolarBearTFC.class, 0xF1FFF1, 0xA0A0A0);
    registerLiving("parrottfc", EntityParrotTFC.class, 0x885040, 0xB0ACAC);
    registerLiving("llamatfc", EntityLlamaTFC.class, 0xA52A2A, 0xAA7722);
    registerLiving("ocelottfc", EntityOcelotTFC.class, 0x3527FA, 0x7F23A0);
    registerLiving("panthertfc", EntityPantherTFC.class, 0x000066, 0x000000);
    registerLiving("ducktfc", EntityDuckTFC.class, 0xFFF91F, 0x462612);
    registerLiving("alpacatfc", EntityAlpacaTFC.class, 0x00CC66, 0x006633);
    registerLiving("goattfc", EntityGoatTFC.class, 0xA0A0A0, 0x404040);
    registerLiving("sabertoothtfc", EntitySaberToothTFC.class, 0xFF8000, 0xFFD700);
    registerLiving("cameltfc", EntityCamelTFC.class, 0xA5886B, 0x006633);
    registerLiving("liontfc", EntityLionTFC.class, 0xDAA520, 0xA0522D);
    registerLiving("hyenatfc", EntityHyenaTFC.class, 0x666600, 0x331900);
    registerLiving("direwolftfc", EntityDireWolfTFC.class, 0x343434, 0x978f7e);
    registerLiving("haretfc", EntityHareTFC.class, 0x866724, 0xDADADA);
    registerLiving("boartfc", EntityBoarTFC.class, 0x463c09, 0xe39ad8);
    registerLiving("zebutfc", EntityZebuTFC.class, 0x2c2507, 0xbcb38e);
    registerLiving("gazelletfc", EntityGazelleTFC.class, 0xa9a76f, 0xc0ab55);
    registerLiving("wildebeesttfc", EntityWildebeestTFC.class, 0x696142, 0x9c8115);
    registerLiving("quailtfc", EntityQuailTFC.class, 0x237ddc, 0xe3e36d);
    registerLiving("grousetfc", EntityGrouseTFC.class, 0xf7a100, 0x71ffd0);
    registerLiving("mongoosetfc", EntityMongooseTFC.class, 0xf9f50f, 0x90ec7f);
    registerLiving("turkeytfc", EntityTurkeyTFC.class, 0xad1d1d, 0xeaa659);
    registerLiving("jackaltfc", EntityJackalTFC.class, 0xb8762b, 0xffffff);
    registerLiving("muskoxtfc", EntityMuskOxTFC.class, 0x620d55, 0xcdaf4f);
    registerLiving("yaktfc", EntityYakTFC.class, 0x837669, 0x3e3d7cc);
    registerLiving("blackbeartfc", EntityBlackBearTFC.class, 0x000000, 0xa18f6c);
    registerLiving("cougartfc", EntityCougarTFC.class, 0x817a00, 0xdcd889);
    registerLiving("coyotetfc", EntityCoyoteTFC.class, 0xb7bc88, 0xdac213);
  }

  private static void register(String name, Class<? extends Entity> cls) {
    EntityRegistry.registerModEntity(new ResourceLocation(MODID_TFC, name), cls, name, id++, TerraFirmaCraft.getInstance(), 160, 20, true);
  }

  private static void registerLiving(String name, Class<? extends Entity> cls, int eggPrimaryColor, int eggSecondaryColor) {
    //Register entity and create a spawn egg for creative
    EntityRegistry.registerModEntity(new ResourceLocation(MODID_TFC, name), cls, name, id++, TerraFirmaCraft.getInstance(), 80, 3, true,
                                     eggPrimaryColor, eggSecondaryColor);
  }
}
