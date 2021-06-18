package team.mixxit.allotment.blocks;

/*
 * This code was adapted from BetterEndForge
 * https://git.io/Jn05t
 *
 * BetterEndForge is licensed under the MIT license
 *
 * Big thanks to Beethoven92!
 */

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class StrippableLogBlock extends RotatedPillarBlock
{
    private final RegistryObject<? extends Block> stripped;
    private final MaterialColor colorSides;
    private final MaterialColor colorTop;

    public StrippableLogBlock(MaterialColor colorTop, MaterialColor colorSides, RegistryObject<? extends Block> stripped)
    {
        super(AbstractBlock.Properties.from(stripped.get()));
        this.stripped = stripped;
        this.colorTop = colorTop;
        this.colorSides = colorSides;
    }

    @Override
    public MaterialColor getMaterialColor()
    {
        return AXIS.equals(Direction.Axis.Y) ? colorTop : colorSides;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (player.getHeldItemMainhand().getItem() instanceof AxeItem)
        {
            worldIn.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!worldIn.isRemote())
            {
                worldIn.setBlockState(pos, stripped.get().getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)), 11);
                if (!player.isCreative())
                {
                    player.getHeldItemMainhand().attemptDamageItem(1, worldIn.rand, (ServerPlayerEntity) player);
                }
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}