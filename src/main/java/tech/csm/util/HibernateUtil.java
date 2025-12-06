package tech.csm.util;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import tech.csm.entity.Member;



public class HibernateUtil {
	
	private static SessionFactory sessionFactory=null;
	
	static {
		Configuration cnf=new Configuration();
		cnf.addAnnotatedClass(Member.class);
//		.addAnnotatedClass(Student.class)
//		.addAnnotatedClass(Aadhar.class).
//		addAnnotatedClass(Person.class);
		sessionFactory=cnf.buildSessionFactory();
		
	}
	
	
	public static SessionFactory getSessionFactory(){
		
		return sessionFactory;
		
	}
	
	public static void closeSessionFactory() {
		if(sessionFactory!=null) {
			sessionFactory.close();
		}
	}
	

}
