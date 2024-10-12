import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class AccountRepositoryTest {

    private static HibernateUtil hibernateUtil;
    private static AccountRepository accountRepository;
    private static SessionFactory sessionFactory;


    static Account account1;
    static Account account2;
    static Account account3;

    @BeforeAll
    static void setUp() {
        hibernateUtil = new HibernateUtil();
        sessionFactory = hibernateUtil.startSession();
        accountRepository = new AccountRepository(sessionFactory);
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();

        account1 = new Account();
        account1.setUsername("user_one");
        account1.setEmailAddress("userone@account.com");
        session.persist(account1);

        account2 = new Account();
        account2.setUsername("user_two");
        account2.setEmailAddress("usertwo@account.com");
        session.persist(account2);

        account3 = new Account();
        account3.setUsername("user_three");
        account3.setEmailAddress("userthree@account.com");
        session.persist(account3);

        session.getTransaction().commit();

    }
    @Test
    void returnsAllAccounts() {
        var result = accountRepository.all().size();
        assertEquals(3, result);
    }
    @Test
    void findsUserWithASpecificID(){
        var result = accountRepository.findByID(1L).getUsername();
        assertEquals("user_one", result);
        var result2 = accountRepository.findByID(2L).getUsername();
        assertEquals("user_two", result2);
    }

    @Test
    void createsAnAccount() {
        accountRepository.addAccount("new record", "email");
        var result = accountRepository.all().size();
        assertEquals(4, result);
    }

    @Test
    void deletesAccount() {
        accountRepository.deleteAccount(account1);
        var result = accountRepository.all().size();
        assertEquals(2, result);
    }
}