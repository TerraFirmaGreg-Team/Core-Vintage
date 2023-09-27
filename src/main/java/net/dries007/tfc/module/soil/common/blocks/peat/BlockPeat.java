package net.dries007.tfc.module.soil.common.blocks.peat;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import net.dries007.tfc.module.api.common.block.BlockBase;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;


public class BlockPeat extends BlockBase {

    public static final String NAME = "peat";

    public BlockPeat() {
        super(Material.GROUND);

        setSoundType(SoundType.GROUND);
        setHardness(0.6F);
        setHarvestLevel("shovel", 0);

        OreDictionaryHelper.register(this, NAME);
        Blocks.FIRE.setFireInfo(this, 5, 10);

        DirtHelper.registerSoil(this.getDefaultState().getBlock(), DirtHelper.GRAVELLIKE);
    }
}
