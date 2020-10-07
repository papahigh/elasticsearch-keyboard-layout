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
package org.elasticsearch.search.suggest.keyboard;

import com.github.papahigh.keyboardswitcher.KeyboardSwitcherProvider;
import org.elasticsearch.ElasticsearchParseException;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.ParsingException;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.index.query.QueryShardContext;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.SuggestionSearchContext;

import java.io.IOException;
import java.util.Objects;


public final class KeyboardLayoutSuggestionBuilder extends SuggestionBuilder<KeyboardLayoutSuggestionBuilder> {

    public static final String SUGGESTION_NAME = "keyboard_layout";

    private static final ParseField LANGUAGE_FIELD = new ParseField("language");
    private static final ParseField MAX_FREQ_FIELD = new ParseField("max_freq");
    private static final ParseField MIN_FREQ_FIELD = new ParseField("min_freq");
    private static final ParseField LOWERCASE_TOKEN_FIELD = new ParseField("lowercase_token");
    private static final ParseField ADD_ORIGINAL_FIELD = new ParseField("add_original");
    private static final ParseField PRESERVE_CASE_FIELD = new ParseField("preserve_case");

    private String language;
    private double minFreq = 0d;
    private double maxFreq = -1d;
    private boolean lowercaseToken = false;
    private boolean addOriginal = false;
    private boolean preserveCase = false;

    private KeyboardLayoutSuggestionBuilder(String field) {
        super(field);
    }

    public KeyboardLayoutSuggestionBuilder(StreamInput in) throws IOException {
        super(in);
        language = in.readString();
        minFreq = in.readDouble();
        maxFreq = in.readDouble();
        lowercaseToken = in.readBoolean();
        preserveCase = in.readBoolean();
        addOriginal = in.readBoolean();
    }

    private KeyboardLayoutSuggestionBuilder(String field, KeyboardLayoutSuggestionBuilder in) {
        super(field);
        language = in.language;
        analyzer = in.analyzer;
        text = in.text;
        minFreq = in.minFreq;
        maxFreq = in.maxFreq;
        lowercaseToken = in.lowercaseToken;
        preserveCase = in.preserveCase;
        addOriginal = in.addOriginal;
    }

    @Override
    protected void doWriteTo(StreamOutput out) throws IOException {
        out.writeString(language);
        out.writeDouble(minFreq);
        out.writeDouble(maxFreq);
        out.writeBoolean(lowercaseToken);
        out.writeBoolean(preserveCase);
        out.writeBoolean(addOriginal);
    }

    @Override
    protected XContentBuilder innerToXContent(XContentBuilder builder, Params params) throws IOException {
        builder.field(LANGUAGE_FIELD.getPreferredName(), language);
        builder.field(MIN_FREQ_FIELD.getPreferredName(), minFreq);
        builder.field(MAX_FREQ_FIELD.getPreferredName(), maxFreq);
        builder.field(LOWERCASE_TOKEN_FIELD.getPreferredName(), lowercaseToken);
        builder.field(PRESERVE_CASE_FIELD.getPreferredName(), preserveCase);
        builder.field(ADD_ORIGINAL_FIELD.getPreferredName(), addOriginal);
        return builder;
    }


    @Override
    protected SuggestionSearchContext.SuggestionContext build(QueryShardContext context) {
        KeyboardLayoutSuggestionContext suggestionContext = new KeyboardLayoutSuggestionContext(context,
                KeyboardSwitcherProvider.provide(language), minFreq, maxFreq, lowercaseToken, preserveCase, addOriginal);
        populateCommonFields(context.getMapperService(), suggestionContext);
        return suggestionContext;
    }

    @Override
    public String getWriteableName() {
        return SUGGESTION_NAME;
    }

    @Override
    protected boolean doEquals(KeyboardLayoutSuggestionBuilder other) {
        return Objects.equals(language, other.language) &&
                Objects.equals(minFreq, other.minFreq) &&
                Objects.equals(maxFreq, other.maxFreq) &&
                Objects.equals(lowercaseToken, other.lowercaseToken) &&
                Objects.equals(preserveCase, other.preserveCase) &&
                Objects.equals(addOriginal, other.addOriginal);
    }

    @Override
    protected int doHashCode() {
        return Objects.hash(language, minFreq, maxFreq, lowercaseToken, preserveCase, addOriginal);
    }

    private void minFreq(double minFreq) {
        if (minFreq < 0.0d) {
            throw new IllegalArgumentException("minDocFreq must be positive");
        }
        if (minFreq > 1.0d && minFreq != Math.floor(minFreq)) {
            throw new IllegalArgumentException("if minDocFreq is greater than 1, it must not be a fraction");
        }
        this.minFreq = minFreq;
    }

    private void maxFreq(double maxFreq) {
        if (maxFreq < 0.0d) {
            throw new IllegalArgumentException("maxFreq must be positive");
        }
        if (maxFreq > 1.0d && maxFreq != Math.floor(maxFreq)) {
            throw new IllegalArgumentException("if maxFreq is greater than 1, it must not be a fraction");
        }
        this.maxFreq = maxFreq;
    }

    private void lowercaseToken(boolean lowercaseToken) {
        this.lowercaseToken = lowercaseToken;
    }

    private void addOriginal(boolean addOriginal) {
        this.addOriginal = addOriginal;
    }

    private void preserveCase(boolean preserveCase) {
        this.preserveCase = preserveCase;
    }

    private void language(String language) {
        this.language = language;
    }

    public static KeyboardLayoutSuggestionBuilder fromXContent(XContentParser parser) throws IOException {

        KeyboardLayoutSuggestionBuilder tmpValuesHolder = new KeyboardLayoutSuggestionBuilder("_na_");
        tmpValuesHolder.analyzer("keyboard_analyzer");

        XContentParser.Token token;
        String currentFieldName = "";
        String fieldName = null;

        while ((token = parser.nextToken()) != XContentParser.Token.END_OBJECT) {
            if (token == XContentParser.Token.FIELD_NAME) {
                currentFieldName = parser.currentName();
            } else if (token.isValue()) {
                if (FIELDNAME_FIELD.match(currentFieldName, parser.getDeprecationHandler())) {
                    fieldName = parser.text();
                } else if (ANALYZER_FIELD.match(currentFieldName, parser.getDeprecationHandler())) {
                    tmpValuesHolder.analyzer(parser.text());
                } else if (TEXT_FIELD.match(currentFieldName, parser.getDeprecationHandler())) {
                    tmpValuesHolder.text(parser.text());
                } else if (SIZE_FIELD.match(currentFieldName, parser.getDeprecationHandler())) {
                    tmpValuesHolder.size(parser.intValue());
                } else if (SHARDSIZE_FIELD.match(currentFieldName, parser.getDeprecationHandler())) {
                    tmpValuesHolder.shardSize(parser.intValue());
                } else if (MAX_FREQ_FIELD.match(currentFieldName, parser.getDeprecationHandler())) {
                    tmpValuesHolder.maxFreq(parser.doubleValue());
                } else if (MIN_FREQ_FIELD.match(currentFieldName, parser.getDeprecationHandler())) {
                    tmpValuesHolder.minFreq(parser.doubleValue());
                } else if (ADD_ORIGINAL_FIELD.match(currentFieldName, parser.getDeprecationHandler())) {
                    tmpValuesHolder.addOriginal(parser.booleanValue());
                } else if (LOWERCASE_TOKEN_FIELD.match(currentFieldName, parser.getDeprecationHandler())) {
                    tmpValuesHolder.lowercaseToken(parser.booleanValue());
                } else if (PRESERVE_CASE_FIELD.match(currentFieldName, parser.getDeprecationHandler())) {
                    tmpValuesHolder.preserveCase(parser.booleanValue());
                } else if (LANGUAGE_FIELD.match(currentFieldName, parser.getDeprecationHandler())) {
                    tmpValuesHolder.language(parser.text());
                } else {
                    throw new ParsingException(parser.getTokenLocation(),
                            "suggester[" + SUGGESTION_NAME + "] doesn't support field [" + currentFieldName + "]");
                }

            } else {
                throw new ParsingException(parser.getTokenLocation(), "suggester[" + SUGGESTION_NAME + " ] " +
                        "parsing failed on [" + currentFieldName + "]");
            }
        }

        if (fieldName == null) {
            throw new ElasticsearchParseException(
                    "the required field option [" + FIELDNAME_FIELD.getPreferredName() + "] is missing");
        }

        return new KeyboardLayoutSuggestionBuilder(fieldName, tmpValuesHolder);
    }
}
