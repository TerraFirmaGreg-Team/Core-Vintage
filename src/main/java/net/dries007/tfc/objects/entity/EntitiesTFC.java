package net.dries007.tfc.objects.entity;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.DataSerializerEntry;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.objects.entity.projectile.EntityThrownJavelin;
import net.dries007.tfc.util.Helpers;

import javax.annotation.Nonnull;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFC;

@Mod.EventBusSubscriber(modid = TFC)
public class EntitiesTFC {

  @GameRegistry.ObjectHolder("tfc:long")
  public static final DataSerializerEntry LONG_DATA_SERIALIZER_ENTRY = Helpers.getNull();

  private static final DataSerializer<Long> LONG_DATA_SERIALIZER = new DataSerializer<Long>() {
    public void write(PacketBuffer buf, @Nonnull Long value) {
      buf.writeLong(value);
    }

    public Long read(PacketBuffer buf) {
      return buf.readLong();
    }

    public DataParameter<Long> createKey(int id) {
      return new DataParameter<>(id, this);
    }

    @Nonnull
    public Long copyValue(@Nonnull Long value) {
      return value;
    }
  };

  private static int id = 1; // don't use id 0, it's easier to debug if something goes wrong

  @SuppressWarnings("unchecked")
  public static DataSerializer<Long> getLongDataSerializer() {
    return (DataSerializer<Long>) LONG_DATA_SERIALIZER_ENTRY.getSerializer();
  }

  @SubscribeEvent
  public static void registerDataSerializers(RegistryEvent.Register<DataSerializerEntry> event) {
    event.getRegistry().register(new DataSerializerEntry(LONG_DATA_SERIALIZER).setRegistryName("long"));
  }

  public static void preInit() {
    register("falling_block", EntityFallingBlockTFC.class);
    register("thrown_javelin", EntityThrownJavelin.class);
    register("boat", EntityBoatTFC.class);
  }

  private static void register(String name, Class<? extends Entity> cls) {
    EntityRegistry.registerModEntity(new ResourceLocation(TFC, name), cls, name, id++, TerraFirmaCraft.getInstance(), 160, 20, true);
  }

  private static void registerLiving(String name, Class<? extends Entity> cls, int eggPrimaryColor, int eggSecondaryColor) {
    //Register entity and create a spawn egg for creative
    EntityRegistry.registerModEntity(new ResourceLocation(TFC, name), cls, name, id++, TerraFirmaCraft.getInstance(), 80, 3, true, eggPrimaryColor, eggSecondaryColor);
  }
}
