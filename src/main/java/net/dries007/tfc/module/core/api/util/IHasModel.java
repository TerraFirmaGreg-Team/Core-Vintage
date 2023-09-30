package net.dries007.tfc.module.core.api.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IHasModel {
    @SideOnly(Side.CLIENT)
    void onModelRegister();
}
