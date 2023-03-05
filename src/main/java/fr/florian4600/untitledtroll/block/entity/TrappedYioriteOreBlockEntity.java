package fr.florian4600.untitledtroll.block.entity;

import fr.florian4600.untitledtroll.MainClass;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TrappedYioriteOreBlockEntity extends LootableContainerBlockEntity {

    private DefaultedList<ItemStack> inventory;
    public float maxLookingDistance;

    public TrappedYioriteOreBlockEntity(BlockPos pos, BlockState state) {
        super(UTBlockEntityTypes.TRAPPED_YIORITE_ORE_ENTITY_TYPE, pos, state);
        this.inventory = DefaultedList.ofSize(45, ItemStack.EMPTY);
        this.maxLookingDistance = -1f;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    public static void tick(World world, BlockPos pos, BlockState state, TrappedYioriteOreBlockEntity blockEntity) {
        blockEntity.setMaxLookingDistance(-1f);
    }

    public float getMaxLookingDistance() {
        return maxLookingDistance;
    }

    public void setMaxLookingDistance(float maxLookingDistance) {
        this.maxLookingDistance = maxLookingDistance;
    }

    public int getComparatorOutput(TrappedYioriteOreBlockEntity blockEntity) {
        return Math.round((Math.min(blockEntity.getMaxLookingDistance(), 32f)-32f)*0.4375f);
    }

    @Override
    protected Text getContainerName() {
        return MainClass.getTranslation("container", "trapped_yiorite");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X5, syncId, playerInventory, this, 5);
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    public int size() {
        return 48;
    }

}
