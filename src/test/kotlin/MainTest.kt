import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class MainTest {
    @Test
    fun assertions() {
        val content = readResource("HamcrestAssertionsTest.java")
        val processedContent = processContent(content)
        val expectedContent = readResource("HamcrestAssertionsTest_Result.java")
        assertThat(processedContent).isEqualTo(expectedContent)
    }

    @Test
    fun noStaticImports1() {
        // No static imports but using JUnit4 Assert.assertThat
        val content = readResource("HamcrestNoStaticImportsTest.java")
        val processedContent = processContent(content)
        val expectedContent = readResource("HamcrestNoStaticImportsTest_Result.java")
        assertThat(processedContent).isEqualTo(expectedContent)
    }

    @Test
    fun noStaticImports2() {
        // No static imports but using JUnit5 and newer Hamcrest classes
        val content = readResource("HamcrestNoStaticImportsTest2.java")
        val processedContent = processContent(content)
        val expectedContent = readResource("HamcrestNoStaticImportsTest2_Result.java")
        assertThat(processedContent).isEqualTo(expectedContent)
    }

    private fun readResource(name: String): String {
        val resource = this::class.java.getResource(name)
            ?: throw IllegalArgumentException("Resource not found: $name")
        resource.openStream().use { stream ->
            stream.bufferedReader().use { reader ->
                return reader.readText()
            }
        }
    }
}