package fr.florian4600.untitledtroll.registry;

import fr.florian4600.untitledtroll.MainClass;
import fr.florian4600.untitledtroll.world.gen.feature.UTOreFeatures;
import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.Map;
import java.util.function.BiConsumer;

public class UTFeaturesRegistry {

    public static void register() {
        BiomeModifications.create(MainClass.newId("overworld_features")).add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), overworld());

        BiomeModifications.create(MainClass.newId("nether_features")).add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInTheNether(), nether());

        BiomeModifications.create(MainClass.newId("end_features")).add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInTheEnd(), end());
    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> overworld() {
        return (biomeSelectionContext, biomeModificationContext) -> {
            for(Map.Entry<RegistryKey<PlacedFeature>, GenerationStep.Feature> entry : UTOreFeatures.getOverworldPFKeys().entrySet()) {
                biomeModificationContext.getGenerationSettings().addFeature(entry.getValue(), entry.getKey());
            }
        };
    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> nether() {
        return (biomeSelectionContext, biomeModificationContext) -> {
            for(Map.Entry<GenerationStep.Feature, RegistryKey<PlacedFeature>> entry : UTOreFeatures.getNetherPFKeys().entrySet()) {
                biomeModificationContext.getGenerationSettings().addFeature(entry.getKey(), entry.getValue());
            }
        };
    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> end() {
        return (biomeSelectionContext, biomeModificationContext) -> {
            for(Map.Entry<GenerationStep.Feature, RegistryKey<PlacedFeature>> entry : UTOreFeatures.getEndPFKeys().entrySet()) {
                biomeModificationContext.getGenerationSettings().addFeature(entry.getKey(), entry.getValue());
            }
        };
    }

}
