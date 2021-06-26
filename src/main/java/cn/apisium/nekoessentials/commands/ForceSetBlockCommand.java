package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

@CommandName("forcesetblock")
public final class ForceSetBlockCommand extends BasicCommand {
    private final static ArrayList<String> list = new ArrayList<>();
    static {
        for (Material it : Material.values()) list.add(it.getKey().toString());
    }
    public ForceSetBlockCommand(Main main) {
        super(main);
    }

    @Override
    public boolean callback(CommandSender sender, String[] args) {
        if (args.length != 4 || !(sender instanceof Player)) return false;
        try {
            final Material type = Material.matchMaterial(args[3]);
            if (type == null) sender.sendMessage("§c找不到对应的方块ID!");
            else ((Player) sender).getWorld()
                    .getBlockAt(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]))
                    .setType(type, false);
            return true;
        } catch (final Exception ignored) {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();
        final Block block = ((Player) sender).getTargetBlockExact(6);
        return switch (args.length) {
            case 1 -> block == null ? Collections.emptyList() : Collections.singletonList(Integer.toString(block.getX()));
            case 2 -> block == null ? Collections.emptyList() : Collections.singletonList(Integer.toString(block.getY()));
            case 3 -> block == null ? Collections.emptyList() : Collections.singletonList(Integer.toString(block.getZ()));
            case 4 -> list;
            default -> Collections.emptyList();
        };
    }
}
