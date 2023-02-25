package fr.florian4600.untitledtroll.item;

import fr.florian4600.untitledtroll.utils.UTExplosionUtils;
import fr.florian4600.untitledtroll.utils.YioriteOreUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

public class YioriteIngotItem extends SwordItem {

    public YioriteIngotItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        if(world.isClient()) return;
        YioriteOreUtils.sendText(player, "explosion", stack.getName());
        UTExplosionUtils.explodeOnEntity(world, player, 0.9f, World.ExplosionSourceType.NONE);
        stack.setCount(0);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(world.isClient() || (entity.isPlayer() && ((PlayerEntity) entity).isCreative())) return;
        UTExplosionUtils.explodeBeneathEntity(world, entity, 0.87773d, 2.1f, World.ExplosionSourceType.NONE);
        stack.setCount(0);
    }
}
