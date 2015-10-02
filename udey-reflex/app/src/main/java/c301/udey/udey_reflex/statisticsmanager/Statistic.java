package c301.udey.udey_reflex.statisticsmanager;

/**
 * Created by rishi on 15-10-01.
 */
public class Statistic<T> {

    private String message;
    private T value;

    public Statistic(String message, T value) {
        setMessage(message);
        setValue(value);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return message + ": " + (value == null ? "N/A" : value.toString());
    }
}
