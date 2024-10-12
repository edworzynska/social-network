import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class PostRepositoryTest {

    private static HibernateUtil hibernateUtil;
    private static PostRepository postRepository;
    private static SessionFactory sessionFactory;

    private static Post post1;
    private static Post post2;
    private static Post post3;
    private static Account account1;
    private static Account account2;
    private static Account account3;

    @BeforeAll
    static void setUp() {
        hibernateUtil = new HibernateUtil();
        sessionFactory = hibernateUtil.startSession();
        postRepository = new PostRepository(sessionFactory);
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

        post1 = new Post();
        post1.setTitle("one");
        post1.setAccount(account1);
        post1.setContents("test content");
        post1.setViews(1000);
        session.persist(post1);

        post2 = new Post();
        post2.setTitle("two");
        post2.setAccount(account2);
        post2.setContents("test content222");
        post2.setViews(100);
        session.persist(post2);

        post3 = new Post();
        post3.setTitle("three");
        post3.setAccount(account1);
        post3.setContents("test content333");
        post3.setViews(10);
        session.persist(post3);

        session.getTransaction().commit();

    }
    @Test
    void returnsAllPosts() {
        var result = postRepository.all().size();
        assertEquals(3, result);
    }

    @Test
    void findsPostsAssignedToOneAccount() {
        var result = postRepository.findPosts(account1).size();
        assertEquals(2, result);

    }

    @Test
    void createsNewPost() {
        postRepository.addPost("title", "contentsss", 50L, account1);
        var result = postRepository.all().size();
        assertEquals(4, result);
    }

    @Test
    void deletesPost() {
        postRepository.deletePost(post1);
        var result = postRepository.all().size();
        assertEquals(2, result);
    }
}