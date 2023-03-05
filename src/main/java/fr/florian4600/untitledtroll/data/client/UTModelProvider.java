package fr.florian4600.untitledtroll.data.client;

import fr.florian4600.untitledtroll.MainClass;
import fr.florian4600.untitledtroll.block.UTBlocks;
import fr.florian4600.untitledtroll.state.propery.UTProperties;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;


public class UTModelProvider extends FabricModelProvider {

    private static final String[] mudParentList;
    private static final String[] mudIdList;
    public static final String[] mudTextureList;
    public static final Block[] mudBlockList;

    public UTModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        for(int i = 0; i < mudIdList.length; i++) {
            String mudId = mudIdList[i];
            String mudTexture = mudTextureList[i];
            String mudParent = mudParentList[i];
            BlockStateVariantMap variantMap = BlockStateVariantMap.create(UTProperties.MUD_LEVEL).registerVariants((level) -> {
                String mudLevel = level == 0 ? "" : "_mud%02d";
                Identifier modelId = MainClass.newId(String.format("block/"+mudId+mudLevel, level));
                HashMap<String, Identifier> textures = new HashMap<>();
                textures.put("all", MainClass.newId(String.format("block/"+mudTexture+mudLevel, level)));
                blockStateModelGenerator.modelCollector.accept(modelId, new UTTextureModelSupplier(new Identifier(mudParent), textures));
                return List.of(BlockStateVariant.create().put(VariantSettings.MODEL, modelId));
            });
            blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(mudBlockList[i]).coordinate(variantMap));
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }

    static {
        mudParentList = new String[] {
                "minecraft:block/cube_all",
                "minecraft:block/cube_all",
                "untitledtroll:block/template_trapped_block_all",
                "untitledtroll:block/template_trapped_block_all"
        };
        mudIdList = new String[] {
                "yiorite_ore",
                "deepslate_yiorite_ore",
                "trapped_yiorite_ore",
                "trapped_deepslate_yiorite_ore"
        };
        mudTextureList = new String[] {
                "yiorite_ore",
                "deepslate_yiorite_ore",
                "yiorite_ore",
                "deepslate_yiorite_ore",
        };
        mudBlockList = new Block[] {
                UTBlocks.YIORITE_ORE,
                UTBlocks.DEEPSLATE_YIORITE_ORE,
                UTBlocks.TRAPPED_YIORITE_ORE,
                UTBlocks.TRAPPED_DEEPSLATE_YIORITE_ORE
        };
    }
}
