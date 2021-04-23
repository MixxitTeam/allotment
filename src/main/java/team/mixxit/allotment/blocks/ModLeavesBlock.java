package team.mixxit.allotment.blocks;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.mixxit.allotment.interf.IBlockColorProvider;
import team.mixxit.allotment.interf.IItemColorProvider;

import javax.annotation.Nullable;

public class ModLeavesBlock extends LeavesBlock implements IBlockColorProvider, IItemColorProvider {
    private static final Block leaf = Blocks.OAK_LEAVES;

    public ModLeavesBlock(Properties properties) {
        super(properties);
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
}
