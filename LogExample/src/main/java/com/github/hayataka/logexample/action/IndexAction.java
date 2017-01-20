package com.github.hayataka.logexample.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.ToString;


@ToString
@WebServlet(name="WebServlet3XTest",urlPatterns={"/hoge/*"})
public class IndexAction extends HttpServlet {
	  private static final long serialVersionUID = 1L;

	  private Logger log = LoggerFactory.getLogger(IndexAction.class); 
	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
	        resp.getWriter().print("it is Servlet 3.0. ");
	    }
}
