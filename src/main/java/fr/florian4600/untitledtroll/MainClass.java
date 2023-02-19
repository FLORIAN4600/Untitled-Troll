package fr.florian4600.untitledtroll;

import fr.florian4600.untitledtroll.block.YioriteOreBlock;
import fr.florian4600.untitledtroll.block.entity.YioriteOreBlockEntity;
import fr.florian4600.untitledtroll.item.YioriteOreItemBlock;
import fr.florian4600.untitledtroll.registry.UTFeaturesRegistry;
import fr.florian4600.untitledtroll.state.propery.UTProperties;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagBuilder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;

public class MainClass implements ModInitializer {

	public static final String MOD_ID = "untitledtroll";
	public static final Logger LOGGER = LoggerFactory.getLogger("untitledtroll");

	public static final Block YIORITE_ORE = new YioriteOreBlock(FabricBlockSettings.of(Material.STONE).dropsNothing().requiresTool().strength(4.3f, 36000f).ticksRandomly().luminance(blockState -> blockState.get(Properties.LIT) ? blockState.get(UTProperties.LIGHT_LEVEL) : 0));
	public static final Block DEEPSLATE_YIORITE_ORE = new YioriteOreBlock(FabricBlockSettings.copyOf(YIORITE_ORE).mapColor(MapColor.DEEPSLATE_GRAY).strength(6.3f, 46000f).sounds(BlockSoundGroup.DEEPSLATE));

	public static final BlockEntityType<YioriteOreBlockEntity> YIORITE_ORE_ENTITY_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, newId("yiorite_ore"), FabricBlockEntityTypeBuilder.create(YioriteOreBlockEntity::new, YIORITE_ORE, DEEPSLATE_YIORITE_ORE).build());

	@Override
	public void onInitialize() {
		LOGGER.info("Yummy");

		Registry.register(Registries.BLOCK, newId("yiorite_ore"), YIORITE_ORE);
		Registry.register(Registries.ITEM, newId("yiorite_ore"), new YioriteOreItemBlock(YIORITE_ORE, new FabricItemSettings().fireproof().rarity(Rarity.EPIC)));

		Registry.register(Registries.BLOCK, newId("deepslate_yiorite_ore"), DEEPSLATE_YIORITE_ORE);
		Registry.register(Registries.ITEM, newId("deepslate_yiorite_ore"), new YioriteOreItemBlock(DEEPSLATE_YIORITE_ORE, new FabricItemSettings().fireproof().rarity(Rarity.EPIC)));

		UTFeaturesRegistry.register();
	}

	public static Identifier newId(String id) {
		return new Identifier(MOD_ID, id);
	}

	public static String newStringId(String id) {
		return MOD_ID+":"+id;
	}

	public static MutableText getTranslation(String type, String key) {
		return Text.translatable(String.format("%s.%s.%s", type, MOD_ID, key));
	}

	public static String getStringTranslation(String type, String key) {
		return Text.translatable(String.format("%s.%s.%s", type, MOD_ID, key)).getString();
	}
}
