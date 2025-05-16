package com.java.akdev.driverservice.e2e;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "com.java.akdev.driverservice.e2e.steps",
        features = "src/test/resources/features",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class DriverE2eRunnerTest {
}
