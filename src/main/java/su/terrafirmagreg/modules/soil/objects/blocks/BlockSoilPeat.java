package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSoilPeat extends BaseBlock {

    public BlockSoilPeat() {
        super(Settings.of(Material.GROUND));

        setHarvestLevel("shovel", 0);
        getSettings()
                .soundType(SoundType.GROUND)
                .hardness(0.6F);

        BlockUtils.setFireInfo(this, 5, 10);
        //DirtHelper.registerSoil(this.getDefaultState().get(), DirtHelper.GRAVELLIKE);
    }

    @Override
    public String getName() {
        return "soil/peat";
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "peat");
    }
}
