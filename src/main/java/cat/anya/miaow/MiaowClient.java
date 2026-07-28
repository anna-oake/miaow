package cat.anya.miaow;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
/*? if >=1.21.11 {*/
import net.minecraft.resources.Identifier;
/*?}*/
/*? if >=1.21.9 && <1.21.11 {*/
/*import net.minecraft.resources.ResourceLocation;
*//*?}*/

public final class MiaowClient {
    /*? if >=1.21.11 {*/
    private static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(
        Identifier.fromNamespaceAndPath(Miaow.MOD_ID, "miaow")
    );
    /*?} else if >=1.21.9 {*/
    /*private static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(
        ResourceLocation.fromNamespaceAndPath(Miaow.MOD_ID, "miaow")
    );
    *//*?} else {*/
    /*private static final String KEY_CATEGORY = "key.category.miaow.miaow";
    *//*?}*/

    private MiaowClient() {
    }

    public static KeyMapping createKey(String translationKey, int keyCode) {
        return new KeyMapping(
            translationKey,
            InputConstants.Type.KEYSYM,
            keyCode,
            KEY_CATEGORY
        );
    }
}
