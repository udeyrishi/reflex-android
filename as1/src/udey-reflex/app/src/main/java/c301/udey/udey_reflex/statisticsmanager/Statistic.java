/*
 * Copyright (C) 2015 Udey Rishi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package c301.udey.udey_reflex.statisticsmanager;

/**
 * A model for a statistic.
 */
public class Statistic<T> {

    private String message;
    private T value;

    /**
     * Creates a new instance of {@link Statistic}.
     *
     * @param message The statistic's message/descriptor.
     * @param value   The value associated to a statistic.
     */
    public Statistic(String message, T value) {
        setMessage(message);
        setValue(value);
    }

    /**
     * Gets the message.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message The message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the value.
     *
     * @return The value.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value The value.
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Creates a human friendly string describing the statistic.
     *
     * @return The string describing the statistic.
     */
    @Override
    public String toString() {
        String valueString;
        if (value == null) {
            valueString = "N/A";
        } else if (value instanceof Double) {
            valueString = String.format("%.2f", (Double) value);
        } else if (value instanceof Float) {
            valueString = String.format("%.2f", (Float) value);
        } else {
            valueString = value.toString();
        }

        return message + ": " + valueString;
    }
}
