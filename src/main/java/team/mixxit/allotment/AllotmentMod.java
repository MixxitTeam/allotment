package team.mixxit.allotment;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.mixxit.allotment.blocks.ModVineBlock;
import team.mixxit.allotment.interf.IBlockColorProvider;
import team.mixxit.allotment.interf.IItemColorProvider;
import team.mixxit.allotment.itemgroups.MainItemGroup;
import team.mixxit.allotment.setup.ModBlocks;
import team.mixxit.allotment.setup.Registration;

@Mod(AllotmentMod.MOD_ID)
public class AllotmentMod
{
//region TODO List
    // TODO Crafting recipes!
    // TODO World generation
    // TODO Elder sign ~& button~
    // TODO Other firewood bundles
    // DONE ~Gutter placeable on walls~
//endregion

    public static final String MOD_ID = "allotment";

    public static final MainItemGroup MAIN_GROUP = new MainItemGroup("group_allotment");

    private static final Logger LOGGER = LogManager.getLogger();

    public static Logger getLogger() {
        return LOGGER;
    }

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
        //LOGGER.info("team.mixxit.allotment::AllotmentMod.setup");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);

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
        //LOGGER.info("Got IMC {}", event.getIMCStream().map(m->m.getMessageSupplier().get()).collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void registerBlockColors(final ColorHandlerEvent.Block event) {
            LOGGER.info("Registering tinted blocks");

            final Block[] blocks = {
                    ModBlocks.LAWN_BLOCK.get(),
                    ModBlocks.PAMPAS_GRASS.get(),
                    ModBlocks.PAMPAS_GRASS_PINK.get(),
                    ModBlocks.ELDER_LEAVES.get(),
            };

            for (Block block : blocks) {
                if (!(block instanceof IBlockColorProvider)) {
                    LOGGER.warn(block.getRegistryName().toString() + " does not implement IBlockColorProvider");
                    continue;
                }

                LOGGER.debug("Registering tinted block: " + block.getRegistryName().toString());

                IBlockColor color = ((IBlockColorProvider)block).getBlockColor(event.getBlockColors());
                event.getBlockColors().register(color, block);
            }

            for (RegistryObject<ModVineBlock> _vine : ModBlocks._COLLECTION_VINES) {
                ModVineBlock _vineBlock = _vine.get();
                if (_vineBlock.getIsTinted()) {
                    if (!(_vineBlock instanceof IBlockColorProvider)) {
                        LOGGER.warn(_vineBlock.getRegistryName().toString() + " does not implement IBlockColorProvider");
                        continue;
                    }

                    LOGGER.debug("Registering tinted block: " + _vineBlock.getRegistryName().toString());

                    IBlockColor color = ((IBlockColorProvider)_vineBlock).getBlockColor(event.getBlockColors());
                    event.getBlockColors().register(color, _vineBlock);
                }
            }

            for (RegistryObject<ModVineBlock> _vine : ModBlocks._COLLECTION_TINTED_OVERLAY_VINES) {
                ModVineBlock _vineBlock = _vine.get();
                if (!(_vineBlock instanceof IBlockColorProvider)) {
                    LOGGER.warn(_vineBlock.getRegistryName().toString() + " does not implement IBlockColorProvider");
                    continue;
                }

                LOGGER.debug("Registering tinted block: " + _vineBlock.getRegistryName().toString());

                IBlockColor color = ((IBlockColorProvider)_vineBlock).getBlockColor(event.getBlockColors());
                event.getBlockColors().register(color, _vineBlock);
            }

            IBlockColor color = ((IBlockColorProvider)ModBlocks.DEBUG_TINT_BLOCK.get()).getBlockColor(event.getBlockColors());
            event.getBlockColors().register(color, ModBlocks.DEBUG_TINT_BLOCK.get());
        }

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void registerItemColors(final ColorHandlerEvent.Item event) {
            LOGGER.info("Registering tinted items");

            final Block[] blocks = {
                    ModBlocks.LAWN_BLOCK.get(),
                    ModBlocks.ELDER_LEAVES.get(),
            };

            for (Block block : blocks) {
                if (!(block instanceof IItemColorProvider)) {
                    LOGGER.warn(block.getRegistryName().toString() + " does not implement IItemColorProvider");
                    continue;
                }

                LOGGER.debug("Registering tinted item: " + block.getRegistryName().toString());

                IItemColor color = ((IItemColorProvider)block).getItemColor(event.getItemColors());
                event.getItemColors().register(color, block);
            }

            for (RegistryObject<ModVineBlock> _vine : ModBlocks._COLLECTION_VINES) {
                ModVineBlock _vineBlock = _vine.get();
                if (_vineBlock.getIsTinted()) {
                    if (!(_vineBlock instanceof IBlockColorProvider)){
                        LOGGER.warn(_vineBlock.getRegistryName().toString() + " does not implement IItemColorProvider");
                        continue;
                    }

                    LOGGER.debug("Registering tinted item: " + _vineBlock.getRegistryName().toString());

                    IItemColor color = ((IItemColorProvider)_vineBlock).getItemColor(event.getItemColors());
                    event.getItemColors().register(color, _vineBlock);
                }
            }
            IItemColor color = ((IBlockColorProvider)ModBlocks.DEBUG_TINT_BLOCK.get()).getItemColor(event.getItemColors());
            event.getItemColors().register(color, ModBlocks.DEBUG_TINT_BLOCK.get());
        }
    }
}
