package tom.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import interfaces.tasks.ITask;
import interfaces.validations.IValidation;
import services.tasks.TaskResolver;
import services.validations.ValidationResolver;
import tom.authentication.dao.UserCredentials;
import tom.checkout.dao.Checkout;
import tom.inventory.dao.Product;
import tom.inventory.dao.ProductList;
import tom.services.TestContext;
import utils.JsonDataLoader;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SharedSteps {
    protected final TestContext testContext;
    protected final Map<Class<?>, ITask<?>> taskMap;
    protected final Map<Class<?>, IValidation<?>> validationMap;
    protected static final ThreadLocal<UserCredentials> user = new ThreadLocal<>();
    protected static final ThreadLocal<Product> product = new ThreadLocal<>();
    protected static final ThreadLocal<Checkout> checkoutData = new ThreadLocal<>();
    private static final String CHECKOUT_DATA_FILE = "data/user.json";

    public SharedSteps(TestContext testContext) {
        this.testContext = testContext;
        this.taskMap = TaskResolver.toTaskMap(testContext.getRegisteredTasks());
        this.validationMap = ValidationResolver.toValidationMap(testContext.getRegisteredValidations());
    }

    public Checkout getCheckoutData(String userType) {
        return getCheckoutDataList(userType)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No checkout data found for user type: " + userType));
    }

    public List<Checkout> getCheckoutDataList(String userType) {
        Map<String, List<Checkout>> dataMap = JsonDataLoader.loadMapList(
                CHECKOUT_DATA_FILE,
                new TypeReference<>() {}
        );

        List<Checkout> users = dataMap.get(userType);
        if (users == null || users.isEmpty()) {
            throw new RuntimeException("No checkout data found for user type: " + userType);
        }
        return users;
    }

    public Optional<Checkout> getCheckoutDataByFilter(String userType, Predicate<Checkout> filter) {
        return getCheckoutDataList(userType)
                .stream()
                .filter(filter)
                .findFirst();
    }

    public List<Product> getFilteredProducts(Predicate<Product> filter) {
        List<Product> allProducts = JsonDataLoader
                .loadFromData("products.json", ProductList.class)
                .getProducts();

        return allProducts.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByTitles(List<String> titles) {
        return getFilteredProducts(product -> titles.contains(product.getTitle()));
    }

    public List<Product> getProductsBelowPrice(double maxPrice) {
        return getFilteredProducts(product -> Double.parseDouble(product.getPrice()) < maxPrice);
    }
}