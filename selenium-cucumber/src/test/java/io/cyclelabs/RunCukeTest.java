package io.cyclelabs;

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"html:cucumberHtmlReport.html", "pretty"},
		features = "classpath:features"
)

public class RunCukeTest {
}
