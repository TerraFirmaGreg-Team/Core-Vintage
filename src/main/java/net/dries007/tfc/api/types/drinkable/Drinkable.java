package net.dries007.tfc.api.types.drinkable;

import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

public class Drinkable {

    private static final Set<Drinkable> DRINKABLES = new LinkedHashSet<>();

    @Nonnull
    private final Supplier<Fluid> fluid;

    @Nonnull
    private final IActionAfterDrink action;

    public Drinkable(@Nonnull Supplier<Fluid> fluid, @Nonnull IActionAfterDrink action) {
        this.fluid = fluid;
        this.action = action;

        if (!DRINKABLES.add(this)) {
            throw new RuntimeException(String.format("Duplicate in drinkables registry! [%s]", fluid.get().getName()));
        }
    }

    @Nullable
    public static IActionAfterDrink getActionByFluid(@Nonnull Fluid fluid) {
        var drinkable = DRINKABLES.stream().filter(s -> s.getFluid() == fluid).findFirst().orElse(null);
        if (drinkable != null)
            return drinkable.getAction();
        return null;
    }

    public static Set<Drinkable> getDrinkables() {
        return DRINKABLES;
    }

    @Nonnull
    public Fluid getFluid() {
        return fluid.get();
    }

    @Nonnull
    public IActionAfterDrink getAction() {
        return action;
    }
}
