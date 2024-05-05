package su.terrafirmagreg.modules.wood.init.recipes;

import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.wood.api.recipes.LoomRecipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;


import com.eerussianguy.firmalife.registry.ItemsFL;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemsTFC;
import tfcflorae.objects.items.ItemsTFCF;

import static su.terrafirmagreg.api.data.Constants.MODID_FL;
import static su.terrafirmagreg.api.data.Constants.MODID_TFCF;

public final class LoomRecipes {

    public static void onRegister() {

        new LoomRecipe.Builder("wool_cloth")
                .setInputStack(IIngredient.of(ItemsAnimal.WOOL_YARN, 16))
                .setOutputStack(new ItemStack(ItemsAnimal.WOOL_CLOTH))
                .setStepCount(16)
                .setInProgressTexture("minecraft", "textures/blocks/wool_colored_white.png")
                .build();

        new LoomRecipe.Builder("pineapple_yarn")
                .setInputStack(IIngredient.of(ItemsFL.PINEAPPLE_YARN, 8))
                .setOutputStack(new ItemStack(ItemsFL.PINEAPPLE_LEATHER))
                .setStepCount(8)
                .setInProgressTexture(MODID_FL, "textures/blocks/pineapple.png")
                .build();

        new LoomRecipe.Builder("burlap_cloth")
                .setInputStack(IIngredient.of(ItemsTFC.JUTE_FIBER, 12))
                .setOutputStack(new ItemStack(ItemsTFC.BURLAP_CLOTH))
                .setStepCount(12)
                .setInProgressTexture("textures/blocks/devices/loom/product/burlap.png")
                .build();

        new LoomRecipe.Builder("silk_cloth")
                .setInputStack(IIngredient.of(Items.STRING, 24))
                .setOutputStack(new ItemStack(ItemsAnimal.SILK_CLOTH))
                .setStepCount(24)
                .setInProgressTexture("minecraft", "textures/blocks/wool_colored_white.png")
                .build();

        new LoomRecipe.Builder("wool_block")
                .setInputStack(IIngredient.of(ItemsAnimal.WOOL_CLOTH, 4))
                .setOutputStack(new ItemStack(Blocks.WOOL, 8))
                .setStepCount(4)
                .setInProgressTexture("minecraft", "textures/blocks/wool_colored_white.png")
                .build();

        new LoomRecipe.Builder("yucca_canvas")
                .setInputStack(IIngredient.of(ItemsTFCF.YUCCA_STRING, 12))
                .setOutputStack(new ItemStack(ItemsTFCF.YUCCA_CANVAS))
                .setStepCount(12)
                .setInProgressTexture(MODID_TFCF, "textures/blocks/devices/loom/product/yucca.png")
                .build();

        new LoomRecipe.Builder("cotton_cloth")
                .setInputStack(IIngredient.of(ItemsTFCF.COTTON_YARN, 12))
                .setOutputStack(new ItemStack(ItemsTFCF.COTTON_CLOTH))
                .setStepCount(12)
                .setInProgressTexture(MODID_TFCF, "textures/blocks/devices/loom/product/cotton.png")
                .build();

        new LoomRecipe.Builder("hemp_cloth")
                .setInputStack(IIngredient.of(ItemsTFCF.HEMP_STRING, 12))
                .setOutputStack(new ItemStack(ItemsTFCF.HEMP_CLOTH))
                .setStepCount(12)
                .setInProgressTexture(MODID_TFCF, "textures/blocks/devices/loom/product/hemp.png")
                .build();

        new LoomRecipe.Builder("linen_cloth")
                .setInputStack(IIngredient.of(ItemsTFCF.LINEN_STRING, 12))
                .setOutputStack(new ItemStack(ItemsTFCF.LINEN_CLOTH))
                .setStepCount(12)
                .setInProgressTexture(MODID_TFCF, "textures/blocks/devices/loom/product/linen.png")
                .build();

        new LoomRecipe.Builder("sisal_cloth")
                .setInputStack(IIngredient.of(ItemsTFCF.SISAL_STRING, 12))
                .setOutputStack(new ItemStack(ItemsTFCF.SISAL_CLOTH))
                .setStepCount(12)
                .setInProgressTexture(MODID_TFCF, "textures/blocks/devices/loom/product/sisal.png")
                .build();

        new LoomRecipe.Builder("wool_block_cotton")
                .setInputStack(IIngredient.of(ItemsTFCF.COTTON_CLOTH, 4))
                .setOutputStack(new ItemStack(Blocks.WOOL, 8))
                .setStepCount(4)
                .setInProgressTexture("minecraft", "textures/blocks/wool_colored_white.png")
                .build();

        new LoomRecipe.Builder("wool_block_linen")
                .setInputStack(IIngredient.of(ItemsTFCF.LINEN_CLOTH, 4))
                .setOutputStack(new ItemStack(Blocks.WOOL, 8))
                .setStepCount(4)
                .setInProgressTexture("minecraft", "textures/blocks/wool_colored_white.png")
                .build();

        new LoomRecipe.Builder("wool_block_silk")
                .setInputStack(IIngredient.of(ItemsAnimal.SILK_CLOTH, 4))
                .setOutputStack(new ItemStack(Blocks.WOOL, 8))
                .setStepCount(4)
                .setInProgressTexture("minecraft", "textures/blocks/wool_colored_white.png")
                .build();
    }
}
