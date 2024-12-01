package su.terrafirmagreg.modules.wood.init.recipes;

import su.terrafirmagreg.modules.wood.api.recipes.NutRecipe;
import su.terrafirmagreg.modules.wood.api.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import net.minecraft.item.ItemStack;

import net.dries007.eerussianguy.firmalife.init.FoodFL;
import net.dries007.eerussianguy.firmalife.registry.ItemsFL;
import net.dries007.tfc.objects.items.ItemsTFCF;

public final class NutRecipes {

  public static void onRegister() {
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

    NutRecipe.builder("hickory_nut")
             .setInputLog(BlocksWood.LOG.get(WoodTypes.HICKORY))
             .setInputLeaves(BlocksWood.LEAVES.get(WoodTypes.HICKORY))
             .setOutputItem(new ItemStack(ItemsFL.getFood(FoodFL.PECAN_NUTS)))
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
