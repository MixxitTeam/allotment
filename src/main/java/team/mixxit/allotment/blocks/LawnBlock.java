package team.mixxit.allotment.blocks;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import team.mixxit.allotment.interf.IBlockColorProvider;
import team.mixxit.allotment.interf.IItemColorProvider;

import javax.annotation.Nullable;
import java.util.Random;

public class LawnBlock extends GrassBlock implements IGrowable, IBlockColorProvider, IItemColorProvider {
    private static final Block grass = Blocks.GRASS_BLOCK;

    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public LawnBlock(Properties properties) {
        super(properties);

        BlockState defaultState = stateContainer.getBaseState()
                .with(FACING, Direction.NORTH)
                .with(SNOWY, Boolean.FALSE)
                ;
        setDefaultState(defaultState);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder
                .add(FACING)
                .add(SNOWY)
        ;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos().up());
        return getDefaultState()
                .with(FACING, context.getPlacementHorizontalFacing().getOpposite())
                .with(SNOWY, blockstate.matchesBlock(Blocks.SNOW_BLOCK) || blockstate.matchesBlock(Blocks.SNOW));
    }

    @Override
    public StateContainer<Block, BlockState> getStateContainer() {
        return super.getStateContainer();
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return facing == Direction.UP && plantable.getPlantType(world, pos) == PlantType.PLAINS;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return worldIn.getBlockState(pos.up()).isAir();
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IBlockColor getBlockColor(BlockColors colors) {
        final BlockState grassState = grass.getDefaultState();

        if (colors == null)
            System.err.println("colors was null");
        if (grass == null)
            System.err.println("grass was null");
        if (grassState == null)
            System.err.println("grassState was null");

        return (state, world, pos, tintIndex) -> colors.getColor(grassState, world, pos, tintIndex);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IItemColor getItemColor(ItemColors colors) {
        final ItemStack grassStack = new ItemStack(grass);
        return (stack, tintIndex) -> colors.getColor(grassStack, tintIndex);
    }
}
