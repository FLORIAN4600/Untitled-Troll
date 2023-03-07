package fr.florian4600.untitledtroll;

import fr.florian4600.untitledtroll.block.UTBlocks;
import fr.florian4600.untitledtroll.block.entity.UTBlockEntityTypes;
import fr.florian4600.untitledtroll.item.UTItemGroups;
import fr.florian4600.untitledtroll.item.UTItems;
import fr.florian4600.untitledtroll.recipe.UTRecipes;
import fr.florian4600.untitledtroll.registry.UTFeaturesRegistry;
import fr.florian4600.untitledtroll.stat.UTStats;
import net.fabricmc.api.ModInitializer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass implements ModInitializer {

	public static final String MOD_ID = "untitledtroll";
	public static final Logger LOGGER = LoggerFactory.getLogger("untitledtroll");

	@Override
	public void onInitialize() {
		LOGGER.info("Yummy");

		UTStats.register();
		UTBlockEntityTypes.register();
		UTBlocks.register();
		UTItems.register();
		UTFeaturesRegistry.register();
		UTRecipes.register();
		UTItemGroups.register();
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
