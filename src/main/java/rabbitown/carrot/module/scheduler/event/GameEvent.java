package rabbitown.carrot.module.scheduler.event;

import lombok.RequiredArgsConstructor;

/**
 * @author Milkory
 */
@RequiredArgsConstructor
public class GameEvent implements Event {

    private final Event event;

    @Override public boolean run() {
        return event.run();
    }

}
