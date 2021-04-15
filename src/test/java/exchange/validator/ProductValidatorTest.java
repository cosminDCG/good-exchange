package exchange.validator;

import com.platform.exchange.exception.ErrorMessage;
import com.platform.exchange.exception.feature.MandatoryFeatureException;
import com.platform.exchange.exception.product.InvalidDescriptionException;
import com.platform.exchange.exception.product.NegativePriceException;
import com.platform.exchange.model.Feature;
import com.platform.exchange.model.product.Product;
import com.platform.exchange.model.product.ProductType;
import com.platform.exchange.validator.product.ProductValidator;
import com.platform.exchange.validator.product.ProductValidatorFactory;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ProductValidatorTest {

    private static final String PRODUCT_NAME = "product-name";
    private static final String BRAND = "brand";
    private static final String TEST_BRAND = "test-brand";
    private static final String YEAR = "year";
    private static final String TEST_YEAR = "test-year";
    private static final String MODEL = "model";
    private static final String TEST_MODEL = "test-model";
    private static final String KILOMETERS = "kilometers";
    private static final String TEST_KILOMETERS = "test-kilometers";
    private static final String FUEL = "fuel";
    private static final String TEST_FUEL = "test-fuel";
    private static final String CUBIC_CAPACITY = "cubic capacity";
    private static final String TEST_CUBIC_CAPACITY = "test-cubic-capacity";

    @Test
    public void validateProduct_negativePrice() {
        Product product = new Product.ProductBuilder(ProductType.OTHER, "product-name", -1.0).build();
        ProductValidator validator = ProductValidatorFactory.getValidator(ProductType.OTHER);
        Exception exception = assertThrows(NegativePriceException.class, () -> validator.validate(product));

        assertEquals(exception.getMessage(), ErrorMessage.NEGATIVE_PRICE.getMessage());
    }

    @Test
    public void validateProduct_invalidDescription() {
        byte[] array = new byte[1001];
        new Random().nextBytes(array);
        String description = new String(array, StandardCharsets.UTF_8);
        Product product = new Product.ProductBuilder(ProductType.OTHER, PRODUCT_NAME, 22.0)
                                     .withDescription(description).build();
        ProductValidator validator = ProductValidatorFactory.getValidator(ProductType.OTHER);
        Exception exception = assertThrows(InvalidDescriptionException.class, () -> validator.validate(product));

        assertEquals(exception.getMessage(), ErrorMessage.INVALID_DESCRIPTION.getMessage());
    }

    @Test
    public void validateAuto_noYearFeature() {
        Product product = new Product.ProductBuilder(ProductType.AUTO, PRODUCT_NAME, 22.0)
                                     .addFeature(new Feature(BRAND, TEST_BRAND))
                                     .build();

        ProductValidator validator = ProductValidatorFactory.getValidator(ProductType.AUTO);
        Exception exception = assertThrows(MandatoryFeatureException.class, () -> validator.validate(product));

        assertEquals(exception.getMessage(), ErrorMessage.MANDATORY_FEATURE_NOT_FOUND.getMessage() + " year");
    }

    @Test
    public void validateAuto_noBrand() {
        Product product = new Product.ProductBuilder(ProductType.AUTO, PRODUCT_NAME, 22.0)
                                     .addFeature(new Feature(YEAR, TEST_YEAR))
                                     .build();

        ProductValidator validator = ProductValidatorFactory.getValidator(ProductType.AUTO);
        Exception exception = assertThrows(MandatoryFeatureException.class, () -> validator.validate(product));

        assertEquals(exception.getMessage(), ErrorMessage.MANDATORY_FEATURE_NOT_FOUND.getMessage() + " brand");
    }

    @Test
    public void validateAuto_noModel() {
        Product product = new Product.ProductBuilder(ProductType.AUTO, PRODUCT_NAME, 22.0)
                                     .addFeature(new Feature(YEAR, TEST_YEAR))
                                     .addFeature(new Feature(BRAND, TEST_BRAND))
                                     .build();

        ProductValidator validator = ProductValidatorFactory.getValidator(ProductType.AUTO);
        Exception exception = assertThrows(MandatoryFeatureException.class, () -> validator.validate(product));

        assertEquals(exception.getMessage(), ErrorMessage.MANDATORY_FEATURE_NOT_FOUND.getMessage() + " model");
    }

    @Test
    public void validateAuto_noKilometers() {
        Product product = new Product.ProductBuilder(ProductType.AUTO, PRODUCT_NAME, 22.0)
                .addFeature(new Feature(YEAR, TEST_YEAR))
                .addFeature(new Feature(BRAND, TEST_BRAND))
                .addFeature(new Feature(MODEL, TEST_MODEL))
                .build();

        ProductValidator validator = ProductValidatorFactory.getValidator(ProductType.AUTO);
        Exception exception = assertThrows(MandatoryFeatureException.class, () -> validator.validate(product));

        assertEquals(exception.getMessage(), ErrorMessage.MANDATORY_FEATURE_NOT_FOUND.getMessage() + " kilometers");
    }

    @Test
    public void validateAuto_noFuel() {
        Product product = new Product.ProductBuilder(ProductType.AUTO, PRODUCT_NAME, 22.0)
                .addFeature(new Feature(YEAR, TEST_YEAR))
                .addFeature(new Feature(BRAND, TEST_BRAND))
                .addFeature(new Feature(MODEL, TEST_MODEL))
                .addFeature(new Feature(KILOMETERS, TEST_KILOMETERS))
                .build();

        ProductValidator validator = ProductValidatorFactory.getValidator(ProductType.AUTO);
        Exception exception = assertThrows(MandatoryFeatureException.class, () -> validator.validate(product));

        assertEquals(exception.getMessage(), ErrorMessage.MANDATORY_FEATURE_NOT_FOUND.getMessage() + " fuel");
    }

    @Test
    public void validateAuto_noCubicCapacity() {
        Product product = new Product.ProductBuilder(ProductType.AUTO, PRODUCT_NAME, 22.0)
                .addFeature(new Feature(YEAR, TEST_YEAR))
                .addFeature(new Feature(BRAND, TEST_BRAND))
                .addFeature(new Feature(MODEL, TEST_MODEL))
                .addFeature(new Feature(KILOMETERS, TEST_KILOMETERS))
                .addFeature(new Feature(FUEL, TEST_FUEL))
                .build();

        ProductValidator validator = ProductValidatorFactory.getValidator(ProductType.AUTO);
        Exception exception = assertThrows(MandatoryFeatureException.class, () -> validator.validate(product));

        assertEquals(exception.getMessage(), ErrorMessage.MANDATORY_FEATURE_NOT_FOUND.getMessage() + " cubic capacity");
    }

    @Test
    public void validateAuto_success() {
        Product product = new Product.ProductBuilder(ProductType.AUTO, PRODUCT_NAME, 22.0)
                .addFeature(new Feature(YEAR, TEST_YEAR))
                .addFeature(new Feature(BRAND, TEST_BRAND))
                .addFeature(new Feature(MODEL, TEST_MODEL))
                .addFeature(new Feature(KILOMETERS, TEST_KILOMETERS))
                .addFeature(new Feature(FUEL, TEST_FUEL))
                .addFeature(new Feature(CUBIC_CAPACITY, TEST_CUBIC_CAPACITY))
                .build();

        ProductValidator validator = ProductValidatorFactory.getValidator(ProductType.AUTO);
        validator.validate(product);

        assertEquals(product.getType(), ProductType.AUTO);
        assertEquals(product.getName(), PRODUCT_NAME);
        assertEquals(product.getPrice(), 22.0);
        assertTrue(product.getFeatures().contains(new Feature(YEAR, TEST_YEAR)));
    }
}
