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

import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.ConstructingObjectParser;
import org.elasticsearch.common.xcontent.ObjectParser;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.search.suggest.Suggest;

import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;

import static org.elasticsearch.common.xcontent.ConstructingObjectParser.constructorArg;


public final class KeyboardLayoutSuggestion extends Suggest.Suggestion<KeyboardLayoutSuggestion.Entry> {

    KeyboardLayoutSuggestion(String name, int size) {
        super(name, size);
    }

    public KeyboardLayoutSuggestion(StreamInput in) throws IOException {
        super(in);
    }

    @Override
    protected Comparator<Suggest.Suggestion.Entry.Option> sortComparator() {
        return FREQUENCY;
    }

    @Override
    public String getWriteableName() {
        return KeyboardLayoutSuggestionBuilder.SUGGESTION_NAME;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getWriteableType() {
        return TYPE;
    }

    @Override
    protected Entry newEntry(StreamInput in) throws IOException {
        return new Entry(in);
    }

    public static class Entry extends Suggest.Suggestion.Entry<KeyboardLayoutSuggestion.Entry.Option> {

        Entry() {
        }

        Entry(StreamInput in) throws IOException {
            super(in);
        }

        @Override
        protected Option newOption(StreamInput in) throws IOException {
            return new Option(in);
        }

        Entry(Text text, int offset, int length) {
            super(text, offset, length);
        }

        static Entry fromXContent(XContentParser parser) {
            return ENTRY_PARSER.apply(parser, null);
        }

        public static class Option extends Suggest.Suggestion.Entry.Option {
            private static Text EMPTY = new Text("_na_");

            static final ParseField TEXT_FIELD = new ParseField("text");
            static final ParseField FREQ_FIELD = new ParseField("freq");
            static final ParseField SWITCH_FIELD = new ParseField("switch");

            private final Text text;

            private int freq;
            private boolean switched;

            Option(Text text, int freq, boolean switched) {
                super(EMPTY, 0);
                this.text = text;
                this.freq = freq;
                this.switched = switched;
            }

            Option(StreamInput in) throws IOException {
                super(EMPTY, 0);
                text = in.readText();
                freq = in.readVInt();
                switched = in.readBoolean();
            }

            @Override
            public void writeTo(StreamOutput out) throws IOException {
                out.writeText(text);
                out.writeVInt(freq);
                out.writeBoolean(switched);
            }

            @Override
            protected void mergeInto(Suggest.Suggestion.Entry.Option otherOption) {
                freq += ((KeyboardLayoutSuggestion.Entry.Option) otherOption).freq;
            }

            @Override
            public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
                builder.field(TEXT.getPreferredName(), text);
                builder.field(FREQ_FIELD.getPreferredName(), freq);
                builder.field(SWITCH_FIELD.getPreferredName(), switched);
                return builder;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }

                Option that = (Option) o;
                return Objects.equals(text, that.text);
            }

            @Override
            public int hashCode() {
                return Objects.hash(text);
            }

            static Option fromXContent(XContentParser parser) {
                return OPTIONS_PARSER.apply(parser, null);
            }

            private static final ConstructingObjectParser<Option, Void> OPTIONS_PARSER = new ConstructingObjectParser<>(
                    "RussianKeyboardSuggestionOptionParser",
                    true,
                    args -> {
                        Text text = new Text((String) args[0]);
                        int freq = (Integer) args[1];
                        boolean switched = (Boolean) args[2];
                        return new Option(text, freq, switched);
                    });

            static {
                OPTIONS_PARSER.declareString(constructorArg(), TEXT_FIELD);
                OPTIONS_PARSER.declareInt(constructorArg(), FREQ_FIELD);
                OPTIONS_PARSER.declareBoolean(constructorArg(), SWITCH_FIELD);
            }
        }

        private static ObjectParser<Entry, Void> ENTRY_PARSER = new ObjectParser<>("KeyboardLayoutSuggestionEntryParser", true, Entry::new);

        static {
            declareCommonFields(ENTRY_PARSER);
            ENTRY_PARSER.declareObjectArray(Entry::addOptions, (p, c) -> Option.fromXContent(p), new ParseField(OPTIONS));
        }
    }

    public static class Frequency implements Comparator<Suggest.Suggestion.Entry.Option> {
        @Override
        public int compare(Suggest.Suggestion.Entry.Option first, Suggest.Suggestion.Entry.Option second) {
            int freqCmp = ((Entry.Option) second).freq - ((Entry.Option) first).freq;
            return freqCmp != 0 ? freqCmp : first.getText().compareTo(second.getText());
        }
    }

    private static final int TYPE = 152;
    private static final Comparator<Suggest.Suggestion.Entry.Option> FREQUENCY = new Frequency();

}
