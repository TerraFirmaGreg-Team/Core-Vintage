package net.dries007.tfc.module.core.objects.items;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.Tags;
import net.dries007.tfc.TerraFirmaGreg;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.module.core.ModuleCore.MISC_TAB;


@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemDebug extends Item {
    public ItemDebug() {

        setRegistryName(Tags.MOD_ID, "wand");
        setTranslationKey(Tags.MOD_ID + ".wand");
        setCreativeTab(MISC_TAB);

        setNoRepair();
        setMaxStackSize(1);
        setFull3D();
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // Block
        try {
            Block block = worldIn.getBlockState(pos).getBlock();
            try {
                block.getClass().getMethod("debug").invoke(block);
            } catch (Exception t) { /* Nothing Burger */ }

            // Tile Entity
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile != null) {
                try {
                    tile.getClass().getMethod("debug").invoke(tile);
                } catch (Exception t) {
                    TerraFirmaGreg.LOGGER.info("No debug method found to invoke on {}", tile);
                }

                TerraFirmaGreg.LOGGER.info("Tile Data: {}", tile.serializeNBT());

                IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (inventory != null) {
                    TerraFirmaGreg.LOGGER.info("Found item handler: {}", inventory);
                }

                IFluidHandler fluids = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                if (fluids != null) {
                    TerraFirmaGreg.LOGGER.info("Found fluid handler: {}", fluids);
                }
            }
        } catch (Exception t) { /* Nothing Burger */ }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return false;
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return false;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return 60;
    }

    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
        return false;
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
