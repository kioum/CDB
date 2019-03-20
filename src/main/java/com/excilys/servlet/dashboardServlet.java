package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.persistence.ComputerDAO;

@WebServlet("/Dashboard")
public class dashboardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -922693522541784648L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);

		ComputerDAO.getList();
		//Page<Computer> pageComputer = new Page<Computer>(ComputerDAO.getList(), 10);

		//RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
		//req.setAttribute("listComputer", pageComputer.currentPage());

		//rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
