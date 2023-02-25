package fr.florian4600.untitledtroll.utils;

import fr.florian4600.untitledtroll.MainClass;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class YioriteOreUtils {

    public static final String defaultName = MainClass.getStringTranslation("block", "yiorite_ore");

    public YioriteOreUtils() {

    }

    public static void sendText(PlayerEntity player, String id, Text name) {
        player.sendMessage(Text.of("[§a" + name + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite."+id)));
    }

    public static void sendText(PlayerEntity player, String id, String name) {
        player.sendMessage(Text.of("[§a" + name + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite."+id)));
    }

    public static void sendText(PlayerEntity player, String id) {
        player.sendMessage(Text.of("[§a" + defaultName + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite."+id)));
    }

    public static Text getText(String id, Text name) {
        return Text.of("[§a" + name + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite."+id));
    }

    public static Text getText(String id, String name) {
        return Text.of("[§a" + name + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite."+id));
    }

    public static Text getText(String id) {
        return Text.of("[§a" + defaultName + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite."+id));
    }

}