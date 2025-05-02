package net.dries007.horsepower.proxy;

import su.terrafirmagreg.modules.device.client.render.TESRQuernHorse;
import su.terrafirmagreg.modules.device.client.render.TESRQuernManual;
import su.terrafirmagreg.modules.device.object.tile.TileChopperHorse;
import su.terrafirmagreg.modules.device.object.tile.TileChopperManual;
import su.terrafirmagreg.modules.device.object.tile.TileQuernHorse;
import su.terrafirmagreg.modules.device.object.tile.TileQuernManual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import net.dries007.horsepower.blocks.ModBlocks;
import net.dries007.horsepower.client.renderer.ClientHandler;
import net.dries007.horsepower.client.renderer.TileEntityChopperRender;
import net.dries007.horsepower.client.renderer.TileEntityChoppingBlockRender;
import net.dries007.horsepower.client.renderer.TileEntityFillerRender;
import net.dries007.horsepower.client.renderer.TileEntityHPBaseRenderer;
import net.dries007.horsepower.client.renderer.TileEntityPressRender;
import net.dries007.horsepower.tileentity.TileEntityFiller;
import net.dries007.horsepower.tileentity.TileEntityPress;
import net.dries007.horsepower.util.HorsePowerCommand;
import net.dries007.horsepower.util.color.ColorGetter;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

  @Override
  public void preInit() {
    super.preInit();
    ClientRegistry.bindTileEntitySpecialRenderer(TileQuernHorse.class, new TESRQuernHorse());
    ClientRegistry.bindTileEntitySpecialRenderer(TileChopperHorse.class, new TileEntityChopperRender());
    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFiller.class, new TileEntityFillerRender());
    ClientRegistry.bindTileEntitySpecialRenderer(TileQuernManual.class, new TESRQuernManual());
    ClientRegistry.bindTileEntitySpecialRenderer(TileChopperManual.class, new TileEntityChoppingBlockRender());
    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPress.class, new TileEntityPressRender());
  }

  @Override
  public void init() {
    MinecraftForge.EVENT_BUS.register(ClientHandler.class);
  }

  @Override
  public void loadComplete() {
    ClientCommandHandler.instance.registerCommand(new HorsePowerCommand());

    ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(resourceManager ->
    {
      TileEntityHPBaseRenderer.clearDestroyStageicons();
    });

    Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
    {
      if (worldIn != null && pos != null) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileQuernHorse te) {
          ItemStack outputStack = te.getStackInSlot(1);
          ItemStack secondaryStack = te.getStackInSlot(2);
          if (outputStack.getCount() < secondaryStack.getCount()) {outputStack = secondaryStack;}
          if (!OreDictionary.itemMatches(te.renderStack, outputStack, true)) {
            te.renderStack = outputStack;
            if (!outputStack.isEmpty()) {te.grindColor = ColorGetter.getColors(outputStack, 2).get(0);} else {te.grindColor = null;}
            te.renderStack = outputStack;
          }

          if (te.grindColor != null) {return te.grindColor.getRGB();}
        }
      }
      return -1;
    }, ModBlocks.BLOCK_GRINDSTONE);
  }
}
