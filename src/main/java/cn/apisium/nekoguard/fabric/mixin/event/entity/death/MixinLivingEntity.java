package cn.apisium.nekoguard.fabric.mixin.event.entity.death;

import cn.apisium.nekoguard.fabric.callback.EntityDeathCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {

    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    // EntityLiving 1490
    // 出于兼容性的考虑 注入在 drop 调用前
    // 这样注入可以保证该事件只会在 serverworld 触发
    @Inject(method = "onDeath",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;drop(Lnet/minecraft/entity/damage/DamageSource;)V",
                    ordinal = 0))
    private void onEntityDeath(DamageSource source, CallbackInfo ci) {
        EntityDeathCallback.EVENT.invoker().interact(this);
    }
}
