package cn.apisium.nekoguard.fabric.mixin.util.world.explosion;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Explosion.class)
public abstract class MixinExplosion implements IMixinExplosion {

    @Final
    @Shadow
    private List<BlockPos> affectedBlocks;

    @Final
    @Shadow
    private World world;

    // 用于记录被摧毁的方块
    private List<BlockPos> destroyedPosList;
    private List<BlockState> destroyedStateList;

    @Override
    public List<BlockPos> getDestroyedPosList() {
        return this.destroyedPosList;
    }

    @Override
    public void setDestroyedPosList(List<BlockPos> destroyedPosList) {
        this.destroyedPosList = destroyedPosList;
    }

    @Override
    public List<BlockState> getDestroyedStateList() {
        return this.destroyedStateList;
    }

    @Override
    public void setDestroyedStateList(List<BlockState> destroyedStateList) {
        this.destroyedStateList = destroyedStateList;
    }

    // 用于在 affectWorld 方法调用前初始化 destroyedPosList, destroyedPosList
    @Inject(method = "affectWorld",
            at = @At(value = "HEAD"))
    private void preAffectWorld(boolean bl, CallbackInfo ci) {
        destroyedPosList = new ArrayList<>();
        destroyedStateList = new ArrayList<>();
    }

    // 用于记录被摧毁的方块列表
    @Inject(method = "affectWorld",
            at = @At(value = "INVOKE",
                    target = "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;<init>()V",
                    ordinal = 0))
    private void logDestroyedBlock(boolean bl, CallbackInfo ci) {
        this.affectedBlocks.forEach(pos -> {
            BlockState state = this.world.getBlockState(pos);
            if (!state.isAir()) {
                this.destroyedPosList.add(pos);
                this.destroyedStateList.add(state);
            }
        });
    }
}
