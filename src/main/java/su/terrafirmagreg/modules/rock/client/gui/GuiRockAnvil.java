package su.terrafirmagreg.modules.rock.client.gui;

import su.terrafirmagreg.api.base.gui.BaseGuiContainerTile;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.data.lib.NBTBuilder;
import su.terrafirmagreg.modules.core.network.CSPacketGuiButton;
import su.terrafirmagreg.modules.metal.ModuleMetal;
import su.terrafirmagreg.modules.metal.objects.recipe.anvil.AnvilRecipeManager;
import su.terrafirmagreg.modules.metal.objects.recipe.anvil.IAnvilRecipe;
import su.terrafirmagreg.modules.rock.object.tile.TileRockAnvil;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.client.button.GuiButtonAnvilPlanIcon;
import net.dries007.tfc.client.button.GuiButtonPage;
import net.dries007.tfc.client.button.IButtonTooltip;

import java.io.IOException;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiRockAnvil
  extends BaseGuiContainerTile<TileRockAnvil> {

  public static final ResourceLocation BACKGROUND = ModUtils.resource("textures/gui/anvil_plan.png");

  private final ItemStack inputStack;
  private int page;
  private GuiButton buttonLeft, buttonRight;

  public GuiRockAnvil(Container container, InventoryPlayer playerInv, TileRockAnvil tile) {
    super(container, playerInv, tile, BACKGROUND);

    IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    this.inputStack =
      cap == null ? ItemStack.EMPTY : cap.getStackInSlot(TileRockAnvil.SLOT_INPUT_1);
  }

  @Override
  public void initGui() {
    super.initGui();
    page = 0;
    updatePage();
  }

  @Override
  protected void renderHoveredToolTip(int mouseX, int mouseY) {
    // Button Tooltips
    for (GuiButton button : buttonList) {
      if (button instanceof IButtonTooltip tooltip && button.isMouseOver()) {
        if (tooltip.hasTooltip()) {
          drawHoveringText(I18n.format(tooltip.getTooltip()), mouseX, mouseY);
        }
      }
    }
    super.renderHoveredToolTip(mouseX, mouseY);
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    String name = I18n.format("tfc.tooltip.anvil_plan") + ": " + I18n.format(
      inputStack.getTranslationKey() + ".name");
    fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);

    super.drawGuiContainerForegroundLayer(mouseX, mouseY);
  }

  private void updatePage() {
    buttonList.clear();
    int buttonID = -1;
    List<IAnvilRecipe> recipeList = AnvilRecipeManager.getAllFor(inputStack);
    for (int i = page * 18; i < (page + 1) * 18 && i < recipeList.size(); i++) {
      int posX = 7 + (i % 9) * 18;
      int posY = 25 + ((i % 18) / 9) * 18;
      addButton(
        new GuiButtonAnvilPlanIcon(recipeList.get(i), ++buttonID, guiLeft + posX, guiTop + posY));
    }
    buttonLeft = addButton(
      new GuiButtonPage(++buttonID, guiLeft + 7, guiTop + 65, GuiButtonPage.Type.LEFT,
                        "tfc.tooltip.previous_page"));

    buttonRight = addButton(
      new GuiButtonPage(++buttonID, guiLeft + 154, guiTop + 65, GuiButtonPage.Type.RIGHT,
                        "tfc.tooltip.next_page"));

    if (recipeList.size() <= 18) {
      buttonLeft.visible = false;
      buttonRight.visible = false;
    } else {
      if (page <= 0) {
        buttonLeft.enabled = false;
      }
      if ((page + 1) * 18 >= recipeList.size()) {
        buttonRight.enabled = false;
      }
    }
  }

  @Override
  protected void actionPerformed(GuiButton button) throws IOException {
    if (button instanceof GuiButtonAnvilPlanIcon) {
      // This fires when you select a plan in the Plan GUI
      ResourceLocation recipeName = ((GuiButtonAnvilPlanIcon) button).getRecipeName();
      ModuleMetal.PACKET_SERVICE.sendToServer(new CSPacketGuiButton(button.id,
                                                                    new NBTBuilder().setString("recipe", recipeName.toString())
                                                                                    .build()));
    } else if (button == buttonLeft) {
      page--;
      updatePage();
    } else if (button == buttonRight) {
      page++;
      updatePage();
    }
    super.actionPerformed(button);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    GlStateManager.color(1, 1, 1, 1);
    mc.getTextureManager().bindTexture(background);
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
  }
}
