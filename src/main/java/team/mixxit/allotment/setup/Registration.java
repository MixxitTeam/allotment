package team.mixxit.allotment.setup;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Bootstrap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.eventhandlers.AxeStripEventHandler;

public class Registration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AllotmentMod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AllotmentMod.MOD_ID);

    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        ModBlocks.register();
        ModItems.register();
        ModDamageSources.register();

        MinecraftForge.EVENT_BUS.register(AxeStripEventHandler.class);
        //modEventBus.register(AxeStripEventHandler.class);

        System.out.println("[[ EVERYTHING REGISTERED ]]");
    }

    public static void postRegister() {
        ModBlocks.postRegister();
    }
}
