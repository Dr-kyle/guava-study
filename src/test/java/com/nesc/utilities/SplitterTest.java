package com.nesc.utilities;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SplitterTest {

    @Test
    public void testSplitOnSplit() {
        List<String> result = Splitter.on("|").splitToList("hello|world");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }

    @Test
    public void testSplitOnSplit_OmitEmpty() {
        String test = "hello|world|||";
        List<String> result = Splitter.on("|").splitToList(test);
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(5));

        result = Splitter.on("|").omitEmptyStrings().splitToList(test);
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
    }

    @Test
    public void testSplit_On_Split_OmitEmpty_TrimResult() {
        String test = "hello | world || |";
        List<String> result = Splitter.on("|").omitEmptyStrings().splitToList(test);
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));

        result = Splitter.on("|").omitEmptyStrings().trimResults().splitToList(test);
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }

    @Test
    public void testSplit_On_Split_OmitEmpty_TrimResultS() {
        String test = "hello | world || ||";
        List<String> result = Splitter.on("|").omitEmptyStrings().trimResults().trimResults(CharMatcher.is('o')).splitToList(test);
        System.out.println(result);
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));

    }

    @Test
    public void testSplitFixLength() {
        String test = "aaabbbcccddd";
        List<String> result = Splitter.fixedLength(3).splitToList(test);
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(4));
        assertThat(result.get(0), equalTo("aaa"));
        assertThat(result.get(3), equalTo("ddd"));
    }

    @Test
    public void testSplitOnSplitLimit() {
        String test = "hello#world#java#hhh";
        List<String> result = Splitter.on("#").limit(3).splitToList(test);
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));
        assertThat(result.get(2), equalTo("java#hhh"));
    }

    @Test
    public void testSplitOnPatterString() {
        //language=RegExp
        String patter = "\\|";
        String test = "hello | world || |";
        List<String> result = Splitter.onPattern(patter).trimResults().omitEmptyStrings().splitToList(test);
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
    }

    @Test
    public void testSplitOnPatter() {
        String test = "hello | world || |";
        List<String> result = Splitter.on(Pattern.compile("\\|")).trimResults().omitEmptyStrings().splitToList(test);
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
    }

    @Test
    public void testSplitOnSplitToMap() {
        String test = "hello=HELLO | world=WORLD || |";
        Map<String, String> result = Splitter.on(Pattern.compile("\\|")).trimResults().omitEmptyStrings().withKeyValueSeparator("=").split(test);
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get("hello"), equalTo("HELLO"));
    }
}
