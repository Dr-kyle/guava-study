package com.nesc.utilites;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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

    private final String tagetFileName = "D:\\ideawork\\guava-study\\file\\guava-joiner.txt";

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

        try (FileWriter writer = new FileWriter(new File(tagetFileName))) {
            Joiner.on("#").useForNull("default").appendTo(writer, stringListWithNullValue);
            assertThat(Files.isFile().test(new File(tagetFileName)), equalTo(true));
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
}
