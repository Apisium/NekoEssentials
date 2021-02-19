package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import cn.apisium.nekoessentials.utils.Pair;
import org.bukkit.GameMode;
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
        if (((Player) sender).getGameMode() == GameMode.SPECTATOR && !sender.isOp()) {
            sender.sendMessage("��c���л�������ģʽ�ٲ���!");
            return true;
        }
        final Pair<Long, Runnable> p = instance.playerTasks.remove(sender);
        if (p != null && p.left > System.currentTimeMillis()) {
            p.right.run();
            sender.sendMessage("��a���ܳɹ�!");
        } else sender.sendMessage("��c��ǰû�д�����Ĵ�������!");
        return true;
    }
}
