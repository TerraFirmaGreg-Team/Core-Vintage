package net.dries007.tfc.objects.blocks.wood;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.wood.IWoodBlock;
import net.dries007.tfc.api.types.wood.block.variant.WoodVariant;
import net.dries007.tfc.api.types.wood.type.Wood;
import net.dries007.tfc.client.CustomStateMap;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockWoodDoor extends BlockDoor implements IWoodBlock {
    private final WoodVariant woodVariant;
    private final Wood wood;
    private final ResourceLocation modelLocation;

    public BlockWoodDoor(WoodVariant woodVariant, Wood wood) {
        super(Material.WOOD);

        this.woodVariant = woodVariant;
        this.wood = wood;
        this.modelLocation = new ResourceLocation(MOD_ID, "wood/" + woodVariant);

        var blockRegistryName = String.format("wood/%s/%s", woodVariant, wood);
        setRegistryName(MOD_ID, blockRegistryName);
        setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
        setCreativeTab(CreativeTabsTFC.WOOD);
        setSoundType(SoundType.WOOD);
        setHardness(3.0F);
        disableStats();
        // No direct item, so no oredict.
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @Override
    public WoodVariant getWoodVariant() {
        return woodVariant;
    }

    @Override
    public Wood getWood() {
        return wood;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

//		@Override
//		public Item getItemDropped(IBlockState state, Random rand, int fortune) {
//				return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : getItemBlock();
//		}
//
//		@Override
//		public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
//				return new ItemStack(getItemBlock());
//		}

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customPath(modelLocation).ignore(BlockDoor.POWERED).build());

        for (IBlockState state : this.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
                    this.getMetaFromState(state),
                    new ModelResourceLocation(modelLocation, "normal"));
        }
    }
}
