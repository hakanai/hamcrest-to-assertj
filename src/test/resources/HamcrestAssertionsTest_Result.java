package com.acme.somewhere;

import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.hamcrest.collection.IsArrayWithSize.emptyArray;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsEmptyCollection.emptyCollectionOf;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterableOf;
import static org.hamcrest.collection.IsIn.isIn;
import static org.hamcrest.collection.IsIn.isOneOf;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.collection.IsMapContaining.hasValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.hamcrest.core.IsSame.theInstance;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.hamcrest.object.IsCompatibleType.typeCompatibleWith;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.hamcrest.MatcherAssert.assertThat;

public class HamcrestAssertionsTest {
    private static final String NULL_STRING = null;

    @Test
    public void simple() {
        assertThat(1).isEqualTo(1);
        assertThat(1).isEqualTo(1);
        assertThat(1).isEqualTo(1);

        assertThat(1).isNotEqualTo(2);
        assertThat(1).isNotEqualTo(2);
        assertThat(1).isNotEqualTo(2);
        assertThat(1).isNotEqualTo(2);
    }

    @Test
    public void numbers() {
        assertThat(2).isLessThan(3);
        assertThat(2).isLessThan(3);
        assertThat(2).isGreaterThanOrEqualTo(2);
        assertThat(2).isGreaterThanOrEqualTo(2);

        assertThat(3).isGreaterThan(2);
        assertThat(3).isGreaterThan(2);
        assertThat(3).isLessThanOrEqualTo(3);
        assertThat(3).isLessThanOrEqualTo(3);

        assertThat(1.0).isCloseTo(2.0, within(1.0));
        assertThat(1.0).isCloseTo(2.0, within(1.0));

        assertThat(2).isLessThanOrEqualTo(2);
        assertThat(2).isLessThanOrEqualTo(3);
        assertThat(4).isGreaterThan(3);
        assertThat(4).isGreaterThan(3);

        assertThat(3).isGreaterThanOrEqualTo(3);
        assertThat(3).isGreaterThanOrEqualTo(2);
        assertThat(1).isLessThan(2);
        assertThat(1).isLessThan(2);
    }

    @Test
    public void strings() {
        assertThat("string").isEqualToIgnoringCase("STRING");
        assertThat("string").isEqualToIgnoringCase("STRING");
        assertThat("string").isNotEqualToIgnoringCase("STRIN");
        assertThat("string").isNotEqualToIgnoringCase("STRIN");

        assertThat("string").startsWith("s");
        assertThat("string").doesNotStartWith("g");

        assertThat("string").endsWith("g");
        assertThat("string").doesNotEndWith("s");

        assertThat("string").contains("i");
        assertThat("string").doesNotContain("q");
    }

    @Test
    public void emptyStrings() {
        assertThat("").isEmpty();
        assertThat("a").isNotEmpty();

        assertThat(NULL_STRING).isNullOrEmpty();
        assertThat("a").isNotEmpty();
    }

    @Test
    public void values() {
        assertThat(NULL_STRING).isNull();
        assertThat(NULL_STRING).isNull();
        assertThat("").isNotNull();
        assertThat("").isNotNull();

        assertThat("").isNotNull();
        assertThat("").isNotNull();

        assertThat("").isInstanceOf(String.class);
        assertThat("").isInstanceOf(String.class);
        assertThat("").isNotInstanceOf(Math.class);
        assertThat("").isNotInstanceOf(Math.class);

        assertThat("").isInstanceOf(String.class);

        assertThat("").isSameAs("");
        assertThat("").isSameAs("");
        assertThat("").isNotSameAs("a");
        assertThat("").isNotSameAs("a");

        assertThat("").isSameAs("");
        assertThat("").isSameAs("");
        assertThat("").isNotSameAs("a");
        assertThat("").isNotSameAs("a");

        assertThat("").isIn(asList("", "a"));
        assertThat("").isNotIn(asList("a", "b"));

        assertThat("").isIn("", "a");
        assertThat("").isNotIn("a", "b");
    }

    @Test
    public void iterables() {
        assertThat(asList(0, 1)).contains(0);
        assertThat(asList(0, 1)).doesNotContain(2);

        assertThat(asList(0, 1)).contains(0, 1);
        assertThat(asList(0, 1)).doesNotContain(2, 3);

        assertThat(emptyList()).isEmpty();
        assertThat(emptyList()).isEmpty();
        assertThat(asList(0, 1)).isNotEmpty();
        assertThat(asList(0, 1)).isNotEmpty();

        assertThat(emptyList()).isEmpty();
        assertThat(emptyList()).isEmpty();
        assertThat(asList(0, 1)).isNotEmpty();
        assertThat(asList(0, 1)).isNotEmpty();

        assertThat(asList(0, 1)).containsExactly(0, 1);
        assertThat(asList(0, 1)).doesNotContain(2);

        assertThat(asList(0, 1)).containsOnly(1, 0);

        assertThat(asList(new Object(), new Object())).hasSize(2);
        assertThat(asList(new Object(), new Object())).hasSize(2);
    }

    @Test
    public void collections() {
        assertThat(emptyList()).isEmpty();
        assertThat(emptyList()).isEmpty();
        assertThat(asList(0, 1)).isNotEmpty();
        assertThat(asList(0, 1)).isNotEmpty();

        assertThat(emptyList()).isEmpty();
        assertThat(emptyList()).isEmpty();
        assertThat(asList(0, 1)).isNotEmpty();
        assertThat(asList(0, 1)).isNotEmpty();

        assertThat(asList(0, 1)).hasSize(2);
    }

    @Test
    public void maps() {
        assertThat(singletonMap(0, 1)).containsEntry(0, 1);
        assertThat(singletonMap(0, 1)).doesNotContainEntry(1, 1);

        assertThat(singletonMap(0, 1)).containsKey(0);
        assertThat(singletonMap(0, 1)).doesNotContainKey(1);

        assertThat(singletonMap(0, 1)).containsValue(1);
        assertThat(singletonMap(0, 1)).doesNotContainValue(0);
    }

    @Test
    public void arrays() {
        assertThat(new Object[]{}).isEmpty();
        assertThat(new Object[]{}).isEmpty();
        assertThat(new Object[]{1}).isNotEmpty();
        assertThat(new Object[]{1}).isNotEmpty();

        assertThat(new Object[]{1}).hasSize(1);
        assertThat(new Object[]{1}).hasSize(1);

        assertThat(new Integer[]{1, 2}).contains(1);
        assertThat(new Integer[]{1}).doesNotContain(2);

        assertThat(new Integer[]{1, 2}).containsExactly(1, 2);
        assertThat(new Integer[]{1, 2}).containsExactly(1, 2);

        assertThat(new Integer[]{1, 2}).containsOnly(2, 1);
        assertThat(new Integer[]{1, 2}).containsOnly(2, 1);
    }

    @Test
    public void specialCases() {
        assertThat(true).isTrue();
        assertThat(false).isFalse();

        assertThat("").isEmpty();

        assertThat(NULL_STRING).isNull();
        assertThat(emptyList()).isNotNull();

        assertThat(emptyList()).isEmpty();
        assertThat(emptyList()).isEmpty();

        assertThat(new Object[]{}).isEmpty();
        assertThat(new Object[]{}).isEmpty();

        assertThat(singletonList("")).hasSize(1);

        assertThat(0).isZero();
    }

    @Test
    public void multiline() {
        assertThat(1).isEqualTo(1);
        assertThat(2).isEqualTo(2);
        assertThat(3).isEqualTo(3);
        assertThat(4).isEqualTo(4);
        assertThat(5).isEqualTo(5);
    }
}
