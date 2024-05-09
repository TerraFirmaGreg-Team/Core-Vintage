package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.model.CustomStateMap;
import su.terrafirmagreg.api.spi.block.BaseBlockWall;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import lombok.Getter;

@Getter
public class BlockWoodWall extends BaseBlockWall implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodWall(WoodBlockVariant modelBlock, WoodBlockVariant variant, WoodType type) {
        super(modelBlock.get(type));

        this.variant = variant;
        this.type = type;

        setSoundType(SoundType.WOOD);
        setHarvestLevel("axe", 0);

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, getVariant(), "wood");
        OreDictUtils.register(this, getVariant(), "wood", getType());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRegisterState() {
        ModelUtils.registerStateMapper(this, new CustomStateMap.Builder()
                .customResource(getResourceLocation())
                .ignore(BlockWall.VARIANT)
                .build());
    }
}
