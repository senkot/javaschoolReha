package ru.senkot.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.senkot.entities.User;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void insertUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    public User selectUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<User> selectAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u from User as u").list();
    }

    public void deleteUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    public User findByUsername(String name) {
        Session session = sessionFactory.getCurrentSession();
        List<User> users = session.createQuery("select u from User as u").list();
        return users.stream().filter(user -> user.getUsername() == name).collect(Collectors.toList()).get(0);
    }
}
