package com.nesc.utilities;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class PreconditionsTest {

    @Test(expected = NullPointerException.class)
    public void testCheckNotNull() {
        checkNotNull(null);
    }

    private void checkNotNull(final List<String> list) {
        Preconditions.checkNotNull(list);
    }

    @Test
    public void testCheckNotNullWithMessage() {
        try {
            checkNotNullWithMessage(null);
        } catch (Exception e) {
            //assertThat(e, isA(NullPointerException.class));
            assertThat(e, instanceOf(NullPointerException.class));
            assertThat(e.getMessage(), equalTo("The list should not be null"));
        }
    }
    private void checkNotNullWithMessage(final List<String> list) {
        Preconditions.checkNotNull(list, "The list should not be null");
    }

    @Test
    public void testCheckNotNullWithFormatMessage() {
        try {
            checkNotNullWithFormatMessage(null);
        } catch (Exception e) {
            //assertThat(e, isA(NullPointerException.class));
            assertThat(e, instanceOf(NullPointerException.class));
            assertThat(e.getMessage(), equalTo("The list should not be null and the size must be 2, 3, 4, 5, 6"));
        }
    }
    private void checkNotNullWithFormatMessage(final List<String> list) {
        Preconditions.checkNotNull(list, "The list should not be null and the size must be %s, %s, %s, %s, %s", 2, 3, 4, 5, 6);
    }

    @Test
    public void testCheckArguments() {
        final String type = "A";
        try {
            Preconditions.checkArgument(type.equals("B"));
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void testCheckState() {
        try {
            final String state = "A";
            Preconditions.checkState(state.equals("B"), "The state is illegal.");
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalStateException.class));
            assertThat(e.getMessage(), equalTo("The state is illegal."));
        }
    }

    @Test
    public void checkElementIndex() {
        try {
            List<String> list = ImmutableList.of("1", "2", "3");
            Preconditions.checkElementIndex(4, list.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertThat(e, instanceOf(IndexOutOfBoundsException.class));
        }
    }

    @Test
    public void checkPositionIndex() {
        try {
            List<String> a = ImmutableList.of("1", "2", "3");
            // 和 checkElementIndex 区别, checkPositionIndex 可以是数组的长度。
            Preconditions.checkPositionIndex(4, a.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertThat(e, instanceOf(IndexOutOfBoundsException.class));
        }
    }

    @Test(expected = NullPointerException.class)
    public void testByObjects() {
        Objects.requireNonNull(null);
    }

    @Test(expected = AssertionError.class)
    public void testAssert() {
        List<String> list = null;
        assert list != null;
    }

    @Test
    public void testAssertWithMessage() {
        try {
            List<String> list = null;
            assert list != null : "The list should not be null ";
        } catch (Error e) {
            assertThat(e, instanceOf(AssertionError.class));
        }
    }
}
