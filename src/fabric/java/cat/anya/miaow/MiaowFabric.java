package cat.anya.miaow;

import cat.anya.miaow.network.MiaowSoundPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public final class MiaowFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        /*? if >=26.1 {*/
        PayloadTypeRegistry.serverboundPlay().register(MiaowSoundPayload.TYPE, MiaowSoundPayload.CODEC);
        /*?} else {*/
        /*PayloadTypeRegistry.playC2S().register(MiaowSoundPayload.TYPE, MiaowSoundPayload.CODEC);
        *//*?}*/
        ServerPlayNetworking.registerGlobalReceiver(
            MiaowSoundPayload.TYPE,
            (payload, context) -> Miaow.handleSound(context.player(), payload)
        );
    }
}
