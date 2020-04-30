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
package org.elasticsearch.plugin;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.AnalyzerProvider;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.index.analysis.keyboard.KeyboardAnalyzerProvider;
import org.elasticsearch.index.analysis.keyboard.KeyboardLayoutTokenizerFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.SearchPlugin;
import org.elasticsearch.search.suggest.keyboard.KeyboardLayoutSuggestion;
import org.elasticsearch.search.suggest.keyboard.KeyboardLayoutSuggestionBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;


public class KeyboardLayoutPlugin extends Plugin implements SearchPlugin, AnalysisPlugin {

    @Override
    public List<SearchPlugin.SuggesterSpec<?>> getSuggesters() {
        return Collections.singletonList(
                new SearchPlugin.SuggesterSpec<>(
                        KeyboardLayoutSuggestionBuilder.SUGGESTION_NAME,
                        KeyboardLayoutSuggestionBuilder::new,
                        KeyboardLayoutSuggestionBuilder::fromXContent,
                        KeyboardLayoutSuggestion::new
                )
        );
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        return singletonMap("keyboard_analyzer", KeyboardAnalyzerProvider::new);
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
        return singletonMap("keyboard_tokenizer", KeyboardLayoutTokenizerFactory::new);
    }
}
