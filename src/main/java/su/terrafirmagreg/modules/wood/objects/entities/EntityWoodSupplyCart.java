package su.terrafirmagreg.modules.wood.objects.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.modules.wood.ModuleWoodConfig;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariants;
import su.terrafirmagreg.modules.wood.data.ItemsWood;

public class EntityWoodSupplyCart extends EntityWoodCartInventory implements IInventoryChangedListener {

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
		for (String entry : ModuleWoodConfig.SUPPLY_CART.canPull) {
			if (entry.equals(pullingIn instanceof EntityPlayer ? "minecraft:player" : EntityList.getKey(pullingIn)
					.toString())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Item getItemCart() {
		var type = getWood();
		if (type != null) {
			return ItemsWood.getItem(WoodItemVariants.SUPPLY_CART, type);
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
				player.openGui(TerraFirmaGreg.getInstance(), 0, this.world, this.getEntityId(), 0, 0);
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
	protected void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		this.dataManager.set(CARGO, compound.getInteger("Cargo"));
	}

	@Override
	protected void writeEntityToNBT(@NotNull NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		compound.setInteger("Cargo", dataManager.get(CARGO));
	}

}
