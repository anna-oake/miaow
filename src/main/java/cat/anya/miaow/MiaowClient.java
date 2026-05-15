package cat.anya.miaow;

import cat.anya.miaow.network.MiaowSoundPayload;
import cat.anya.miaow.sound.MiaowSoundAction;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public final class MiaowClient implements ClientModInitializer {
    private static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(
        Identifier.fromNamespaceAndPath(Miaow.MOD_ID, "miaow")
    );

    private static final KeyMapping MIAOW_KEY = registerKey("key.miaow.miaow", GLFW.GLFW_KEY_G);
    private static final KeyMapping HISS_KEY = registerKey("key.miaow.hiss", GLFW.GLFW_KEY_H);
    private static final KeyMapping PURR_KEY = registerKey("key.miaow.purr", GLFW.GLFW_KEY_J);

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                return;
            }

            sendWhilePressed(client, MIAOW_KEY, MiaowSoundAction.MIAOW);
            sendWhilePressed(client, HISS_KEY, MiaowSoundAction.HISS);
            sendWhilePressed(client, PURR_KEY, MiaowSoundAction.PURR);
        });
    }

    private static KeyMapping registerKey(String translationKey, int keyCode) {
        return KeyMappingHelper.registerKeyMapping(new KeyMapping(
            translationKey,
            InputConstants.Type.KEYSYM,
            keyCode,
            KEY_CATEGORY
        ));
    }

    private static void sendWhilePressed(net.minecraft.client.Minecraft client, KeyMapping keyMapping, MiaowSoundAction action) {
        while (keyMapping.consumeClick()) {
            ClientPlayNetworking.send(new MiaowSoundPayload(action.id()));
        }
    }
}
