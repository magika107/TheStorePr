import model.entity.Customer;
import model.entity.enums.Gender;
import model.service.CustomerService;

import java.net.CacheRequest;
import java.time.LocalDate;

public class CustomerTest {
    public static void main(String[] args) throws Exception {
        Customer customer = Customer
                .builder()
//                .id(1)
                .gender(Gender.Male)
                .name("hasan")
                .family("hasani")
                .birthDate(LocalDate.of(1999, 10, 11))
                .phoneNumber("2356563265")
                .build();


//        System.out.println(customer);

        CustomerService customer1 = new CustomerService();
//        customer1.save(customer);
//        System.out.println(customer1.findById(1));
        customer1.delete(1);
    }
}
