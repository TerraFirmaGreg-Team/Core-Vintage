package su.terrafirmagreg.api.spi.recipe;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.helper.ingredient.FluidStackList;
import com.cleanroommc.groovyscript.helper.ingredient.IngredientList;
import com.cleanroommc.groovyscript.helper.ingredient.ItemStackList;


import java.util.Collection;

public abstract class AbstractRecipeBuilder<T> implements IRecipeBuilder<T> {

    protected ResourceLocation name;
    protected final IngredientList<IIngredient> input = new IngredientList<>();
    protected final ItemStackList output = new ItemStackList();
    protected final FluidStackList fluidInput = new FluidStackList();
    protected final FluidStackList fluidOutput = new FluidStackList();

    public String getRecipeNamePrefix() {
        return "tfg/";
    }

    public AbstractRecipeBuilder<T> name(String name) {
        if (name.contains(":")) {
            this.name = new ResourceLocation(name);
        } else {
            this.name = ModUtils.resource(name);
        }
        return this;
    }

    public AbstractRecipeBuilder<T> name(ResourceLocation name) {
        this.name = name;
        return this;
    }

    public AbstractRecipeBuilder<T> input(IIngredient ingredient) {
        this.input.add(ingredient);
        return this;
    }

    public AbstractRecipeBuilder<T> input(IIngredient... ingredients) {
        for (IIngredient ingredient : ingredients) {
            input(ingredient);
        }
        return this;
    }

    public AbstractRecipeBuilder<T> input(Collection<IIngredient> ingredients) {
        for (IIngredient ingredient : ingredients) {
            input(ingredient);
        }
        return this;
    }

    public AbstractRecipeBuilder<T> output(ItemStack output) {
        this.output.add(output);
        return this;
    }

    public AbstractRecipeBuilder<T> output(ItemStack... outputs) {
        for (ItemStack output : outputs) {
            output(output);
        }
        return this;
    }

    public AbstractRecipeBuilder<T> output(Collection<ItemStack> outputs) {
        for (ItemStack output : outputs) {
            output(output);
        }
        return this;
    }

    public AbstractRecipeBuilder<T> fluidInput(FluidStack ingredient) {
        this.fluidInput.add(ingredient);
        return this;
    }

    public AbstractRecipeBuilder<T> fluidInput(FluidStack... ingredients) {
        for (FluidStack ingredient : ingredients) {
            fluidInput(ingredient);
        }
        return this;
    }

    public AbstractRecipeBuilder<T> fluidInput(Collection<FluidStack> ingredients) {
        for (FluidStack ingredient : ingredients) {
            fluidInput(ingredient);
        }
        return this;
    }

    public AbstractRecipeBuilder<T> fluidOutput(FluidStack output) {
        this.fluidOutput.add(output);
        return this;
    }

    public AbstractRecipeBuilder<T> fluidOutput(FluidStack... outputs) {
        for (FluidStack output : outputs) {
            fluidOutput(output);
        }
        return this;
    }

    public AbstractRecipeBuilder<T> fluidOutput(Collection<FluidStack> outputs) {
        for (FluidStack output : outputs) {
            fluidOutput(output);
        }
        return this;
    }

    @Override
    public boolean validate() {
        LoggingHelper.Msg msg = LoggingHelper.msg(getErrorMsg()).error();
        validate(msg);
        return !msg.postIfNotEmpty();
    }

    public abstract String getErrorMsg();

    public abstract void validate(LoggingHelper.Msg msg);

    public void validateName() {
        if (name == null) {
            name = RecipeName.generateRl(getRecipeNamePrefix());
        }
    }

}
