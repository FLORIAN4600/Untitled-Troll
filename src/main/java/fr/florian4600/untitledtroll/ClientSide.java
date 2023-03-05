package fr.florian4600.untitledtroll;

import fr.florian4600.untitledtroll.block.UTBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ClientSide implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), UTBlocks.TRAPPED_YIORITE_ORE, UTBlocks.TRAPPED_DEEPSLATE_YIORITE_ORE);
    }
}
