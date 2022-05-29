package rabbitown.carrot.module.scheduler.event;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

/**
 * Timed event will occur after specified ticks
 * and repeat a certain number of times.<p>
 *
 * Field {@link #times}, {@link #interval} can be changed during its career,
 * for example when it is needed to lengthen interval each time the event is fired.
 *
 * @author Milkory
 */
public class TimedEvent implements Event {

    /** Max times to repeat, -1 for unlimited */
    @Getter @Setter private int times;

    /** Ticks to wait between two event fires */
    @Getter @Setter private long interval;

    /** Times the event have been fired */
    @Getter private int currentTimes = 0;

    /** The internal timer to control when to fire event */
    @Getter private int timer;

    @Getter private final Consumer<TimedEvent> consumer;

    public TimedEvent(Consumer<TimedEvent> consumer, int times, long interval, int startOffset) {
        this.consumer = consumer;
        this.times = times;
        this.interval = interval;
        this.timer = startOffset;
    }

    /** Construct with a {@link Runnable}, which ignored the provided event instance. */
    public TimedEvent(Runnable runnable, int times, long interval, int startOffset) {
        this(it -> runnable.run(), times, interval, startOffset);
    }

    @Override public final boolean run() {
        timer++;
        if (timer >= interval) {
            timer = 0;
            currentTimes++;
            consumer.accept(this);
            return currentTimes < times;
        }
        return true;
    }
}
