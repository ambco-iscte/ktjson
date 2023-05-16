import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite

@Suite
@SelectClasses(
    TestModel::class,
    TestSerialization::class,
    TestParsing::class,
    TestDeserialization::class,
    TestListeners::class,
    TestCommands::class
)
class RunAllTests