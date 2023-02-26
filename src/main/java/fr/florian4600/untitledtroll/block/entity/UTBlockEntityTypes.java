package fr.florian4600.untitledtroll.block.entity;

import fr.florian4600.untitledtroll.MainClass;
import fr.florian4600.untitledtroll.block.UTBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class UTBlockEntityTypes {
    public static final BlockEntityType<YioriteOreBlockEntity> YIORITE_ORE_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(YioriteOreBlockEntity::new, UTBlocks.YIORITE_ORE, UTBlocks.DEEPSLATE_YIORITE_ORE).build();
    public static final BlockEntityType<TrappedYioriteOreBlockEntity> TRAPPED_YIORITE_ORE_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(TrappedYioriteOreBlockEntity::new, UTBlocks.TRAPPED_YIORITE_ORE, UTBlocks.TRAPPED_DEEPSLATE_YIORITE_ORE).build();

    public static void register() {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, MainClass.newId("yiorite_ore"), YIORITE_ORE_ENTITY_TYPE);
        Registry.register(Registries.BLOCK_ENTITY_TYPE, MainClass.newId("trapped_yiorite_ore"), TRAPPED_YIORITE_ORE_ENTITY_TYPE);
    }
}
