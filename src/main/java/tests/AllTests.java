package tests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	
	DisasterTests.class,
	ManagerTests.class,
	ReportTests.class,
	RequestTests.class
	
})
public class AllTests {
	

}
