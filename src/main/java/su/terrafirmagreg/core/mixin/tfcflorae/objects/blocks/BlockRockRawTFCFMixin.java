package su.terrafirmagreg.core.mixin.tfcflorae.objects.blocks;

import su.terrafirmagreg.core.util.GemsFromRawRocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import gregtech.common.items.ToolItems;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.blocks.stone.BlockStoneAnvil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfcflorae.objects.blocks.blocktype.BlockRockRawTFCF;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.types.BlockTypesTFCF;

@Mixin(value = BlockRockRawTFCF.class, remap = false)
public class BlockRockRawTFCFMixin extends BlockRockVariantTFCF {

  public BlockRockRawTFCFMixin(BlockTypesTFCF.RockTFCF rockTFCF, Rock rock) {
    super(rockTFCF, rock);
  }

  /**
   * @author SpeeeDCraft
   * @reason Drop GT gems from raw rocks instead of TFC
   */
  @Inject(method = "getDrops", at = @At(value = "HEAD"), remap = false, cancellable = true)
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune, CallbackInfo ci) {
    super.getDrops(drops, world, pos, state, fortune);

    if (RANDOM.nextDouble() < ConfigTFC.General.MISC.stoneGemDropChance) {drops.add(GemsFromRawRocks.getRandomGem());}

    ci.cancel();
  }

  /**
   * @author SpeeeDCraft
   * @reason Allow to use GT hammer to create stone anvil
   */
  @Overwrite
  public boolean func_180639_a(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = playerIn.getHeldItemMainhand();
    if (ConfigTFC.General.OVERRIDES.enableStoneAnvil && stack.getItem() == ToolItems.HARD_HAMMER && !worldIn.isBlockNormalCube(pos.up(), true)) {
      if (!worldIn.isRemote) {
        // Create a stone anvil
        BlockRockVariant anvil = BlockRockVariant.get(this.rock, Rock.Type.ANVIL);
        if (anvil instanceof BlockStoneAnvil) {
          worldIn.setBlockState(pos, anvil.getDefaultState());
        }
      }
      return true;
    }
    return false;
  }
}
