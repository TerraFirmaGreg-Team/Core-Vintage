package net.dries007.tfc.api.types.wood;

import net.dries007.tfc.api.types.rock.category.RockCategory;
import net.dries007.tfc.api.types.wood.type.WoodType;

import javax.annotation.Nonnull;

public interface IWoodItem {

    @Nonnull
    WoodType getWoodType();
}
