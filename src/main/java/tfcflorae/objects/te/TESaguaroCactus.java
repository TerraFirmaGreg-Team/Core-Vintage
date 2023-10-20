package tfcflorae.objects.te;

import net.dries007.tfc.objects.te.TEBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;

public class TESaguaroCactus extends TEBase {
    private final boolean[] faces;
    private boolean horizontal;
    private EnumFacing facing;
    private boolean isCorrect = false;

    public TESaguaroCactus() {
        this.faces = new boolean[6];
        this.horizontal = false;
        this.facing = EnumFacing.NORTH;
    }

    /**
     * Checks if sheet is present for the given face
     *
     * @param face The face to check
     * @return true if present
     */
    public boolean getFace(EnumFacing face) {
        return faces[face.getIndex()];
    }

    public boolean getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean value) {
        if (!world.isRemote) this.horizontal = value;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public void setFacing(EnumFacing facing) {
        if (!world.isRemote) this.facing = facing;
    }

    public boolean isSet() {
        return isCorrect;
    }

    public void setFace(EnumFacing facing, boolean value) {
        if (!world.isRemote) {
            faces[facing.getIndex()] = value;
            markForBlockUpdate();
        }
    }

    public void set() {
        isCorrect = true;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        markForBlockUpdate();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        for (EnumFacing face : EnumFacing.values()) {
            faces[face.getIndex()] = nbt.getBoolean(face.getName());
        }
        horizontal = nbt.getBoolean("HORIZONTAL");
        facing = EnumFacing.byIndex(nbt.getInteger("facing"));
        isCorrect = nbt.getBoolean("CORRECT");

        super.readFromNBT(nbt);
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        for (EnumFacing face : EnumFacing.values()) {
            nbt.setBoolean(face.getName(), faces[face.getIndex()]);
        }
        nbt.setBoolean("HORIZONTAL", horizontal);
        nbt.setInteger("facing", facing.getIndex());
        nbt.setBoolean("CORRECT", isCorrect);

        return super.writeToNBT(nbt);
    }
}
