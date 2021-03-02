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
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;
import java.util.Random;

public class LawnBlock extends GrassBlock implements IGrowable, IBlockColor, IItemColor {
    private static final GrassBlock grass = (GrassBlock)Blocks.GRASS_BLOCK;

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
                .with(SNOWY, blockstate.isIn(Blocks.SNOW_BLOCK) || blockstate.isIn(Blocks.SNOW));
    }

    @Override
    public StateContainer<Block, BlockState> getStateContainer() {
        return super.getStateContainer();
    }

    @Override
    public int getColor(BlockState p_getColor_1_, @Nullable IBlockDisplayReader p_getColor_2_, @Nullable BlockPos p_getColor_3_, int p_getColor_4_) {
        final BlockColors colors = Minecraft.getInstance().getBlockColors();
        final BlockState grassState = grass.getDefaultState();
        return colors.getColor(grassState, p_getColor_2_, p_getColor_3_, p_getColor_4_);
    }

    @Override
    public int getColor(ItemStack p_getColor_1_, int p_getColor_2_) {
        final ItemColors colors = Minecraft.getInstance().getItemColors();
        final ItemStack grassStack = new ItemStack(grass);
        return colors.getColor(grassStack, p_getColor_2_);
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
}
