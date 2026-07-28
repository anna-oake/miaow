package cat.anya.miaow;

import cat.anya.miaow.network.MiaowSoundPayload;
import cat.anya.miaow.sound.MiaowSoundAction;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
/*? if >=1.21.7 {*/
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
/*?} else {*/
/*import net.neoforged.neoforge.network.PacketDistributor;
*//*?}*/
import org.lwjgl.glfw.GLFW;

@Mod(value = Miaow.MOD_ID, dist = Dist.CLIENT)
public final class MiaowNeoForgeClient {
    private static final KeyMapping MIAOW_KEY = createKey("key.miaow.miaow", GLFW.GLFW_KEY_G);
    private static final KeyMapping HISS_KEY = createKey("key.miaow.hiss", GLFW.GLFW_KEY_H);
    private static final KeyMapping PURR_KEY = createKey("key.miaow.purr", GLFW.GLFW_KEY_J);

    public MiaowNeoForgeClient(IEventBus modBus) {
        modBus.addListener(this::registerKeys);
        NeoForge.EVENT_BUS.addListener(this::onClientTick);
    }

    private void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(MIAOW_KEY);
        event.register(HISS_KEY);
        event.register(PURR_KEY);
    }

    private void onClientTick(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            return;
        }

        sendWhilePressed(MIAOW_KEY, MiaowSoundAction.MIAOW);
        sendWhilePressed(HISS_KEY, MiaowSoundAction.HISS);
        sendWhilePressed(PURR_KEY, MiaowSoundAction.PURR);
    }

    private static KeyMapping createKey(String translationKey, int keyCode) {
        return MiaowClient.createKey(translationKey, keyCode);
    }

    private static void sendWhilePressed(KeyMapping keyMapping, MiaowSoundAction action) {
        while (keyMapping.consumeClick()) {
            /*? if >=1.21.7 {*/
            ClientPacketDistributor.sendToServer(new MiaowSoundPayload(action.id()));
            /*?} else {*/
            /*PacketDistributor.sendToServer(new MiaowSoundPayload(action.id()));
            *//*?}*/
        }
    }
}
