package framework.actions;

import framework.factory.WebDriverFactory;

public class Refresh {
    public static void page(){
        WebDriverFactory.getInstance().getWebDriver().navigate().refresh();
    }
}
