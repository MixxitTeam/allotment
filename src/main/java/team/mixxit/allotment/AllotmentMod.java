package team.mixxit.allotment;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.mixxit.allotment.itemgroups.MainItemGroup;
import team.mixxit.allotment.setup.ModBlocks;
import team.mixxit.allotment.setup.Registration;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AllotmentMod.MOD_ID)
public class AllotmentMod
{
    public static final String MOD_ID = "allotment";

    public static final MainItemGroup MAIN_GROUP = new MainItemGroup("group_allotment");


    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    private static Block TestBlock;

    public AllotmentMod() {
        Registration.register();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        // Register the commonSetup method for commonSetup
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent evt) {
        evt.enqueueWork(() -> Registration.postRegister());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("team.mixxit.allotment::AllotmentMod.setup");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
        ModBlocks.registerRenderTypes(event);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        //InterModComms.sendTo("allotment", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }


    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        /*@SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }*/

        @SubscribeEvent
        public static void registerBlockColors(final ColorHandlerEvent.Block event) {
            event.getBlockColors().register((IBlockColor) ModBlocks.LAWN_BLOCK.get(), ModBlocks.LAWN_BLOCK.get());
            event.getBlockColors().register((IBlockColor) ModBlocks.PAMPAS_GRASS.get(), ModBlocks.PAMPAS_GRASS.get());
            event.getBlockColors().register((IBlockColor) ModBlocks.PAMPAS_GRASS_PINK.get(), ModBlocks.PAMPAS_GRASS_PINK.get());
            event.getBlockColors().register((IBlockColor) ModBlocks.ELDER_LEAVES.get(), ModBlocks.ELDER_LEAVES.get());
        }

        @SubscribeEvent
        public static void registerItemColors(final ColorHandlerEvent.Item event) {
            event.getItemColors().register((IItemColor) ModBlocks.LAWN_BLOCK.get(), ModBlocks.LAWN_BLOCK.get());
            event.getItemColors().register((IItemColor) ModBlocks.PAMPAS_GRASS.get(), ModBlocks.PAMPAS_GRASS.get());
            event.getItemColors().register((IItemColor) ModBlocks.PAMPAS_GRASS_PINK.get(), ModBlocks.PAMPAS_GRASS_PINK.get());
            event.getItemColors().register((IItemColor) ModBlocks.ELDER_LEAVES.get(), ModBlocks.ELDER_LEAVES.get());
        }

        /*
        @SubscribeEvent
        public static void blockToolInteract(final BlockEvent.BlockToolInteractEvent event) {
            System.out.println("~~~~~[[[[ALLOTMENT---INTERACT]]]]~~~~~");
        }
        */
    }
}
