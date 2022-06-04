package rabbitown.carrot.module.scheduler.event;

/**
 * The base event will be run every tick.
 *
 * @author Milkory
 * @see TimedEvent
 */
public interface Event {

    /** @return To keep the event in tick or not */
    boolean tick();

}
