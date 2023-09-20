package net.dries007.tfc.module.core.common.objects.recipes.handlers;

import net.dries007.tfc.api.registries.TFCRegistries;

public class LoomRecipes {

    public static void register() {
        var registry = TFCRegistries.LOOM;

//        registry.registerAll(
//                new LoomRecipe(TerraFirmaCraft.identifier("burlap_cloth"), IIngredient.of(ItemsTFC_old.JUTE_FIBER, 12), new ItemStack(ItemsTFC_old.BURLAP_CLOTH), 12, TerraFirmaCraft.identifier("textures/blocks/devices/loom/product/burlap.png")),
//                new LoomRecipe(TerraFirmaCraft.identifier("wool_cloth"), IIngredient.of(ItemsTFC_old.WOOL_YARN, 16), new ItemStack(ItemsTFC_old.WOOL_CLOTH), 16, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png")),
//                new LoomRecipe(TerraFirmaCraft.identifier("silk_cloth"), IIngredient.of(Items.STRING, 24), new ItemStack(ItemsTFC_old.SILK_CLOTH), 24, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png")),
//
//                new LoomRecipe(TerraFirmaCraft.identifier("wool_block"), IIngredient.of(ItemsTFC_old.WOOL_CLOTH, 4), new ItemStack(Blocks.WOOL, 8), 4, new ResourceLocation("minecraft", "textures/blocks/wool_colored_white.png"))
//        );
    }
}
