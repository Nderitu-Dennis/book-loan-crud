package tech.csm.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.build.AllowSysOut;

import tech.csm.entity.Member;
import tech.csm.service.MemberService;
import tech.csm.service.MemberServiceImpl;
import tech.csm.util.FileUtil;
import tech.csm.util.HibernateUtil;

@MultipartConfig
public class MainController extends HttpServlet {

	
	private MemberService memberService= new MemberServiceImpl();
	
	@Override
	public void init() throws ServletException {
		HibernateUtil hUtil = new HibernateUtil();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String endPoint = req.getServletPath().trim();

		if (endPoint.equals("/home")) {
			req.getRequestDispatcher("index.jsp").forward(req, resp);
			
		} else if (endPoint.equals("/memberhome")) {
			req.setAttribute("members", memberService.getAllMembers());
			req.getRequestDispatcher("member.jsp").forward(req, resp);
		} else if (endPoint.equals("/savemember")) {
			
			Member m=new Member();
			m.setMemberCode(req.getParameter("membercodeid").trim());
			m.setName(req.getParameter("nameId").trim());
			m.setEmail(req.getParameter("emailId").trim());
			m.setPhoto(FileUtil.uploadFile(req.getPart("photoId")));			
			req.getSession().setAttribute("msg", memberService.addMember(m));
			resp.sendRedirect("http://localhost:8060/BookLoanCRUD/memberhome");
			
		} else if (endPoint.equals("/download")) {
			String pName=req.getParameter("pname");
			String filePath = FileUtil.getDirPath()+pName; // Path to the file
	        File file = new File(filePath);
	        resp.setContentType("application/octet-stream");
	        resp.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
	        FileInputStream inputStream = new FileInputStream(file);
	        OutputStream outputStream = resp.getOutputStream();
	        byte[] data=inputStream.readAllBytes();
	        outputStream.write(data);
	        
//	        byte[] buffer = new byte[4096];
//	        int bytesRead;
//	        while ((bytesRead = inputStream.read(buffer)) != -1) {
//	            outputStream.write(buffer, 0, bytesRead);
//	        }
	        inputStream.close();
	        outputStream.close();
		}
		

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	public void destroy() {
		HibernateUtil.closeSessionFactory();
	}

}
