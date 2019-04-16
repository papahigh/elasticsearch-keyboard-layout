/*
 * Copyright 2019 Nikolay Papakha
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

import org.apache.commons.codec.EncoderException;
import org.apache.lucene.util.LuceneTestCase;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;

public class RussianKeyboardSwitcherTests extends LuceneTestCase {

    private KeyboardSwitcher getSwitcher() {
        return new RussianKeyboardSwitcher();
    }

    public void testEncodeObject() throws EncoderException {

        KeyboardSwitcher switcher = getSwitcher();

        assertEquals(KeyboardSwitcher.EMPTY_STRING, switcher.encode(null));
        assertEquals(KeyboardSwitcher.EMPTY_STRING, switcher.encode((String) null));
        assertEquals(KeyboardSwitcher.EMPTY_STRING, switcher.encode((Integer) null));
        assertEquals(KeyboardSwitcher.EMPTY_STRING, switcher.encode((Object) null));

        Throwable e = expectThrows(Throwable.class, () -> switcher.encode(123));
        assertThat(e, instanceOf(EncoderException.class));
        assertThat(e.getMessage(), containsString("Unsupported parameter type supplied to [KeyboardSwitcher]"));

        e = expectThrows(Throwable.class, () -> switcher.encode(getSwitcher()));
        assertThat(e, instanceOf(EncoderException.class));
        assertThat(e.getMessage(), containsString("Unsupported parameter type supplied to [KeyboardSwitcher]"));

    }

    public void testSwitchLayout() {

        KeyboardSwitcher switcher = getSwitcher();

        char[] firstRowEnglish = "qwertyuiop[]QWERTYUIOP{}".toCharArray();
        char[] firstRowRussian = "йцукенгшщзхъЙЦУКЕНГШЩЗХЪ".toCharArray();

        assertTrue(Arrays.equals(firstRowEnglish, switcher.switchLayout(firstRowRussian,0, firstRowRussian.length, false)));
        assertTrue(Arrays.equals(firstRowRussian, switcher.switchLayout(firstRowEnglish, 0, firstRowEnglish.length, false )));

        char[] secondRowEnglish = "asdfghjkl;'\\ASDFGHJKL:\"|".toCharArray();
        char[] secondRowRussian = "фывапролджэёФЫВАПРОЛДЖЭЁ".toCharArray();

        assertTrue(Arrays.equals(secondRowEnglish, switcher.switchLayout(secondRowRussian, 0, secondRowRussian.length, false)));
        assertTrue(Arrays.equals(secondRowRussian, switcher.switchLayout(secondRowEnglish, 0, secondRowEnglish.length, false)));

        char[] thirdRowEnglish = "zxcvbnm,.ZXCVBNM<>".toCharArray();
        char[] thirdRowRussian = "ячсмитьбюЯЧСМИТЬБЮ".toCharArray();

        assertTrue(Arrays.equals(thirdRowEnglish, switcher.switchLayout(thirdRowRussian,0,thirdRowRussian.length, false)));
        assertTrue(Arrays.equals(thirdRowRussian, switcher.switchLayout(thirdRowEnglish,0,thirdRowEnglish.length, false)));

    }

    public void testEncodeString() {
        KeyboardSwitcher switcher = getSwitcher();

        String firstRowEnglish = "qwertyuiop[]QWERTYUIOP{}";
        String firstRowRussian = "йцукенгшщзхъЙЦУКЕНГШЩЗХЪ";

        assertEquals(firstRowEnglish, switcher.encode(firstRowRussian));
        assertEquals(firstRowRussian, switcher.encode(firstRowEnglish));

        String secondRowEnglish = "asdfghjkl;'\\ASDFGHJKL:\"|";
        String secondRowRussian = "фывапролджэёФЫВАПРОЛДЖЭЁ";

        assertEquals(secondRowEnglish, switcher.encode(secondRowRussian));
        assertEquals(secondRowRussian, switcher.encode(secondRowEnglish));

        String thirdRowEnglish = "zxcvbnm,.ZXCVBNM<>";
        String thirdRowRussian = "ячсмитьбюЯЧСМИТЬБЮ";

        assertEquals(thirdRowEnglish, switcher.encode(thirdRowRussian));
        assertEquals(thirdRowRussian, switcher.encode(thirdRowEnglish));

    }

}