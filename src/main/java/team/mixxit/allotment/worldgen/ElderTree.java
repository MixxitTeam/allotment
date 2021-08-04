package team.mixxit.allotment.worldgen;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import team.mixxit.allotment.setup.ModFeatures;

import javax.annotation.Nullable;
import java.util.Random;

public class ElderTree extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        return (ConfiguredFeature<BaseTreeFeatureConfig, ?>) ModFeatures.ELDER_TREE.get();
    }
}
