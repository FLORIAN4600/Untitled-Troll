package fr.florian4600.untitledtroll.mixins;

import com.mojang.authlib.GameProfile;
import fr.florian4600.untitledtroll.item.YioriteOreItemBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {


    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(at = @At("HEAD"), method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", ordinal = 0)
    public ItemStack dropItem(ItemStack itemStack, ItemStack stack, boolean throwRandomly, boolean retainOwnership) {
        if(itemStack != null && !itemStack.isEmpty() && itemStack.getItem() instanceof YioriteOreItemBlock) {
            NbtCompound nbt = itemStack.getNbt();
            if(nbt != null) {
                nbt.putString("lastUser", "");
                nbt.putInt("InventoryTicks", 0);
            }
        }
        return itemStack;
    }

}
