package net.dries007.tfc.objects.items.rock;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static su.terrafirmagreg.modules.soil.init.BlocksSoil.*;

public class ItemRockShovel extends ItemSpade implements ICapabilitySize, IRockObject {

    private static final Map<RockCategory, ItemRockShovel> MAP = new HashMap<>();
    public final RockCategory category;

    public ItemRockShovel(RockCategory category) {
        super(category.getToolMaterial());
        this.category = category;
        if (MAP.put(category, this) != null) throw new IllegalStateException("There can only be one.");
        attackDamage = 0.875f * category.getToolMaterial().getAttackDamage();
        attackSpeed = -3f;
        setHarvestLevel("shovel", category.getToolMaterial().getHarvestLevel());
        OreDictionaryHelper.register(this, "shovel");
        OreDictionaryHelper.register(this, "shovel", "stone");
        OreDictionaryHelper.register(this, "shovel", "stone", category);
        OreDictionaryHelper.registerDamageType(this, DamageType.CRUSHING);
    }

    public static ItemRockShovel get(RockCategory category) {
        return MAP.get(category);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Rock type: " + OreDictionaryHelper.toString(category));
    }

    @Override
    @NotNull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY,
                                      float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
            return EnumActionResult.FAIL;
        } else {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();
            if (!(block instanceof ISoilBlock soilBlock)) {
                return EnumActionResult.PASS;
            }
            if (ConfigTFC.General.OVERRIDES.enableGrassPath && facing != EnumFacing.DOWN &&
                    worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR &&
                    BlockUtils.isVariant(soilBlock.getVariant(), GRASS, DRY_GRASS, DIRT)) {
                IBlockState iblockstate1 = GRASS_PATH.get(soilBlock.getType()).getDefaultState();
                worldIn.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

                if (!worldIn.isRemote) {
                    worldIn.setBlockState(pos, iblockstate1, 11);
                    itemstack.damageItem(1, player);
                }

                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return Size.LARGE; // Stored only in chests
    }

    @Override
    public @NotNull Weight getWeight(ItemStack stack) {
        return Weight.MEDIUM;
    }

    @Override
    public boolean canStack(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public Rock getRock(ItemStack stack) {
        return null;
    }

    @NotNull
    @Override
    public RockCategory getRockCategory(ItemStack stack) {
        return category;
    }
}
