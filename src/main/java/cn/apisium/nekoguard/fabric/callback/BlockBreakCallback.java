package cn.apisium.nekoguard.fabric.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface BlockBreakCallback {
    Event<BlockBreakCallback> EVENT = EventFactory.createArrayBacked(BlockBreakCallback.class,
            (listeners) -> (player, pos) -> {
                for (BlockBreakCallback event : listeners) {
                    event.interact(player, pos);
                }
            });

    /**
     * 玩家破坏方块事件
     * <dl>
     *     <dt>blockState</dt>
     *     <dd>爆炸方块 blockState 可通过 {@link World#getBlockState(BlockPos)} 获得</dd>
     *     <dt>world</dt>
     *     <dd>world 可通过 {@link PlayerEntity#world} 获得</dd>
     * </dl>
     *
     * @param player 玩家
     * @param pos    位置
     */
    void interact(@NotNull PlayerEntity player, @NotNull BlockPos pos);
}
