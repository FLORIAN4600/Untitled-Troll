package fr.florian4600.untitledtroll.block;

import fr.florian4600.untitledtroll.block.entity.TrappedYioriteOreBlockEntity;
import fr.florian4600.untitledtroll.block.entity.UTBlockEntityTypes;
import fr.florian4600.untitledtroll.stat.UTStats;
import fr.florian4600.untitledtroll.state.propery.UTProperties;
import fr.florian4600.untitledtroll.utils.YioriteOreUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TrappedYioriteBlock extends LookableBlock {

    public static final VoxelShape SHAPE;
    public static final VoxelShape BOTTOM_SHAPE;
    public static final VoxelShape TOP_SHAPE;
    public static final VoxelShape BARS_SHAPE;
    public static final VoxelShape OUTLINE_SHAPE;

    public static final IntProperty MUD_LEVEL;

    public TrappedYioriteBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TrappedYioriteOreBlockEntity(pos, state);
    }

    @SuppressWarnings("deprecated")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient) return ActionResult.SUCCESS;
        if (world.getBlockEntity(pos) instanceof TrappedYioriteOreBlockEntity blockEntity) {
            player.openHandledScreen(blockEntity);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @SuppressWarnings("deprecated")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecated")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if(world.isClient()) return;
        world.setBlockState(pos, state.with(MUD_LEVEL, (itemStack.getNbt() == null || !itemStack.getNbt().contains("MudLevel")) ? 0 : itemStack.getNbt().getInt("MudLevel")));
        if (world.getBlockEntity(pos) instanceof TrappedYioriteOreBlockEntity blockEntity) {
            blockEntity.setCustomName(itemStack.getName());
            if(placer == null || !placer.isPlayer()) return;
            YioriteOreUtils.sendTrappedText((PlayerEntity) placer, "placed", blockEntity.getCustomName());
        }
    }

    @Override
    public void onLookedAt(World world, BlockState state, BlockHitResult hitResult, Entity entity) {
        if(world.isClient || !entity.isPlayer()) return;
        PlayerEntity player = (PlayerEntity) entity;
        if(player.isCreative() || player.isSpectator()) return;
        player.incrementStat(UTStats.YIORITE_LOOK_TIME);
        if(world.getBlockEntity(hitResult.getBlockPos()) instanceof TrappedYioriteOreBlockEntity blockEntity) {
            blockEntity.setMaxLookingDistance(hitResult.getBlockPos().getManhattanDistance(entity.getBlockPos())*1.33f/(state.contains(UTProperties.MUD_LEVEL) ? state.get(UTProperties.MUD_LEVEL) : 1));
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, UTBlockEntityTypes.TRAPPED_YIORITE_ORE_ENTITY_TYPE, TrappedYioriteOreBlockEntity::tick);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient && world.getBlockEntity(pos) instanceof TrappedYioriteOreBlockEntity blockEntity && !(player.isCreative() && blockEntity.isEmpty())) {
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

    @SuppressWarnings("deprecated")
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecated")
    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity == null) return 0;
        return ((TrappedYioriteOreBlockEntity) blockEntity).getComparatorOutput((TrappedYioriteOreBlockEntity) blockEntity);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MUD_LEVEL);
    }

    static {
        SHAPE = VoxelShapes.cuboid(0.0625d, 0d, 0.0625d, 0.9375d, 0.875d, 0.9375d);
        BOTTOM_SHAPE = VoxelShapes.cuboid(0d, 0d, 0d, 1d, 0.0625d, 1d);
        TOP_SHAPE = VoxelShapes.cuboid(0.0625d, 0.8125d, 0.0625d, 0.9375d, 0.875d, 0.9375d);
        BARS_SHAPE = VoxelShapes.cuboid(0.125d, 0.0625d, 0.125d, 0.875d, 0.8125d, 0.875d);
        OUTLINE_SHAPE = VoxelShapes.union(BOTTOM_SHAPE, BARS_SHAPE, TOP_SHAPE);

        MUD_LEVEL = UTProperties.MUD_LEVEL;
    }
}
