package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import cn.apisium.nekoessentials.utils.DatabaseSingleton;
import cn.apisium.nekoessentials.utils.Serializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandName("delwarp")
public final class DelWarpCommand extends BasicCommand {
    public static final String WARPS = "warps";

    public DelWarpCommand(Main main) {
        super(main);
    }

    @Override
    public boolean callback(CommandSender sender, String[] args) {
        if (!(sender instanceof final Player p) || args.length < 1) return false;
        if (DatabaseSingleton.INSTANCE.get("warp." + args[0]) == null) {
            p.sendMessage("��c�õر겻���ڻ��ѱ�ɾ��!");
            return false;
        }
        DatabaseSingleton.INSTANCE.delete("warp." + args[0]);
        instance.warps.remove(args[0]);
        try {
            DatabaseSingleton.INSTANCE.set(WARPS, Serializer.serializeObject(instance.warps));
        } catch (IOException e) {
            e.printStackTrace();
            p.sendMessage("��cδ֪������鿴��������־!");
        }
        p.sendMessage("��a�ɹ�ɾ���ر�!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return args.length == 1 ? new ArrayList<>(instance.warps) : Collections.emptyList();
    }
}
