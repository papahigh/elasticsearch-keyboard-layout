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


import java.util.Objects;

/**
 * KeyboardSwitcher for Belarusian/English keyboard layout
 */
public class BelarusianKeyboardSwitcher extends KeyboardSwitcher {

    private static final char[] charMappings;

    @Override
    public char[] getCharMappings() {
        return charMappings;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(RussianKeyboardSwitcher.class);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BelarusianKeyboardSwitcher;
    }

    @Override
    public String toString() {
        return "BelarusianKeyboardSwitcher{}";
    }


    static {

        charMappings = new char['ў' + 1];

        String eng = "qwertyuiop[QWERTYUIOP{asdfghjkl;'ASDFGHJKL:\"zxcvbnm,.ZXCVBNM<>";
        String by = "йцукенгшўзхЙЦУКЕНГШЎЗХфывапролджэФЫВАПРОЛДЖЭячсмітьбюЯЧСМІТЬБЮ";

        for (int i = 0; i < eng.length(); i++) {
            charMappings[eng.charAt(i)] = by.charAt(i);
            charMappings[by.charAt(i)] = eng.charAt(i);
        }

    }

}
