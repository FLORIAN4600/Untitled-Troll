package fr.florian4600.untitledtroll.item;

import fr.florian4600.untitledtroll.MainClass;
import fr.florian4600.untitledtroll.block.UTBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class UTItemGroups {

    public static final ItemGroup UT_GROUP;

    public UTItemGroups() {

    }

    static {
        UT_GROUP = FabricItemGroup.builder(MainClass.newId("ut_group")).displayName(MainClass.getTranslation("itemgroup", "ut_group")).icon(() -> new ItemStack(UTBlocks.DEEPSLATE_YIORITE_ORE)).entries((enabledFeatures, entries, operatorEnabled) -> {
            entries.add(UTItems.YIORITE_ORE);
            entries.add(UTItems.DEEPSLATE_YIORITE_ORE);
            entries.add(UTItems.YIORITE_INGOT);
        }).build();
    }

}
