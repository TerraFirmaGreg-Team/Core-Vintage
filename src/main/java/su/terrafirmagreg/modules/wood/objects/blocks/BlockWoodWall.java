package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.lib.model.CustomStateMap;
import su.terrafirmagreg.api.base.block.BaseBlockWall;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import gregtech.api.items.toolitem.ToolClasses;

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

        setHarvestLevel(ToolClasses.AXE, model.get(type).getHarvestLevel(model.get(type).getDefaultState()));

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IStateMapper getStateMapper() {
        return new CustomStateMap.Builder().customResource(getResourceLocation()).ignore(BlockWall.VARIANT).build();
    }
}
