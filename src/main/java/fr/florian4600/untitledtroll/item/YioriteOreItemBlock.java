package fr.florian4600.untitledtroll.item;

import com.google.common.collect.Iterables;
import fr.florian4600.untitledtroll.MainClass;
import fr.florian4600.untitledtroll.entity.damage.UTItemDamageSource;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Random;

public class YioriteOreItemBlock extends BlockItem {

    public YioriteOreItemBlock(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if(world.isClient || entity.isInvulnerable()) return;

        if(stack.getNbt() == null) stack.setNbt(new NbtCompound());

        String lastInvUUID = stack.getNbt().getString("lastUser");
        int tickSincePickedUp = (stack.getNbt().contains("InventoryTicks") && lastInvUUID == entity.getUuidAsString()) ? stack.getNbt().getInt("InventoryTicks") : 0;

        if(entity.isPlayer() && !((PlayerEntity) entity).isCreative()) {
            PlayerEntity player = (PlayerEntity) entity;
            if (tickSincePickedUp >= 200) {
                player.sendMessage(Text.of("[§a" + stack.getName().getString() + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite.item")), false);

                Random random = new Random();
                int randomInt = random.nextInt(5);

                if(randomInt == 4) {
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_LLAMA_SPIT, SoundCategory.HOSTILE, 1.1f, 0.8f + (random.nextFloat() - random.nextFloat()) * 0.35F);
                }

                player.damage(new UTItemDamageSource("yiorite."+randomInt, stack).setBypassesArmor(), 1.5f);

                for(int i = 0; i < player.getInventory().size(); i++) {
                    if(!player.getInventory().getStack(i).isEmpty() && i != slot) {
                        ItemStack stolenStack = player.getInventory().getStack(i);
                        stolenStack.setCount(stolenStack.getCount()-1);
                        player.getInventory().setStack(i, stolenStack);
                        break;
                    }
                }
                player.getInventory().setStack(slot, ItemStack.EMPTY);
            }
        }else if (entity.isLiving() && Iterables.contains(entity.getHandItems(), stack)) {

            Random random = new Random();
            int randomInt = random.nextInt(5);

            if(randomInt == 4) {
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_LLAMA_SPIT, SoundCategory.HOSTILE, 1.1f, 0.8f + (random.nextFloat() - random.nextFloat()) * 0.35F);
            }

            entity.damage(new UTItemDamageSource("yiorite."+randomInt, stack).setBypassesArmor(), 1.5f);

            LivingEntity livingEntity = (LivingEntity) entity;

            livingEntity.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
            livingEntity.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
        }

        stack.getNbt().putString("lastUser", entity.getUuidAsString());
        stack.getNbt().putInt("InventoryTicks", tickSincePickedUp+1);
    }

}
