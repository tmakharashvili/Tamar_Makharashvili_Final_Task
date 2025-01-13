package steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import elements.ShopElements;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ShopSteps extends ShopElements {
    protected WebDriverWait wait;

    public ShopSteps(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));// wait-ის ინიციალიზაცია
    }

    public void register(String email, String password) {
        driver.findElement(emailFieldLocator).sendKeys(email);
        driver.findElement(passwordFieldLocator).sendKeys(password);
        driver.findElement(registerButtonLocator).click();
    }

    public void goToShop() {
        driver.findElement(shop).click();
    }

    public void sortByPriceLowToHigh() {
        new Select(driver.findElement(By.name("orderby"))).selectByValue("price");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double getFirstPrice() {
        String price = driver.findElement(By.cssSelector(".price .amount")).getText()
                .replace("₹", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(price);
    }

    public double getLastPrice() {
        return Double.parseDouble(driver.findElements(By.cssSelector(".price .amount"))
                .get(driver.findElements(By.cssSelector(".price .amount")).size() - 1)
                .getText()
                .replace("₹", "")
                .replace(",", "")
                .trim());
    }

    public void verifyPriceSorting() {
        double firstPrice = getFirstPrice();
        double lastPrice = getLastPrice();

        System.out.println("First price: " + firstPrice);
        System.out.println("Last price: " + lastPrice);

        assertTrue(firstPrice <= lastPrice,
                "Prices are not sorted correctly. First price " + firstPrice +
                        " should be less than or equal to last price " + lastPrice);
    }

    public void filterByJavaScript() {
        driver.findElement(filterJavaScript).click();
    }

    public void verifyJavaScriptFilter() {
        List<WebElement> books = driver.findElements(filteredBooks);
        System.out.println("Found JavaScript books: " + books.size());

        for (WebElement book : books) {
            String bookTitle = book.getText();
            assertTrue(bookTitle.contains("JavaScript"),
                    "Book '" + bookTitle + "' is not a JavaScript book!");
        }
    }

    public void addToCart() {
        WebElement firstProduct = driver.findElement(addToCartButton);
        firstProduct.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(cartCount, "1"));
    }

    public void verifyCartCountUpdated() {
        String count = driver.findElement(cartCount).getText();
        Assert.assertNotEquals(count, "0", "Cart count was not updated");
        System.out.println("Cart count updated to: " + count);
    }

    public void openCart() {
        driver.findElement(cartIcon).click();
    }

    public void verifyProductInCart() {
        List<WebElement> cartItems = driver.findElements(productInCart);
        Assert.assertFalse(cartItems.isEmpty(), "No products found in the cart.");
    }

    public void removeFromCart() {
        driver.findElement(removeButton).click();
    }

    public void verifyCartEmpty() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emptyMessage = wait.until(ExpectedConditions.presenceOfElementLocated(emptyCartMessage));
        assertTrue(emptyMessage.isDisplayed(), "Cart is not empty");
        System.out.println("Cart is empty");
    }

    public void selectBooksAndAddToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(html5Book).click();
        driver.findElement(addHtml5BookToCart).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(cartCount, "1"));

        driver.findElement(seleniumRubyBook).click();
        driver.findElement(addSeleniumRubyToCart).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(cartCount, "2"));
    }
    public void applyCoupon(String couponCode) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(couponField).sendKeys(couponCode);
        driver.findElement(applyCouponButton).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(couponAppliedMessage, "Coupon code applied successfully"));
    }
    public void verifyCouponApplied() {
        String message = driver.findElement(couponAppliedMessage).getText();
        Assert.assertEquals(message, "Coupon code applied successfully.");
    }
    public void verifyCartTotalUpdated() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // ვიღებთ ჯამურ თანხას კუპონამდე
        String subtotalText = driver.findElement(subToTal).getText()
                .replace("₹", "")
                .replace(",", "");
        double subtotal = Double.parseDouble(subtotalText);

        // ვიღებთ ჯამურ თანხას კუპონის შემდეგ
        String totalText = driver.findElement(cartTotal).getText()
                .replace("₹", "")
                .replace(",", "");
        double total = Double.parseDouble(totalText);

        // ვამოწმებთ რომ კუპონის გამოყენების შემდეგ თანხა შემცირდა
        assertTrue(total < subtotal,
                "Total price (" + total + ") should be less than subtotal (" + subtotal + ") after coupon applied");

        System.out.println("Price before coupon: " + subtotal);
        System.out.println("Price after coupon: " + total);
    }

    public void removeCouponAndCheckStatus() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(removeCouponButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".woocommerce-message")));
    }
    public void proceedToCheckout() {
        WebElement checkoutButton = driver.findElement(proceedToCheckoutButton);
        checkoutButton.click();
    }
    public void fillBillingDetails(String firstName, String lastName, String email, String phone, String address, String city, String postalCode) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(cityField).sendKeys(city);
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }
    public void selectCashOnDelivery() {
        WebElement cashOnDeliveryOption = driver.findElement(cashOnDeliveryRadioButton);
        cashOnDeliveryOption.click();
    }
    public void placeOrder() {
        WebElement placeOrderBtn = driver.findElement(placeOrderButton);
        placeOrderBtn.click();
    }
    public void verifyOrderConfirmation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement orderConfirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(orderReceivedMessage));
        String message = orderConfirmationMessage.getText();
        assert message.contains("Thank you. Your order has been received.");

        WebElement paymentMethod = driver.findElement(paymentMethodLabel);
        assert paymentMethod.getText().contains("Cash on Delivery");
    }
    public void goToMyAccount() {
        WebElement myAccount = driver.findElement(myAccountLink);
        myAccount.click();
    }
    public void loginToAccount(String email, String password) {
        WebElement emailField = driver.findElement(loginEmailField);
        WebElement passwordField = driver.findElement(loginPasswordField);
        WebElement loginBtn = driver.findElement(loginButton);

        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginBtn.click();
    }
    public void goToOrders() {
        WebElement orders = driver.findElement(ordersLink);
        orders.click();
    }
    public void verifyOrderExists() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(orderNumber));
        WebElement order = driver.findElement(orderNumber);
        assertTrue(order.isDisplayed());
    }
    public void goToAccountDetails() {
        WebElement accountDetails = driver.findElement(accountDetailsLink);
        accountDetails.click();
    }
    public void changePassword(String currentPassword, String newPassword) {
        WebElement currentPasswordFieldElement = driver.findElement(currentPasswordField);
        WebElement newPasswordFieldElement = driver.findElement(newPasswordField);
        WebElement confirmPasswordFieldElement = driver.findElement(confirmPasswordField);

        currentPasswordFieldElement.sendKeys(currentPassword);
        newPasswordFieldElement.sendKeys(newPassword);
        confirmPasswordFieldElement.sendKeys(newPassword);
    }
    public void saveChanges() {
        WebElement saveChangesButtonElement = driver.findElement(saveChangesButton);
        saveChangesButtonElement.click();
    }
    public void logOut() {
        WebElement logOutElement = driver.findElement(logOut);
        logOutElement.click();
    }
    public void verifyLogin(String expectedUsername) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement accountInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(accountUsername));
        String loggedInUsername = accountInfo.getText();
        if (!loggedInUsername.contains(expectedUsername)) {
            throw new AssertionError("Login failed! Expected username: " + expectedUsername + ", but found: " + loggedInUsername);
        }
    }

}


