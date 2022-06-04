package rabbitown.carrot.module.scheduler.event;

import lombok.Getter;
import lombok.Setter;
import rabbitown.carrot.util.Watcher;

import java.util.List;
import java.util.function.Supplier;

/**
 * Conditional events will occur when some conditions are
 *
 * @author Milkory
 */
public abstract class ConditionalEvent implements Event {

    @Getter boolean alive = true;

    @Getter private final List<CWatcher<?>> watchers;

    public ConditionalEvent(List<CWatcher<?>> watchers) {
        for (CWatcher<?> watcher : watchers) {
            watcher.setEvent(this);
        }
        this.watchers = watchers;
    }

    @Override public final boolean tick() {
        if (!alive) return false;
        for (CWatcher<?> watcher : watchers) {
            watcher.tick();
        }
        return true;
    }

    public final void destroy() {
        alive = false;
    }

    static abstract class CWatcher<T> extends Watcher<T> {

        @Getter @Setter ConditionalEvent event;

        public CWatcher(Supplier<T> supplier) {
            super(supplier);
        }

        @Override public final void trigger(T oldValue) {
            trigger(event, oldValue);
        }

        public abstract void trigger(ConditionalEvent event, T oldValue);

    }

}
