package com.java.akdev.ridesservice.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "com.java.akdev.ridesservice.e2e.steps",
        features = "src/test/resources/features",
        plugin = {"pretty", "html:build/cucumber-reports.html"}
)
public class RideE2eRunnerTest {
}
