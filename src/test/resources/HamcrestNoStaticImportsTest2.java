package com.acme.somewhere;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public class HamcrestAssertionsNoStaticImportsTest2 {
    @Test
    public void noStaticImports() {
        MatcherAssert.assertThat(1, Is.is(1));
        MatcherAssert.assertThat(2, IsEqual.equalTo(2));
        MatcherAssert.assertThat(3, Matchers.is(3));
        MatcherAssert.assertThat(4, CoreMatchers.is(4));
        MatcherAssert.assertThat(5, IsNot.not(6));
        MatcherAssert.assertThat(6, IsNull.notNullValue());
    }
}