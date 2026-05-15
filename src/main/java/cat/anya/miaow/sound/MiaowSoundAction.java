package cat.anya.miaow.sound;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.animal.feline.CatSoundVariant;
import net.minecraft.world.entity.animal.feline.CatSoundVariants;

public enum MiaowSoundAction {
    MIAOW(0) {
        @Override
        public SoundEvent soundFor(Player player) {
            CatSoundVariant.CatSoundSet soundSet = classicAdultSoundSet(player);
            return player.isShiftKeyDown() ? soundSet.strayAmbientSound().value() : soundSet.ambientSound().value();
        }
    },
    HISS(1) {
        @Override
        public SoundEvent soundFor(Player player) {
            return classicAdultSoundSet(player).hissSound().value();
        }
    },
    PURR(2) {
        @Override
        public SoundEvent soundFor(Player player) {
            return classicAdultSoundSet(player).purrSound().value();
        }
    };

    private static final MiaowSoundAction[] VALUES = values();

    private final int id;

    MiaowSoundAction(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public abstract SoundEvent soundFor(Player player);

    private static CatSoundVariant.CatSoundSet classicAdultSoundSet(Player player) {
        return player.registryAccess()
            .lookupOrThrow(Registries.CAT_SOUND_VARIANT)
            .get(CatSoundVariants.CLASSIC)
            .orElseThrow()
            .value()
            .adultSounds();
    }

    public static MiaowSoundAction fromId(int id) {
        return id >= 0 && id < VALUES.length ? VALUES[id] : null;
    }
}
