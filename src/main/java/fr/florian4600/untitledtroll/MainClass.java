package fr.florian4600.untitledtroll;

import fr.florian4600.untitledtroll.block.UTBlocks;
import fr.florian4600.untitledtroll.block.entity.YioriteOreBlockEntity;
import fr.florian4600.untitledtroll.item.UTItems;
import fr.florian4600.untitledtroll.registry.UTFeaturesRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass implements ModInitializer {

	public static final String MOD_ID = "untitledtroll";
	public static final Logger LOGGER = LoggerFactory.getLogger("untitledtroll");

	public static final BlockEntityType<YioriteOreBlockEntity> YIORITE_ORE_ENTITY_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, newId("yiorite_ore"), FabricBlockEntityTypeBuilder.create(YioriteOreBlockEntity::new, UTBlocks.YIORITE_ORE, UTBlocks.DEEPSLATE_YIORITE_ORE).build());

	@Override
	public void onInitialize() {
		LOGGER.info("Yummy");

		UTBlocks.register();
		UTItems.register();
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
