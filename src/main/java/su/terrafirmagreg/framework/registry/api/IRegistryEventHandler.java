package su.terrafirmagreg.framework.registry.api;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.DataSerializerEntry;

public interface IRegistryEventHandler {

  void onRegisterBlock(RegistryEvent.Register<Block> event);


  void onRegisterItem(RegistryEvent.Register<Item> event);


  void onRegisterPotion(RegistryEvent.Register<Potion> event);


  void onRegisterPotionType(RegistryEvent.Register<PotionType> event);


  void onRegisterBiome(RegistryEvent.Register<Biome> event);


  void onRegisterSound(RegistryEvent.Register<SoundEvent> event);


  void onRegisterEntity(RegistryEvent.Register<EntityEntry> event);


  void onRegisterEnchantment(RegistryEvent.Register<Enchantment> event);


  void onRegisterVillagerProfession(RegistryEvent.Register<VillagerProfession> event);


  void onRegisterRecipe(RegistryEvent.Register<IRecipe> event);


  void onRegisterDataSerializerEntry(RegistryEvent.Register<DataSerializerEntry> event);


  void onRegisterLootTableLoad(LootTableLoadEvent event);


  void onRegisterModels(ModelRegistryEvent event);


  void onRegisterBlockColor(ColorHandlerEvent.Block event);


  void onRegisterItemColor(ColorHandlerEvent.Item event);

}
