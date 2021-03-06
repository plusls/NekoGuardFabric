package cn.apisium.nekoguard.fabric.mixin;

import cn.apisium.nekoguard.fabric.PushHandler;
import cn.apisium.nekoguard.fabric.callback.EntityChangeBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"net.minecraft.entity.passive.RabbitEntity$EatCarrotCropGoal"})
public abstract class MixinRabbitEntity_EatCarrotCropGoal_EntityChangeBlock {

    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;get(Lnet/minecraft/state/property/Property;)Ljava/lang/Comparable;"))
    private <T extends Comparable<T>> T onEntityChangeBlock(BlockState blockState, Property<T> property){
        EntityChangeBlockCallback.EVENT.invoker().interact(null, blockState.getBlock());
        return (T) blockState.get(property);
    }

    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;syncWorldEvent(ILnet/minecraft/util/math/BlockPos;I)V"))
    private void onEntityChangeBlock(World world, int eventId, BlockPos pos, int data){
        EntityChangeBlockCallback.EVENT.invoker().interact(null, world.getBlockState(pos).getBlock());
        world.syncWorldEvent(eventId, pos, data);
    }
}
