package net.dries007.tfctech.client;

import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfctech.client.render.teisr.TEISRTechDevices;
import net.dries007.tfctech.client.render.tesr.TESRFridge;
import net.dries007.tfctech.client.render.tesr.TESRLatexExtractor;
import net.dries007.tfctech.client.render.tesr.TESRWireDrawBench;
import net.dries007.tfctech.objects.blocks.TechBlocks;
import net.dries007.tfctech.objects.items.TechItems;
import net.dries007.tfctech.objects.items.glassworking.ItemBlowpipe;
import net.dries007.tfctech.objects.items.glassworking.ItemGlassMolder;
import net.dries007.tfctech.objects.items.metal.ItemGear;
import net.dries007.tfctech.objects.items.metal.ItemTechMetal;
import net.dries007.tfctech.objects.tileentities.TEFridge;
import net.dries007.tfctech.objects.tileentities.TELatexExtractor;
import net.dries007.tfctech.objects.tileentities.TEWireDrawBench;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.util.Collections;
import java.util.Map;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCTECH;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TFCTECH)
public final class ClientRegisterEvents {

  @SubscribeEvent
  @SuppressWarnings("ConstantConditions")
  public static void registerModels(final ModelRegistryEvent event) {
    // ITEMS //

    //Fluid containers
    ModelLoader.setCustomModelResourceLocation(TechItems.FLUID_BOWL, 0, new ModelResourceLocation(TechItems.FLUID_BOWL.getRegistryName(), "inventory"));

    // Simple Items
    for (Item item : TechItems.getAllSimpleItems()) {
      ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));
    }

    for (Block block : TechBlocks.getAllFluidBlocks()) {ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());}

    // Item Blocks
    for (ItemBlock item : TechBlocks.getAllInventoryItemBlocks()) {
      ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    //TEISR item blocks
    for (ItemBlock item : TechBlocks.getAllTEISRBlocks()) {
      ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
      item.setTileEntityItemStackRenderer(new TEISRTechDevices());
    }

    // Metals
    for (Item item : TechItems.getAllMetalItems()) {
      if (item instanceof ItemTechMetal metalItem) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(TFCTECH, "metal/" + metalItem.getType().name()
          .toLowerCase()), "inventory"));
        if (metalItem.getType() == ItemTechMetal.ItemType.WIRE) {
          for (int i = 1; i <= 4; i++) {
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(TFCTECH, "metal/" + metalItem.getType().name()
              .toLowerCase()), "inventory"));
          }

        }
      } else if (item instanceof ItemBlowpipe) {
        final ModelResourceLocation EMPTY = new ModelResourceLocation(new ResourceLocation(TFCTECH, "metal/blowpipe_empty"), "inventory");
        final ModelResourceLocation FILLED = new ModelResourceLocation(new ResourceLocation(TFCTECH, "metal/blowpipe_filled"), "inventory");
        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
          @Override
          @Nonnull
          public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
            IFluidHandlerItem cap = stack.getCapability(FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (cap instanceof ItemGlassMolder.GlassMolderCapability) {
              FluidStack fluid = ((ItemGlassMolder.GlassMolderCapability) cap).getFluid();
              if (fluid != null) {
                return FILLED;
              }
            }
            return EMPTY;
          }
        });
        ModelBakery.registerItemVariants(item, EMPTY, FILLED);
      }
    }

    // Molds
    for (Item item : TechItems.getAllCeramicMoldItems()) {
      final ModelResourceLocation EMPTY = new ModelResourceLocation(new ResourceLocation(item.getRegistryName().toString() + "_empty"), "inventory");
      final ModelResourceLocation FILLED = new ModelResourceLocation(new ResourceLocation(item.getRegistryName().toString()), "inventory");
      ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
        @Override
        @Nonnull
        public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
          IFluidHandlerItem cap = stack.getCapability(FLUID_HANDLER_ITEM_CAPABILITY, null);
          IFluidHandler moldCap = stack.getCapability(FLUID_HANDLER_CAPABILITY, null);
          if (cap instanceof ItemGlassMolder.GlassMolderCapability) {
            FluidStack fluid = ((ItemGlassMolder.GlassMolderCapability) cap).getFluid();
            if (fluid != null) {
              return FILLED;
            }
          } else if (moldCap instanceof IMoldHandler) {
            Metal metal = ((IMoldHandler) moldCap).getMetal();
            if (metal != null) {
              return FILLED;
            }
          }
          return EMPTY;
        }
      });
      ModelBakery.registerItemVariants(item, EMPTY, FILLED);
    }

    // Ignored states
    ModelLoader.setCustomStateMapper(TechBlocks.WIRE_DRAW_BENCH, new IStateMapper() {
      @Override
      @Nonnull
      public Map<IBlockState, ModelResourceLocation> putStateModelLocations(@Nonnull Block blockIn) {
        return Collections.emptyMap();
      }
    });
    ModelLoader.setCustomStateMapper(TechBlocks.FRIDGE, new IStateMapper() {
      @Override
      @Nonnull
      public Map<IBlockState, ModelResourceLocation> putStateModelLocations(@Nonnull Block blockIn) {
        return Collections.emptyMap();
      }
    });

    // TESRs //

    ClientRegistry.bindTileEntitySpecialRenderer(TELatexExtractor.class, new TESRLatexExtractor());
    ClientRegistry.bindTileEntitySpecialRenderer(TEWireDrawBench.class, new TESRWireDrawBench());
    ClientRegistry.bindTileEntitySpecialRenderer(TEFridge.class, new TESRFridge());
  }

  @SubscribeEvent
  public static void registerItemColourHandlers(final ColorHandlerEvent.Item event) {
    final ItemColors itemColors = event.getItemColors();

    for (Item item : TechItems.getAllMetalItems()) {
      itemColors.registerItemColorHandler(
        (stack, tintIndex) -> {
          if (tintIndex == 1 && stack.getItem() instanceof ItemGear) {
            return (new Color(((ItemGear) stack.getItem()).getSleeveMetal().getColor())).brighter().getRGB();
          } else if (tintIndex == 1 && stack.getItem() instanceof ItemBlowpipe) {
            IFluidHandlerItem cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (cap instanceof ItemGlassMolder.GlassMolderCapability) {
              FluidStack fluid = ((ItemGlassMolder.GlassMolderCapability) cap).getFluid();
              if (fluid != null) {
                return fluid.getFluid().getColor();
              }
            }
            return -1;
          }
          //noinspection ConstantConditions
          return (new Color(((ICapabilityMetal) stack.getItem()).getMetal(stack).getColor())).brighter().getRGB();
        },
        item);
    }

    for (Item item : TechItems.getAllCeramicMoldItems()) {
      itemColors.registerItemColorHandler(
        (stack, tintIndex) -> {
          if (tintIndex == 1) {
            IFluidHandler capFluidHandler = stack.getCapability(FLUID_HANDLER_CAPABILITY, null);
            if (capFluidHandler instanceof IMoldHandler) {
              Metal metal = ((IMoldHandler) capFluidHandler).getMetal();
              if (metal != null) {
                return (new Color(metal.getColor())).brighter().getRGB();
              }
            } else if (stack.getItem() instanceof ItemGlassMolder) {
              IFluidHandlerItem cap = stack.getCapability(FLUID_HANDLER_ITEM_CAPABILITY, null);
              if (cap instanceof ItemGlassMolder.GlassMolderCapability && ((ItemGlassMolder.GlassMolderCapability) cap).getFluid() != null) {
                FluidStack fluidStack = ((ItemGlassMolder.GlassMolderCapability) cap).getFluid();
                //noinspection ConstantConditions
                return fluidStack.getFluid().getColor();
              }
            }
            return 0xFF000000;
          }
          return -1;
        },
        item);
    }
  }
}
