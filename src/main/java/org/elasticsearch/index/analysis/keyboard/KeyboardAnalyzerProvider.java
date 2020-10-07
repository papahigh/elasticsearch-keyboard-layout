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
package org.elasticsearch.index.analysis.keyboard;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;


public class KeyboardAnalyzerProvider extends AbstractIndexAnalyzerProvider<WhitespaceAnalyzer> {

    private final WhitespaceAnalyzer analyzer;

    public KeyboardAnalyzerProvider(IndexSettings indexSettings, Environment env,
                                    String name, Settings settings) {
        super(indexSettings, name, settings);
        analyzer = new WhitespaceAnalyzer();
        analyzer.setVersion(version);
    }

    @Override
    public WhitespaceAnalyzer get() {
        return this.analyzer;
    }
}
