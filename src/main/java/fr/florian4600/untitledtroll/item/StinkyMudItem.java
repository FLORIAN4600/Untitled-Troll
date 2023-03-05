package fr.florian4600.untitledtroll.item;

import fr.florian4600.untitledtroll.state.propery.UTProperties;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class StinkyMudItem extends Item {

    public StinkyMudItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getPlayer() == null || (!context.getPlayer().isCreative() && context.getStack().getCount() < 16) || !context.getPlayer().isSneaking()) return ActionResult.PASS;
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        if(state.contains(UTProperties.MUD_LEVEL) && UTProperties.MUD_LEVEL.getValues().contains(state.get(UTProperties.MUD_LEVEL)+1)) {
            System.out.println("updating block state from mud_level="+state.get(UTProperties.MUD_LEVEL)+" to mud_level="+(state.get(UTProperties.MUD_LEVEL)+1));
            context.getWorld().setBlockState(context.getBlockPos(), state.with(UTProperties.MUD_LEVEL, state.get(UTProperties.MUD_LEVEL)+1));
            if(!context.getWorld().isClient()) {
                context.getStack().setCount(context.getStack().getCount()-16);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (this.isFood() && !user.isSneaking()) {
            ItemStack itemStack = user.getStackInHand(hand);
            if (user.canConsume(this.getFoodComponent().isAlwaysEdible())) {
                user.setCurrentHand(hand);
                return TypedActionResult.consume(itemStack);
            } else {
                return TypedActionResult.fail(itemStack);
            }
        } else {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
    }
}