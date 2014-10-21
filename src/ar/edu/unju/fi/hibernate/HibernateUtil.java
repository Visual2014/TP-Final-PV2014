

package ar.edu.unju.fi.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private SessionFactory sessionFactory;
    private Session session;

    public HibernateUtil()
    {
      sessionFactory = new Configuration().configure("ar/edu/unju/fi/hibernate/hibernate.cfg.xml").buildSessionFactory();
      session = sessionFactory.openSession();

    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
