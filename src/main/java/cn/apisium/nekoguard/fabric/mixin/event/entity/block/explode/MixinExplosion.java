package cn.apisium.nekoguard.fabric.mixin.event.entity.block.explode;

import cn.apisium.nekoguard.fabric.callback.BlockExplodeCallback;
import cn.apisium.nekoguard.fabric.mixin.util.world.explosion.IMixinExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
    private double x;

    @Final
    @Shadow
    private double y;

    @Final
    @Shadow
    private double z;

    @Final
    @Shadow
    private Entity entity;

    @Final
    @Shadow
    private World world;

    // BlockExplodeEvent  Explosion 255
    @Inject(method = "affectWorld",
            at = @At(value = "INVOKE_ASSIGN",
                    target = "Ljava/util/List;iterator()Ljava/util/Iterator;",
                    ordinal = 0))
    private void logDestroyedBlock(boolean bl, CallbackInfo ci) {
        if (this.entity == null) {
            BlockPos pos = new BlockPos(this.x, this.y, this.z);
            BlockExplodeCallback.EVENT.invoker().interact(
                    this.getDestroyedPosList(),
                    this.getDestroyedStateList(),
                    this.world,
                    pos);
        }
    }
}
