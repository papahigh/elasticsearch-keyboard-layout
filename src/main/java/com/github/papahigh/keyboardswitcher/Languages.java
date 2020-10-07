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


/**
 * Represents an enumeration of all supported keyboard layouts
 * and their mapping to the implementation class.
 */
public enum Languages {

    RUSSIAN("RussianKeyboardSwitcher"),

    UKRAINIAN("UkrainianKeyboardSwitcher"),

    BELARUSIAN("BelarusianKeyboardSwitcher");
    

    private final String clazzName;

    Languages(String clazzName) {
        this.clazzName = clazzName;
    }

    public KeyboardSwitcher newInstance() {
        try {
            Class<? extends KeyboardSwitcher> switcherClass = Class.forName("com.github.papahigh.keyboardswitcher."
                    + clazzName).asSubclass(KeyboardSwitcher.class);
            return switcherClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
