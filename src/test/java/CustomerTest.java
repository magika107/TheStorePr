import model.entity.Customer;
import model.entity.enums.Gender;
import model.service.CustomerService;

import java.net.CacheRequest;
import java.time.LocalDate;

public class CustomerTest {
    public static void main(String[] args) throws Exception {
        Customer customer = Customer.builder()
                .gender(Gender.Male)
                .name("Hasan")
                .family("Hasani")
                .birthDate(LocalDate.of(1999, 10, 11))
                .phoneNumber("2356563265")
                .build();

        CustomerService customerService = CustomerService.getService();
//        customerService.save(customer);
//        Customer cu = customerService.findById(customer.getId());
//        System.out.println(cu);
//
//        customer.setPhoneNumber("1234567890");
//        customerService.edit(customer);
//        customerService.delete(customer.getId());
    }
}
