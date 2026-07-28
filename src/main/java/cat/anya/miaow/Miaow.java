package cat.anya.miaow;

import cat.anya.miaow.network.MiaowSoundPayload;
import cat.anya.miaow.sound.MiaowSoundAction;
/*? if >=1.21.5 && <26.1 {*/
/*import net.minecraft.core.Holder;
*//*?}*/
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Miaow {
    public static final String MOD_ID = "miaow";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private Miaow() {
    }

    public static void handleSound(Player player, MiaowSoundPayload payload) {
        MiaowSoundAction action = MiaowSoundAction.fromId(payload.actionId());
        if (action == null) {
            LOGGER.warn("Ignoring unknown miaow sound action id {}", payload.actionId());
            return;
        }

        player.level().playSound(
            null,
            player.getX(),
            player.getY(),
            player.getZ(),
            /*? if >=1.21.5 && <26.1 {*/
            /*Holder.direct(action.soundFor(player)),
            *//*?} else {*/
            action.soundFor(player),
            /*?}*/
            player.getSoundSource(),
            1.0F,
            1.0F
        );
    }
}
