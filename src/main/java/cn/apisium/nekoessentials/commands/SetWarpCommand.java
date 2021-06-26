package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import cn.apisium.nekoessentials.utils.DatabaseSingleton;
import cn.apisium.nekoessentials.utils.Serializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashSet;

@CommandName("setwarp")
public final class SetWarpCommand extends BasicCommand {
    public static final String WARPS = "warps";

    @SuppressWarnings("unchecked")
    public SetWarpCommand(Main main) {
        super(main);
        final byte[] bytes = DatabaseSingleton.INSTANCE.get(WARPS);
        if (bytes != null) try {
            main.warps.addAll((HashSet<String>) Serializer.deserializeObject(bytes));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean callback(CommandSender sender, String[] args) {
        if (!(sender instanceof final Player p) || args.length < 1) return false;
        DatabaseSingleton.INSTANCE.set("warp." + args[0], Serializer.serializeLocation(p.getLocation()));
        instance.warps.add(args[0]);
        try {
            DatabaseSingleton.INSTANCE.set(WARPS, Serializer.serializeObject(instance.warps));
        } catch (IOException e) {
            e.printStackTrace();
            p.sendMessage("��cδ֪������鿴��������־!");
        }
        p.sendMessage("��a�ɹ����õر�!");
        return true;
    }
}
