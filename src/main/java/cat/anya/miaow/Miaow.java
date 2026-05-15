package cat.anya.miaow;

import cat.anya.miaow.network.MiaowSoundPayload;
import cat.anya.miaow.sound.MiaowSoundAction;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Miaow implements ModInitializer {
    public static final String MOD_ID = "miaow";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.serverboundPlay().register(MiaowSoundPayload.TYPE, MiaowSoundPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MiaowSoundPayload.TYPE, (payload, context) -> {
            MiaowSoundAction action = MiaowSoundAction.fromId(payload.actionId());
            if (action == null) {
                LOGGER.warn("Ignoring unknown miaow sound action id {}", payload.actionId());
                return;
            }

            context.player()
                .level()
                .playSound(
                    null,
                    context.player().getX(),
                    context.player().getY(),
                    context.player().getZ(),
                    action.soundFor(context.player()),
                    context.player().getSoundSource(),
                    1.0F,
                    1.0F
                );
        });
    }
}
