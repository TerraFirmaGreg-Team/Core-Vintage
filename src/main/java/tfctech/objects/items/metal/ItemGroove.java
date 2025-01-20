package tfctech.objects.items.metal;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.Metal;
import tfctech.TFCTech;
import tfctech.TechConfig;
import tfctech.client.TechSounds;
import tfctech.objects.blocks.TechBlocks;
import tfctech.objects.blocks.devices.BlockLatexExtractor;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraft.block.BlockHorizontal.FACING;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemGroove extends ItemTechMetal {

  public ItemGroove(Metal metal, ItemTechMetal.ItemType type) {
    super(metal, type);
  }

  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    IBlockState state = worldIn.getBlockState(pos);
    if (isValidBlock(state)) {
      //Check if we can place this treetap here
      for (EnumFacing face : EnumFacing.HORIZONTALS) {
        if (!worldIn.isAirBlock(pos.offset(face))) {
          return EnumActionResult.PASS; //Can't place
        }
      }

      //Find if no other treetap is placed in this tree
      BlockPos trunkPos = pos;
      while (worldIn.getBlockState(trunkPos.down()).getBlock() == state.getBlock()) {
        trunkPos = trunkPos.down();
      }
      do {
        for (EnumFacing face : EnumFacing.HORIZONTALS) {
          if (worldIn.getBlockState(trunkPos.offset(face)).getBlock() instanceof BlockLatexExtractor) {
            return EnumActionResult.PASS; //Found one, cancel
          }
        }
        trunkPos = trunkPos.up();
      } while (worldIn.getBlockState(trunkPos).getBlock() == state.getBlock());

      int hammerSlot = -1;
      boolean isOffhand = false;
      ItemStack offhand = player.getHeldItemOffhand();
      if (offhand.getItem().getToolClasses(offhand).contains("hammer")) {
        isOffhand = true;
      } else {
        //Check if player has hammer in toolbar
        for (int i = 0; i < 9; i++) {
          ItemStack stack = player.inventory.getStackInSlot(i);
          if (stack.getItem().getToolClasses(stack).contains("hammer")) {
            hammerSlot = i;
            break;
          }
        }
      }

      //Place latex extractor
      if (hammerSlot > -1 || isOffhand) {
        if (isOffhand) {
          offhand.damageItem(1, player);
        } else {
          player.inventory.getStackInSlot(hammerSlot).damageItem(1, player);
        }
        player.getHeldItem(hand).shrink(1);
        if (!worldIn.isRemote) {
          worldIn.playSound(null, pos, TechSounds.RUBBER_GROOVE_FIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
          worldIn.setBlockState(pos.offset(facing), TechBlocks.LATEX_EXTRACTOR.getDefaultState().withProperty(FACING, facing));
        }
        return EnumActionResult.SUCCESS;
      }
    }
    return EnumActionResult.PASS;
  }


  /**
   * Check if said block is a valid rubber tree block (from config)
   *
   * @param state the block state to check
   * @return true if valid (from config)
   */
  private boolean isValidBlock(IBlockState state) {
    ResourceLocation resourceLocation = state.getBlock().getRegistryName();
    for (String entry : TechConfig.TWEAKS.rubberTrees) {
      int paramStart = entry.indexOf("{");
      int paramEnd = entry.indexOf("}");
      if (paramStart > -1 && paramEnd > -1) {
        String id = entry.substring(0, paramStart).trim();
        if (resourceLocation != null && id.equals(resourceLocation.toString())) {
          String[] params = entry.substring(paramStart + 1, paramEnd).split(",");
          for (String param : params) {
            boolean valid = false;
            String paramName = param.substring(0, param.indexOf("=")).trim();
            String paramValue = param.substring(param.indexOf("=") + 1).trim();
            for (IProperty<?> property : state.getProperties().keySet()) {
              if (property.getName().equals(paramName)) {
                if (state.getValue(property).toString().equals(paramValue)) {
                  valid = true;
                }
                break;
              }
            }
            if (!valid) {
              return false;
            }
          }
          return true;
        }
      } else if (paramStart <= -1 && paramEnd <= -1) {
        if (resourceLocation != null && entry.equals(resourceLocation.toString())) {
          return true;
        }
      } else {
        // Incorrect config, warning user
        TFCTech.getLog().error("Incorrect rubber tree config found: " + entry);
      }
    }
    return false;
  }
}
