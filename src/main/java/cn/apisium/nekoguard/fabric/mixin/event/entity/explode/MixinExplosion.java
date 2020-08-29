package cn.apisium.nekoguard.fabric.mixin.event.entity.explode;

import cn.apisium.nekoguard.fabric.callback.EntityExplodeCallback;
import cn.apisium.nekoguard.fabric.mixin.util.world.explosion.IMixinExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public abstract class MixinExplosion implements IMixinExplosion {

    @Final
    @Shadow
    private Entity entity;

    // EntityExplodeEvent Explosion 249
    @Inject(method = "affectWorld",
            at = @At(value = "INVOKE_ASSIGN",
                    target = "Ljava/util/List;iterator()Ljava/util/Iterator;",
                    ordinal = 0))
    private void logDestroyedBlock(boolean bl, CallbackInfo ci) {
        if (this.entity != null) {
            EntityExplodeCallback.EVENT.invoker().interact(entity,
                    this.getDestroyedPosList(),
                    this.getDestroyedStateList());
        }
    }
}
