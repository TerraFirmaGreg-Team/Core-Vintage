package su.terrafirmagreg.api.util;

import su.terrafirmagreg.TerraFirmaGreg;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.blocks.BlocksTFCF;
import net.dries007.tfc.objects.blocks.groundcover.BlockCoral;
import net.dries007.tfc.objects.blocks.groundcover.BlockCoralBlock;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.items.ItemPowder;
import net.dries007.tfc.types.PlantsTFCF;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
@SuppressWarnings("unused")
public final class OreDictUtils {

  public static void register(Item item, Object... parts) {
    register(item, OreDictionary.WILDCARD_VALUE, parts);
  }

  public static void register(Item item, int meta, Object... parts) {
    register(new ItemStack(item, 1, meta), toString(parts));
  }

  public static void register(ItemStack itemStack, String oreName) {
    if (oreName == null) {
      return;
    }

    if (itemStack != null && !itemStack.isEmpty()) {
      OreDictionary.registerOre(oreName, itemStack);
    } else {
      TerraFirmaGreg.LOGGER.error(
        "Failed to register ore dict entry for {}. Another unknown mod is likely responsible.",
        oreName);
    }
  }

  /**
   * Преобразует массив объектов в строку в формате lowerCamelCase, разделяя элементы подчеркиванием.
   *
   * @param parts Массив объектов для преобразования в строку.
   * @return Строка, представляющая объединенные элементы массива в формате lowerCamelCase.
   */
  public static String toString(@NotNull Object... parts) {
    return CaseFormat.UPPER_UNDERSCORE
      .converterTo(CaseFormat.LOWER_CAMEL)
      .convert(Joiner.on('_').skipNulls().join(parts));
  }

  public static void register(Block block, Object... parts) {
    register(new ItemStack(block), toString(parts));
  }

  /**
   * Gets all of the ore dictionary names for an ItemStack.
   *
   * @param stack The ItemStack to look at.
   * @return A set of the ore names.
   */
  public static Set<String> getOreNames(ItemStack stack) {
    final Set<String> names = new HashSet<>();

    for (final int id : OreDictionary.getOreIDs(stack)) {
      names.add(OreDictionary.getOreName(id));
    }

    return names;
  }

  public static boolean playerHasItemMatchingOre(InventoryPlayer playerInv, String ore) {
    for (ItemStack stack : playerInv.mainInventory) {
      if (!stack.isEmpty() && contains(stack, ore)) {
        return true;
      }
    }
    for (ItemStack stack : playerInv.armorInventory) {
      if (!stack.isEmpty() && contains(stack, ore)) {
        return true;
      }
    }
    for (ItemStack stack : playerInv.offHandInventory) {
      if (!stack.isEmpty() && contains(stack, ore)) {
        return true;
      }
    }
    return false;
  }

  public static boolean contains(ItemStack itemStack, String oreDict) {
    if (!OreDictionary.doesOreNameExist(oreDict)) {
      TerraFirmaGreg.LOGGER.warn("Method called with non-existing name. stack: {} name: {}", itemStack, oreDict);
      return false;
    }

    if (itemStack.isEmpty()) {
      return false;
    }

    int needle = OreDictionary.getOreID(oreDict);
    int[] oreIDs = OreDictionary.getOreIDs(itemStack);

    //noinspection ForLoopReplaceableByForEach
    for (int i = 0; i < oreIDs.length; i++) {
      if (oreIDs[i] == needle) {
        return true;
      }
    }

    return false;
  }

  public static void init() {

    OreDictionaryHelper.MAP.forEach((t, s) -> OreDictionary.registerOre(s, t.toItemStack()));
    OreDictionaryHelper.MAP.clear();

    // Vanilla ore dict values
    OreDictionary.registerOre("lead", Items.LEAD);
    OreDictionary.registerOre("clay", Items.CLAY_BALL);
    OreDictionary.registerOre("gemCoal", new ItemStack(Items.COAL, 1, 0));
    OreDictionary.registerOre("charcoal", new ItemStack(Items.COAL, 1, 1));
    OreDictionary.registerOre("fireStarter",
                              new ItemStack(Items.FLINT_AND_STEEL, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("fireStarter", new ItemStack(Items.FIRE_CHARGE));
    OreDictionary.registerOre("bowl", Items.BOWL);
    OreDictionary.registerOre("blockClay", Blocks.CLAY);

    //adding oredict to dyeables for dye support. Instead of adding specific recipes color can be changed universally.
    OreDictionary.registerOre("bed", new ItemStack(Items.BED, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("carpet",
                              new ItemStack(Blocks.CARPET, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("powderConcrete",
                              new ItemStack(Blocks.CONCRETE_POWDER, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("terracotta",
                              new ItemStack(Blocks.HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE));
    OreDictionary.registerOre("terracotta",
                              new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE));

    //oredict support for TFC dyes
    OreDictionary.registerOre("dyePink", new ItemStack(ItemPowder.get(Powder.KAOLINITE)));
    OreDictionary.registerOre("dyeGray", new ItemStack(ItemPowder.get(Powder.GRAPHITE)));
    OreDictionary.registerOre("dyeRed", new ItemStack(ItemPowder.get(Powder.HEMATITE)));
    OreDictionary.registerOre("dyeBlue", new ItemStack(ItemPowder.get(Powder.LAPIS_LAZULI)));
    OreDictionary.registerOre("dyeYellow", new ItemStack(ItemPowder.get(Powder.LIMONITE)));
    OreDictionary.registerOre("dyeGreen", new ItemStack(ItemPowder.get(Powder.MALACHITE)));
    OreDictionary.registerOre("dyeBrown", new ItemStack(ItemPowder.get(Powder.FERTILIZER)));
    OreDictionary.registerOre("dyeBlack", new ItemStack(ItemPowder.get(Powder.CHARCOAL)));
    OreDictionary.registerOre("dyeBlack", new ItemStack(ItemPowder.get(Powder.COKE)));

    OreDictionary.registerOre("thatch", new ItemStack(Blocks.HAY_BLOCK));
    OreDictionary.registerOre("bale", new ItemStack(Blocks.HAY_BLOCK));
    OreDictionary.registerOre("baleHay", new ItemStack(Blocks.HAY_BLOCK));
    OreDictionary.registerOre("baleCotton", new ItemStack(BlocksTFCF.COTTON_BALE));
    OreDictionary.registerOre("baleCottonYarn", new ItemStack(BlocksTFCF.COTTON_YARN_BALE));
    OreDictionary.registerOre("baleFlax", new ItemStack(BlocksTFCF.FLAX_BALE));
    OreDictionary.registerOre("baleFlaxFiber", new ItemStack(BlocksTFCF.FLAX_FIBER_BALE));
    OreDictionary.registerOre("baleHemp", new ItemStack(BlocksTFCF.HEMP_BALE));
    OreDictionary.registerOre("baleHempFiber", new ItemStack(BlocksTFCF.HEMP_FIBER_BALE));
    OreDictionary.registerOre("baleJute", new ItemStack(BlocksTFCF.JUTE_BALE));
    OreDictionary.registerOre("baleJuteFiber", new ItemStack(BlocksTFCF.JUTE_FIBER_BALE));
    OreDictionary.registerOre("baleLinen", new ItemStack(BlocksTFCF.LINEN_BALE));
    OreDictionary.registerOre("baleLinenString", new ItemStack(BlocksTFCF.LINEN_STRING_BALE));
    OreDictionary.registerOre("balePapyrusFiber", new ItemStack(BlocksTFCF.PAPYRUS_FIBER_BALE));
    OreDictionary.registerOre("baleSilkString", new ItemStack(BlocksTFCF.SILK_STRING_BALE));
    OreDictionary.registerOre("baleSisalFiber", new ItemStack(BlocksTFCF.SISAL_FIBER_BALE));
    OreDictionary.registerOre("baleYucca", new ItemStack(BlocksTFCF.YUCCA_BALE));
    OreDictionary.registerOre("baleYuccaFiber", new ItemStack(BlocksTFCF.YUCCA_FIBER_BALE));
    OreDictionary.registerOre("glue", new ItemStack(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.RESIN))));
    OreDictionary.registerOre("slimeball", new ItemStack(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.RESIN))));

    // Flint
    OreDictionary.registerOre("flint", new ItemStack(Items.FLINT));
    OreDictionary.registerOre("itemFlint", new ItemStack(Items.FLINT));

    // Corals
    BlockCoral.TUBE_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralTube",
                                                                                      new ItemStack(blockCoral)));
    BlockCoral.BRAIN_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralBrain",
                                                                                       new ItemStack(blockCoral)));
    BlockCoral.BUBBLE_CORAL.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coralBubble",
                                                          new ItemStack(blockCoral)));
    BlockCoral.FIRE_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralFire",
                                                                                      new ItemStack(blockCoral)));
    BlockCoral.HORN_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralHorn",
                                                                                      new ItemStack(blockCoral)));

    BlockCoral.TUBE_CORAL.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.BRAIN_CORAL.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.BUBBLE_CORAL.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.FIRE_CORAL.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.HORN_CORAL.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));

    OreDictionary.registerOre("coralTubeDead", new ItemStack(BlocksTFCF.TUBE_CORAL_DEAD));
    OreDictionary.registerOre("coralBrainDead", new ItemStack(BlocksTFCF.BRAIN_CORAL_DEAD));
    OreDictionary.registerOre("coralBubbleDead", new ItemStack(BlocksTFCF.BUBBLE_CORAL_DEAD));
    OreDictionary.registerOre("coralFireDead", new ItemStack(BlocksTFCF.FIRE_CORAL_DEAD));
    OreDictionary.registerOre("coralHornDead", new ItemStack(BlocksTFCF.HORN_CORAL_DEAD));

    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.TUBE_CORAL_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.BRAIN_CORAL_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.BUBBLE_CORAL_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.FIRE_CORAL_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.HORN_CORAL_DEAD));

    BlockCoral.TUBE_CORAL_FAN.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coralFanTube",
                                                          new ItemStack(blockCoral)));
    BlockCoral.BRAIN_CORAL_FAN.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coralFanBrain",
                                                          new ItemStack(blockCoral)));
    BlockCoral.BUBBLE_CORAL_FAN.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coralFanBubble",
                                                          new ItemStack(blockCoral)));
    BlockCoral.FIRE_CORAL_FAN.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coralFanFire",
                                                          new ItemStack(blockCoral)));
    BlockCoral.HORN_CORAL_FAN.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coralFanHorn",
                                                          new ItemStack(blockCoral)));

    BlockCoral.TUBE_CORAL_FAN.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.BRAIN_CORAL_FAN.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.BUBBLE_CORAL_FAN.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.FIRE_CORAL_FAN.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.HORN_CORAL_FAN.forEach(
      (dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));

    OreDictionary.registerOre("coralFanTubeDead", new ItemStack(BlocksTFCF.TUBE_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralFanBrainDead", new ItemStack(BlocksTFCF.BRAIN_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralFanBubbleDead",
                              new ItemStack(BlocksTFCF.BUBBLE_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralFanFireDead", new ItemStack(BlocksTFCF.FIRE_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralFanHornDead", new ItemStack(BlocksTFCF.HORN_CORAL_FAN_DEAD));

    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.TUBE_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.BRAIN_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.BUBBLE_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.FIRE_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.HORN_CORAL_FAN_DEAD));

    BlockCoralBlock.TUBE_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralFanTube",
                                                               new ItemStack(blockCoralBlock)));
    BlockCoralBlock.BRAIN_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralFanBrain",
                                                               new ItemStack(blockCoralBlock)));
    BlockCoralBlock.BUBBLE_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralFanBubble",
                                                               new ItemStack(blockCoralBlock)));
    BlockCoralBlock.FIRE_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralFanFire",
                                                               new ItemStack(blockCoralBlock)));
    BlockCoralBlock.HORN_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralFanHorn",
                                                               new ItemStack(blockCoralBlock)));

    BlockCoralBlock.TUBE_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralBlock",
                                                               new ItemStack(blockCoralBlock)));
    BlockCoralBlock.BRAIN_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralBlock",
                                                               new ItemStack(blockCoralBlock)));
    BlockCoralBlock.BUBBLE_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralBlock",
                                                               new ItemStack(blockCoralBlock)));
    BlockCoralBlock.FIRE_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralBlock",
                                                               new ItemStack(blockCoralBlock)));
    BlockCoralBlock.HORN_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralBlock",
                                                               new ItemStack(blockCoralBlock)));

    BlockCoralBlock.TUBE_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coral",
                                                               new ItemStack(blockCoralBlock)));
    BlockCoralBlock.BRAIN_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coral",
                                                               new ItemStack(blockCoralBlock)));
    BlockCoralBlock.BUBBLE_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coral",
                                                               new ItemStack(blockCoralBlock)));
    BlockCoralBlock.FIRE_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coral",
                                                               new ItemStack(blockCoralBlock)));
    BlockCoralBlock.HORN_CORAL_BLOCK.forEach(
      (dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coral",
                                                               new ItemStack(blockCoralBlock)));

    OreDictionary.registerOre("coralBlockTubeDead",
                              new ItemStack(BlocksTFCF.TUBE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockBrainDead",
                              new ItemStack(BlocksTFCF.BRAIN_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockBubbleDead",
                              new ItemStack(BlocksTFCF.BUBBLE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockFireDead",
                              new ItemStack(BlocksTFCF.FIRE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockHornDead",
                              new ItemStack(BlocksTFCF.HORN_CORAL_BLOCK_DEAD));

    OreDictionary.registerOre("coralBlockDead", new ItemStack(BlocksTFCF.TUBE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockDead", new ItemStack(BlocksTFCF.BRAIN_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockDead", new ItemStack(BlocksTFCF.BUBBLE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockDead", new ItemStack(BlocksTFCF.FIRE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockDead", new ItemStack(BlocksTFCF.HORN_CORAL_BLOCK_DEAD));

    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.TUBE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.BRAIN_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.BUBBLE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.FIRE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.HORN_CORAL_BLOCK_DEAD));

    // Register a name without any items
    OreDictionary.getOres("infiniteFire", true);

  }

}
