import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class PostRepository {
    private SessionFactory sessionFactory;

    public PostRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Post> all() {
        List<Post> allPosts = null;
        try (Session session = sessionFactory.openSession()) {
            allPosts = session.createQuery("from Post", Post.class).getResultList();

        } catch (Exception e) {
            throw new RuntimeException();
        }
        return allPosts;
    }
    public List<Post> findPosts(Account account) {
        List<Post> postsAssignedToAccount = null;
        try (Session session = sessionFactory.openSession()) {
            postsAssignedToAccount = session.createQuery("from Post where account= :account", Post.class)
                    .setParameter("account", account)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return postsAssignedToAccount;
    }
    public void addPost(String title, String contents, long views, Account account){
        Post post = new Post();
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();

        post.setTitle(title);
        post.setContents(contents);
        post.setViews(views);
        post.setAccount(account);

        session.persist(post);
        session.getTransaction().commit();
        session.close();
    }
    public void deletePost(Post post){
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.remove(post);
        session.getTransaction().commit();
        session.close();
    }
}
