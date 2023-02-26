package fr.florian4600.untitledtroll.world.gen.feature;

import fr.florian4600.untitledtroll.MainClass;
import fr.florian4600.untitledtroll.block.UTBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class UTOreFeatures extends FabricDynamicRegistryProvider {

    public static final RegistryKey<ConfiguredFeature<?,?>> ORE_YIORITE = ConfiguredFeatures.of(MainClass.newStringId("ore_yiorite"));
    public static final RegistryKey<ConfiguredFeature<?,?>> ORE_YIORITE_E = ConfiguredFeatures.of(MainClass.newStringId("ore_yiorite_e"));

    public static final RegistryKey<PlacedFeature> ORE_YIORITE_SMALL = PlacedFeatures.of(MainClass.newStringId("ore_yiorite_small"));
    public static final RegistryKey<PlacedFeature> ORE_YIORITE_EXTRA = PlacedFeatures.of(MainClass.newStringId("ore_yiorite_extra"));

    public UTOreFeatures(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public static Map<RegistryKey<PlacedFeature>, GenerationStep.Feature> getOverworldPFKeys() {
        return Map.of(ORE_YIORITE_SMALL, GenerationStep.Feature.UNDERGROUND_ORES, ORE_YIORITE_EXTRA, GenerationStep.Feature.UNDERGROUND_ORES);
    }

    public static Map<GenerationStep.Feature, RegistryKey<PlacedFeature>> getNetherPFKeys() {
        return Map.of();
    }

    public static Map<GenerationStep.Feature, RegistryKey<PlacedFeature>> getEndPFKeys() {
        return Map.of();
    }

    @Override
    public String getName() {
        return "UntitledTroll Ore Features";
    }

    private static ConfiguredFeature<?,?> oreCF(int size, OreFeatureConfig.Target... targets) {
        return new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(Arrays.stream(targets).toList(), size));
    }

    private static ConfiguredFeature<?,?> oreCF(List<OreFeatureConfig.Target> targets, int size) {
        return new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(targets, size));
    }

    private static ConfiguredFeature<?,?> oreCF(OreFeatureConfig config) {
        return new ConfiguredFeature<>(Feature.ORE, config);
    }

    //TODO: Add real searchSize function option
    private static EnvironmentScanPlacementModifier placementModifier(Direction direction, Block block, int maxSteps, int searchSize, int pauses, int random) {
        ArrayList<BlockPredicate> predicates = new ArrayList<>();
        Random random1 = new Random();
        if(searchSize == 1) {
            predicates.add(BlockPredicate.matchingBlocks(new Vec3i(1, 0, 0), block));
            predicates.add(BlockPredicate.matchingBlocks(new Vec3i(0, 0, 1), block));
            predicates.add(BlockPredicate.matchingBlocks(new Vec3i(-1, 0, 0), block));
            predicates.add(BlockPredicate.matchingBlocks(new Vec3i(0, 0, -1), block));
        }else {
            for(int i = 0; i < searchSize; i++) {
                if(i % pauses == 1 || pauses == -1) {
                    for(int a = -i; a < i; a++) {
                        if(random != -1 && random1.nextInt(random/8) != 3) {
                            if(random1.nextInt(random) == 0) {
                                if(random1.nextInt(2) == 0) {
                                    predicates.add(BlockPredicate.matchingBlocks(new Vec3i(a, 0, i), block));
                                    predicates.add(BlockPredicate.matchingBlocks(new Vec3i(a, 0, -i), block));
                                }else {
                                    predicates.add(BlockPredicate.matchingBlocks(new Vec3i(i, 0, a), block));
                                    predicates.add(BlockPredicate.matchingBlocks(new Vec3i(-i, 0, a), block));
                                }
                            }
                        }else {
                            predicates.add(BlockPredicate.matchingBlocks(new Vec3i(a, 0, i), block));
                            predicates.add(BlockPredicate.matchingBlocks(new Vec3i(i, 0, a), block));
                            predicates.add(BlockPredicate.matchingBlocks(new Vec3i(-i, 0, a), block));
                            predicates.add(BlockPredicate.matchingBlocks(new Vec3i(a, 0, -i), block));
                        }
                    }
                }
            }
        }
        return EnvironmentScanPlacementModifier.of(direction, BlockPredicate.anyOf(predicates), maxSteps);
    }

    private static List<PlacementModifier> basicOrePlacements(int count, int minY, int maxY) {
        return List.of(CountPlacementModifier.of(count), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(minY), YOffset.fixed(maxY)), BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> basicOrePlacements(int count, int minY, int maxY, PlacementModifier placement1, PlacementModifier placement2) {
        return List.of(CountPlacementModifier.of(count), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(minY), YOffset.fixed(maxY)), BiomePlacementModifier.of(), placement1, placement2);
    }

    private static List<PlacementModifier> basicOrePlacements(int count, int minY, int maxY, PlacementModifier placement1) {
        return List.of(CountPlacementModifier.of(count), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(minY), YOffset.fixed(maxY)), BiomePlacementModifier.of(), placement1);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        RuleTest STONE_ORE_REPLACEABLES = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest DEEPSLATE_ORE_REPLACEABLES = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreFeatureConfig.Target> ORE_YIORITE_TARGETS = List.of(OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, UTBlocks.YIORITE_ORE.getDefaultState()), OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, UTBlocks.YIORITE_ORE.getDefaultState()));

        RegistryEntry<ConfiguredFeature<?,?>> ORE_YIORITE_CF = entries.add(ORE_YIORITE, oreCF(ORE_YIORITE_TARGETS, 4));
        RegistryEntry<ConfiguredFeature<?,?>> ORE_YIORITE_ECF = entries.add(ORE_YIORITE_E, oreCF(ORE_YIORITE_TARGETS, 9));

        entries.add(ORE_YIORITE_SMALL, new PlacedFeature(ORE_YIORITE_CF, basicOrePlacements(4, -18, -9)));
        entries.add(ORE_YIORITE_EXTRA, new PlacedFeature(ORE_YIORITE_ECF, basicOrePlacements(2, 5, 14, EnvironmentScanPlacementModifier.of(Direction.UP, BlockPredicate.matchingBlocks(Blocks.AIR), 3))));
    }

}