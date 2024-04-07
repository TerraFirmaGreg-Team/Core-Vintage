package com.buuz135.hotornot.types;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.buuz135.hotornot.object.item.HONItems;
import com.buuz135.hotornot.object.item.ItemMetalTongsHead;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Metal.ItemType;
import net.dries007.tfc.api.types.Metal.Tier;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.metal.ItemMetal;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.skills.SmithingSkill.Type;

import static su.terrafirmagreg.api.lib.Constants.MODID_HOTORNOT;

@EventBusSubscriber(modid = MODID_HOTORNOT)
public final class RegisterRecipes {

    @SubscribeEvent
    public static void onRegisterAnvilRecipe(final Register<AnvilRecipe> event) {
        final IForgeRegistry<AnvilRecipe> registry = event.getRegistry();
        for (final Metal metal : TFCRegistries.METALS.getValuesCollection()) {
            if (!metal.isToolMetal()) continue;

            registry.register(new AnvilRecipe(new ResourceLocation(MODID_HOTORNOT, metal + "_tongs_head"), IIngredient.of(new ItemStack(
                    ItemMetal.get(metal, ItemType.INGOT))),
                    new ItemStack(ItemMetalTongsHead.get(metal)),
                    metal.getTier(), Type.TOOLS,
                    ForgeRule.PUNCH_LAST, ForgeRule.DRAW_SECOND_LAST, ForgeRule.DRAW_THIRD_LAST));
        }
    }

    @SubscribeEvent
    public static void onRegisterKnappingRecipeEvent(final Register<KnappingRecipe> event) {
        event.getRegistry().register(
                // TODO change the recipe to be something more interesting than a straight line
                new KnappingRecipeSimple(KnappingType.CLAY, false, new ItemStack(HONItems.TONGS_JAW_UNFIRED_MOLD),
                        "X", "X", "X", "X", "X").setRegistryName("unfired_tongs_jaw_mold")
        );
    }

    @SubscribeEvent
    public static void onRegisterHeatRecipe(final Register<HeatRecipe> event) {
        event.getRegistry().register(
                new HeatRecipeSimple(IIngredient.of(HONItems.TONGS_JAW_UNFIRED_MOLD), new ItemStack(HONItems.TONGS_JAW_FIRED_MOLD), 1599F,
                        Tier.TIER_I).setRegistryName("fired_tongs_jaw_mold")
        );
    }
}
