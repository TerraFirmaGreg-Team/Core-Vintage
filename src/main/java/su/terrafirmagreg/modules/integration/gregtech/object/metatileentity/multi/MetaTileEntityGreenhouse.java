package su.terrafirmagreg.modules.integration.gregtech.object.metatileentity.multi;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static gregtech.api.unification.material.Materials.Steel;

public class MetaTileEntityGreenhouse extends RecipeMapMultiblockController {

  public static final RecipeMap<SimpleRecipeBuilder> GREENHOUSE_RECIPES = new RecipeMap<>("greenhouse", 4, 4, 1, 1, new SimpleRecipeBuilder(), false).setSound(GTSoundEvents.COOLING);

  protected static final List<IBlockState> GRASSES = new ArrayList<>();


  public MetaTileEntityGreenhouse(ResourceLocation metaTileEntityId) {
    super(metaTileEntityId, GREENHOUSE_RECIPES);
    this.recipeMapWorkable = new GreenhouseWorkable(this);
  }

  public static void addGrasses() {

//    OreDictUnifier.getAllWithOreDictionaryName()
    TFCRegistries.ROCKS.forEach(rock -> {
      GRASSES.add(BlockRockVariant.get(rock, Rock.Type.GRASS).getDefaultState());
      GRASSES.add(BlockRockVariant.get(rock, Rock.Type.DIRT).getDefaultState());
    });

  }

  @Override
  protected BlockPattern createStructurePattern() {
    return FactoryBlockPattern.start()
      .aisle("CCCCCCC", "XXXFXXX", "XXXFXXX", "XXXFXXX", "XXXFXXX", "XXXFXXX", "XXXFXXX", "XXXFXXX", "   F   ")
      .aisle("CDDDDDC", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", " XXFXX ")
      .aisle("CDDDDDC", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", " XXFXX ")
      .aisle("CDDDDDC", "F#####F", "F#####F", "F#####F", "F#####F", "F#####F", "F#####F", "F#####F", "FFFFFFF")
      .aisle("CDDDDDC", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", " XXFXX ")
      .aisle("CDDDDDC", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", "X#####X", " XXFXX ")
      .aisle("CCCYCCC", "XXXFXXX", "XXXFXXX", "XXXFXXX", "XXXFXXX", "XXXFXXX", "XXXFXXX", "XXXFXXX", "   F   ")
      .where('X', states(getCasingState()))
      .where('F', states(getFrameState()))
      .where('D', states(GRASSES.toArray(new IBlockState[0])))
      .where('C', states(getCasingState2()).setMinGlobalLimited(10).or(autoAbilities()))
      .where('#', air())
      .where(' ', any())
      .where('Y', selfPredicate())
      .build();
  }

  protected IBlockState getCasingState() {
    return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
  }

  protected IBlockState getCasingState2() {
    return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
  }

  protected IBlockState getFrameState() {
    return MetaBlocks.FRAMES.get(Steel).getBlock(Steel);
  }


  @Override
  public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
    return Textures.SOLID_STEEL_CASING;
  }

  @Override
  public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
    return new MetaTileEntityGreenhouse(metaTileEntityId);
  }

  public boolean checkNaturalLighting() {
    if (!this.getWorld().isDaytime()) {return false;}
    for (BlockPos pos : BlockPos.getAllInBox(this.getPos().up(8).offset(this.frontFacing.rotateY(), 3),
      this.getPos().up(8).offset(this.getFrontFacing().rotateYCCW(), 3)
        .offset(this.getFrontFacing().getOpposite(), 6))) {
      if (!this.getWorld().canSeeSky(pos.up())) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
    super.addInformation(stack, player, tooltip, advanced);
    tooltip.add(I18n.format("tfg.machine.greenhouse.tooltip.1"));
    tooltip.add(I18n.format("tfg.machine.greenhouse.tooltip.2"));
    tooltip.add(I18n.format("tfg.machine.greenhouse.tooltip.3"));
  }

  @Override
  protected void addWarningText(List<ITextComponent> textList) {
    super.addWarningText(textList);
    if (this.isStructureFormed()) {
      if (!((GreenhouseWorkable) this.recipeMapWorkable).hasSun()) {
        textList.add(new TextComponentTranslation("gregtech.multiblock.not_enough_sun")
          .setStyle(new Style().setColor(TextFormatting.RED)));
      }
    }
  }

  @Override
  protected ModularUI createUI(EntityPlayer entityPlayer) {
    ((GreenhouseWorkable) this.recipeMapWorkable).hasSun = this.checkNaturalLighting();
    return super.createUI(entityPlayer);
  }

  @Override
  public boolean canBeDistinct() {
    return true;
  }

  @Override
  public boolean isMultiblockPartWeatherResistant(@NotNull IMultiblockPart part) {
    return true;
  }

  @Override
  public boolean getIsWeatherOrTerrainResistant() {
    return true;
  }

  public static class GreenhouseWorkable extends MultiblockRecipeLogic {

    private boolean hasSun;

    public GreenhouseWorkable(RecipeMapMultiblockController tileEntity) {
      super(tileEntity);
    }

    @Override
    protected void setupRecipe(Recipe recipe) {
      super.setupRecipe(recipe);
      this.hasSun = ((MetaTileEntityGreenhouse) metaTileEntity).checkNaturalLighting();
    }

    public boolean hasSun() {
      return hasSun;
    }

    @Override
    protected void updateRecipeProgress() {
      if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
        this.drawEnergy(this.recipeEUt, false);
        if (this.hasSun) {this.progressTime++;} else {this.progressTime += (int) (Math.random() * 2);}

        if (this.progressTime > this.maxProgressTime) {
          this.completeRecipe();
        }

        if (this.hasNotEnoughEnergy && this.getEnergyInputPerSecond() > 19L * (long) this.recipeEUt) {
          this.hasNotEnoughEnergy = false;
        }
      } else if (this.recipeEUt > 0) {
        this.hasNotEnoughEnergy = true;
        if (this.progressTime >= 2) {
          if (ConfigHolder.machines.recipeProgressLowEnergy) {
            this.progressTime = 1;
          } else {
            this.progressTime = Math.max(1, this.progressTime - 2);
          }
        }
      }
    }

    @Override
    public NBTTagCompound serializeNBT() {
      NBTTagCompound tag = super.serializeNBT();
      tag.setBoolean("hasSun", hasSun);
      return tag;
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagCompound compound) {
      super.deserializeNBT(compound);
      this.hasSun = compound.getBoolean("hasSun");
    }
  }
}
