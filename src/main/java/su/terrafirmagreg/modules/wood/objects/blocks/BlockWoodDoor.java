package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlockDoor;
import su.terrafirmagreg.api.lib.model.CustomStateMap;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.IStateMapper;


import lombok.Getter;

@Getter
public class BlockWoodDoor extends BaseBlockDoor implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodDoor(WoodBlockVariant variant, WoodType type) {
        super(Settings.of(Material.WOOD));

        this.variant = variant;
        this.type = type;

        getSettings()
                .soundType(SoundType.WOOD)
                .addOreDict(variant, "wood")
                .addOreDict(variant, "wood", type);

        disableStats();
        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    @Override
    public IStateMapper getStateMapper() {
        return new CustomStateMap.Builder().customResource(getResourceLocation()).ignore(BlockDoor.POWERED).build();
    }
}
