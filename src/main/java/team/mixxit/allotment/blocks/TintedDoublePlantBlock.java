package team.mixxit.allotment.blocks;

import net.minecraft.block.*;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.mixxit.allotment.interf.IBlockColorProvider;
import team.mixxit.allotment.interf.IItemColorProvider;

public class TintedDoublePlantBlock extends DoublePlantBlock implements IBlockColorProvider, IItemColorProvider {
    private static final Block grass = Blocks.TALL_GRASS;

    public TintedDoublePlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IBlockColor getBlockColor(BlockColors colors) {
        final BlockState grassState = grass.getDefaultState();
        return (state, world, pos, tintIndex) -> colors.getColor(grassState, world, pos, tintIndex);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IItemColor getItemColor(ItemColors colors) {
        final ItemStack grassStack = new ItemStack(grass);
        return (stack, tintIndex) -> colors.getColor(grassStack, tintIndex);
    }
}
