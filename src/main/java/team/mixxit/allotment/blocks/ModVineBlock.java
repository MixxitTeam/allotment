package team.mixxit.allotment.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.mixxit.allotment.interf.IBlockColorProvider;
import team.mixxit.allotment.interf.IItemColorProvider;

public class ModVineBlock extends VineBlock implements IBlockColorProvider, IItemColorProvider {
    private static final Block vine = Blocks.VINE;
    private Boolean isTinted;

    public ModVineBlock(Properties properties, Boolean isTinted) {
        super(properties);
        this.isTinted = isTinted;
    }

    public Boolean getIsTinted() {
        return isTinted;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IBlockColor getBlockColor(BlockColors colors) {
        final BlockState leafState = vine.getDefaultState();
        return (state, world, pos, tintIndex) -> colors.getColor(leafState, world, pos, tintIndex);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IItemColor getItemColor(ItemColors colors) {
        final ItemStack leafStack = new ItemStack(vine);
        return (stack, tintIndex) -> colors.getColor(leafStack, tintIndex);
    }
}
