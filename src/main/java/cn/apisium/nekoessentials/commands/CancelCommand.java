package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandName("tpcancel")
public final class CancelCommand extends BasicCommand {
    public CancelCommand(Main main) {
        super(main);
    }

    @Override
    public boolean callback(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return false;
        sender.sendMessage(instance.cancelTeleport((Player) sender) ? "��e���������ѱ�ȡ��!" : "��c��ǰû�д�������!");
        return true;
    }
}
