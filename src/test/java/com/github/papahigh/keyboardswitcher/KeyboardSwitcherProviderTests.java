/*
 * Copyright 2020 Nikolay Papakha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.papahigh.keyboardswitcher;

import org.apache.lucene.util.LuceneTestCase;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;

public class KeyboardSwitcherProviderTests extends LuceneTestCase {

    public void testCaseInsensitivity() {
        KeyboardSwitcher switcher1 = KeyboardSwitcherProvider.provide("RUSSIAN");
        KeyboardSwitcher switcher2 = KeyboardSwitcherProvider.provide("russian");
        KeyboardSwitcher switcher3 = KeyboardSwitcherProvider.provide("Russian");
        KeyboardSwitcher switcher4 = KeyboardSwitcherProvider.provide("RuSsIaN");

        assertEquals(switcher1.getClass(), RussianKeyboardSwitcher.class);
        assertEquals(switcher2.getClass(), RussianKeyboardSwitcher.class);
        assertEquals(switcher3.getClass(), RussianKeyboardSwitcher.class);
        assertEquals(switcher4.getClass(), RussianKeyboardSwitcher.class);
    }

    public void testRussianSwitcher() {
        KeyboardSwitcher switcher = KeyboardSwitcherProvider.provide("russian");
        assertEquals(switcher.getClass(), RussianKeyboardSwitcher.class);
    }

    public void testUkrainianSwitcher() {
        KeyboardSwitcher switcher = KeyboardSwitcherProvider.provide("ukrainian");
        assertEquals(switcher.getClass(), UkrainianKeyboardSwitcher.class);
    }

    public void testBelarusianSwitcher() {
        KeyboardSwitcher switcher = KeyboardSwitcherProvider.provide("belarusian");
        assertEquals(switcher.getClass(), BelarusianKeyboardSwitcher.class);
    }

    public void testNullLanguage() {
        Throwable e = expectThrows(Throwable.class, () -> KeyboardSwitcherProvider.provide((String) null));
        assertThat(e, instanceOf(IllegalArgumentException.class));
        assertThat(e.getMessage(), containsString("No language was provided"));
    }

    public void testUnknownLanguage() {
        Throwable e = expectThrows(Throwable.class, () -> KeyboardSwitcherProvider.provide("QwerTYUioIUYTdfgHJK"));
        assertThat(e, instanceOf(IllegalArgumentException.class));
        assertThat(e.getMessage(), containsString("Unknown language: QwerTYUioIUYTdfgHJK"));
    }
}