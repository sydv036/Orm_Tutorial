package orm.thi_final_orm.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import orm.thi_final_orm.common.ValidationCommon;

public class HibernateConfig {
    private static final SessionFactory FACTORY;

    static {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        FACTORY = configuration.buildSessionFactory();
    }

    public static SessionFactory getFactory() {
        return FACTORY;
    }

    public static void main(String[] args) {
        System.out.println(getFactory());
    }

}
