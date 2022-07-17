package team.mixxit.allotment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.mixxit.allotment.blocks.ModVineBlock;
import team.mixxit.allotment.debug.BlockMetadata;
import team.mixxit.allotment.debug.ItemMetadata;
import team.mixxit.allotment.events.ToolEvents;
import team.mixxit.allotment.interf.IBlockColorProvider;
import team.mixxit.allotment.interf.IItemColorProvider;
import team.mixxit.allotment.itemgroups.MainItemGroup;
import team.mixxit.allotment.setup.ModBlocks;
import team.mixxit.allotment.setup.ModCommands;
import team.mixxit.allotment.setup.Registration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

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
        MinecraftForge.EVENT_BUS.register(new ToolEvents());
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private void commonSetup(FMLCommonSetupEvent evt) {
        evt.enqueueWork(() -> Registration.postRegister());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        //LOGGER.info("team.mixxit.allotment::AllotmentMod.setup");
        MinecraftForge.EVENT_BUS.register(ModCommands.class);
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

    public static void dumpBlocks() {
        ArrayList<BlockMetadata> metadataList = new ArrayList<>();
        for (RegistryObject<Block> entry : Registration.BLOCKS.getEntries()) {
            metadataList.add(BlockMetadata.fromRegistryObject(entry));
        }

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        String blocks = gson.toJson(metadataList);

        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                        Files.newOutputStream(Paths.get("allotment.blocks.json")),
                        StandardCharsets.UTF_8
                )
        )) {
            writer.write(blocks);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dumpItems() {
        ArrayList<ItemMetadata> metadataList = new ArrayList<>();
        for (RegistryObject<Item> entry : Registration.ITEMS.getEntries()) {
            metadataList.add(ItemMetadata.fromRegistryObject(entry));
        }

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        String items = gson.toJson(metadataList);

        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                        Files.newOutputStream(Paths.get("allotment.items.json")),
                        StandardCharsets.UTF_8
                )
        )) {
            writer.write(items);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    ModBlocks.ELDER_LEAVES_FLOWERING.get(),
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

            {
                IBlockColor color = ((IBlockColorProvider) ModBlocks.DEBUG_TINT_BLOCK.get()).getBlockColor(event.getBlockColors());
                event.getBlockColors().register(color, ModBlocks.DEBUG_TINT_BLOCK.get());
            }
            {
                IBlockColor color = ((IBlockColorProvider) ModBlocks.DEBUG_FOLIAGE_BLOCK.get()).getBlockColor(event.getBlockColors());
                event.getBlockColors().register(color, ModBlocks.DEBUG_FOLIAGE_BLOCK.get());
            }
        }

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public static void registerItemColors(final ColorHandlerEvent.Item event) {
            LOGGER.info("Registering tinted items");

            final Block[] blocks = {
                    ModBlocks.LAWN_BLOCK.get(),
                    ModBlocks.ELDER_LEAVES.get(),
                    ModBlocks.ELDER_LEAVES_FLOWERING.get(),
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

            {
                IItemColor color = ((IBlockColorProvider) ModBlocks.DEBUG_TINT_BLOCK.get()).getItemColor(event.getItemColors());
                event.getItemColors().register(color, ModBlocks.DEBUG_TINT_BLOCK.get());
            }
            {
                IItemColor color = ((IBlockColorProvider) ModBlocks.DEBUG_FOLIAGE_BLOCK.get()).getItemColor(event.getItemColors());
                event.getItemColors().register(color, ModBlocks.DEBUG_FOLIAGE_BLOCK.get());
            }
        }

        @SubscribeEvent
        public static void FMLLoadCompleteEvent(FMLLoadCompleteEvent event) {
            for (Biome biome : ForgeRegistries.BIOMES) {

            }
        }
    }
}
