package team.mixxit.allotment.sound;

import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModSoundType extends SoundType {
    private Supplier<SoundEvent> breakSound;
    private Supplier<SoundEvent> stepSound;
    private Supplier<SoundEvent> placeSound;
    private Supplier<SoundEvent> hitSound;
    private Supplier<SoundEvent> fallSound;

    public ModSoundType(
            float volumeIn,
            float pitchIn,
            Supplier<SoundEvent> breakSoundIn,
            Supplier<SoundEvent> stepSoundIn,
            Supplier<SoundEvent> placeSoundIn,
            Supplier<SoundEvent> hitSoundIn,
            Supplier<SoundEvent> fallSoundIn
    ) {
        super(volumeIn, pitchIn, null, null, null, null, null);
        breakSound = breakSoundIn;
        stepSound = stepSoundIn;
        placeSound = placeSoundIn;
        hitSound = hitSoundIn;
        fallSound = fallSoundIn;
    }

    @Override
    public SoundEvent getBreakSound() {
        return breakSound.get();
    }

    @Override
    public SoundEvent getFallSound() {
        return fallSound.get();
    }

    @Override
    public SoundEvent getHitSound() {
        return hitSound.get();
    }

    @Override
    public SoundEvent getPlaceSound() {
        return placeSound.get();
    }

    @Override
    public SoundEvent getStepSound() {
        return stepSound.get();
    }
}
