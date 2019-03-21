package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.persistence.ComputerDAO;

@WebServlet("/Dashboard")
public class DashboardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -922693522541784648L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<Computer> computers = ComputerDAO.getList().get();
		
		HttpSession session = req.getSession();
		Page<Computer> pageComputer = null;
		if(session.getAttribute("page") == null)
			pageComputer = new Page<Computer>(computers, 10);
		else pageComputer = (Page<Computer>) session.getAttribute("page");
		
		pageComputer.setList(computers);
		
		String maxElement = req.getParameter("maxElement");
		if(maxElement != null) {
			pageComputer.setMaxElement(Integer.valueOf(maxElement));
		}
		
		String numPage = req.getParameter("numPage");
		if(numPage != null) {
			pageComputer.setNumPage(Integer.valueOf(numPage));
		}
		
		session.setAttribute("page", pageComputer);
		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
