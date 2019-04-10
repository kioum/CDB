package com.excilys.servlet;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.AppConfig;
import com.excilys.dto.ComputerDTO;
import com.excilys.exception.ValidatorException;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Page;
import com.excilys.service.ComputerService;

@WebServlet("/Dashboard")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = -922693522541784648L;
	private static final String VIEW_DASHBOARD = "WEB-INF/views/dashboard.jsp";
	private ComputerService computerService;
	
	public DashboardServlet() {
		computerService = AppConfig.context.getBean(ComputerService.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Page<ComputerDTO> pageComputer = new Page<ComputerDTO>(computerService.getAll(), 10);

		String search = req.getParameter("search");
		if(search != null && !search.equals("")) {
			pageComputer.setList(computerService.findByName(search));
		}

		String sortBy = req.getParameter("sortBy");
		if(sortBy != null && !sortBy.equals("")) {
			pageComputer.setList(ComputerMapper.sortBy(pageComputer.getList(), sortBy.toUpperCase()));
		}

		String asc = req.getParameter("asc");
		if(asc == null) asc = "true";
		else if(asc != "") {
			if(!Boolean.valueOf(asc))
				Collections.reverse(pageComputer.getList());
		}

		String maxElement = req.getParameter("maxElement");
		if(maxElement != null && !maxElement.equals("")) {
			pageComputer.setMaxElement(Integer.valueOf(maxElement));
		}

		String numPage = req.getParameter("numPage");
		if(numPage != null && !numPage.equals("")) {
			pageComputer.setNumPage(Integer.valueOf(numPage));
		}

		req.setAttribute("page", pageComputer);
		req.setAttribute("search", search);
		req.setAttribute("sortBy", sortBy);
		req.setAttribute("asc", asc);

		RequestDispatcher rd = req.getRequestDispatcher(VIEW_DASHBOARD);
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] listComputers = req.getParameterValues("cb");

		if(listComputers != null)
			for(String id: listComputers)
				try {
					computerService.deleteById(Long.valueOf(id));
				} catch (ValidatorException e) {
					req.setAttribute("exception", e.getMessage());
				}

		doGet(req, resp);
	}
}
