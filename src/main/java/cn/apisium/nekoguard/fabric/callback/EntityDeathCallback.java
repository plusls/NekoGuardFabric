package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface EntityDeathCallback {
    Event<EntityDeathCallback> EVENT = EventFactory.createArrayBacked(EntityDeathCallback.class,
            (listeners) -> (entity) -> {
                for (EntityDeathCallback event : listeners) {
                    event.interact(entity);
                }
            });

    /**
     * 实体死亡事件
     * <dl>
     *     <dt>world</dt>
     *     <dd>world 可通过 {@link Entity#world} 获得</dd>
     *     <dt>pos</dt>
     *     <dd>实体位置 pos 可通过 {@link Entity#getBlockPos()} 和 {@link Entity#getPos()} 获取</dd>
     * </dl>
     *
     * @param entity 实体
     */

    void interact(@NotNull Entity entity);
}
