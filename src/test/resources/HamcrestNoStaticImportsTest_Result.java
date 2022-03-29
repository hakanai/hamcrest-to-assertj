package com.acme.somewhere;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Test;

public class HamcrestAssertionsNoStaticImportsTest {
    @Test
    public void noStaticImports() {
        assertThat(1).isEqualTo(1);
        assertThat(2).isEqualTo(2);
        assertThat(3).isEqualTo(3);
        assertThat(4).isEqualTo(4);
        assertThat(5).isNotEqualTo(6);
        assertThat(6).isNotNull();
    }
}