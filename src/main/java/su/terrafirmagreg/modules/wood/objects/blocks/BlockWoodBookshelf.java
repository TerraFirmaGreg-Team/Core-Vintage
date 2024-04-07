package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockBookshelf;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class BlockWoodBookshelf extends BlockBookshelf implements IWoodBlock {

    private final WoodBlockVariant blockVariant;
    private final WoodType type;

    public BlockWoodBookshelf(WoodBlockVariant blockVariant, WoodType type) {
        this.blockVariant = blockVariant;
        this.type = type;

        setSoundType(SoundType.WOOD);
        setHardness(2.0F);
        setResistance(5.0F);
        setHarvestLevel("axe", 0);

        BlockUtils.setFireInfo(this, 30, 20);
    }

    public void onRegisterOreDict() {
        OreDictUtils.register(this, blockVariant);
        OreDictUtils.register(this, blockVariant, type);
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockBase(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    @NotNull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public float getEnchantPowerBonus(@NotNull World world, @NotNull BlockPos pos) {
        return 1.0F;
    }
}
