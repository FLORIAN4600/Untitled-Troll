package fr.florian4600.untitledtroll.item;

import fr.florian4600.untitledtroll.MainClass;
import fr.florian4600.untitledtroll.block.UTBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class UTItemGroups {

    public static final ItemGroup UT_GROUP = FabricItemGroup.builder(MainClass.newId("ut_group")).displayName(MainClass.getTranslation("itemgroup", "ut_group")).icon(() -> new ItemStack(UTBlocks.DEEPSLATE_YIORITE_ORE)).build();

    public UTItemGroups() {

    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(UT_GROUP).register(entries -> {
            entries.add(UTItems.YIORITE_ORE);
            entries.add(UTItems.DEEPSLATE_YIORITE_ORE);
            entries.add(UTItems.TRAPPED_YIORITE_ORE);
            entries.add(UTItems.TRAPPED_DEEPSLATE_YIORITE_ORE);
            entries.add(UTItems.YIORITE_INGOT);
        });
    }

}
