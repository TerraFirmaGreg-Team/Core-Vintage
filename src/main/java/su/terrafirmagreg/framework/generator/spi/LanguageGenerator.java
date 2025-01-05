package su.terrafirmagreg.framework.generator.spi;

import su.terrafirmagreg.api.data.enums.ResourceType;
import su.terrafirmagreg.framework.generator.spi.aggregator.TranslationsAggregator;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;


public abstract class LanguageGenerator extends ResourceGenerator {

  protected final String langCode;
  private final Map<String, String> translations = new LinkedHashMap<>();

  public LanguageGenerator(IModule module, ResourceCache cache, String langCode) {
    super(module, cache);
    if (!langCode.matches("[a-z]{2}_[a-z]{2}")) {throw new IllegalArgumentException("Invalid lang code '" + langCode + "'!");}
    this.langCode = langCode;
  }

  @Override
  public void save() {
    // Save the object to the cache
    this.cache.saveResource(ResourceType.ASSET, TranslationsAggregator.INSTANCE, this.translations, this.modid, "lang", this.langCode, ".json");
  }

  /**
   * Adds the given translation.
   *
   * @param translationKey key for the translation
   * @param translation    the translation
   */
  protected void translation(String translationKey, String translation) {
    if (translationKey.trim().isEmpty()) {
      throw new IllegalArgumentException("Translation key '" + translation + "' for translation '" + translation + "' must not be empty!");
    }

    this.translations.put(translationKey, translation);
  }

  /**
   * Adds the given translation for the item group.
   *
   * @param group       group to add the translation for
   * @param translation translation of the group name
   */
  protected void itemGroup(CreativeTabs group, String translation) {
    this.translation(group.getTranslationKey(), translation);
  }

  /**
   * Adds the given translation for the item.
   *
   * @param item        item to add the translation for
   * @param translation translation of the item name
   */
  protected void item(Item item, String translation) {
    this.translation(item.getTranslationKey(), translation);
  }

  /**
   * Adds the given translation for the item.
   *
   * @param block       block to add the translation for
   * @param translation translation of the block name
   */
  protected void block(Block block, String translation) {
    this.translation(block.getTranslationKey(), translation);
  }

  public String getName() {
    return this.modName + " Language Generator";
  }
}
