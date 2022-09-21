# test-comparisons

This repository is intended to foster conversation around how Cycle compares to other test automation frameworks.
Submissions of other examples are welcome. They should be standardized around the grocer.io feature in the cycle folder
to provide a valid comparison. 

Note that I've set up circle-ci to run the examples and tests in this repository. You can view the console output from these runs in [CircleCI](https://app.circleci.com/pipelines/github/dumpsterfireproject/test-comparisons).
Examples that are run currently include:
- selenium-cucumber

[![CircleCI](https://circleci.com/gh/dumpsterfireproject/test-comparisons.svg?style=svg)](https://circleci.com/gh/dumpsterfireproject/test-comparisons)

## Cycle

Cycle is a test automation platform designed to test commerial off the shelf (COTS) applications. It allows to to test your
installation, configuration, and customization of COTS applications within a business.

### Pros
- There are many steps readily available when you purchase cycle, so you don't have to write your own glue code, just jump straight into writing a feature.
- Comes with its own UI and engine, so you don't have to use an IDE or mess around with plug-ins or extensions.
- You can invoke a scenario from within another scenario, allowing you to create new reusable components from existing steps.
- For web steps, Cycle uses selenium under the hood. So you get to utilize that tool without having to really do the heavy lifting yourself.
- Cycle has a Background section in a feature, which can be used to execute some steps before each scenario in the feature being run (just the top level scenarios, not inner scenarios)
- Cycle has an After Scenario section in a feature, whis is similar to the Background but executed after each top level scenario
### Cons
- If there is not an existing step that meets your project's needs, you cannot create a new step. Several work arounds exist that approximate creating a new step, but don't quite allow you to create a new step with explicit parameters.
- You still have to use features, scenarios, and given/when/then keywords when just creating non-test automation tasks, which can be confusing.
- While there are Background and After Scenario sections available, there are no hooks to run before/after the entire test suite or before/after each step.

## selenium-cucumber

*Selenium with JUnit and Cucumber*

Selenium Web Driver, JUnit, and Cucumber are all open source tools with can be used together to create automated tests.
Each of these tools can be used in conjunction with other tools; it is not required that these three tools be
used together in combination with each other. Selenium automates web browsers. Cucumber is the world's leading tool for
Behavior Driven Development. 

Note that in this example, I've configured it to generate the HTML report in the root of the selenium-cucumber directory. I've committed an example for easy viewing.

### Pros
- Cucumber is ported to many programming languages and has a large community and toolset.
- You can create you own step syntax as you like.
- There is an optional Background section which can be included in the feature to run after each scenario.  The background is a series of steps.
- There are hooks to run code before/after the test suite, before/after each scenario, and before/after each step.
### Cons
- You have to write the glue code in Cucumber yourself to back your steps, or else find another project to provide the step syntax and glue code. This may be challenging for teams that lack those skill sets.
- Some of the big benefits from using Cucumber is in the collaboration with business people and producing living documentation. If you are engaging in neither of these, you may be just adding an additional abstraction layer to maintain without benefit.
- All hooks (not including a background section) are implemented in code and are not included in the feature.  This is nice that it doesn't necessarily clutter feature files, but also isn't visible to users who only look at feature files.

## selenium-automation

*Selenium without tests, only performing automation*

Selenium automates web browsers and while it is often used as part of a solution to create automated tests, it is not
limited to testing at all.

The context for this example might be something like loading data for a test or just simply automating a process.  In these
type of use cases, Given/When/Then keywords might not make sense. Since those key words correlate to the arrange/act/assert test
pattern, it might not make sense to make test assertions while loading test data, for example. I.e., if the test data isn't loaded
correctly, should that fail in the same way that asserting that they system under test behaves as expected? Or should it just be
a different sort of exception or failure indicating that something else in the process of running the test went wrong, which is
easily distiguisable from a test failing due to assertions/expections not being satisfied?
This example was included just for discussion around automating tasks without having to use and conform to any type of test framework.
