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
            sender.sendMessage("§c请切换至生存模式再操作!");
            return true;
        }
        final Pair<Long, Runnable> p = instance.playerTasks.remove(sender);
        if (p != null && p.left > System.currentTimeMillis()) {
            p.right.run();
            sender.sendMessage("§a接受成功!");
        } else sender.sendMessage("§c当前没有待处理的传送请求!");
        return true;
    }
}
