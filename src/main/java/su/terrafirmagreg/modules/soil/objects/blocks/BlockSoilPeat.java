package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import org.jetbrains.annotations.NotNull;

public class BlockSoilPeat extends BlockBase {

    public BlockSoilPeat() {
        super(Material.GROUND);

        setSoundType(SoundType.GROUND);
        setHardness(0.6F);
        setHarvestLevel("shovel", 0);

        BlockUtils.setFireInfo(this, 5, 10);
        //DirtHelper.registerSoil(this.getDefaultState().get(), DirtHelper.GRAVELLIKE);
    }

    @Override
    public @NotNull String getName() {
        return "soil/peat";
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "peat");
    }
}
