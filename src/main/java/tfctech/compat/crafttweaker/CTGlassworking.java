package tfctech.compat.crafttweaker;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tfctech.api.recipes.GlassworkingRecipe;
import tfctech.registry.TechRegistries;

@ZenClass("mods.tfctech.Glassworking")
@ZenRegister
public class CTGlassworking
{
    @ZenMethod
    public static void addRecipe(String registryName, IItemStack output, String... pattern)
    {
        if (output == null || pattern.length < 1 || pattern.length > 5)
            throw new IllegalArgumentException("Output item must be non-null and pattern must be a closed interval [1, 5]");
        ItemStack outputStack = (ItemStack) output.getInternal();
        GlassworkingRecipe recipe = new GlassworkingRecipe(outputStack, pattern).setRegistryName(registryName);
        CraftTweakerAPI.apply(new IAction()
        {
            @Override
            public void apply()
            {
                TechRegistries.GLASSWORKING.register(recipe);
            }

            @Override
            public String describe()
            {
                //noinspection ConstantConditions
                return "Adding glassworking recipe " + recipe.getRegistryName().toString();
            }
        });
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output)
    {
        if (output == null) throw new IllegalArgumentException("Output not allowed to be empty");
        ItemStack item = (ItemStack) output.getInternal();
        List<GlassworkingRecipe> removeList = TechRegistries.GLASSWORKING.getValuesCollection()
            .stream().filter(x -> x.getOutput().isItemEqual(item))
            .collect(Collectors.toList());
        for (GlassworkingRecipe rem : removeList)
        {
            CraftTweakerAPI.apply(new IAction()
            {
                @Override
                public void apply()
                {
                    IForgeRegistryModifiable<GlassworkingRecipe> modRegistry = (IForgeRegistryModifiable<GlassworkingRecipe>) TechRegistries.GLASSWORKING;
                    modRegistry.remove(rem.getRegistryName());
                }

                @Override
                public String describe()
                {
                    //noinspection ConstantConditions
                    return "Removing glassworking recipe " + rem.getRegistryName().toString();
                }
            });
        }
    }

    @ZenMethod
    public static void removeRecipe(String registryName)
    {
        GlassworkingRecipe recipe = TechRegistries.GLASSWORKING.getValue(new ResourceLocation(registryName));
        if (recipe != null)
        {
            CraftTweakerAPI.apply(new IAction()
            {
                @Override
                public void apply()
                {
                    IForgeRegistryModifiable<GlassworkingRecipe> modRegistry = (IForgeRegistryModifiable<GlassworkingRecipe>) TechRegistries.GLASSWORKING;
                    modRegistry.remove(recipe.getRegistryName());
                }

                @Override
                public String describe()
                {
                    //noinspection ConstantConditions
                    return "Removing glassworking recipe " + recipe.getRegistryName().toString();
                }
            });
        }
    }
}
