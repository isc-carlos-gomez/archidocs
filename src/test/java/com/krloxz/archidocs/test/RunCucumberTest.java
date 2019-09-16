package com.krloxz.archidocs.test;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * @author Carlos Gomez
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "src/test/resources/features")
public class RunCucumberTest {
  // empty
}
