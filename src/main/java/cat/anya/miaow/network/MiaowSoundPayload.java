package cat.anya.miaow.network;

import cat.anya.miaow.Miaow;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
/*? if >=1.21.11 {*/
import net.minecraft.resources.Identifier;
/*?} else {*/
/*import net.minecraft.resources.ResourceLocation;
*//*?}*/

public record MiaowSoundPayload(int actionId) implements CustomPacketPayload {
    /*? if >=1.21.11 {*/
    private static final Identifier ID = Identifier.fromNamespaceAndPath(Miaow.MOD_ID, "sound");
    /*?} else {*/
    /*private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Miaow.MOD_ID, "sound");
    *//*?}*/

    public static final Type<MiaowSoundPayload> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MiaowSoundPayload> CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        MiaowSoundPayload::actionId,
        MiaowSoundPayload::new
    );

    @Override
    public Type<MiaowSoundPayload> type() {
        return TYPE;
    }
}
