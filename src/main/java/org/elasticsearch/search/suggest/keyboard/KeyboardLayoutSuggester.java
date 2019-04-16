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
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefBuilder;
import org.apache.lucene.util.CharsRefBuilder;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.suggest.Suggester;
import org.elasticsearch.search.suggest.phrase.DirectCandidateGenerator;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;


public class KeyboardLayoutSuggester extends Suggester<KeyboardLayoutSuggestionContext> {

    static KeyboardLayoutSuggester INSTANCE = new KeyboardLayoutSuggester();

    private KeyboardLayoutSuggester() {
    }

    @Override
    protected KeyboardLayoutSuggestion innerExecute(String name, KeyboardLayoutSuggestionContext context,
                                                    IndexSearcher searcher, CharsRefBuilder spare) throws IOException {
        KeyboardLayoutSuggestion accumulator = new KeyboardLayoutSuggestion(name, context.getSize());
        SuggestionsGenerator generator = new SuggestionsGenerator(searcher.getIndexReader(), accumulator, context);
        DirectCandidateGenerator.analyze(context.getAnalyzer(), context.getText(), context.getField(), generator, spare);
        return accumulator;
    }

    static class SuggestionsGenerator extends DirectCandidateGenerator.TokenConsumer {

        final IndexReader ir;
        final String field;
        final KeyboardSwitcher switcher;
        final KeyboardLayoutSuggestion acc;
        final boolean lowercaseToken;
        final boolean preserveCase;
        final boolean addOriginal;

        private SuggestionsGenerator(IndexReader ir, KeyboardLayoutSuggestion acc, KeyboardLayoutSuggestionContext context) {
            this.ir = ir;
            this.acc = acc;
            this.field = context.getField();
            this.switcher = context.switcher;
            this.lowercaseToken = context.lowercaseToken;
            this.preserveCase = context.preserveCase;
            this.addOriginal = context.addOriginal;
        }

        @Override
        public void nextToken() throws IOException {

            BytesRef originalRef = BytesRef.deepCopyOf(fillBytesRef(new BytesRefBuilder()));
            KeyboardLayoutSuggestion.Entry suggestion = newEntry(originalRef, offsetAttr);

            Term originalTerm = new Term(field, originalRef);

            String token = originalTerm.text();

            int length = token.length();
            char[] tokenChars = token.toCharArray();
            char[] tokenCharsCased = lowercaseToken
                    ? token.toLowerCase(Locale.ROOT).toCharArray()
                    : tokenChars;


            char[] tokenCharsCasedAndSwitched = switcher.switchLayout(tokenCharsCased, 0, length, false);

            if (!Arrays.equals(tokenCharsCased, tokenCharsCasedAndSwitched)) {

                BytesRef switchedFreqCountingRef = toBytesRef(tokenCharsCasedAndSwitched);
                int docFreq = ir.docFreq(new Term(field, switchedFreqCountingRef));

                if (docFreq > 0) {

                    BytesRef optionValueRef = lowercaseToken && preserveCase
                            ? toBytesRef(switcher.switchLayout(tokenChars, 0, length, false))
                            : switchedFreqCountingRef;

                    suggestion.addOption(newSwitchedOption(optionValueRef, docFreq));

                    if (addOriginal) {
                        int originalCasedFreq = ir.docFreq(
                                lowercaseToken ? new Term(field, toBytesRef(tokenCharsCased)) : originalTerm
                        );
                        suggestion.addOption(newOriginalOption(originalRef, originalCasedFreq));
                    }

                }
            }

            acc.addTerm(suggestion);
        }
    }

    private static BytesRef toBytesRef(char[] chars) {
        BytesRefBuilder builder = new BytesRefBuilder();
        builder.copyChars(chars, 0, chars.length);
        return BytesRef.deepCopyOf(builder.get());
    }

    private static KeyboardLayoutSuggestion.Entry.Option newSwitchedOption(BytesRef ref, int freq) {
        return new KeyboardLayoutSuggestion.Entry.Option(new Text(new BytesArray(ref)), freq, true);
    }

    private static KeyboardLayoutSuggestion.Entry.Option newOriginalOption(BytesRef ref, int freq) {
        return new KeyboardLayoutSuggestion.Entry.Option(new Text(new BytesArray(ref)), freq, false);
    }

    private static KeyboardLayoutSuggestion.Entry newEntry(BytesRef bytes, OffsetAttribute offsetAttr) {
        return new KeyboardLayoutSuggestion.Entry(new Text(new BytesArray(bytes)), offsetAttr.startOffset(),
                offsetAttr.endOffset() - offsetAttr.startOffset());
    }
}
