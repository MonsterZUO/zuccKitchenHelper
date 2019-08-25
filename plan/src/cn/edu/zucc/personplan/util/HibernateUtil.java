package cn.edu.zucc.personplan.util;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import cn.edu.zucc.personplan.model.BeanPlan;
public class HibernateUtil {
	private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	public static Session getSession(){
		Session session = sessionFactory.openSession();
        return session;
	}
	public static void main(String[] args){
//		Session session=getSession();
//		System.out.println(session);
		Session session=getSession();
		List<BeanPlan> pubs=session.createQuery("from tbl_Plan").list();
		System.out.println(pubs.size());

	}
}
