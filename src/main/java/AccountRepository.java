import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class AccountRepository {
    private SessionFactory sessionFactory;

    public AccountRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Account> all() {
        List<Account> allAccounts = null;
        try (Session session = sessionFactory.openSession()) {
            allAccounts = session.createQuery("from Account", Account.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return allAccounts;
    }
    public Account findByID(Long id){
        Account account = null;
        try (Session session = sessionFactory.openSession()){
            account = session.createQuery("from Account where id = :id", Account.class)
                    .setParameter("id", id)
                    .getSingleResultOrNull();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return account;
    }
    public void addAccount(String username, String emailAddress){
        Account account = new Account();
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        account.setUsername(username);
        account.setEmailAddress(emailAddress);
        session.persist(account);
        session.getTransaction().commit();
        session.close();
    }
    public void deleteAccount(Account account){
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.remove(account);
        session.getTransaction().commit();
        session.close();
    }
}
