package fr.florian4600.untitledtroll.block;

import fr.florian4600.untitledtroll.block.entity.UTBlockEntityTypes;
import fr.florian4600.untitledtroll.block.entity.YioriteOreBlockEntity;
import fr.florian4600.untitledtroll.state.propery.UTProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class YioriteOreBlock extends LookableBlock {

    public static final BooleanProperty LIT;
    public static final BooleanProperty LOOKED;
    public static final IntProperty LIGHT_LEVEL;
    public static final IntProperty MUD_LEVEL;

    public YioriteOreBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false));
        this.setDefaultState(this.getDefaultState().with(LOOKED, false));
        this.setDefaultState(this.getDefaultState().with(MUD_LEVEL, 0));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new YioriteOreBlockEntity(pos, state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if(world.isClient()) return;
        world.setBlockState(pos, state.with(MUD_LEVEL, (itemStack.getNbt() == null || !itemStack.getNbt().contains("MudLevel")) ? 0 : itemStack.getNbt().getInt("MudLevel")));
        if (world.getBlockEntity(pos) instanceof YioriteOreBlockEntity blockEntity) {
            blockEntity.setCustomName(itemStack.getName());
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient && world.getBlockEntity(pos) instanceof YioriteOreBlockEntity blockEntity && !(player.isCreative() && blockEntity.isEmpty())) {
            ItemStack itemStack = new ItemStack(state.getBlock());
            if(itemStack.getNbt() == null) {
                itemStack.setNbt(new NbtCompound());
            }
            blockEntity.setStackNbt(itemStack);
            itemStack.setCustomName(blockEntity.getCustomName());
            itemStack.getNbt().putInt("MudLevel", state.get(MUD_LEVEL));
            ItemEntity itemEntity = new ItemEntity(world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, itemStack);
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public void onLookedAt(World world, BlockState state, BlockHitResult hitResult, Entity entity) {
        if(world.isClient || !entity.isPlayer() || ((PlayerEntity) entity).isCreative()) return;
        if(world.getBlockEntity(hitResult.getBlockPos()) instanceof YioriteOreBlockEntity blockEntity) {
            blockEntity.onLookedAt(world, state, hitResult, (PlayerEntity) entity);
        }
    }

    public static void light(BlockState state, World world, BlockPos pos) {
        if (!state.get(LIT)) {
            world.setBlockState(pos, state.with(LIT, true), 3);
        }

    }

    public static void setLightLevel(BlockState state, World world, BlockPos pos, int level) {
        if(state.get(LIGHT_LEVEL) != level) {
            world.setBlockState(pos, state.with(LIGHT_LEVEL, level));
        }
    }

    public static void setLooked(BlockState state, World world, BlockPos pos, boolean isLooked) {
        if(state.get(LOOKED) != isLooked) {
            world.setBlockState(pos, state.with(LOOKED, isLooked));
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(LIT) && !state.get(LOOKED);
    }

    @SuppressWarnings("deprecated")
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            world.setBlockState(pos, state.with(LIT, false), 3);
        }
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, UTBlockEntityTypes.YIORITE_ORE_ENTITY_TYPE, (world.isClient ? YioriteOreBlockEntity::clientTick : YioriteOreBlockEntity::serverTick));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, LOOKED, LIGHT_LEVEL, MUD_LEVEL);
    }

    static {
        LIT = RedstoneTorchBlock.LIT;
        LOOKED = UTProperties.LOOKED;
        LIGHT_LEVEL = UTProperties.LIGHT_LEVEL;
        MUD_LEVEL = UTProperties.MUD_LEVEL;
    }
}
