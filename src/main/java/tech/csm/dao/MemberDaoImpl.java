package tech.csm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import tech.csm.entity.Member;
import tech.csm.util.HibernateUtil;

public class MemberDaoImpl implements MemberDao {

	@Override
	public String addMember(Member m) {
		Session ses = null;
		Transaction tx = null;
		String msg = null;
		try {
			ses = HibernateUtil.getSessionFactory().openSession();
			tx = ses.beginTransaction();
			ses.saveOrUpdate(m);
			msg = "1 member added!!";
			tx.commit();
			ses.close();
		} catch (Exception e) {
			tx.rollback();
			msg = "Opps, something went wrong!!";
			e.printStackTrace();
		}
		return msg;
	}

	@Override
	public List<Member> getAllMembers() {
		Session ses = HibernateUtil.getSessionFactory().openSession();
		final String qr="from Member";
		
		Query<Member> q=ses.createQuery(qr,Member.class);
		return q.list();	
		
	}

}
