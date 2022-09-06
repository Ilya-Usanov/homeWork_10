package com.github;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class AllureLambdaTest {

    private static final String REPOSITORY = "allure-framework/allure-java";
    private static final int ISSUE = 819;

    @Test
    public void testLambdaStep() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открыть главную страницу gihub", () -> open("https://github.com"));
        step("Найти репозиторий " + REPOSITORY, () -> {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();
        });
        step("Кликнуть по ссылке-названию репозитория" + REPOSITORY, () -> $(linkText(REPOSITORY)).click());
        step("Открыть таб Issues", () -> $("#issues-tab").click());
        step("Найти указанный раздел в Issues с номером " + ISSUE, () -> {
            $(withText("#819")).should(Condition.exist);
        });
    }

    @Test
    public void testAnnotatedStepAttachment() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        AllureWebTest steps = new AllureWebTest();
        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openIssuesTab();
        steps.shouldSeeIssueWithNumber(ISSUE);
        steps.takeScreenshot();
    }
}
