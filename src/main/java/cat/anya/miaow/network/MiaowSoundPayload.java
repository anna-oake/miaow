package cat.anya.miaow.network;

import cat.anya.miaow.Miaow;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record MiaowSoundPayload(int actionId) implements CustomPacketPayload {
    private static final Identifier ID = Identifier.fromNamespaceAndPath(Miaow.MOD_ID, "sound");

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
