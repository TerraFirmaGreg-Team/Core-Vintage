package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.rock.block.type.RockBlockType;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariant;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.util.IRockBlock;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class BlockRock extends Block implements IRockBlock, IItemSize {

    private final RockBlockType rockBlockType;
    private final RockBlockVariant rockBlockVariant;
    private final RockType rockType;

    public BlockRock(Material material, RockBlockType rockBlockType, RockBlockVariant rockBlockVariant, RockType rockType) {
        super(material);

        this.rockBlockType = rockBlockType;
        this.rockBlockVariant = rockBlockVariant;
        this.rockType = rockType;

        this.setCreativeTab(CreativeTabsTFC.ROCK);
        this.setSoundType(SoundType.STONE);
        this.setHardness(getFinalHardness());
        this.setHarvestLevel("pickaxe", 0);
        this.setRegistryName(MOD_ID, getRegistryString());
        this.setTranslationKey(getTranslationString());

        // TODO: 07.08.2023
        /*
        if (rockVariant.canFall())
            FallingBlockManager.registerFallable(this, rockVariant.getFallingSpecification());*/
    }

    public BlockRock(RockBlockType rockBlockType, RockBlockVariant rockBlockVariant, RockType rockType) {
        this(Material.ROCK, rockBlockType, rockBlockVariant, rockType);
    }

    @Nonnull
    @Override
    public RockBlockType getRockBlockType() {
        return rockBlockType;
    }

    @Nullable
    @Override
    public RockBlockVariant getRockBlockVariant() {
        return rockBlockVariant;
    }

    @Nonnull
    @Override
    public RockType getRockType() {
        return rockType;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.LIGHT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(getResourceLocation(), "rocktype=" + rockType.toString());
            }
        });


        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                this.getMetaFromState(this.getBlockState().getBaseState()),
                new ModelResourceLocation(getResourceLocation(), "rocktype=" + rockType.toString()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("stonecategory.name").getFormattedText() + ": " + getRockType().getRockCategory().getLocalizedName());
    }
}
