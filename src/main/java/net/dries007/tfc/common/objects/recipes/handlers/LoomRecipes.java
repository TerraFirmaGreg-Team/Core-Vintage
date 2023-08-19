package net.dries007.tfc.common.objects.recipes.handlers;

import net.dries007.tfc.api.registries.TFCRegistries;

public class LoomRecipes {

    public static void register() {
        var registry = TFCRegistries.LOOM;

//        registry.registerAll(
//                new LoomRecipe(new ResourceLocation(MOD_ID, "burlap_cloth"), IIngredient.of(ItemsTFC_old.JUTE_FIBER, 12), new ItemStack(ItemsTFC_old.BURLAP_CLOTH), 12, new ResourceLocation(MOD_ID, "textures/blocks/devices/loom/product/burlap.png")),
//                new LoomRecipe(new ResourceLocation(MOD_ID, "wool_cloth"), IIngredient.of(ItemsTFC_old.WOOL_YARN, 16), new ItemStack(ItemsTFC_old.WOOL_CLOTH), 16, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png")),
//                new LoomRecipe(new ResourceLocation(MOD_ID, "silk_cloth"), IIngredient.of(Items.STRING, 24), new ItemStack(ItemsTFC_old.SILK_CLOTH), 24, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png")),
//
//                new LoomRecipe(new ResourceLocation(MOD_ID, "wool_block"), IIngredient.of(ItemsTFC_old.WOOL_CLOTH, 4), new ItemStack(Blocks.WOOL, 8), 4, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png"))
//        );
    }
}
