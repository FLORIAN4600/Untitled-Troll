package fr.florian4600.untitledtroll.item;

import fr.florian4600.untitledtroll.MainClass;
import fr.florian4600.untitledtroll.block.UTBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class UTItems {

    public static final Item YIORITE_ORE = new YioriteOreItemBlock(UTBlocks.YIORITE_ORE, new FabricItemSettings().fireproof().rarity(Rarity.RARE));
    public static final Item DEEPSLATE_YIORITE_ORE = new YioriteOreItemBlock(UTBlocks.DEEPSLATE_YIORITE_ORE, new FabricItemSettings().fireproof().rarity(Rarity.RARE));

    public static final Item YIORITE_INGOT = new YioriteIngotItem(ToolMaterials.GOLD, 1, -0.2f,  new FabricItemSettings().fireproof().rarity(Rarity.EPIC).food(new FoodComponent.Builder().alwaysEdible().hunger(7).saturationModifier(1.8f).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 15, 3), 1.0F).statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 25, 1), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 32, 2), 0.3F).build()));


    public UTItems() {

    }

    public static void register() {
        Registry.register(Registries.ITEM, MainClass.newId("yiorite_ore"), YIORITE_ORE);
        Registry.register(Registries.ITEM, MainClass.newId("deepslate_yiorite_ore"), DEEPSLATE_YIORITE_ORE);

        Registry.register(Registries.ITEM, MainClass.newId("yiorite_ingot"), YIORITE_INGOT);

    }

}
