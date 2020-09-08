package ru.senkot.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.senkot.entities.User;

import java.util.List;

@Repository
public class UserDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }


    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u from User as u").list();
    }

    public void deleteUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    public User findByUsername(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, name);
    }
}
