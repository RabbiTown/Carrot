package rabbitown.carrot.module.scheduler;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import rabbitown.carrot.module.scheduler.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Milkory
 */
public class Scheduler {

    @Getter private final List<Event> events = new ArrayList<>();

    private final BukkitRunnable ticker = new BukkitRunnable() {
        @Override public void run() {
            events.removeIf(it -> !it.tick());
        }
    };

    public Scheduler(Plugin plugin) {
        ticker.runTaskTimer(plugin, 0, 1);
    }

    public void schedule(Event event) {
        events.add(event);
    }

    public void destroy() {
        ticker.cancel();
    }

    public boolean isAlive() {
        return !ticker.isCancelled();
    }

}
