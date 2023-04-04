import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite

@Suite
@SelectClasses(
    TestModel::class,
    TestSerialization::class,
    TestParsing::class,
    TestDeserialization::class
)
class RunAllTests