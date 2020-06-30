package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import cn.apisium.nekoessentials.utils.Pair;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandName("tpaccept")
public final class TpAcceptCommand extends BasicCommand {
    public TpAcceptCommand(Main main) {
        super(main);
    }

    @Override
    public boolean callback(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return false;
        final Pair<Long, Runnable> p = instance.playerTasks.get(sender);
        if (p != null) {
            instance.playerTasks.remove(sender);
            if (p.left > System.currentTimeMillis()) {
                p.right.run();
                return true;
            }
        }
        sender.sendMessage("��c��ǰû�д�����Ĵ�������!");
        return true;
    }
}
