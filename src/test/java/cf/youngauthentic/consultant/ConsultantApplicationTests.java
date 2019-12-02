package cf.youngauthentic.consultant;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static cf.youngauthentic.consultant.service.PasswordHash.createHash;
import static cf.youngauthentic.consultant.service.PasswordHash.validatePassword;

@SpringBootTest
class ConsultantApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void password() {
        try {
            // Print out 10 hashes
            for (int i = 0; i < 10; i++)
                System.out.println(createHash("p\r\nassw0Rd!"));

            // Test password validation
            boolean failure = false;
            System.out.println("Running tests...");
            for (int i = 0; i < 100; i++) {
                String password = "" + i;
                String hash = createHash(password);
                String secondHash = createHash(password);
                if (hash.equals(secondHash)) {
                    System.out.println("FAILURE: TWO HASHES ARE EQUAL!");
                    failure = true;
                }
                String wrongPassword = "" + (i + 1);
                if (validatePassword(wrongPassword, hash)) {
                    System.out.println("FAILURE: WRONG PASSWORD ACCEPTED!");
                    failure = true;
                }
                if (!validatePassword(password, hash)) {
                    System.out.println("FAILURE: GOOD PASSWORD NOT ACCEPTED!");
                    failure = true;
                }
            }
            if (failure)
                System.out.println("TESTS FAILED!");
            else
                System.out.println("TESTS PASSED!");
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
    }

}
