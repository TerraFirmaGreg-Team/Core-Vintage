package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.wood.api.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.object.recipe.loom.LoomRecipe;
import su.terrafirmagreg.modules.wood.object.recipe.nut.NutRecipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.dries007.eerussianguy.firmalife.init.FoodFL;
import net.dries007.eerussianguy.firmalife.registry.ItemsFL;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.ItemsTFCF;

import static su.terrafirmagreg.api.data.Reference.MODID_FL;
import static su.terrafirmagreg.api.data.Reference.MODID_TFCF;

public class RecipesWood {

  public static void onRegister() {
    loomRecipes();
    nutRecipes();
  }

  private static void loomRecipes() {
    LoomRecipe.builder("wool_cloth")
              .setInputStack(IIngredient.of(ItemsAnimal.WOOL_YARN, 16))
              .setOutputStack(new ItemStack(ItemsAnimal.WOOL_CLOTH))
              .setStepCount(16)
              .setInProgressTexture("minecraft", "textures/blocks/wool_colored_white.png")
              .build();

    LoomRecipe.builder("pineapple_yarn")
              .setInputStack(IIngredient.of(ItemsFL.PINEAPPLE_YARN, 8))
              .setOutputStack(new ItemStack(ItemsFL.PINEAPPLE_LEATHER))
              .setStepCount(8)
              .setInProgressTexture(MODID_FL, "textures/blocks/pineapple.png")
              .build();

    LoomRecipe.builder("burlap_cloth")
              .setInputStack(IIngredient.of(ItemsTFC.JUTE_FIBER, 12))
              .setOutputStack(new ItemStack(ItemsTFC.BURLAP_CLOTH))
              .setStepCount(12)
              .setInProgressTexture("textures/blocks/devices/loom/product/burlap.png")
              .build();

    LoomRecipe.builder("silk_cloth")
              .setInputStack(IIngredient.of(Items.STRING, 24))
              .setOutputStack(new ItemStack(ItemsAnimal.SILK_CLOTH))
              .setStepCount(24)
              .setInProgressTexture("minecraft", "textures/blocks/wool_colored_white.png")
              .build();

    LoomRecipe.builder("wool_block")
              .setInputStack(IIngredient.of(ItemsAnimal.WOOL_CLOTH, 4))
              .setOutputStack(new ItemStack(Blocks.WOOL, 8))
              .setStepCount(4)
              .setInProgressTexture("minecraft", "textures/blocks/wool_colored_white.png")
              .build();

    LoomRecipe.builder("yucca_canvas")
              .setInputStack(IIngredient.of(ItemsTFCF.YUCCA_STRING, 12))
              .setOutputStack(new ItemStack(ItemsTFCF.YUCCA_CANVAS))
              .setStepCount(12)
              .setInProgressTexture(MODID_TFCF, "textures/blocks/devices/loom/product/yucca.png")
              .build();

    LoomRecipe.builder("cotton_cloth")
              .setInputStack(IIngredient.of(ItemsTFCF.COTTON_YARN, 12))
              .setOutputStack(new ItemStack(ItemsTFCF.COTTON_CLOTH))
              .setStepCount(12)
              .setInProgressTexture(MODID_TFCF, "textures/blocks/devices/loom/product/cotton.png")
              .build();

    LoomRecipe.builder("hemp_cloth")
              .setInputStack(IIngredient.of(ItemsTFCF.HEMP_STRING, 12))
              .setOutputStack(new ItemStack(ItemsTFCF.HEMP_CLOTH))
              .setStepCount(12)
              .setInProgressTexture(MODID_TFCF, "textures/blocks/devices/loom/product/hemp.png")
              .build();

    LoomRecipe.builder("linen_cloth")
              .setInputStack(IIngredient.of(ItemsTFCF.LINEN_STRING, 12))
              .setOutputStack(new ItemStack(ItemsTFCF.LINEN_CLOTH))
              .setStepCount(12)
              .setInProgressTexture(MODID_TFCF, "textures/blocks/devices/loom/product/linen.png")
              .build();

    LoomRecipe.builder("sisal_cloth")
              .setInputStack(IIngredient.of(ItemsTFCF.SISAL_STRING, 12))
              .setOutputStack(new ItemStack(ItemsTFCF.SISAL_CLOTH))
              .setStepCount(12)
              .setInProgressTexture(MODID_TFCF, "textures/blocks/devices/loom/product/sisal.png")
              .build();

    LoomRecipe.builder("wool_block_cotton")
              .setInputStack(IIngredient.of(ItemsTFCF.COTTON_CLOTH, 4))
              .setOutputStack(new ItemStack(Blocks.WOOL, 8))
              .setStepCount(4)
              .setInProgressTexture("minecraft", "textures/blocks/wool_colored_white.png")
              .build();

    LoomRecipe.builder("wool_block_linen")
              .setInputStack(IIngredient.of(ItemsTFCF.LINEN_CLOTH, 4))
              .setOutputStack(new ItemStack(Blocks.WOOL, 8))
              .setStepCount(4)
              .setInProgressTexture("minecraft", "textures/blocks/wool_colored_white.png")
              .build();

    LoomRecipe.builder("wool_block_silk")
              .setInputStack(IIngredient.of(ItemsAnimal.SILK_CLOTH, 4))
              .setOutputStack(new ItemStack(Blocks.WOOL, 8))
              .setStepCount(4)
              .setInProgressTexture("minecraft", "textures/blocks/wool_colored_white.png")
              .build();
  }

  private static void nutRecipes() {
    NutRecipe.builder("common_oak_nut")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.EUROPEAN_OAK))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.EUROPEAN_OAK))
             .setOutputItem(new ItemStack(ItemsFL.getFood(FoodFL.ACORNS)))
             .build();

    NutRecipe.builder("oak_nut")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.OAK))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.OAK))
             .setOutputItem(new ItemStack(ItemsFL.getFood(FoodFL.ACORNS)))
             .build();

    NutRecipe.builder("chestnut_nut")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.CHESTNUT))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.CHESTNUT))
             .setOutputItem(new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUTS)))
             .build();

    NutRecipe.builder("hickory_nut")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.HICKORY))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.HICKORY))
             .setOutputItem(new ItemStack(ItemsFL.getFood(FoodFL.PECAN_NUTS)))
             .build();

    NutRecipe.builder("beech_nut")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.BEECH))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.BEECH))
             .setOutputItem(new ItemStack(ItemsTFCF.BEECHNUT))
             .build();

    NutRecipe.builder("black_walnut_nut")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.BLACK_WALNUT))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.BLACK_WALNUT))
             .setOutputItem(new ItemStack(ItemsTFCF.BLACK_WALNUT))
             .build();

    NutRecipe.builder("butternut_nut")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.BUTTERNUT))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.BUTTERNUT))
             .setOutputItem(new ItemStack(ItemsTFCF.BUTTERNUT))
             .build();

    NutRecipe.builder("ginkgo_nut")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.GINKGO))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.GINKGO))
             .setOutputItem(new ItemStack(ItemsTFCF.GINKGO_NUT))
             .build();

    NutRecipe.builder("hazel_nut")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.HAZEL))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.HAZEL))
             .setOutputItem(new ItemStack(ItemsTFCF.HAZELNUT))
             .build();

    NutRecipe.builder("hemlock_pinecone")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.HEMLOCK))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.HEMLOCK))
             .setOutputItem(new ItemStack(ItemsTFCF.PINECONE))
             .build();

    NutRecipe.builder("ironwood_hops")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.IRONWOOD))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.IRONWOOD))
             .setOutputItem(new ItemStack(ItemsTFCF.HOPS))
             .build();

    NutRecipe.builder("kauri_pinecone")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.KAURI))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.KAURI))
             .setOutputItem(new ItemStack(ItemsTFCF.PINECONE))
             .build();

    NutRecipe.builder("larch_pinecone")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.LARCH))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.LARCH))
             .setOutputItem(new ItemStack(ItemsTFCF.PINECONE))
             .build();

    NutRecipe.builder("nordmann_fir_pinecone")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.NORDMANN_FIR))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.NORDMANN_FIR))
             .setOutputItem(new ItemStack(ItemsTFCF.PINECONE))
             .build();

    NutRecipe.builder("norway_spruce_pinecone")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.NORWAY_SPRUCE))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.NORWAY_SPRUCE))
             .setOutputItem(new ItemStack(ItemsTFCF.PINECONE))
             .build();

    NutRecipe.builder("redwood_pinecone")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.REDWOOD))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.REDWOOD))
             .setOutputItem(new ItemStack(ItemsTFCF.PINECONE))
             .build();

    NutRecipe.builder("walnut_fruit")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.WALNUT))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.WALNUT))
             .setOutputItem(new ItemStack(ItemsTFCF.WALNUT))
             .build();

    NutRecipe.builder("chestnut")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.CHESTNUT))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.CHESTNUT))
             .setOutputItem(new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUTS)))
             .build();

    NutRecipe.builder("pine_pinecone")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.PINE))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.PINE))
             .setOutputItem(new ItemStack(ItemsFL.getFood(FoodFL.PINECONE)))
             .build();

    NutRecipe.builder("coconut")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.PALM))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.PALM))
             .setOutputItem(new ItemStack(ItemsFL.getFood(FoodFL.COCONUT)))
             .build();
  }
}
