import model.entity.Product;
import model.service.ProductService;
import model.service.UserService;

public class ProductTest {
    public static void main(String[] args) throws Exception {
        Product product =Product
                .builder()
                .title("mobile")
                .brand("apple")
                .model("13PrMax")
                .serialNumber("132")
                .buyPrice(123)
                .build();
        System.out.print(product);
        ProductService.getService().save(product);

    }
}
