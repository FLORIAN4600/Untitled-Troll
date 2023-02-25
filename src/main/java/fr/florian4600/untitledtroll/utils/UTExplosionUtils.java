package fr.florian4600.untitledtroll.utils;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.joml.Vector3d;

public class UTExplosionUtils {

    public UTExplosionUtils() {

    }

    public static Explosion explodeOnEntity(World world, Entity entity, float power, World.ExplosionSourceType explosionType) {
        return world.createExplosion(null, entity.getX(), entity.getY(), entity.getZ(), power, explosionType);
    }

    public static Explosion explodeBeneathEntity(World world, Entity entity, double YOffset, float power, World.ExplosionSourceType explosionType) {
        return world.createExplosion(null, entity.getX(), entity.getY()+YOffset, entity.getZ(), power, explosionType);
    }

    public static Explosion explodeOnPos(World world, Vector3d pos, float power, World.ExplosionSourceType explosionType) {
        return world.createExplosion(null, pos.x, pos.y, pos.z, power, explosionType);
    }

}
