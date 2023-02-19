package fr.florian4600.untitledtroll.mixins;

import fr.florian4600.untitledtroll.item.YioriteOreItemBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput {

    public EntityMixin(EntityType<?> type, World world) {

    }

    @ModifyVariable(at = @At("HEAD"), method = "dropStack(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/ItemEntity;", ordinal = 0)
    public ItemStack dropStack(ItemStack itemStack, ItemStack stack, float yOffset) {
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
