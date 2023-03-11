package fr.florian4600.untitledtroll.state.propery;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;

public class UTProperties {
    public static final BooleanProperty LOOKED;
    public static final IntProperty COMPARATOR_OUT;
    public static final IntProperty LIGHT_LEVEL;
    public static final IntProperty MUD_LEVEL;

    public UTProperties() {

    }

    static {
        LOOKED = BooleanProperty.of("looked");
        COMPARATOR_OUT = IntProperty.of("comparator_output", 0, 14);
        LIGHT_LEVEL = IntProperty.of("light_level", 0, 12);
        MUD_LEVEL = IntProperty.of("mud_level", 0, 3);
    }
}
