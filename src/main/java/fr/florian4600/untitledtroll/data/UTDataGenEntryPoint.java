package fr.florian4600.untitledtroll.data;

import fr.florian4600.untitledtroll.data.client.UTModelProvider;
import fr.florian4600.untitledtroll.world.gen.feature.UTOreFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;

public class UTDataGenEntryPoint implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        System.out.println("---Initializing Data Generator---");

        FabricDataGenerator.Pack registryPack = fabricDataGenerator.createPack();

        registryPack.addProvider(UTOreFeatures::new);
        registryPack.addProvider(UTModelProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        System.out.println("---Building Registries---");
    }
}
