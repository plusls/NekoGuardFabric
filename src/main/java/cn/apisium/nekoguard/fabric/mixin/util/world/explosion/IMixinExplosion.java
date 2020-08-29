package cn.apisium.nekoguard.fabric.mixin.util.world.explosion;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface IMixinExplosion {

    List<BlockPos> getDestroyedPosList();
    void setDestroyedPosList(List<BlockPos> destroyedPosList);

    List<BlockState> getDestroyedStateList();
    void setDestroyedStateList(List<BlockState> destroyedStateList);

}
