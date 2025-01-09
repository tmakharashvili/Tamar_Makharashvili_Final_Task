import org.testng.annotations.Test;
import steps.TravelSteps;
import utils.SetUpClassSelenide;

import static com.codeborne.selenide.Selenide.open;

public class TravelClass extends SetUpClassSelenide {
    TravelSteps travelSteps = new TravelSteps();
    @Test
    public void register(){
        open("https://www.phptravels.net/");

        travelSteps.goToAccount();
    }

}
