package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.client.model.CustomStateMap;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import lombok.Getter;

@Getter
public class BlockWoodFenceGate extends BlockFenceGate implements IWoodBlock {

    protected final Settings settings;
    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodFenceGate(WoodBlockVariant variant, WoodType type) {
        super(BlockPlanks.EnumType.OAK);

        this.variant = variant;
        this.type = type;

        this.settings = Settings.of(Material.WOOD)
                .soundType(SoundType.WOOD)
                .hardness(2.0F)
                .resistance(15.0F)
                .addOreDict("fence", "gate", "wood")
                .addOreDict("fence", "gate", "wood", type);

        setHarvestLevel("axe", 0);

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IStateMapper getStateMapper() {
        return new CustomStateMap.Builder().customResource(getResourceLocation()).ignore(IN_WALL, POWERED).build();
    }
}
