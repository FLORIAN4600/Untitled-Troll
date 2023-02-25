package fr.florian4600.untitledtroll.mixins;

import fr.florian4600.untitledtroll.block.LookableBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "tick()V")
    public void tick(CallbackInfo info) {
        HitResult raycast = this.raycast(50d, 1.0f, false);

        if(raycast.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) raycast;
            BlockState hitState = this.world.getBlockState(blockHitResult.getBlockPos());
            if(hitState.getBlock() instanceof LookableBlock block && !info.isCancelled()) {
                block.onLookedAt(this.world, hitState, blockHitResult, this);
            }
        }
    }
}
