package com.nesc.utilites;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Concatenate string together with a specified delimiter
 */
public class JoinerTest {
    private final List<String> stringList = Arrays.asList(
            "google","guava", "java", "scala", "kafka"
    );

    private final List<String> stringListWithNullValue = Arrays.asList(
            "google","guava", null, "scala", "kafka"
    );

    private final List<String> stringListWithEmptyValue = Arrays.asList(
            "google","guava", null, "", "kafka"
    );

    private final String targetFileName = "D:\\ideawork\\guava-study\\file\\guava-joiner.txt";

    @Test
    public void testJoinOnJoin() {
        String result = Joiner.on("#").join(stringList);
        assertThat(result, equalTo("google#guava#java#scala#kafka"));
    }

    @Test(expected = NullPointerException.class)
    public void testJoinOnJoinWithNullValue() {
        String result = Joiner.on("#").join(stringListWithNullValue);
        assertThat(result, equalTo("google#guava#java#scala#kafka"));
    }

    @Test
    public void testJoinOnJoinWithEmptyValue() {
        String result = Joiner.on("#").skipNulls().join(stringListWithEmptyValue);
        assertThat(result, equalTo("google#guava##kafka"));
    }

    @Test
    public void testJoinOnJoinWithNullValueButSkip() {
        String result = Joiner.on("#").skipNulls().join(stringListWithNullValue);
        assertThat(result, equalTo("google#guava#scala#kafka"));
    }

    @Test
    public void testJoinOnJoinWithNullValueButUseDefaultValue() {
        String result = Joiner.on("#").useForNull("default").join(stringListWithNullValue);
        assertThat(result, equalTo("google#guava#default#scala#kafka"));
    }

    @Test
    public void testJoin_On_Append_To_StringBuilder() {
        final StringBuilder builder = new StringBuilder();
        StringBuilder  resultBuilder = Joiner.on("#").useForNull("default").appendTo(builder, stringListWithNullValue);
        assertThat(resultBuilder, sameInstance(builder));
        assertThat(resultBuilder.toString(), equalTo("google#guava#default#scala#kafka"));
        assertThat(builder.toString(), equalTo("google#guava#default#scala#kafka"));
    }

    @Test
    public void testJoin_On_Append_To_Writer() {

        try (FileWriter writer = new FileWriter(new File(targetFileName))) {
            Joiner.on("#").useForNull("default").appendTo(writer, stringListWithNullValue);
            assertThat(Files.isFile().test(new File(targetFileName)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error.");
            e.printStackTrace();
        }
    }

    @Test
    public void testJoiningByStream() {
        String result = stringListWithNullValue.stream().filter(item -> null != item && !item.isEmpty()).collect(Collectors.joining("#"));
        assertThat(result, equalTo("google#guava#scala#kafka"));
    }

    @Test
    public void testJoiningByStreamWithDeafultValue() {
        /*
            map 传入function 返回function
         */
        String result = stringListWithNullValue.stream().map(this::defaultValue).collect(Collectors.joining("#"));
        assertThat(result, equalTo("google#guava#default#scala#kafka"));
    }

    private String defaultValue(final String item) {
        return null == item || item.isEmpty() ? "default" : item;
    }

    private final Map<String, String> stringMap = ImmutableMap.of("Hello", "Guava", "Java", "Scala");

    private final String targetFileNameToMap = "D:\\ideawork\\guava-study\\file\\guava-joiner-map.txt";

    @Test
    public void testJoinOnWithMap() {
        assertThat(Joiner.on('#').withKeyValueSeparator("=").join(stringMap), equalTo("Hello=Guava#Java=Scala"));
    }

    @Test
    public void testJoinOnWithMapToAppendable() {
        try (FileWriter writer = new FileWriter(new File(targetFileNameToMap))){
            Joiner.on("#").withKeyValueSeparator("=").appendTo(writer, stringMap);
            assertThat(Files.isFile().test(new File(targetFileNameToMap)), equalTo(true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
