package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@CommandName("disrobe")
public final class DisrobeCommand extends TeleportCommand {
    public DisrobeCommand(final Main main) {
        super(main);
    }

    @Override
    public void doTeleport(final CommandSender sender, final Player p, boolean now) {
        final PlayerInventory pi = p.getInventory();
        final World world = p.getWorld();
        final Location loc = p.getLocation();
        for (final ItemStack it : pi.getArmorContents()) {
            if (it == null) continue;
            world.dropItem(loc, it);
            pi.remove(it);
        }
        sender.sendMessage("��a�����ɹ�!");
    }
}
