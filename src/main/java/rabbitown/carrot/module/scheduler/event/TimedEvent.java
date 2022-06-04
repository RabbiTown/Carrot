package rabbitown.carrot.module.scheduler.event;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

/**
 * Timed event will occur after specified ticks
 * and repeat a certain number of times.<p>
 * <p>
 * Field {@link #times}, {@link #interval} can be changed during its career,
 * for example when it is needed to lengthen interval each time the event is fired.
 *
 * @author Milkory
 */
public abstract class TimedEvent implements Event {

    /** Max times to repeat, -1 for unlimited */
    @Getter @Setter private int times;

    /** Ticks to wait between two event fires */
    @Getter @Setter private long interval;

    /** Times the event have been fired */
    @Getter private int currentTimes = 0;

    /** The internal timer to control when to fire event */
    @Getter private int timer;

    public abstract void occur(TimedEvent event);

    public TimedEvent(int times, long interval, int startOffset) {
        this.times = times;
        this.interval = interval;
        this.timer = startOffset;
    }

    @Override public final boolean tick() {
        timer++;
        if (timer >= interval) {
            timer = 0;
            currentTimes++;
            occur(this);
            return currentTimes < times;
        }
        return true;
    }

    public static TimedEvent fromConsumer(Consumer<TimedEvent> consumer, int times, long interval, int startOffset) {
        return new TimedEvent(times, interval, startOffset) {
            @Override public void occur(TimedEvent event) {
                consumer.accept(event);
            }
        };
    }

    public static TimedEvent fromRunnable(Runnable runnable, int times, long interval, int startOffset) {
        return new TimedEvent(times, interval, startOffset) {
            @Override public void occur(TimedEvent event) {
                runnable.run();
            }
        };
    }

}
