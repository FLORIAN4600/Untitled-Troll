package fr.florian4600.untitledtroll.block;

import fr.florian4600.untitledtroll.MainClass;
import fr.florian4600.untitledtroll.state.propery.UTProperties;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;

public class UTBlocks {
    public static final Block YIORITE_ORE = new YioriteOreBlock(FabricBlockSettings.of(Material.STONE).dropsNothing().requiresTool().strength(4.3f, 36000f).ticksRandomly().luminance(blockState -> blockState.get(Properties.LIT) ? blockState.get(UTProperties.LIGHT_LEVEL) : 0));
    public static final Block DEEPSLATE_YIORITE_ORE = new YioriteOreBlock(FabricBlockSettings.copyOf(YIORITE_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(6.3f, 46000f).sounds(BlockSoundGroup.DEEPSLATE));
    public static final Block TRAPPED_YIORITE_ORE = new Block(FabricBlockSettings.of(Material.METAL).luminance(3).strength(2.4f, 12000f));

    public static final Block TRAPPED_DEEPSLATE_YIORITE_ORE = new Block(FabricBlockSettings.copyOf(TRAPPED_YIORITE_ORE).luminance(2));

    public UTBlocks() {

    }

    public static void register() {
        Registry.register(Registries.BLOCK, MainClass.newId("yiorite_ore"), YIORITE_ORE);
        Registry.register(Registries.BLOCK, MainClass.newId("deepslate_yiorite_ore"), DEEPSLATE_YIORITE_ORE);
        Registry.register(Registries.BLOCK, MainClass.newId("trapped_yiorite_ore"), TRAPPED_YIORITE_ORE);
        Registry.register(Registries.BLOCK, MainClass.newId("deepslate_trapped_yiorite_ore"), TRAPPED_DEEPSLATE_YIORITE_ORE);
    }
}
