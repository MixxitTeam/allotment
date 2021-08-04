package team.mixxit.allotment.blocks;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.mixxit.allotment.interf.IBlockColorProvider;
import team.mixxit.allotment.interf.IItemColorProvider;

import javax.annotation.Nullable;

public class ModLeavesBlock extends LeavesBlock implements IBlockColorProvider, IItemColorProvider {
    private static final Block leaf = Blocks.OAK_LEAVES;
    public IntegerProperty DISTANCE;
    private int decayDistance = 7;

    public ModLeavesBlock(Properties properties) {
        super(properties);
        DISTANCE = BlockStateProperties.DISTANCE_1_7;
    }

    public ModLeavesBlock(Properties properties, int decayDistance) {
        super(properties);
        this.decayDistance = decayDistance;
        DISTANCE = IntegerProperty.create("distance", 1, decayDistance);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IBlockColor getBlockColor(BlockColors colors) {
        final BlockState leafState = leaf.getDefaultState();
        return (state, world, pos, tintIndex) -> colors.getColor(leafState, world, pos, tintIndex);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IItemColor getItemColor(ItemColors colors) {
        final ItemStack leafStack = new ItemStack(leaf);
        return (stack, tintIndex) -> colors.getColor(leafStack, tintIndex);
    }

    private BlockState updateDistance(BlockState state, IWorld worldIn, BlockPos pos) {
        int i = decayDistance;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(Direction direction : Direction.values()) {
            blockpos$mutable.setAndMove(pos, direction);
            i = Math.min(i, getDistance(worldIn.getBlockState(blockpos$mutable)) + 1);
            if (i == 1) {
                break;
            }
        }

        return state.with(DISTANCE, Integer.valueOf(i));
    }

    private int getDistance(BlockState neighbor) {
        if (BlockTags.LOGS.contains(neighbor.getBlock())) {
            return 0;
        } else {
            return neighbor.getBlock() instanceof LeavesBlock ? neighbor.get(DISTANCE) : decayDistance;
        }
    }
}
