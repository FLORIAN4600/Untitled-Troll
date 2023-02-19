package fr.florian4600.untitledtroll.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public abstract class LookableBlock extends BlockWithEntity {

    protected LookableBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    public void onLookedAt(World world, BlockState state, BlockHitResult hitResult, Entity entity) {}



}
