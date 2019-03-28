package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.dto.ComputerDTO;
import com.excilys.exception.ValidatorException;
import com.excilys.model.Page;
import com.excilys.service.ComputerService;

@WebServlet("/Dashboard")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = -922693522541784648L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<ComputerDTO> computers = ComputerService.getInstance().getAll();

		Page<ComputerDTO> pageComputer = new Page<ComputerDTO>(computers, 10);

		if (req.getAttribute("page") != null) {
			pageComputer = (Page<ComputerDTO>) req.getAttribute("page");
		}

		pageComputer.setList(computers);

		String maxElement = req.getParameter("maxElement");
		if(maxElement != null) {
			pageComputer.setMaxElement(Integer.valueOf(maxElement));
		}

		String numPage = req.getParameter("numPage");
		if(numPage != null) {
			pageComputer.setNumPage(Integer.valueOf(numPage));
		}

		req.setAttribute("page", pageComputer);
		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] listComputers = req.getParameterValues("cb");

		if(listComputers != null)
			for(String id: listComputers)
				try {
					ComputerService.getInstance().deleteById(Long.valueOf(id));
				} catch (ValidatorException e) {
					req.setAttribute("exception", e.getMessage());
				}
		doGet(req, resp);
	}
}
