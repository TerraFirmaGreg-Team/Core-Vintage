package se.gory_moon.horsepower.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;


import se.gory_moon.horsepower.blocks.ModBlocks;
import se.gory_moon.horsepower.client.renderer.ClientHandler;
import se.gory_moon.horsepower.client.renderer.TESRChopper;
import se.gory_moon.horsepower.client.renderer.TESRChoppingBlock;
import se.gory_moon.horsepower.client.renderer.TESRFiller;
import se.gory_moon.horsepower.client.renderer.TESRGrindstone;
import se.gory_moon.horsepower.client.renderer.TESRHPBase;
import se.gory_moon.horsepower.client.renderer.TESRHandGrindstone;
import se.gory_moon.horsepower.client.renderer.TESRPress;
import se.gory_moon.horsepower.tileentity.TileChopper;
import se.gory_moon.horsepower.tileentity.TileFiller;
import se.gory_moon.horsepower.tileentity.TileGrindstone;
import se.gory_moon.horsepower.tileentity.TileHandGrindstone;
import se.gory_moon.horsepower.tileentity.TileManualChopper;
import se.gory_moon.horsepower.tileentity.TilePress;
import se.gory_moon.horsepower.util.color.ColorGetter;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

  @Override
  public void preInit() {
    super.preInit();
    ClientRegistry.bindTileEntitySpecialRenderer(TileGrindstone.class, new TESRGrindstone());
    ClientRegistry.bindTileEntitySpecialRenderer(TileChopper.class, new TESRChopper());
    ClientRegistry.bindTileEntitySpecialRenderer(TileFiller.class, new TESRFiller());
    ClientRegistry.bindTileEntitySpecialRenderer(TileHandGrindstone.class, new TESRHandGrindstone());
    ClientRegistry.bindTileEntitySpecialRenderer(TileManualChopper.class, new TESRChoppingBlock());
    ClientRegistry.bindTileEntitySpecialRenderer(TilePress.class, new TESRPress());
  }

  @Override
  public void init() {
    MinecraftForge.EVENT_BUS.register(ClientHandler.class);
  }

  @Override
  public void loadComplete() {

    ((IReloadableResourceManager) Minecraft.getMinecraft()
            .getResourceManager()).registerReloadListener(resourceManager ->
    {
      TESRHPBase.clearDestroyStageicons();
    });

    Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
    {
      if (worldIn != null && pos != null) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileGrindstone tile) {
          ItemStack outputStack = tile.getStackInSlot(1);
          ItemStack secondaryStack = tile.getStackInSlot(2);
          if (outputStack.getCount() < secondaryStack.getCount()) {
            outputStack = secondaryStack;
          }
          if (!OreDictionary.itemMatches(tile.renderStack, outputStack, true)) {
            tile.renderStack = outputStack;
            if (!outputStack.isEmpty()) {
              tile.grindColor = ColorGetter.getColors(outputStack, 2).get(0);
            } else {
              tile.grindColor = null;
            }
            tile.renderStack = outputStack;
          }

          if (tile.grindColor != null) {
            return tile.grindColor.getRGB();
          }
        }
      }
      return -1;
    }, ModBlocks.BLOCK_GRINDSTONE);
  }
}
