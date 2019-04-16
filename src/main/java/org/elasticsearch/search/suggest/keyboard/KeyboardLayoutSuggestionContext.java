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
package org.elasticsearch.search.suggest.keyboard;

import com.github.papahigh.keyboardswitcher.KeyboardSwitcher;
import org.elasticsearch.index.query.QueryShardContext;
import org.elasticsearch.search.suggest.SuggestionSearchContext;

final class KeyboardLayoutSuggestionContext extends SuggestionSearchContext.SuggestionContext {

    final KeyboardSwitcher switcher;

    final double minFreq;
    final double maxFreq;
    final boolean lowercaseToken;
    final boolean addOriginal;
    final boolean preserveCase;

    KeyboardLayoutSuggestionContext(QueryShardContext shardContext, KeyboardSwitcher switcher,
                                    double minFreq, double maxFreq, boolean lowercaseToken, boolean preserveCase, boolean addOriginal) {
        super(KeyboardLayoutSuggester.INSTANCE, shardContext);
        this.switcher = switcher;
        this.minFreq = minFreq;
        this.maxFreq = maxFreq;
        this.lowercaseToken = lowercaseToken;
        this.preserveCase = preserveCase;
        this.addOriginal = addOriginal;
    }

    @Override
    public String toString() {
        return "KeyboardSwitchSuggestionContext[" +
                ", switcher=" + switcher +
                ", minFreq=" + minFreq +
                ", maxFreq=" + maxFreq +
                ", lowercaseToken=" + lowercaseToken +
                ", addOriginal=" + addOriginal +
                ", preserveCase=" + preserveCase +
                ", context=" + super.toString() +
                "]";
    }

}
