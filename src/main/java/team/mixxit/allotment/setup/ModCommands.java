package team.mixxit.allotment.setup;

import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import team.mixxit.allotment.AllotmentMod;

public class ModCommands {
    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
        event
                .getDispatcher()
                .register(
                        Commands.literal("allotment")
                                .requires((commandSource -> commandSource.hasPermissionLevel(1)))
                                .then(
                                        Commands.literal("dump")
                                                .then(
                                                        Commands.literal("blocks")
                                                                .executes((commandContext) -> {
                                                                    AllotmentMod.dumpBlocks();
                                                                    ITextComponent c = new StringTextComponent("Dumped block list");
                                                                    Entity entity = commandContext.getSource().getEntity();
                                                                    if (entity != null) {
                                                                        commandContext.getSource().getServer().getPlayerList().func_232641_a_(c, ChatType.SYSTEM, entity.getUniqueID());
                                                                    } else {
                                                                        commandContext.getSource().getServer().getPlayerList().func_232641_a_(c, ChatType.SYSTEM, Util.DUMMY_UUID);
                                                                    }
                                                                    return 1;
                                                                })
                                                ).then(
                                                        Commands.literal("items")
                                                                .executes((commandContext) -> {
                                                                    AllotmentMod.dumpItems();
                                                                    ITextComponent c = new StringTextComponent("Dumped item list");
                                                                    Entity entity = commandContext.getSource().getEntity();
                                                                    if (entity != null) {
                                                                        commandContext.getSource().getServer().getPlayerList().func_232641_a_(c, ChatType.SYSTEM, entity.getUniqueID());
                                                                    } else {
                                                                        commandContext.getSource().getServer().getPlayerList().func_232641_a_(c, ChatType.SYSTEM, Util.DUMMY_UUID);
                                                                    }
                                                                    return 1;
                                                                })
                                                ).then(
                                                        Commands.literal("*")
                                                                .executes((commandContext) -> {
                                                                    AllotmentMod.dumpBlocks();
                                                                    AllotmentMod.dumpItems();
                                                                    ITextComponent c = new StringTextComponent("Dumped all lists");
                                                                    Entity entity = commandContext.getSource().getEntity();
                                                                    if (entity != null) {
                                                                        commandContext.getSource().getServer().getPlayerList().func_232641_a_(c, ChatType.SYSTEM, entity.getUniqueID());
                                                                    } else {
                                                                        commandContext.getSource().getServer().getPlayerList().func_232641_a_(c, ChatType.SYSTEM, Util.DUMMY_UUID);
                                                                    }
                                                                    return 1;
                                                                })
                                                )
                                )
                );
    }
}
