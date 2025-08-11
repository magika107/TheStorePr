
import model.entity.User;
import model.entity.enums.Gender;
import model.repository.UserRepository;
import model.service.UserService;

import java.time.LocalDate;

public class UserTest {
    public static void main(String[] args) throws Exception {
        User user =
                User
                        .builder()
                        .id(8911)
                        .gender(Gender.Male)
                        .name("hamed")
                        .family("aslani")
                        .birthDate(LocalDate.of(1994, 1, 1))
                        .phoneNumber("45648911")
                        .username("654321111")
                        .password("1689")
                        .build();

        UserService.getService().save(user);
        System.out.println(user);


//         System.out.println(user4.findById(5));
//      user4.save(user);
//        user4.delete(1);
//      System.out.println(UserService.findByNameAndFamily("safdar", "aslani"));
//        user.setPhoneNumber("1234567890");
//        user3.edit(user);

    }
}
