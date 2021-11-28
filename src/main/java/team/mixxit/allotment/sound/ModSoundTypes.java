package team.mixxit.allotment.sound;

import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModSoundTypes {
    public static final Supplier<SoundType> METAL = () ->
            new ModSoundType(
                    1.0F,
                    1.5F,
                    ModSoundEvents.BLOCK_METAL_BREAK,
                    ModSoundEvents.BLOCK_METAL_STEP,
                    ModSoundEvents.BLOCK_METAL_PLACE,
                    ModSoundEvents.BLOCK_METAL_DIG,
                    ModSoundEvents.BLOCK_METAL_FALL
            );
}
