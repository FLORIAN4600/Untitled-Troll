package fr.florian4600.untitledtroll.block;

import fr.florian4600.untitledtroll.block.entity.TrappedYioriteOreBlockEntity;
import fr.florian4600.untitledtroll.utils.YioriteOreUtils;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TrappedYioriteBlock extends BlockWithEntity {

    public static final VoxelShape SHAPE;
    public static final VoxelShape BOTTOM_SHAPE;
    public static final VoxelShape TOP_SHAPE;
    public static final VoxelShape BARS_SHAPE;
    public static final VoxelShape OUTLINE_SHAPE;

    public TrappedYioriteBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TrappedYioriteOreBlockEntity(pos, state);
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
        if(world.isClient || placer == null || !placer.isPlayer() && world.getBlockEntity(pos) == null) return;
        YioriteOreUtils.sendText((PlayerEntity) placer, "trapped.placed", ((TrappedYioriteOreBlockEntity) world.getBlockEntity(pos)).getCustomName());
    }

    static {
        SHAPE = VoxelShapes.cuboid(0.0625d, 0d, 0.0625d, 0.9375d, 0.875d, 0.9375d);
        BOTTOM_SHAPE = VoxelShapes.cuboid(0d, 0d, 0d, 1d, 0.0625d, 1d);
        TOP_SHAPE = VoxelShapes.cuboid(0.0625d, 0.8125d, 0.0625d, 0.9375d, 0.875d, 0.9375d);
        BARS_SHAPE = VoxelShapes.cuboid(0.125d, 0.0625d, 0.125d, 0.875d, 0.8125d, 0.875d);
        OUTLINE_SHAPE = VoxelShapes.union(BOTTOM_SHAPE, BARS_SHAPE, TOP_SHAPE);
    }
}
