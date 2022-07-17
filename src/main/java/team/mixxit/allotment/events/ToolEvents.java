package team.mixxit.allotment.events;

import com.sun.corba.se.spi.activation.Server;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.blocks.LawnBlock;
import team.mixxit.allotment.setup.ModBlocks;

import java.util.Objects;

@SuppressWarnings("unused")
public class ToolEvents {
    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);
        Direction face = event.getFace();
        PlayerEntity player = event.getPlayer();

        if (stack.getItem() instanceof ShearsItem && state.getBlock().equals(Blocks.GRASS_BLOCK) && Objects.equals(face, Direction.UP)) {
            if (!player.isCreative()) {
                if (player instanceof ServerPlayerEntity) {
                    /*if (stack.attemptDamageItem(1, world.rand, (ServerPlayerEntity) player))
                        stack.break*/

                    // FIXME
                    stack.damageItem(1, player, (i) -> {
                        world.playSound(player, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    });
                }
            }

            BlockState above = world.getBlockState(pos.up());
            world.setBlockState(
                    pos,
                    ModBlocks.LAWN_BLOCK
                            .get()
                            .getDefaultState()
                            .with(LawnBlock.SNOWY, above.matchesBlock(Blocks.SNOW_BLOCK) || above.matchesBlock(Blocks.SNOW))
                            .with(LawnBlock.FACING, player.getHorizontalFacing().getOpposite())
            );

            world.playSound(player, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            player.swing(event.getHand(), true);
            event.setCanceled(true);
        }
    }
}
