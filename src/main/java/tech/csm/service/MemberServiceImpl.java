package tech.csm.service;

import java.util.List;

import tech.csm.dao.MemberDao;
import tech.csm.dao.MemberDaoImpl;
import tech.csm.entity.Member;

public class MemberServiceImpl implements MemberService {
	private MemberDao memberDao=new MemberDaoImpl();
	@Override
	public String addMember(Member m) {
		return memberDao.addMember(m);
	}
	@Override
	public List<Member> getAllMembers() {
		return memberDao.getAllMembers();
	}

}
