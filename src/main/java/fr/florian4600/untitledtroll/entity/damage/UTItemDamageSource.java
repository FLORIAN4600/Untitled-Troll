package fr.florian4600.untitledtroll.entity.damage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class UTItemDamageSource extends DamageSource {

    protected final ItemStack source;

    public UTItemDamageSource(String name, ItemStack source) {
        super(name);
        this.source = source;
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        LivingEntity livingEntity = entity.getPrimeAdversary();
        String string = "death.itemattack." + this.name;
        String string2 = string + ".player";
        return livingEntity != null ? Text.translatable(string2, entity.getDisplayName(), livingEntity.getDisplayName(), this.source.getName()) : Text.translatable(string, entity.getDisplayName(), this.source.getName());
    }

    @Override
    public String toString() {
        return "UTItemDamageSource (" + this.source + ")";
    }
}
