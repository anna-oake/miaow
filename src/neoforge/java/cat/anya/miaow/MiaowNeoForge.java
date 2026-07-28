package cat.anya.miaow;

import cat.anya.miaow.network.MiaowSoundPayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(Miaow.MOD_ID)
public final class MiaowNeoForge {
    public MiaowNeoForge(IEventBus modBus) {
        modBus.addListener(this::registerPayloads);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToServer(
            MiaowSoundPayload.TYPE,
            MiaowSoundPayload.CODEC,
            (payload, context) -> Miaow.handleSound(context.player(), payload)
        );
    }
}
