package net.dries007.tfc.compat.jei.wrappers;

import su.terrafirmagreg.modules.rock.api.types.category.RockCategory;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.rock.init.ItemsRock;
import su.terrafirmagreg.modules.world.classic.objects.generator.vein.VeinRegistry;
import su.terrafirmagreg.modules.world.classic.objects.generator.vein.VeinType;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import gregtech.api.worldgen.config.OreConfigUtils;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.types.Ore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RockLayerWrapper implements IRecipeWrapper {

  private final RockType type;
  private final List<List<ItemStack>> oreList;

  public RockLayerWrapper(RockType type) {
    this.type = type;
    oreList = new ArrayList<>();
    Set<Ore> ores = new HashSet<>();
    List<IBlockState> customOres = new ArrayList<>();
    for (VeinType vein : VeinRegistry.INSTANCE.getVeins().values()) {
      if (vein.canSpawnIn(type)) {
        if (vein.getOre() != null) {
          ores.add(vein.getOre());
        } else {
          // Custom ore entry
          customOres.add(vein.getOreState(type));
        }
      }
    }
    for (Ore ore : ores) {
      // Add every permutation of BlockOreTFC for better readability (this means that it's gonna work for any ore block clicked for recipes)
      List<ItemStack> oreItems = RockType.getTypes()
                                         .stream()
                                         .map(rockType -> new ItemStack(OreConfigUtils.getOreForMaterial(rockType.getMaterial())
                                                                                      .get(BlocksRock.RAW.getStoneType()).getBlock()))
                                         .collect(Collectors.toList());
      oreList.add(oreItems);
    }
    if (!customOres.isEmpty()) {
      // Add custom ores
      oreList.addAll(customOres.stream().filter(state -> state.getMaterial() != Material.AIR)
                               .map(state -> new ItemStack(state.getBlock(), 1, state.getBlock()
                                                                                     .getMetaFromState(state)))
                               .map(Collections::singletonList)
                               .collect(Collectors.toList()));

    }

  }

  @Override
  public void getIngredients(IIngredients recipeIngredients) {
    List<ItemStack> input = new ArrayList<>();
    input.add(new ItemStack(BlocksRock.RAW.get(type)));
    input.add(new ItemStack(ItemsRock.LOOSE.get(type)));
    recipeIngredients.setInputs(VanillaTypes.ITEM,
                                input); // This will only show the raw block, but let use right click stones to open the "recipe"

    recipeIngredients.setOutputLists(VanillaTypes.ITEM, oreList);
  }

  @Override
  public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    final int newLine = 11;
    float x = 33f;
    float y = 6f;
    // Draw Rock Category
    String text = I18n.format("jei.tooltips.tfc.rock_layer.category");
    x = x - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
    minecraft.fontRenderer.drawString(text, x, y, 0x00C300, false);

    text = I18n.format(type.getCategory().getLocalizedName());
    List<String> listString = minecraft.fontRenderer.listFormattedStringToWidth(text, 64); // To fit igneous intrusive/extrusive
    for (String str : listString) {
      x = 33f;
      y += newLine;
      x = x - minecraft.fontRenderer.getStringWidth(str) / 2.0f;
      minecraft.fontRenderer.drawString(str, x, y, 0xFFFFFF, false);
    }

    // Draw Layers
    x = 128f;
    y = 6;
    text = I18n.format("jei.tooltips.tfc.rock_layer.layers");
    x = x - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
    minecraft.fontRenderer.drawString(text, x, y, 0x00C300, false);

    if (RockCategory.Layer.TOP.test(type)) {
      x = 128f;
      y += newLine;
      text = I18n.format("jei.tooltips.tfc.rock_layer.top");
      x = x - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
      minecraft.fontRenderer.drawString(text, x, y, 0xFFFFFF, false);
    }
    if (RockCategory.Layer.MIDDLE.test(type)) {
      x = 128f;
      y += newLine;
      text = I18n.format("jei.tooltips.tfc.rock_layer.middle");
      x = x - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
      minecraft.fontRenderer.drawString(text, x, y, 0xFFFFFF, false);
    }
    if (RockCategory.Layer.BOTTOM.test(type)) {
      x = 128f;
      y += newLine;
      text = I18n.format("jei.tooltips.tfc.rock_layer.bottom");
      x = x - minecraft.fontRenderer.getStringWidth(text) / 2.0f;
      minecraft.fontRenderer.drawString(text, x, y, 0xFFFFFF, false);
    }
  }
}
