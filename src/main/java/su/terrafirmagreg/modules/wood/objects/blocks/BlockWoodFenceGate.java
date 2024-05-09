package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.model.CustomStateMap;
import su.terrafirmagreg.api.spi.itemblock.BaseItemBlock;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class BlockWoodFenceGate extends BlockFenceGate implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodFenceGate(WoodBlockVariant variant, WoodType type) {
        super(BlockPlanks.EnumType.OAK);

        this.variant = variant;
        this.type = type;

        setSoundType(SoundType.WOOD);
        setHarvestLevel("axe", 0);
        setHardness(2.0F);
        setResistance(15.0F);

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "fence", "gate", "wood");
        OreDictUtils.register(this, "fence", "gate", "wood", type);
    }

    @Override
    public @Nullable BaseItemBlock getItemBlock() {
        return new BaseItemBlock(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRegisterState() {
        ModelUtils.registerStateMapper(this, new CustomStateMap.Builder()
                .customResource(getResourceLocation())
                .ignore(IN_WALL, POWERED)
                .build());
    }

}
