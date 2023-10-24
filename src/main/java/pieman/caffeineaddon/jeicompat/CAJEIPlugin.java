package pieman.caffeineaddon.jeicompat;

import java.util.List;
import java.util.stream.Collectors;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.compat.jei.categories.AlloyCategory;
import net.dries007.tfc.compat.jei.categories.AnvilCategory;
import net.dries007.tfc.compat.jei.categories.BarrelCategory;
import net.dries007.tfc.compat.jei.categories.BlastFurnaceCategory;
import net.dries007.tfc.compat.jei.categories.BloomeryCategory;
import net.dries007.tfc.compat.jei.categories.HeatCategory;
import net.dries007.tfc.compat.jei.categories.KnappingCategory;
import net.dries007.tfc.compat.jei.categories.LoomCategory;
import net.dries007.tfc.compat.jei.categories.MetalHeatingCategory;
import net.dries007.tfc.compat.jei.categories.QuernCategory;
import net.dries007.tfc.compat.jei.categories.WeldingCategory;
import net.dries007.tfc.compat.jei.wrappers.SimpleRecipeWrapper;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.minecraft.item.ItemStack;
import pieman.caffeineaddon.Reference;
import pieman.caffeineaddon.client.GuiDryingMat;
import pieman.caffeineaddon.init.ModBlocks;
import pieman.caffeineaddon.recipes.Registries;

@JEIPlugin
public class CAJEIPlugin implements IModPlugin {
	
    private static final String DRYINGMAT_UID = Reference.MOD_ID + ".drying_mat";

    private static IModRegistry REGISTRY;
	
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry){
        //Add new JEI recipe categories 
        registry.addRecipeCategories(new DryingMatCategory(registry.getJeiHelpers().getGuiHelper(), DRYINGMAT_UID));
    } 
    
    @Override
    public void register(IModRegistry registry){
        REGISTRY = registry;
        //Wraps all quern recipes
        List<DryingMatRecipeWrapper> quernList = Registries.DRYINGMAT.getValuesCollection()
            .stream()
            .map(DryingMatRecipeWrapper::new)
            .collect(Collectors.toList());

        registry.addRecipes(quernList, DRYINGMAT_UID); //Register recipes to quern category
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.DRYING_MAT_BLOCK), DRYINGMAT_UID); //Register BlockQuern as the device that do quern recipes

        registry.addRecipeClickArea(GuiDryingMat.class, 103, 35, 9, 14, DRYINGMAT_UID);
    }
        
}
