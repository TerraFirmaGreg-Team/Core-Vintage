package net.dries007.tfc.module.core.objects.blocks;

import net.dries007.tfc.module.core.api.objects.block.BlockBase;
import net.minecraft.block.material.Material;


public class BlockDebug extends BlockBase {

    public static final String NAME = "debug";

    public BlockDebug() {
        super(Material.SPONGE);
    }
}
