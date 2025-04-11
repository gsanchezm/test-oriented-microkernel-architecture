package tom.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import config.TOMException;
import interfaces.tasks.ITask;
import interfaces.validations.IValidation;
import services.tasks.TaskResolver;
import services.validations.ValidationResolver;
import tom.authentication.dao.UserInformation;
import tom.inventory.dao.Product;
import tom.inventory.dao.ProductList;
import tom.services.TestContext;
import tom.services.TestDataContext;
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
    protected static final ThreadLocal<UserInformation> user = new ThreadLocal<>();
    protected static final ThreadLocal<Product> product = new ThreadLocal<>();
    private static final String USER_DATA_FILE = "data/user.json";

    public SharedSteps(TestContext testContext) {
        this.testContext = testContext;
        this.taskMap = TaskResolver.toTaskMap(testContext.getRegisteredTasks());
        this.validationMap = ValidationResolver.toValidationMap(testContext.getRegisteredValidations());
    }

    public UserInformation getUserData() {
        return getUserDataList(TestDataContext.getPlatform().equals("web")?
                "standard_user":"bod")
                .stream()
                .findFirst()
                .orElseThrow(() -> new TOMException("No checkout data found for user"));
    }

    public List<UserInformation> getUserDataList(String userType) {
        Map<String, List<UserInformation>> dataMap = JsonDataLoader.loadMapList(
                USER_DATA_FILE,
                new TypeReference<>() {}
        );

        List<UserInformation> users = dataMap.get(userType);
        if (users == null || users.isEmpty()) {
            throw new TOMException("No checkout data found for user type: " + userType);
        }
        return users;
    }

    public Optional<UserInformation> getUserDataByFilter(String userType, Predicate<UserInformation> filter) {
        return getUserDataList(userType)
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