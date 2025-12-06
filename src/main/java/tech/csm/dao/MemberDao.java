package tech.csm.dao;

import java.util.List;

import tech.csm.entity.Member;

public interface MemberDao {

	String addMember(Member m);

	List<Member> getAllMembers();

}
