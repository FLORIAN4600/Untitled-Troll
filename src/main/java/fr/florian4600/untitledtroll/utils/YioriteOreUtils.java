package fr.florian4600.untitledtroll.utils;

import fr.florian4600.untitledtroll.MainClass;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class YioriteOreUtils {

    public static final String defaultName = MainClass.getStringTranslation("block", "yiorite_ore");
    private static final float PARTICLE_SIZE = 1.0f;
    private static final Vector3f PARTICLE_COLOR = new Vector3f(0.1f, 1f, 0.05f);

    public YioriteOreUtils() {

    }

    public static void sendText(PlayerEntity player, String id, Text name) {
        player.sendMessage(Text.of("[§a" + (name == null ? Text.of(defaultName) : name.getString()) + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite."+id)));
    }

    public static void sendText(PlayerEntity player, String id, String name) {
        player.sendMessage(Text.of("[§a" + name + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite."+id)));
    }

    public static void sendText(PlayerEntity player, String id) {
        player.sendMessage(Text.of("[§a" + defaultName + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite."+id)));
    }

    public static Text getText(String id, Text name) {
        return Text.of("[§a" + (name == null ? Text.of(defaultName) : name.getString()) + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite."+id));
    }

    public static Text getText(String id, String name) {
        return Text.of("[§a" + name + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite."+id));
    }

    public static Text getText(String id) {
        return Text.of("[§a" + defaultName + "§r]:   " + MainClass.getStringTranslation("speech", "yiorite."+id));
    }

    public static void spawnParticles(World world, BlockPos pos, Vector3f color, Float size) {
        double d = 0.5625;
        Random random = world.random;
        Direction[] directions = Direction.values();

        for (Direction direction : directions) {
            BlockPos blockPos = pos.offset(direction);
            if (!world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos)) {
                Direction.Axis axis = direction.getAxis();
                double e = axis == Direction.Axis.X ? 0.5 + d * (double) direction.getOffsetX() : (double) random.nextFloat();
                double f = axis == Direction.Axis.Y ? 0.5 + d * (double) direction.getOffsetY() : (double) random.nextFloat();
                double g = axis == Direction.Axis.Z ? 0.5 + d * (double) direction.getOffsetZ() : (double) random.nextFloat();
                world.addParticle(new DustParticleEffect(color, size), (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, 0.0, 0.0, 0.0);
            }
        }

    }

    public static void spawnParticles(World world, BlockPos pos, Vector3f color) {
        spawnParticles(world, pos, color, PARTICLE_SIZE);
    }

    public static void spawnParticles(World world, BlockPos pos, Float size) {
        spawnParticles(world, pos, PARTICLE_COLOR, size);
    }

    public static void spawnParticles(World world, BlockPos pos) {
        spawnParticles(world, pos, PARTICLE_COLOR, PARTICLE_SIZE);
    }

    public static void spawnParticles(World world, BlockPos pos, int particleType) {
        switch (particleType) {
            case 1 -> spawnParticles(world, pos, new Vector3f(0.1f, 1f, 0.05f));
            case 2 -> spawnParticles(world, pos, new Vector3f(0.12f, 0.12f, 0.12f), 0.4f);
            default -> spawnParticles(world, pos);
        }
    }
}