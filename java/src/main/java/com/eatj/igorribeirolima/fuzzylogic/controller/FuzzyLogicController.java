package com.eatj.igorribeirolima.fuzzylogic.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Named
@RequestMapping( value = "/" )
public class FuzzyLogicController {
	
	@RequestMapping( value = "logout", method = RequestMethod.GET )
    public void download( HttpServletRequest request, HttpServletResponse response ) throws IOException{
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<head>");
		out.println("<title>Logout</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"#FFFFFF\">");
		out.println("Usuário deslogado!!!");
		out.println("</body>");
		out.println("</html>");
		
	}
	
}
