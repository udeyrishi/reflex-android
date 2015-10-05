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

package c301.udey.udey_reflex;

/**
 * An interface for a class that contains multiple fragments as sections, and it wants to listen
 * to these fragment's attach events.
 * Should be used in combination with {@link SectionFragment}.
 */
public interface SectionHolder {
    /**
     * Callback for the section attached event of a {@link android.support.v4.app.Fragment}.
     *
     * @param sectionNumber The fragments section number.
     */
    void onSectionAttached(int sectionNumber);
}
