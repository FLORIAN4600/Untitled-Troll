package fr.florian4600.untitledtroll.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class TrappedYioriteOreBlockEntity extends BlockEntity {

    private String customName;

    public TrappedYioriteOreBlockEntity(BlockPos pos, BlockState state) {
        super(UTBlockEntityTypes.TRAPPED_YIORITE_ORE_ENTITY_TYPE, pos, state);
        this.customName = state.getBlock().getName().getString();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("CustomName", 8)) {
            this.customName = nbt.getString("CustomName");
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("CustomName", this.customName);
    }
}
