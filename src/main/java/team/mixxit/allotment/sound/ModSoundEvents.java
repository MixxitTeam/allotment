package team.mixxit.allotment.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import team.mixxit.allotment.AllotmentMod;
import team.mixxit.allotment.setup.Registration;

public class ModSoundEvents {
    public static final RegistryObject<SoundEvent> BLOCK_METAL_STEP  = register("block_metal_step");
    public static final RegistryObject<SoundEvent> BLOCK_METAL_BREAK = register("block_metal_break");
    public static final RegistryObject<SoundEvent> BLOCK_METAL_DIG = register("block_metal_dig");
    public static final RegistryObject<SoundEvent> BLOCK_METAL_PLACE = register("block_metal_place");
    public static final RegistryObject<SoundEvent> BLOCK_METAL_FALL = register("block_metal_fall");

    private static RegistryObject<SoundEvent> register(String key) {
        return Registration.SOUNDS.register(key, () -> {
            ResourceLocation loc = new ResourceLocation(AllotmentMod.MOD_ID, key);
            return new SoundEvent(loc);
        });
    }

    public static void register() {}
}
