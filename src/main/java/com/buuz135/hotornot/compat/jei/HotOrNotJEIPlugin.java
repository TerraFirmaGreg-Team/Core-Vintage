package com.buuz135.hotornot.compat.jei;

import com.buuz135.hotornot.compat.jei.wrappers.CastJawMoldRecipeWrapper;
import com.buuz135.hotornot.compat.jei.wrappers.UnMoldJawPieceRecipeWrapper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.compat.jei.TFCJEIPlugin;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@JEIPlugin
@ParametersAreNonnullByDefault
public class HotOrNotJEIPlugin implements IModPlugin {

    @Override
    public void register(final IModRegistry registry) {

        final List<Metal> tongMetalsInOrder = TFCRegistries.METALS.getValuesCollection()
                .stream()
                .sorted(Comparator.comparingInt(metal -> metal.getTier().ordinal()))
                .filter(metal -> metal.isToolMetal() && metal.getTier().isAtMost(Metal.Tier.TIER_II))
                .collect(Collectors.toList());

        final List<UnMoldJawPieceRecipeWrapper> jawMoldRecipes = new ArrayList<>();
        final List<CastJawMoldRecipeWrapper> castingList = new ArrayList<>();

        for (final Metal metal : tongMetalsInOrder) {
            jawMoldRecipes.add(new UnMoldJawPieceRecipeWrapper(metal));
            castingList.add(new CastJawMoldRecipeWrapper(metal));
        }

        registry.addRecipes(jawMoldRecipes, VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipes(castingList, TFCJEIPlugin.CASTING_UID);
    }
}
