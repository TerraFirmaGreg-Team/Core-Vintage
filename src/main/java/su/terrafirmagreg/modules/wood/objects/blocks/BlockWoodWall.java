package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.model.CustomStateMap;
import su.terrafirmagreg.api.spi.block.BaseBlockWall;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModelUtils;
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

    public BlockWoodWall(WoodBlockVariant model, WoodBlockVariant variant, WoodType type) {
        super(model.get(type));

        this.variant = variant;
        this.type = type;

        getSettings()
                .soundType(SoundType.WOOD)
                .addOreDict("wall", "wood")
                .addOreDict("wall", "wood", type);

        setHarvestLevel("axe", model.get(type).getHarvestLevel(model.get(type).getDefaultState()));

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
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
