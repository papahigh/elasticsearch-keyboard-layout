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
package org.elasticsearch.index.analysis.keyboard;

import com.github.papahigh.keyboardswitcher.KeyboardSwitcher;
import com.github.papahigh.keyboardswitcher.RussianKeyboardSwitcher;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.phonetic.PhoneticFilter;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;


public class KeyboardLayoutTokenFilterFactory extends AbstractTokenFilterFactory {

    private final boolean replace;
    private final KeyboardSwitcher switcher;

    public KeyboardLayoutTokenFilterFactory(IndexSettings indexSettings,
                                            Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);
        this.replace = settings.getAsBoolean("replace", true);
        this.switcher = new RussianKeyboardSwitcher();
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new PhoneticFilter(tokenStream, switcher, !replace);
    }
}
