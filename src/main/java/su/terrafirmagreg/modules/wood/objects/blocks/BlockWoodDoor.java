package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.model.CustomStateMap;
import su.terrafirmagreg.api.spi.block.BaseBlockDoor;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import lombok.Getter;

@Getter
public class BlockWoodDoor extends BaseBlockDoor implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodDoor(WoodBlockVariant variant, WoodType type) {
        super(Settings.of()
                .material(Material.WOOD)
                .soundType(SoundType.WOOD));

        this.variant = variant;
        this.type = type;

        disableStats();
        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, getVariant(), "wood");
        OreDictUtils.register(this, getVariant(), "wood", getType());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onStateMapperRegister() {
        ModelUtils.registerStateMapper(this, new CustomStateMap.Builder().customResource(getResourceLocation()).ignore(BlockDoor.POWERED).build());
    }
}
