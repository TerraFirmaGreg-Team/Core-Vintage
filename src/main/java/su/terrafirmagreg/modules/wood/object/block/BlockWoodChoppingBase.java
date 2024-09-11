package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.data.lib.property.PropertyUnlistedString;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.event.ForgeEventFactory;


import gregtech.api.items.toolitem.ToolClasses;
import se.gory_moon.horsepower.blocks.BlockHPBase;
import se.gory_moon.horsepower.tileentity.TileEntityChopper;
import se.gory_moon.horsepower.tileentity.TileEntityHPBase;
import se.gory_moon.horsepower.tileentity.TileEntityManualChopper;
import se.gory_moon.horsepower.util.RenderUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockWoodChoppingBase extends BlockHPBase {

  public static final PropertyUnlistedString SIDE_TEXTURE = new PropertyUnlistedString(
          "side_texture");
  public static final PropertyUnlistedString TOP_TEXTURE = new PropertyUnlistedString(
          "top_texture");

  public BlockWoodChoppingBase() {
    super(Material.WOOD);
    setHarvestLevel(ToolClasses.AXE, 0);
    setSoundType(SoundType.WOOD);
  }

  public static ItemStack createItemStack(BlockWoodChoppingBase table, int amount,
          ItemStack blockItem) {
    ItemStack stack = new ItemStack(table, amount);
    Block block = Block.getBlockFromItem(blockItem.getItem());

    if (block != Blocks.AIR) {
      ItemStack blockStack = new ItemStack(block, 1, blockItem.getItemDamage());
      NBTTagCompound nbt = new NBTTagCompound();
      NBTTagCompound subTag = new NBTTagCompound();
      if (block instanceof BlockWoodChoppingBase) {
        subTag = blockItem.getSubCompound("textureBlock");
        subTag = subTag != null ? subTag : new NBTTagCompound();
      } else {
        blockStack.writeToNBT(subTag);
      }
      NBTUtils.setGenericNBTValue(nbt, "textureBlock", subTag);
      stack.setTagCompound(nbt);
    }

    return stack;
  }

  @Override
  public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state,
          float chance, int fortune) {
    if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {

      List<ItemStack> items = this.getDrops(worldIn, pos, state, fortune);
      chance = ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, fortune, chance,
              false, harvesters.get());

      for (ItemStack item : items) {
        // save the data from the block onto the item
        if (item.getItem() == Item.getItemFromBlock(this)) {
          writeDataOntoItemstack(item, worldIn, pos, state, chance >= 1f);
        }
      }

      for (ItemStack item : items) {
        if (worldIn.rand.nextFloat() <= chance) {
          spawnAsEntity(worldIn, pos, item);
        }
      }
    }
  }

  @Override
  public void onBlockPlacedBy(@NotNull World worldIn, BlockPos pos, IBlockState state,
          EntityLivingBase placer, ItemStack stack) {
    NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
    TileEntityHPBase tile = getTileEntity(worldIn, pos);
    if (tile == null) {
      return;
    }
    NBTTagCompound baseTag =
            tag != null ? tag.getCompoundTag("textureBlock") : new NBTTagCompound();
    tile.getTileData().setTag("textureBlock", baseTag);
  }

  @Override
  @NotNull
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
          EntityPlayer player) {
    List<ItemStack> drops = new ArrayList<>();
    Item item = this.getItemDropped(state, world.rand, 0);
    if (item != Items.AIR) {
      drops.add(new ItemStack(item, 1, this.damageDropped(state)));
    }

    if (!drops.isEmpty()) {
      ItemStack stack = drops.get(0);
      writeDataOntoItemstack(stack, world, pos, state, false);
      return stack;
    }

    return super.getPickBlock(state, target, world, pos, player);
  }

  //    @Override
  //    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
  //        List<ItemStack> stacks = Utils.getCraftingItems(this);
  //        for (ItemStack stack : stacks) {
  //            if (!Configs.general.useDynamicDisplay && !"tfc".equals(stack.get().getRegistryName().getNamespace()))
  //                continue;
  //            Block block = getBlockFromItem(stack.get());
  //            int blockMeta = stack.getItemDamage();
  //
  //            if (blockMeta == OreDictionary.WILDCARD_VALUE) {
  //                NonNullList<ItemStack> subBlocks = NonNullList.create();
  //                block.getSubBlocks(null, subBlocks);
  //
  //                for (ItemStack subBlock : subBlocks) {
  //                    list.add(createItemStack(this, 1, subBlock));
  //                }
  //            } else {
  //                list.add(createItemStack(this, 1, stack));
  //            }
  //        }
  //    }

  @Override
  @NotNull
  public IBlockState getExtendedState(@NotNull IBlockState state, @NotNull IBlockAccess world,
          @NotNull BlockPos pos) {
    IExtendedBlockState extendedState = (IExtendedBlockState) state;

    TileEntityHPBase tile = getTileEntity(world, pos);
    if (tile != null) {
      return getExtendedState(tile, tile.getExtendedState(extendedState));
    }

    return super.getExtendedState(state, world, pos);
  }

  public static IExtendedBlockState getExtendedState(TileEntityHPBase tile,
          IExtendedBlockState state) {
    String side_texture = tile.getTileData().getString("side_texture");
    String top_texture = tile.getTileData().getString("top_texture");

    if (side_texture.isEmpty() || top_texture.isEmpty()) {
      ItemStack stack = new ItemStack(tile.getTileData().getCompoundTag("textureBlock"));
      if (!stack.isEmpty() && tile.getWorld().isRemote) {
        Block block = Block.getBlockFromItem(stack.getItem());
        IBlockState state1 = block.getStateFromMeta(stack.getMetadata());
        side_texture = RenderUtils.getTextureFromBlockstate(state1).getIconName();
        top_texture = RenderUtils.getTopTextureFromBlockstate(state1).getIconName();
        tile.getTileData().setString("side_texture", side_texture);
        tile.getTileData().setString("top_texture", top_texture);
      }
    }

    if (!side_texture.isEmpty()) {
      state = state.withProperty(SIDE_TEXTURE, side_texture);
    }
    if (!top_texture.isEmpty()) {
      state = state.withProperty(TOP_TEXTURE, top_texture);
    }

    return state;
  }

  private void writeDataOntoItemstack(@NotNull ItemStack item, @NotNull IBlockAccess world,
          @NotNull BlockPos pos, @NotNull IBlockState state,
          boolean inventorySave) {
    // get block data from the block
    TileEntity tile = world.getTileEntity(pos);
    if (tile != null && (tile instanceof TileEntityChopper
            || tile instanceof TileEntityManualChopper)) {
      NBTTagCompound tag = item.hasTagCompound() ? item.getTagCompound() : new NBTTagCompound();

      // texture
      NBTTagCompound data = tile.getTileData().getCompoundTag("textureBlock");

      if (!data.isEmpty()) {
        tag.setTag("textureBlock", data);
      }

      if (!tag.isEmpty()) {
        item.setTagCompound(tag);
      }
    }
  }
}
