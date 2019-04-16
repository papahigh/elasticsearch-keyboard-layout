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
 * KeyboardSwitcher for Russian/English keyboard layout
 */
public class RussianKeyboardSwitcher extends KeyboardSwitcher {

    private static final char[] charMapping = new char['№' + 1];

    @Override
    public char[] switchLayout(char[] source, int offset, int length, boolean replace) {
        if (source == null || source.length == 0) {
            return new char[0];
        }
        char[] switched = replace ? source : new char[length];
        for (int i = offset; i < offset + length; i++) {
            final char curr = source[i];
            if (curr > 0 && curr < charMapping.length) {
                char mapped = charMapping[curr];
                switched[i] = mapped != Character.MIN_VALUE ? mapped : curr;
            }
        }
        return switched;
    }

    static {

        charMapping['`'] = ']';
        charMapping['~'] = '[';
        charMapping['@'] = '"';
        charMapping['#'] = '№';
        charMapping['$'] = '%';
        charMapping['%'] = ':';
        charMapping['^'] = ',';
        charMapping['&'] = '.';
        charMapping['*'] = ';';
        charMapping['"'] = '@';
        charMapping['№'] = '#';
        charMapping['%'] = '$';
        charMapping[':'] = '%';
        charMapping[','] = '^';
        charMapping['.'] = '&';
        charMapping[';'] = '*';
        charMapping['q'] = 'й';
        charMapping['w'] = 'ц';
        charMapping['e'] = 'у';
        charMapping['r'] = 'к';
        charMapping['t'] = 'е';
        charMapping['y'] = 'н';
        charMapping['u'] = 'г';
        charMapping['i'] = 'ш';
        charMapping['o'] = 'щ';
        charMapping['p'] = 'з';
        charMapping['['] = 'х';
        charMapping[']'] = 'ъ';
        charMapping['Q'] = 'Й';
        charMapping['W'] = 'Ц';
        charMapping['E'] = 'У';
        charMapping['R'] = 'К';
        charMapping['T'] = 'Е';
        charMapping['Y'] = 'Н';
        charMapping['U'] = 'Г';
        charMapping['I'] = 'Ш';
        charMapping['O'] = 'Щ';
        charMapping['P'] = 'З';
        charMapping['{'] = 'Х';
        charMapping['}'] = 'Ъ';
        charMapping['й'] = 'q';
        charMapping['ц'] = 'w';
        charMapping['у'] = 'e';
        charMapping['к'] = 'r';
        charMapping['е'] = 't';
        charMapping['н'] = 'y';
        charMapping['г'] = 'u';
        charMapping['ш'] = 'i';
        charMapping['щ'] = 'o';
        charMapping['з'] = 'p';
        charMapping['х'] = '[';
        charMapping['ъ'] = ']';
        charMapping['Й'] = 'Q';
        charMapping['Ц'] = 'W';
        charMapping['У'] = 'E';
        charMapping['К'] = 'R';
        charMapping['Е'] = 'T';
        charMapping['Н'] = 'Y';
        charMapping['Г'] = 'U';
        charMapping['Ш'] = 'I';
        charMapping['Щ'] = 'O';
        charMapping['З'] = 'P';
        charMapping['Х'] = '{';
        charMapping['Ъ'] = '}';
        charMapping['a'] = 'ф';
        charMapping['s'] = 'ы';
        charMapping['d'] = 'в';
        charMapping['f'] = 'а';
        charMapping['g'] = 'п';
        charMapping['h'] = 'р';
        charMapping['j'] = 'о';
        charMapping['k'] = 'л';
        charMapping['l'] = 'д';
        charMapping[';'] = 'ж';
        charMapping['\''] = 'э';
        charMapping['\\'] = 'ё';
        charMapping['A'] = 'Ф';
        charMapping['S'] = 'Ы';
        charMapping['D'] = 'В';
        charMapping['F'] = 'А';
        charMapping['G'] = 'П';
        charMapping['H'] = 'Р';
        charMapping['J'] = 'О';
        charMapping['K'] = 'Л';
        charMapping['L'] = 'Д';
        charMapping[':'] = 'Ж';
        charMapping['"'] = 'Э';
        charMapping['|'] = 'Ё';
        charMapping['ф'] = 'a';
        charMapping['ы'] = 's';
        charMapping['в'] = 'd';
        charMapping['а'] = 'f';
        charMapping['п'] = 'g';
        charMapping['р'] = 'h';
        charMapping['о'] = 'j';
        charMapping['л'] = 'k';
        charMapping['д'] = 'l';
        charMapping['ж'] = ';';
        charMapping['э'] = '\'';
        charMapping['ё'] = '\\';
        charMapping['Ф'] = 'A';
        charMapping['Ы'] = 'S';
        charMapping['В'] = 'D';
        charMapping['А'] = 'F';
        charMapping['П'] = 'G';
        charMapping['Р'] = 'H';
        charMapping['О'] = 'J';
        charMapping['Л'] = 'K';
        charMapping['Д'] = 'L';
        charMapping['Ж'] = ':';
        charMapping['Э'] = '"';
        charMapping['Ё'] = '|';
        charMapping['z'] = 'я';
        charMapping['x'] = 'ч';
        charMapping['c'] = 'с';
        charMapping['v'] = 'м';
        charMapping['b'] = 'и';
        charMapping['n'] = 'т';
        charMapping['m'] = 'ь';
        charMapping[','] = 'б';
        charMapping['.'] = 'ю';
        charMapping['Z'] = 'Я';
        charMapping['X'] = 'Ч';
        charMapping['C'] = 'С';
        charMapping['V'] = 'М';
        charMapping['B'] = 'И';
        charMapping['N'] = 'Т';
        charMapping['M'] = 'Ь';
        charMapping['<'] = 'Б';
        charMapping['>'] = 'Ю';
        charMapping['я'] = 'z';
        charMapping['ч'] = 'x';
        charMapping['с'] = 'c';
        charMapping['м'] = 'v';
        charMapping['и'] = 'b';
        charMapping['т'] = 'n';
        charMapping['ь'] = 'm';
        charMapping['б'] = ',';
        charMapping['ю'] = '.';
        charMapping['Я'] = 'Z';
        charMapping['Ч'] = 'X';
        charMapping['С'] = 'C';
        charMapping['М'] = 'V';
        charMapping['И'] = 'B';
        charMapping['Т'] = 'N';
        charMapping['Ь'] = 'M';
        charMapping['Б'] = '<';
        charMapping['Ю'] = '>';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(RussianKeyboardSwitcher.class);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RussianKeyboardSwitcher;
    }

    @Override
    public String toString() {
        return "RussianKeyboardSwitcher{}";
    }
}
