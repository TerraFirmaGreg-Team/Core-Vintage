package net.dries007.tfc.api.util;

import javax.annotation.Nullable;
import net.minecraft.item.ItemBlock;

public interface IItemProvider {
    @Nullable ItemBlock getItemBlock();
}
