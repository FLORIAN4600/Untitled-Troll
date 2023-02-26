package fr.florian4600.untitledtroll.block.entity;

import fr.florian4600.untitledtroll.block.YioriteOreBlock;
import fr.florian4600.untitledtroll.utils.YioriteOreUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class YioriteOreBlockEntity extends BlockEntity {

    private DefaultedList<ItemStack> inventory;
    private String customName;
    private Boolean haveGhostLooking;
    private HashMap<String, Integer> lastTargets;
    private HashMap<String, Integer> targets;
    public int ticksSinceLooked;
    public boolean isLooked;

    public YioriteOreBlockEntity(BlockPos pos, BlockState state) {
        super(UTBlockEntityTypes.YIORITE_ORE_ENTITY_TYPE, pos, state);
        this.inventory = DefaultedList.ofSize(48, ItemStack.EMPTY);
        this.customName = state.getBlock().getName().getString();
        this.targets = new HashMap<>();
        this.lastTargets = new HashMap<>();
        this.haveGhostLooking = false;
    }

    public void onLookedAt(World world, BlockState state, BlockHitResult hitResult, PlayerEntity player) {

        if(player.isSpectator()) {
            this.haveGhostLooking = true;
            return;
        }

        String playerUuid = player.getUuidAsString();

        if(targets.get(playerUuid) != null) return;

        Integer nullStaringTicks = this.lastTargets.get(playerUuid);
        int lastStaringTicks = nullStaringTicks == null ? 0 : nullStaringTicks;

        this.targets.put(playerUuid, lastStaringTicks >= Integer.MAX_VALUE-100 ? 147 : lastStaringTicks+1);

    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        if (nbt.contains("CustomName", 8)) {
            this.customName = nbt.getString("CustomName");
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putString("CustomName", this.customName);
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, YioriteOreBlockEntity blockEntity) {

        if(blockEntity.ticksSinceLooked % 20 == 1) {
            YioriteOreBlock.blockParticle(world, pos, 1);
        }

        if(blockEntity.ticksSinceLooked % 10 == 1) {
            YioriteOreBlock.blockParticle(world, pos, 2);
        }

    }

    public static void serverTick(World world, BlockPos pos, BlockState state, YioriteOreBlockEntity blockEntity) {

        blockEntity.isLooked = !blockEntity.targets.isEmpty();

        if(blockEntity.isLooked) {

            for(Map.Entry<String, Integer> target : blockEntity.targets.entrySet()) {

                PlayerEntity player = world.getPlayerByUuid(UUID.fromString(target.getKey()));

                if(player == null) return;

                int lastStaringTicks = target.getValue();

                switch (lastStaringTicks) {
                    case 15 -> {
                        YioriteOreUtils.sendText(player, "warning" + (blockEntity.haveGhostLooking ? ".spectator" : ""), blockEntity.customName);
                    }
                    case 20 -> {
                        if (player.getInventory().armor.get(3).isOf(Blocks.CARVED_PUMPKIN.asItem())) player.sendMessage(Text.of("test pumpkin"), false);
                    }
                    default -> {
                        if (lastStaringTicks % 100 == 20) {

                            YioriteOreUtils.sendText(player, "stealing", blockEntity.customName);

                            if(blockEntity.canAcceptItems()) {

                                Random random = new Random();
                                boolean missed = true;

                                PlayerInventory playerInv = player.getInventory();

                                for (int a = 0; a < 6; a++) {
                                    int stolenNbr = random.nextInt(playerInv.size());
                                    ItemStack stolenItem = playerInv.getStack(stolenNbr);
                                    if (!stolenItem.isEmpty()) {
                                        int stackCount = stolenItem.getCount();
                                        int stolenCount = random.nextInt(Math.max(1, Math.round(stackCount * 0.66f))+1);
                                        for(int i = 0; i < blockEntity.inventory.size(); i++) {
                                            ItemStack storingSlot = blockEntity.inventory.get(i);
                                            if(storingSlot.isEmpty() || (storingSlot.getItem() == stolenItem.getItem() && Objects.equals(storingSlot.getNbt(), stolenItem.getNbt()) && storingSlot.getCount()+stolenCount <= 64)) {
                                                if(stolenCount != 0) missed = false;
                                                playerInv.setStack(stolenNbr, stolenItem.copyWithCount(stackCount - stolenCount));
                                                stolenItem.setCount(stolenCount+(storingSlot.isEmpty() ? 0 : storingSlot.getCount()));
                                                blockEntity.inventory.set(i, stolenItem);
                                                break;
                                            }
                                        }
                                        if (random.nextInt(10) >= 2) {
                                            break;
                                        }
                                    }
                                }

                                if (missed) {
                                    YioriteOreUtils.sendText(player, "missed", blockEntity.customName);
                                }

                            }else {
                                YioriteOreUtils.sendText(player, "full", blockEntity.customName);
                            }

                        }
                    }
                }
            }

            if(blockEntity.lastTargets.isEmpty()) blockEntity.markDirty();
            blockEntity.lastTargets = blockEntity.targets;
            blockEntity.targets = new HashMap<>();
        }else {
            if(!blockEntity.lastTargets.isEmpty()) blockEntity.lastTargets = new HashMap<>();
        }

        if(blockEntity.ticksSinceLooked % 20 == 1) {
            YioriteOreBlock.light(state, world, pos);
        }

        if(blockEntity.ticksSinceLooked % 10 == 1) {
            YioriteOreBlock.setLightLevel(state, world, pos, (int) Math.min(blockEntity.ticksSinceLooked*0.3f, 9.0f));
        }

        YioriteOreBlock.setLooked(state, world, pos, blockEntity.isLooked);

        blockEntity.ticksSinceLooked = blockEntity.isLooked ? blockEntity.ticksSinceLooked == Integer.MAX_VALUE ? 7 : blockEntity.ticksSinceLooked+1 : 0;
        blockEntity.haveGhostLooking = false;
    }

    public Boolean isEmpty() {
        return inventory.stream().allMatch(ItemStack::isEmpty);
    }

    public Boolean isFull() {
        return inventory.stream().noneMatch(ItemStack::isEmpty);
    }

    public Boolean canAcceptItems() {
        for(ItemStack item : inventory) {
            if(item.getCount() < 64) return true;
        }
        return false;
    }

}
