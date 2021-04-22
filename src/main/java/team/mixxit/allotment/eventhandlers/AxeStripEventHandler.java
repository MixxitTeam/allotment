package team.mixxit.allotment.eventhandlers;

import com.google.common.base.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import team.mixxit.allotment.setup.ModBlocks;

import java.util.HashMap;
import java.util.Map;

public class AxeStripEventHandler {

    public static Map<Block, Block> BLOCK_STRIPPING_MAP = new HashMap<>();

    static {
        BLOCK_STRIPPING_MAP.put(ModBlocks.ELDER_LOG.get(), ModBlocks.STRIPPED_ELDER_LOG.get());
        BLOCK_STRIPPING_MAP.put(ModBlocks.ELDER_WOOD.get(), ModBlocks.STRIPPED_ELDER_WOOD.get());
    }

    @SubscribeEvent
    public static void onBlockClicked(PlayerInteractEvent.RightClickBlock event) {
        final int FLAG_BLOCK_UPDATE = 1;
        final int FLAG_SEND_TO_CLIENTS = 2;
        final int FLAG_FORCE_RERENDER_MAIN_THREAD = 8;

        if (event.getItemStack().getItem() instanceof AxeItem) {
            World world = event.getWorld();
            BlockPos blockpos = event.getPos();
            BlockState blockstate = world.getBlockState(blockpos);
            Block block = BLOCK_STRIPPING_MAP.get(blockstate.getBlock());
            if (block != null) {
                PlayerEntity playerentity = event.getPlayer();
                world.playSound(playerentity, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isRemote) {
                    world.setBlockState(blockpos, block.getDefaultState()
                            .with(RotatedPillarBlock.AXIS,
                                  blockstate.get(RotatedPillarBlock.AXIS)),
                             FLAG_BLOCK_UPDATE | FLAG_SEND_TO_CLIENTS | FLAG_FORCE_RERENDER_MAIN_THREAD);
                    if (playerentity != null) {
                        playerentity.swingArm(event.getHand());
                        event.getItemStack().damageItem(1, playerentity, (entityIn) -> {
                            entityIn.sendBreakAnimation(event.getHand());
                        });
                    }
                }
            }
        }
    }

}