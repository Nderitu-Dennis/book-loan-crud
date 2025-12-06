package tech.csm.service;

import java.util.List;

import tech.csm.entity.Member;

public interface MemberService {

	String addMember(Member m);

	List<Member> getAllMembers();

}
