import java.io.File
import java.io.IOException
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println("usage: hamcrest-to-assertj <path to root dir>")
        exitProcess(1)
    }

    val rootDir = args[0]

    try {
        File(rootDir).walk()
            .filter { file -> file.extension == "java" }
            .forEach { file ->
                val content = file.readText()
                if (content.contains("hamcrest")) {
                    val newContent = processContent(content)
                    if (newContent != content) {
                        file.writeText(newContent)
                    }
                }
            }
    } catch (e: IOException) {
        System.err.println("Error walking directory: $rootDir")
        e.printStackTrace()
        exitProcess(1)
    }
}

internal fun processContent(contentIn: String): String {
    var content = contentIn
    content = performReplacements(content)
    content = performFixes(content)
    content = addStaticImports(content)
    return content
}

// Optional pattern used for many match patterns below.
// Match group in here becomes match 3.
private const val argsPattern = """\(([^;]+)\)"""

private val replacementRegexes = mapOf(
    """hasSize$argsPattern""" to "hasSize($2",
    """iterableWithSize$argsPattern""" to "hasSize($2",
    """arrayWithSize$argsPattern""" to "hasSize($2",
    """arrayWithSize$argsPattern""" to "hasSize($2",
    """emptyCollectionOf$argsPattern""" to "isEmpty(",
    """not\(emptyCollectionOf$argsPattern\)""" to "isNotEmpty(",
    """emptyIterableOf$argsPattern""" to "isEmpty(",
    """not\(emptyIterableOf$argsPattern\)""" to "isNotEmpty(",
    """emptyIterable\(""" to "isEmpty(",
    """not\(emptyIterable\(""" to "isNotEmpty(",
    """emptyArray\(""" to "isEmpty(",
    """not\(emptyArray\(""" to "isNotEmpty(",
    """empty\(""" to "isEmpty(",
    """isEmptyString\(""" to "isEmpty(",
    """not\(isEmptyString\(""" to "isNotEmpty(",
    """isEmptyOrNullString\(""" to "isNullOrEmpty(",
    """not\(isEmptyOrNullString\(""" to "isNotEmpty(",
    """not\(empty\(""" to "isNotEmpty(",
    """notNullValue\(""" to "isNotNull(",
    """not\(nullValue\(""" to "isNotNull(",
    """nullValue\(""" to "isNull(",
    """nullValue$argsPattern""" to "isNull(",
    """containsString$argsPattern""" to "contains($2",
    """not\(containsString$argsPattern\)""" to "doesNotContain($2",
    """startsWith$argsPattern""" to "startsWith($2",
    """not\(startsWith$argsPattern\)""" to "doesNotStartWith($2",
    """endsWith$argsPattern""" to "endsWith($2",
    """not\(endsWith$argsPattern\)""" to "doesNotEndWith($2",
    """containsInAnyOrder$argsPattern""" to "containsOnly($2",
    """arrayContaining$argsPattern""" to "containsExactly($2",
    """not\(arrayContaining$argsPattern\)""" to "doesNotContain($2",
    """arrayContainingInAnyOrder$argsPattern""" to "containsOnly($2",
    """hasItems$argsPattern""" to "contains($2",
    """not\(hasItems$argsPattern\)""" to "doesNotContain($2",
    """contains$argsPattern""" to "containsExactly($2",
    """not\(contains$argsPattern\)""" to "doesNotContain($2",
    """isIn$argsPattern""" to "isIn($2",
    """isOneOf$argsPattern""" to "isIn($2",
    """not\(isOneOf$argsPattern\)""" to "isNotIn($2",
    """not\(isIn$argsPattern\)""" to "isNotIn($2",
    """hasItem$argsPattern""" to "contains($2",
    """hasItemInArray$argsPattern""" to "contains($2",
    """not\(hasItemInArray$argsPattern\)""" to "doesNotContain($2",
    """not\(hasItem$argsPattern\)""" to "doesNotContain($2",
    """hasKey$argsPattern""" to "containsKey($2",
    """not\(hasKey$argsPattern\)""" to "doesNotContainKey($2",
    """hasValue$argsPattern""" to "containsValue($2",
    """not\(hasValue$argsPattern\)""" to "doesNotContainValue($2",
    """hasEntry$argsPattern""" to "containsEntry($2",
    """not\(hasEntry$argsPattern\)""" to "doesNotContainEntry($2",
    """equalToIgnoringCase$argsPattern""" to "isEqualToIgnoringCase($2",
    """not\(equalToIgnoringCase$argsPattern\)""" to "isNotEqualToIgnoringCase($2",
    """sameInstance$argsPattern""" to "isSameAs($2",
    """not\(sameInstance$argsPattern\)""" to "isNotSameAs($2",
    """not\(is\(sameInstance$argsPattern\)\)""" to "isNotSameAs($2",
    // These were in the original but don't produce the right assertion
    //"""typeCompatibleWith$argsPattern""" to "isInstanceOf($2",
    //"""not\(typeCompatibleWith$argsPattern\)""" to "isAssignableFrom($2",
    """instanceOf$argsPattern""" to "isInstanceOf($2",
    """not\(instanceOf$argsPattern\)""" to "isNotInstanceOf($2",
    """isA$argsPattern""" to "isInstanceOf($2",
    """not\(isA$argsPattern\)""" to "isNotInstanceOf($2",
    """theInstance$argsPattern""" to "isSameAs($2",
    """not\(theInstance$argsPattern\)""" to "isNotSameAs($2",
    """closeTo\(([^,]+),\s+([^;]+)\)""" to "isCloseTo($2, within($3)",
    """lessThanOrEqualTo$argsPattern""" to "isLessThanOrEqualTo($2",
    """not\(lessThanOrEqualTo$argsPattern\)""" to "isGreaterThan($2",
    """lessThan$argsPattern""" to "isLessThan($2",
    """not\(lessThan$argsPattern\)""" to "isGreaterThanOrEqualTo($2",
    """greaterThanOrEqualTo$argsPattern""" to "isGreaterThanOrEqualTo($2",
    """not\(greaterThanOrEqualTo$argsPattern\)""" to "isLessThan($2",
    """greaterThan$argsPattern""" to "isGreaterThan($2",
    """not\(greaterThan$argsPattern\)""" to "isLessThanOrEqualTo($2",
    """not\(equalTo$argsPattern\)""" to "isNotEqualTo($2",
    """not$argsPattern""" to "isNotEqualTo($2",
    """equalTo$argsPattern""" to "isEqualTo($2",
    """is\(not$argsPattern\)""" to "isNotEqualTo($2",
    """not\(is$argsPattern\)""" to "isNotEqualTo($2",
    """is$argsPattern""" to "isEqualTo($2",
).flatMap { (findFragment, replaceFragment) ->
    sequenceOf(
        buildFindRegex(findFragment) to buildReplacement(replaceFragment),
        buildFindRegex("""is\(\s*$findFragment\s*\)""") to buildReplacement(replaceFragment),
    )
}.toMap()

private fun buildFindRegex(findFragment: String): Regex {
    // IDEA workaround, seems like it doesn't notice the COMMENTS flag was used?
    @Suppress("RegExpRepeatedSpace")
    return Regex("""
        (?:(?:Assert|MatcherAssert)\.)?                             # Optional static classes for assertThat
        assertThat\(
        \s*
        ([^;]*?)                                                    # Group 1, the object being asserted
        ,
        \s+
        (?:(?:Is|Matchers|CoreMatchers|IsEqual|IsNot|IsNull)\.)?    # Optional static classes for matcher
        $findFragment
        \s*\)+;
    """.trimIndent(), RegexOption.COMMENTS)
}

private fun buildReplacement(replaceFragment: String): String {
    return "assertThat(\$1).$replaceFragment);"
}

private fun performReplacements(contentIn: String): String {
    var content = contentIn
    replacementRegexes.forEach { (regex, replacement) ->
        content = content.replace(regex, replacement)
    }
    return content
}

private val fixRegexes = mapOf(
    Regex("""isEqualTo\(null\)""") to "isNull()",
    Regex("""isNotEqualTo\(null\)""") to "isNotNull()",
    Regex("""isEqualTo\(true\)""") to "isTrue()",
    Regex("""isEqualTo\(false\)""") to "isFalse()",
    Regex("""isEqualTo\(0\)""") to "isZero()",
    Regex("""hasSize\(0\)""") to "isEmpty()",
    Regex("""isEqualTo\(""\)""") to "isEmpty()",
    Regex(""".size\(\)\).isZero\(\)""") to ").isEmpty()",
    Regex(""".length\).isZero\(\)""") to ").isEmpty()",
    Regex(""".size\(\)\).isEqualTo\(([^;]+)\)""") to ").hasSize($1)"
)

private fun performFixes(contentIn: String): String {
    var content = contentIn
    fixRegexes.forEach { (regex, replacement) ->
        content = content.replace(regex, replacement)
    }
    return content
}

private fun addStaticImports(contentIn: String): String {
    var content = contentIn
    if (content.contains(Regex("""\bassertThat\("""))) {
        content = addStaticImport(content, "org.assertj.core.api.Assertions.assertThat")
    }
    if (content.contains(Regex("""\bwithin\("""))) {
        content = addStaticImport(content, "org.assertj.core.api.Assertions.within")
    }
    return content
}

private fun addStaticImport(content: String, import: String): String {
    if (content.contains("import $import;")) {
        return content
    }

    // Matches any newline sequence but then uses a matching group to add the right newline to the output
    val nl = "(?:\r\n?|\n)"
    return content.replace(Regex("""^(package .*?($nl)$nl)"""), "$1import static $import;$2")
}
