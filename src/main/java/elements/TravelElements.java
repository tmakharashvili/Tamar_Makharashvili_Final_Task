package elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class TravelElements {
    public SelenideElement account = $("a[href='/register']");
}
