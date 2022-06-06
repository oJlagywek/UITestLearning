package ru.ojladywek;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PerfomanceLabUiTest {

    private final static String BLUE_COLOR_RGBA = "rgba(79, 173, 255, 1)";

    @BeforeEach
    public void prepareBrowserAndOpenGoogle() {
        Configuration.pageLoadStrategy = "eager";
        open("https://www.google.com/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @Test
    public void homeTask() {
        setTextToSearchFieldAndClickEnter();
        findPerfomanceLabSiteAndOpenIt();
        openWebsiteTestingLink();
        checkPriceButtonColor();
    }

    public void setTextToSearchFieldAndClickEnter() {
        $("input[title='Поиск']")
                .shouldBe(exist, Duration.ofSeconds(6))
                .setValue("performance lab")
                .sendKeys(Keys.ENTER);
    }

    public void findPerfomanceLabSiteAndOpenIt() {
        $$("h3")
                .filterBy(text("Перфоманс Лаб - Услуги по тестированию программного"))
                .shouldHave(size(1))
                .first()
                .click();
    }

    public void openWebsiteTestingLink() {
        SelenideElement menuItem = $$("li.menu-item")
                .filterBy(text("Услуги и продукты"))
                .first();
        actions().moveToElement(menuItem).perform();
        $$("ul.sub-menu > li.menu-item")
                .filterBy(exactTextCaseSensitive("Тестирование сайта"))
                .shouldHave(size(1))
                .first()
                .find(By.tagName("a")).click();
        switchTo().window(1); // Ссылка открывается в новой вкладке
    }

    public void checkPriceButtonColor() {
        $$("a.elementor-button-link")
                .filterBy(text("Узнать цены"))
                .shouldHave(size(1))
                .first()
                .shouldHave(cssValue("background-color", BLUE_COLOR_RGBA));
    }
}
