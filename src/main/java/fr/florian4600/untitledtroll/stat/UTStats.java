package fr.florian4600.untitledtroll.stat;

import fr.florian4600.untitledtroll.MainClass;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class UTStats {

    public static final Identifier YIORITE_LOOK_TIME = MainClass.newId("yiorite_look_time");

    public UTStats() {

    }

    public static void register() {
        Registry.register(Registries.CUSTOM_STAT, "yiorite_look_time", YIORITE_LOOK_TIME);
        Stats.CUSTOM.getOrCreateStat(YIORITE_LOOK_TIME, StatFormatter.TIME);
    }

}
