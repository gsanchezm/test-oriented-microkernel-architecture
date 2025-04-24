package tom.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import config.TOMException;
import tom.authentication.dao.UserInformation;
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
    protected static final ThreadLocal<UserInformation> user = new ThreadLocal<>();
    protected static final ThreadLocal<Product> product = new ThreadLocal<>();
    private static final String USER_DATA_FILE = "data/user.json";

    public SharedSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    public UserInformation getDefaultUser() {
        String defaultUserKey = getPlatformKey().equals("web") ? "standard_user" : "bod";
        return getUserData(defaultUserKey);
    }

    public UserInformation getLockedUser() {
        String lockedUserKey = getPlatformKey().equals("web") ? "locked_out_user" : "alice";
        return getUserData(lockedUserKey);
    }


    public UserInformation getUserData(String userType) {
        String effectiveUserKey = resolveUserKey(userType);
        return getUserDataList(effectiveUserKey)
                .stream()
                .findFirst()
                .orElseThrow(() -> new TOMException("❌ No data found for user type: " + userType));
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

    private String resolveUserKey(String userType) {
        String platform = getPlatformKey();

        // Validate allowed keys for each platform
        Map<String, List<String>> userMap = Map.of(
                "web", List.of("standard_user", "locked_out_user"),
                "mobile", List.of("bod", "alice")
        );

        if (!userMap.containsKey(platform)) {
            throw new TOMException("❌ Unsupported platform: " + platform);
        }

        if (!userMap.get(platform).contains(userType)) {
            throw new TOMException("❌ User type '" + userType + "' not available for platform: " + platform);
        }

        return userType;
    }

    private String getPlatformKey() {
        return testContext.getCurrentPlatform().name().toLowerCase();
    }
}