package team.mixxit.allotment.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import team.mixxit.allotment.interf.IFlowerDyeProvider;
import team.mixxit.allotment.setup.ModDamageSources;

public class TallThistleBlock extends TallFlowerBlock implements IFlowerDyeProvider {
    public TallThistleBlock(Properties properties) {
        super(properties);
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.FOX && entityIn.getType() != EntityType.BEE) {
            //entityIn.setMotionMultiplier(state, new Vector3d((double)0.8F, 0.75D, (double)0.8F));
            entityIn.setMotionMultiplier(state, new Vector3d((double)0.8F, 1D, (double)0.8F));
            if (!worldIn.isRemote && (entityIn.lastTickPosX != entityIn.getPosX() || entityIn.lastTickPosZ != entityIn.getPosZ())) {
                double d0 = Math.abs(entityIn.getPosX() - entityIn.lastTickPosX);
                double d1 = Math.abs(entityIn.getPosZ() - entityIn.lastTickPosZ);
                if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
                    entityIn.attackEntityFrom(ModDamageSources.TALL_PHISTLE, 1.0F);
                }
            }

        }
    }

    @Override
    public DyeItem getDye() {
        return (DyeItem) Items.PINK_DYE;
    }
}
