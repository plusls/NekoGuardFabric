package cn.apisium.nekoguard.fabric.mixin.event.block.broken;

import cn.apisium.nekoguard.fabric.callback.BlockBreakCallback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class MixinServerPlayerInteractionManager {

    @Shadow
    public ServerPlayerEntity player;

    // PlayerInteractManager 295
    @Inject(method = "tryBreakBlock",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/server/world/ServerWorld;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
                    ordinal = 0))
    private void onBlockBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockBreakCallback.EVENT.invoker().interact(player, pos);
    }
}
