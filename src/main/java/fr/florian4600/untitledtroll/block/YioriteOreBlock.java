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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class YioriteOreBlock extends LookableBlock {

    public static final BooleanProperty LIT;
    public static final BooleanProperty LOOKED;
    public static final IntProperty LIGHT_LEVEL;
    private static final float PARTICLE_SIZE = 1.0f;
    private static final Vector3f PARTICLE_COLOR = new Vector3f(0.1f, 1f, 0.05f);

    public YioriteOreBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false));
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
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient && world.getBlockEntity(pos) instanceof YioriteOreBlockEntity blockEntity && !(player.isCreative() && blockEntity.isEmpty())) {
            ItemStack itemStack = new ItemStack(UTBlocks.YIORITE_ORE);
            blockEntity.setStackNbt(itemStack);
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

    public static void blockParticle(World world, BlockPos pos, int particleType) {
        switch (particleType) {
            case 1 -> spawnParticles(world, pos, new Vector3f(0.1f, 1f, 0.05f));
            case 2 -> spawnParticles(world, pos, new Vector3f(0.12f, 0.12f, 0.12f), 0.4f);
            default -> spawnParticles(world, pos);
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

    private static void spawnParticles(World world, BlockPos pos, Vector3f color, Float size) {
        double d = 0.5625;
        Random random = world.random;
        Direction[] directions = Direction.values();

        for (Direction direction : directions) {
            System.out.println("check");
            BlockPos blockPos = pos.offset(direction);
            if (!world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) {
                System.out.println("particles");
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5 + d * (double) direction.getOffsetX() : (double) random.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5 + d * (double) direction.getOffsetY() : (double) random.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5 + d * (double) direction.getOffsetZ() : (double) random.nextFloat();
                world.addParticle(new DustParticleEffect(color, size), (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, 0.0, 0.0, 0.0);
            }
        }

    }

    private static void spawnParticles(World world, BlockPos pos, Vector3f color) {
        spawnParticles(world, pos, color, PARTICLE_SIZE);
    }

    private static void spawnParticles(World world, BlockPos pos, Float size) {
        spawnParticles(world, pos, PARTICLE_COLOR, size);
    }

    private static void spawnParticles(World world, BlockPos pos) {
        spawnParticles(world, pos, PARTICLE_COLOR, PARTICLE_SIZE);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, LOOKED, LIGHT_LEVEL);
    }

    static {
        LIT = RedstoneTorchBlock.LIT;
        LOOKED = UTProperties.LOOKED;
        LIGHT_LEVEL = UTProperties.LIGHT_LEVEL;
    }
}
