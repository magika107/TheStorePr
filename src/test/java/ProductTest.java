import model.entity.Product;
import model.service.ProductService;

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
        ProductService product1=new ProductService();
        product1.save(product);

    }
}
