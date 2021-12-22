import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.io.PrintStream

class Test : FreeSpec() {
    private lateinit var newSystemOutContent: ByteArrayOutputStream
    private val originalSystemOutContent: PrintStream = System.out
    private val originalSystemIn: InputStream = System.`in`

    override fun beforeEach(testCase: TestCase) {
        newSystemOutContent = ByteArrayOutputStream()
        System.setOut(PrintStream(newSystemOutContent))
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        System.setOut(originalSystemOutContent)
        System.setIn(originalSystemIn)
    }

    init {
        "Operation 'determinante'" - {
            "Test case 1A" {
                runTestCase("1A")
            }

            "Test case 1F" {
                runTestCase("1F")
            }

            "Test case 2H" {
                runTestCase("2H")
            }

            "Test case 2I" {
                runTestCase("2I")
            }

            "Test case 2J" {
                runTestCase("2J")
            }

            "Test case 3B" {
                runTestCase("3B")
            }

            "Test case 3C" {
                runTestCase("3C")
            }

            "Test case 3D" {
                runTestCase("3D")
            }

            "Test case 3E" {
                runTestCase("3E")
            }
        }

        "Operation 'inverte'" - {
            "Test case 1B" {
                runTestCase("1B")
            }

            "Test case 1G" {
                runTestCase("1G")
            }

            "Test case 2G" {
                runTestCase("2G")
            }

            "Test case 2K" {
                runTestCase("2K")
            }

            "Test case 3A" {
                runTestCase("3A")
            }

            "Test case 3F" {
                runTestCase("3F")
            }

            "Test case 3G" {
                runTestCase("3G")
            }

            "Test case 3H" {
                runTestCase("3H")
            }
        }

        "Operation 'resolve'" - {
            "Test case 1C" {
                runTestCase("1C")
            }

            "Test case 1D" {
                runTestCase("1D")
            }

            "Test case 1E" {
                runTestCase("1E")
            }

            "Test case 2A" {
                runTestCase("2A")
            }

            "Test case 2B" {
                runTestCase("2B")
            }

            "Test case 2C" {
                runTestCase("2C")
            }

            "Test case 2D" {
                runTestCase("2D")
            }

            "Test case 2E" {
                runTestCase("2E")
            }

            "Test case 2F" {
                runTestCase("2F")
            }

            "Test case 4A" {
                runTestCase("4A")
            }

            "Test case 4B" {
                runTestCase("4B")
            }

            "Test case 4C" {
                runTestCase("4C")
            }
        }
    }

    private fun runTestCase(testCase: String) {
        val testCaseData = getTestCaseData(testCase)

        val input = testCaseData.first.byteInputStream()
        System.setIn(input)

        EP1.main(emptyArray())

        newSystemOutContent.toString() shouldBe testCaseData.second
    }

    private fun getTestCaseData(file: String): Pair<String, String> {
        val inputFile = File("src/test/resources/casos_de_teste/input/$file.txt")
        val input = getFileAsString(inputFile)

        val outputFile = File("src/test/resources/casos_de_teste/output/$file.txt")
        val output = getFileAsString(outputFile)

        return Pair(input, output)
    }

    private fun getFileAsString(file: File): String {
        val bufferedReader = file.bufferedReader()
        return bufferedReader.use { it.readText() }
    }
}