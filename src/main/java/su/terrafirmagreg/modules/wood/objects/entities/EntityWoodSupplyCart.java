package su.terrafirmagreg.modules.wood.objects.entities;

import su.terrafirmagreg.api.network.datasync.DataSerializers;
import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.wood.ConfigWood;
import su.terrafirmagreg.modules.wood.client.gui.GuiWoodSupplyCart;
import su.terrafirmagreg.modules.wood.init.ItemsWood;
import su.terrafirmagreg.modules.wood.objects.containers.ContainerWoodSupplyCart;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

public class EntityWoodSupplyCart extends EntityWoodCartInventory
        implements IInventoryChangedListener, IProviderContainer<ContainerWoodSupplyCart, GuiWoodSupplyCart> {

    private static final DataParameter<Integer> CARGO = EntityDataManager.createKey(EntityWoodSupplyCart.class, DataSerializers.VARINT);

    public EntityWoodSupplyCart(World worldIn) {
        super(worldIn);
        this.setSize(1.5F, 1.4F);
        this.spacing = 2.4D;
        this.initInventory(this.getName(), true, 54);
        this.inventory.addInventoryChangeListener(this);
    }

    @Override
    public boolean canBePulledBy(Entity pullingIn) {
        if (this.isPassenger(pullingIn)) {
            return false;
        }
        for (String entry : ConfigWood.ITEMS.SUPPLY_CART.canPull) {
            if (entry.equals(pullingIn instanceof EntityPlayer ? "minecraft:player" : EntityList.getKey(pullingIn).toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Item getItemCart() {
        var type = getWood();
        if (type != null) {
            return ItemsWood.SUPPLY_CART.get(type);
        }
        return getItemCart();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(CARGO, 0);
    }

    @Override
    public boolean processInitialInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
        if (!this.world.isRemote) {
            if (player.isSneaking()) {
                GuiHandler.openGui(world, new BlockPos(this.getEntityId(), 0, 0), player);
            } else if (this.pulling != player) {
                player.startRiding(this);
            }

        }

        return true;
    }

    @Override
    public void updatePassenger(@NotNull Entity passenger) {
        if (this.isPassenger(passenger)) {
            Vec3d vec3d = (new Vec3d(-0.68D, 0.0D, 0.0D)).rotateYaw(-this.rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
            passenger.setPosition(this.posX + vec3d.x, this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ + vec3d.z);
        }
    }

    @Override
    public double getMountedYOffset() {
        return 0.62D;
    }

    public int getCargo() {
        return this.dataManager.get(CARGO);
    }

    @Override
    public void onInventoryChanged(@NotNull IInventory invBasic) {
        if (!this.world.isRemote) {
            int tempload = 0;
            for (int i = 0; i < this.inventory.getSizeInventory(); i++) {
                if (!this.inventory.getStackInSlot(i).isEmpty()) {
                    tempload++;
                }
            }
            int newValue;
            if (tempload > 31)
                newValue = 4;
            else if (tempload > 16)
                newValue = 3;
            else if (tempload > 8)
                newValue = 2;
            else if (tempload > 3)
                newValue = 1;
            else
                newValue = 0;
            if (this.dataManager.get(CARGO) != newValue) {
                this.dataManager.set(CARGO, newValue);
            }
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        this.dataManager.set(CARGO, nbt.getInteger("cargo"));
    }

    @Override
    protected void writeEntityToNBT(@NotNull NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);

        NBTUtils.setGenericNBTValue(nbt, "cargo", dataManager.get(CARGO));
    }

    @Override
    public ContainerWoodSupplyCart getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new ContainerWoodSupplyCart(inventoryPlayer, inventory, this, inventoryPlayer.player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiWoodSupplyCart getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new GuiWoodSupplyCart(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, inventory);
    }

}
