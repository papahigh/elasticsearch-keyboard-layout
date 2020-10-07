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

import java.util.EnumMap;
import java.util.Locale;

public class KeyboardSwitcherProvider {

    private static final EnumMap<Languages, KeyboardSwitcher> cache = new EnumMap<>(Languages.class);

    public static KeyboardSwitcher provide(String language) {
        if (language == null) {
            throw new IllegalArgumentException("No language was provided");
        }
        try {
            Languages lang = Languages.valueOf(language.toUpperCase(Locale.ROOT));
            return cache.computeIfAbsent(lang, Languages::newInstance);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown language: " + language, e);
        }
    }
}
