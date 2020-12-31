package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import cn.apisium.nekoessentials.utils.DatabaseSingleton;
import cn.apisium.nekoessentials.utils.Serializer;
import cn.apisium.nekoessentials.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandName("warp")
public final class WarpCommand extends BasicCommand {
    public WarpCommand(Main main) {
        super(main);
    }

    @Override
    public boolean callback(CommandSender sender, String[] args) {
        if (!(sender instanceof Player) || args.length < 1) return false;
        final byte[] bytes = DatabaseSingleton.INSTANCE.get("warp." + args[0]);
        if (bytes == null) sender.sendMessage("��c�õر겻����!");
        else {
            Utils.teleportPlayer((Player) sender, Serializer.deserializeLocation(bytes));
            sender.sendMessage("��a���ͳɹ�!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return args.length == 1 ? new ArrayList<>(instance.warps) : Collections.emptyList();
    }
}
