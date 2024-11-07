package tfctech.compat.crafttweaker;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import net.dries007.tfc.compat.crafttweaker.CTHelper;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tfctech.api.recipes.SmelteryRecipe;
import tfctech.registry.TechRegistries;

@ZenClass("mods.tfctech.Smeltery")
@ZenRegister
public class CTSmeltery
{
    @ZenMethod
    public static CTSmelteryRecipeBuilder addRecipe(String registryName, ILiquidStack output, float meltTemp)
    {
        if (output == null)
            throw new IllegalArgumentException("Output must be non-null");
        FluidStack outputStack = (FluidStack) output.getInternal();
        return new CTSmelteryRecipeBuilder(new ResourceLocation(registryName), outputStack, meltTemp);
    }

    @ZenMethod
    public static void removeRecipe(ILiquidStack output)
    {
        if (output == null) throw new IllegalArgumentException("Output not allowed to be empty");
        FluidStack fluid = (FluidStack) output.getInternal();
        List<SmelteryRecipe> removeList = TechRegistries.SMELTERY.getValuesCollection()
            .stream().filter(x -> x.getOutput().isFluidEqual(fluid))
            .collect(Collectors.toList());
        for (SmelteryRecipe rem : removeList)
        {
            CraftTweakerAPI.apply(new IAction()
            {
                @Override
                public void apply()
                {
                    IForgeRegistryModifiable<SmelteryRecipe> modRegistry = (IForgeRegistryModifiable<SmelteryRecipe>) TechRegistries.SMELTERY;
                    modRegistry.remove(rem.getRegistryName());
                }

                @Override
                public String describe()
                {
                    //noinspection ConstantConditions
                    return "Removing smeltery recipe " + rem.getRegistryName().toString();
                }
            });
        }
    }

    @ZenMethod
    public static void removeRecipe(String registryName)
    {
        SmelteryRecipe recipe = TechRegistries.SMELTERY.getValue(new ResourceLocation(registryName));
        if (recipe != null)
        {
            CraftTweakerAPI.apply(new IAction()
            {
                @Override
                public void apply()
                {
                    IForgeRegistryModifiable<SmelteryRecipe> modRegistry = (IForgeRegistryModifiable<SmelteryRecipe>) TechRegistries.SMELTERY;
                    modRegistry.remove(recipe.getRegistryName());
                }

                @Override
                public String describe()
                {
                    //noinspection ConstantConditions
                    return "Removing smeltery recipe " + recipe.getRegistryName().toString();
                }
            });
        }
    }

    @ZenClass("mods.tfctech.SmelteryRecipeBuilder")
    @ZenRegister
    public static class CTSmelteryRecipeBuilder
    {
        private final SmelteryRecipe.Builder internal;
        private final ResourceLocation registryName;

        private CTSmelteryRecipeBuilder(ResourceLocation registryName, FluidStack fluidStack, float meltTemp)
        {
            this.registryName = registryName;
            internal = new SmelteryRecipe.Builder().setOutput(fluidStack, meltTemp);
        }

        @ZenMethod
        public CTSmelteryRecipeBuilder addInput(crafttweaker.api.item.IIngredient input)
        {
            if (input == null)
                throw new IllegalArgumentException("Input must be non-null");
            //noinspection rawtypes
            IIngredient ingredient = CTHelper.getInternalIngredient(input);
            //noinspection unchecked
            this.internal.addInput(ingredient);
            return this;
        }

        @ZenMethod
        public void build()
        {
            SmelteryRecipe recipe = internal.build();
            recipe.setRegistryName(registryName);
            CraftTweakerAPI.apply(new IAction()
            {
                @Override
                public void apply()
                {
                    TechRegistries.SMELTERY.register(recipe);
                }

                @Override
                public String describe()
                {
                    //noinspection ConstantConditions
                    return "Adding smeltery recipe " + recipe.getRegistryName().toString();
                }
            });
        }
    }
}
