package su.terrafirmagreg.framework.registry;

import su.terrafirmagreg.api.base.object.group.spi.BaseItemGroup;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.api.library.IdSupplier;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.framework.registry.spi.RegistryWrapper;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.DataSerializerEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;
import java.util.function.Supplier;

@Getter
public class RegistryManager implements IRegistryManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(RegistryManager.class.getSimpleName());

  private static final Map<ResourceLocation, RegistryWrapper> REGISTRY_WRAPPER_MAP = new Object2ObjectOpenHashMap<>();
  private static final Map<String, IdSupplier> ENTITY_ID_SUPPLIER_MAP = new Object2ObjectOpenHashMap<>();


  private final IModule module;
  private final RegistryWrapper wrapper;
  private final IdSupplier idSupplier;

  private BaseItemGroup group;

  private RegistryManager(IModule module) {

    var moduleIdentifier = module.getIdentifier();

    this.module = module;
    this.idSupplier = ENTITY_ID_SUPPLIER_MAP.computeIfAbsent(moduleIdentifier.getNamespace(), s -> new IdSupplier());
    this.wrapper = REGISTRY_WRAPPER_MAP.computeIfAbsent(moduleIdentifier, s -> new RegistryWrapper());

  }

  public static synchronized IRegistryManager of(IModule module) {
    return new RegistryManager(module);
  }

  @Override
  public IRegistryManager group(Supplier<BaseItemGroup> group) {
    this.group = group.get();
    return this;
  }

  // region Event

  @Override
  public void onRegisterBlock(RegistryEvent.Register<Block> event) {

    this.getWrapper().getBlocks().register(event);
    this.getWrapper().getBlocks().register(TileUtils::registerTileEntity);

  }

  @Override
  public void onRegisterItem(RegistryEvent.Register<Item> event) {

    this.getWrapper().getItems().register(event);
//    this.getWrapper().getBlocks().register(OreDictUtils::register);
    this.getWrapper().getItems().register(OreDictUtils::register);
  }

  @Override
  public void onRegisterPotion(RegistryEvent.Register<Potion> event) {

    this.getWrapper().getPotions().register(event);
  }

  @Override
  public void onRegisterPotionType(RegistryEvent.Register<PotionType> event) {

    this.getWrapper().getPotionTypes().register(event);
  }

  @Override
  public void onRegisterBiome(RegistryEvent.Register<Biome> event) {

    this.getWrapper().getBiomes().register(event);

//    this.getWrapper().biomes.forEachValues(biome -> {
//      if (biome instanceof IBiomeSettings provider) {
//        if (provider.getTypes().length > 0) {
//          BiomeDictionary.addTypes(biome, provider.getTypes());
//        }
//      }
//    });

  }

  @Override
  public void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {

    this.getWrapper().getSounds().register(event);
  }

  @Override
  public void onRegisterEntity(RegistryEvent.Register<EntityEntry> event) {

    this.getWrapper().getEntities().register(event);
  }

  @Override
  public void onRegisterEnchantment(RegistryEvent.Register<Enchantment> event) {

    this.getWrapper().getEnchantments().register(event);
  }

  @Override
  public void onRegisterVillagerProfession(RegistryEvent.Register<VillagerProfession> event) {

    this.getWrapper().getProfessions().register(event);
  }

  @Override
  public void onRegisterRecipe(RegistryEvent.Register<IRecipe> event) {

    this.getWrapper().getRecipes().register(event);
  }

  @Override
  public void onRegisterDataSerializerEntry(RegistryEvent.Register<DataSerializerEntry> event) {

    this.getWrapper().getDataSerializer().register(event);
  }

  @Override
  public void onRegisterLootTableLoad(LootTableLoadEvent event) {

//    this.getWrapper().lootTable.get(event.getName()).forEach(builder -> {
//      event.getTable()
//           .getPool(builder.getPool())
//           .addEntry(builder.build());
//    });
  }

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  @Override
  @SideOnly(Side.CLIENT)
  public void onRegisterModels(ModelRegistryEvent event) {

    this.getWrapper().getBlocks().register(block -> {
      ModelUtils.stateMapper(block);
      ModelUtils.model(block);
      ModelUtils.tesr(block);

    });
    this.getWrapper().getItems().register(item -> {
      ModelUtils.model(item);
      ModelUtils.customMeshDefinition(item);
    });
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void onRegisterBlockColor(ColorHandlerEvent.Block event) {

    this.getWrapper().getBlocks().register(block -> {
      ModelUtils.colorHandler(event.getBlockColors(), block);
    });
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void onRegisterItemColor(ColorHandlerEvent.Item event) {

    this.getWrapper().getBlocks().register(block -> {
      ModelUtils.colorHandler(event.getItemColors(), block);
    });

    this.getWrapper().getItems().register(item -> {
      ModelUtils.colorHandler(event.getItemColors(), item);
    });
  }

  // endregion

  public static class NoOp extends RegistryManager {

    public NoOp(IModule module) {
      super(module);
    }

    @Override
    public void onRegisterBlock(RegistryEvent.Register<Block> event) {}

    @Override
    public void onRegisterItem(RegistryEvent.Register<Item> event) {}

    @Override
    public void onRegisterPotion(RegistryEvent.Register<Potion> event) {}

    @Override
    public void onRegisterPotionType(RegistryEvent.Register<PotionType> event) {}

    @Override
    public void onRegisterBiome(RegistryEvent.Register<Biome> event) {}

    @Override
    public void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {}

    @Override
    public void onRegisterEntity(RegistryEvent.Register<EntityEntry> event) {}

    @Override
    public void onRegisterEnchantment(RegistryEvent.Register<Enchantment> event) {}

    @Override
    public void onRegisterVillagerProfession(RegistryEvent.Register<VillagerProfession> event) {}

    @Override
    public void onRegisterRecipe(RegistryEvent.Register<IRecipe> event) {}

    @Override
    public void onRegisterDataSerializerEntry(RegistryEvent.Register<DataSerializerEntry> event) {}

    @Override
    public void onRegisterLootTableLoad(LootTableLoadEvent event) {}

    @Override
    public void onRegisterModels(ModelRegistryEvent event) {}

    @Override
    public void onRegisterBlockColor(ColorHandlerEvent.Block event) {}

    @Override
    public void onRegisterItemColor(ColorHandlerEvent.Item event) {}
  }


}
