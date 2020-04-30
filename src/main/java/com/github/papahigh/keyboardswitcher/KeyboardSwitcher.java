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

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

import java.nio.charset.Charset;


/**
 * Encodes an input string into its switched variant
 * according to a keyboard layout.
 */
public abstract class KeyboardSwitcher implements StringEncoder {

    static String EMPTY_STRING = new String("".getBytes(Charset.defaultCharset()), Charset.defaultCharset());

    protected abstract char[] getCharMappings();

    public char[] switchLayout(char[] source, int offset, int length, boolean replace) {
        if (source == null || source.length == 0) {
            return new char[0];
        }
        char[] switched = replace ? source : new char[length];
        char[] charMappings = getCharMappings();
        for (int i = offset; i < offset + length; i++) {
            final char curr = source[i];
            if (curr > 0 && curr < charMappings.length) {
                char mapped = charMappings[curr];
                switched[i] = mapped != Character.MIN_VALUE ? mapped : curr;
            }
        }
        return switched;
    }

    @Override
    public String encode(String source) {
        int length;
        if (source == null || (length = source.length()) == 0) {
            return EMPTY_STRING;
        }
        char[] chars = source.toCharArray();
        return new String(switchLayout(chars, 0, length, true));
    }

    @Override
    public Object encode(Object source) throws EncoderException {
        if (source instanceof String) {
            return encode((String) source);
        }
        if (source == null) {
            return EMPTY_STRING;
        }
        throw new EncoderException("Unsupported parameter type supplied to [KeyboardSwitcher]");
    }

}
