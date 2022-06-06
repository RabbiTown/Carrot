package rabbitown.carrot.util;

import lombok.Getter;

import java.util.function.Supplier;

/**
 * A watcher can be used to listen to the change of a field.
 *
 * @author Milkory
 */
public abstract class Watcher<T> {

    @Getter Supplier<T> supplier;
    @Getter T oldValue;

    public Watcher(Supplier<T> supplier) {
        this.supplier = supplier;
        oldValue = supplier.get();
    }

    public final void tick() {
        var newValue = supplier.get();
        if (newValue != oldValue) {
            trigger(oldValue);
            oldValue = newValue;
        }
    }

    public abstract void trigger(T oldValue);

}
