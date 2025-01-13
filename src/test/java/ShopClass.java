import org.testng.annotations.Test;
import steps.ShopSteps;
import utils.SetUpClassSelenium;

public class ShopClass extends SetUpClassSelenium {
    ShopSteps registrationSteps = new ShopSteps(driver);

    @Test
    public void registration() {
        registrationSteps.register("tamarmakharashvili@credo.ge", "Testireba111");
    }

    @Test
    public void goToShopAndSort() {
        registrationSteps.goToShop();
        registrationSteps.sortByPriceLowToHigh();
        registrationSteps.verifyPriceSorting();
    }

    @Test
    public void javascript() {
        registrationSteps.goToShop();
        registrationSteps.filterByJavaScript();
        registrationSteps.verifyJavaScriptFilter();
    }

    @Test
    public void cart() {
        registrationSteps.goToShop();
        registrationSteps.filterByJavaScript();
        registrationSteps.verifyJavaScriptFilter();
        registrationSteps.addToCart();
        registrationSteps.verifyCartCountUpdated();
        registrationSteps.openCart();
        registrationSteps.verifyProductInCart();
        registrationSteps.removeFromCart();
        registrationSteps.verifyCartEmpty();
    }
    @Test
    public void html(){
        registrationSteps.goToShop();
        registrationSteps.selectBooksAndAddToCart();
        registrationSteps.openCart();
        registrationSteps.applyCoupon("krishnasakinala");
        registrationSteps.verifyCouponApplied();
        registrationSteps.verifyCartTotalUpdated();
        registrationSteps.removeCouponAndCheckStatus();
        registrationSteps.applyCoupon("krishnasakinala");
        registrationSteps.verifyCouponApplied();
    }
    @Test
    public void order(){
        registrationSteps.goToShop();
        registrationSteps.selectBooksAndAddToCart();
        registrationSteps.openCart();
        registrationSteps.proceedToCheckout();
        registrationSteps.fillBillingDetails("Tamar","Makharashvili","tamarmakharashvili@credo.ge","123456789","main street","tbilisi","0110");
        registrationSteps.selectCashOnDelivery();
        registrationSteps.placeOrder();
        registrationSteps.verifyOrderConfirmation();
    }

    @Test
    public void checkout() {
        registrationSteps.goToMyAccount();
        registrationSteps.loginToAccount("tamarmakharashvili@credo.ge","Testireba111");
        registrationSteps.goToOrders();
        registrationSteps.verifyOrderExists();
    }
    @Test
    public void changePass(){
        registrationSteps.loginToAccount("tamarmakharashvili@credo.ge","Testireba111");
        registrationSteps.goToMyAccount();
        registrationSteps.goToAccountDetails();
        registrationSteps.changePassword("Testireba111","Testireba222");
        registrationSteps.saveChanges();
        registrationSteps.logOut();
        registrationSteps.loginToAccount("tamarmakharashvili@credo.ge","Testireba222");
        registrationSteps.verifyLogin("tamarmakharashvili");
    }
}
