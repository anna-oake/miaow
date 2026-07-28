package cat.anya.miaow.sound;

import net.minecraft.sounds.SoundEvent;
/*? if >=26.1 {*/
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.animal.feline.CatSoundVariant;
import net.minecraft.world.entity.animal.feline.CatSoundVariants;
/*?} else {*/
/*import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
*//*?}*/

public enum MiaowSoundAction {
    MIAOW(0) {
        @Override
        public SoundEvent soundFor(Player player) {
            /*? if >=26.1 {*/
            CatSoundVariant.CatSoundSet soundSet = classicAdultSoundSet(player);
            return player.isShiftKeyDown() ? soundSet.strayAmbientSound().value() : soundSet.ambientSound().value();
            /*?} else {*/
            /*return player.isShiftKeyDown() ? SoundEvents.CAT_STRAY_AMBIENT : SoundEvents.CAT_AMBIENT;
            *//*?}*/
        }
    },
    HISS(1) {
        @Override
        public SoundEvent soundFor(Player player) {
            /*? if >=26.1 {*/
            return classicAdultSoundSet(player).hissSound().value();
            /*?} else {*/
            /*return SoundEvents.CAT_HISS;
            *//*?}*/
        }
    },
    PURR(2) {
        @Override
        public SoundEvent soundFor(Player player) {
            /*? if >=26.1 {*/
            return classicAdultSoundSet(player).purrSound().value();
            /*?} else {*/
            /*return SoundEvents.CAT_PURR;
            *//*?}*/
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

    /*? if >=26.1 {*/
    private static CatSoundVariant.CatSoundSet classicAdultSoundSet(Player player) {
        return player.registryAccess()
            .lookupOrThrow(Registries.CAT_SOUND_VARIANT)
            .get(CatSoundVariants.CLASSIC)
            .orElseThrow()
            .value()
            .adultSounds();
    }
    /*?}*/

    public static MiaowSoundAction fromId(int id) {
        return id >= 0 && id < VALUES.length ? VALUES[id] : null;
    }
}
