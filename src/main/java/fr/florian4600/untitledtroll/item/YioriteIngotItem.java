package fr.florian4600.untitledtroll.item;

import fr.florian4600.untitledtroll.MainClass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class YioriteIngotItem extends Item {
    public YioriteIngotItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        if(world.isClient()) return;
        player.sendMessage(Text.of("[§a" + stack.getName() + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite.explosion")), false);
        world.createExplosion(player, player.getX(), player.getY(), player.getZ(), 1.9f, World.ExplosionSourceType.BLOCK);
        stack.setCount(0);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(world.isClient() || (entity.isPlayer() && ((PlayerEntity) entity).isCreative())) return;
        world.createExplosion(entity, entity.getX(), entity.getY(), entity.getZ(), 2.1f, World.ExplosionSourceType.BLOCK);
        stack.setCount(0);
    }
}
