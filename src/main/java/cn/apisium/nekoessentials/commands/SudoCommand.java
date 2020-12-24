package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

@CommandName("sudo")
public final class SudoCommand extends BasicCommand {
    public SudoCommand(Main main) {
        super(main);
    }

    @Override
    public boolean callback(CommandSender sender, String[] args) {
        if (!(sender instanceof Player) || args.length < 2) return false;
        final Player p = instance.getPlayer(sender, args[0]);
        if (p == null) return true;
        if (p.hasPermission("nekoess.sudo.avoid")) {
            sender.sendMessage("��c������Ŀ�������ִ�е�ǰָ��!");
            return true;
        }
        sender.sendMessage(p.performCommand(String.join(" ", Arrays.copyOfRange(args, 1, args.length))) ? "��aִ�гɹ�!" : "��cִ��ʧ��!");
        return true;
    }
}
