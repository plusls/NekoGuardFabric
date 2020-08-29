package cn.apisium.nekoguard.fabric.mixin.event.entity.death;

import cn.apisium.nekoguard.fabric.callback.EntityDeathCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntity.class)
public abstract class MixinArmorStandEntity extends LivingEntity {

    public MixinArmorStandEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    // EntityArmorStand 706
    // 正常情况下 实体死亡时会经过 LivingEntity.onDeath
    // 但是 mojang 为 kill 命令单独设计了一个 kill 方法
    // 正常情况下 kill 方法会产生虚空伤害，此时实体死亡也会经过 LivingEntity.onDeath
    // 但是盔甲架重写了 kill 方法让盔甲架直接 remove
    // 因此需要在这里额外 Mixin 一次用于产生 EntityDeath 事件
    @Inject(method = "kill", at = @At("HEAD"))
    private void onEntityDeath(CallbackInfo ci) {
        EntityDeathCallback.EVENT.invoker().interact(this);
    }
}
