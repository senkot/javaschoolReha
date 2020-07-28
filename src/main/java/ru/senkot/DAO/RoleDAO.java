package ru.senkot.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.senkot.entities.Role;

@Repository
public class RoleDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void insertRole(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(role);
    }

    public void updateRole(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.update(role);
    }

    public Role selectRole(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Role.class, id);
    }

    public void deleteRole(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(role);
    }

}
