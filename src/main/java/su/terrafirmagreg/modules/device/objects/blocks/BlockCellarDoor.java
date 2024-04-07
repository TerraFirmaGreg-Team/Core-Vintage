package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BlockDoorBase;

import net.minecraft.block.material.Material;

import org.jetbrains.annotations.NotNull;

public class BlockCellarDoor extends BlockDoorBase {

    public BlockCellarDoor() {
        super(Material.WOOD);

        setHardness(2F);
    }

    @Override
    public @NotNull String getName() {
        return "device/cellar/door";
    }
}
