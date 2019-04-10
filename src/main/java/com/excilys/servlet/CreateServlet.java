package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.AppConfig;
import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.exception.TimestampException;
import com.excilys.exception.ValidatorException;
import com.excilys.mapper.ComputerMapper;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {
	
	private ComputerService computerService;
	private CompanyService companyService;

	private static final long serialVersionUID = 1L;
	private static final String VIEW_ADDCOMPUTER = "WEB-INF/views/addComputer.jsp";

	public CreateServlet() {
		computerService = AppConfig.context.getBean(ComputerService.class);
		companyService = AppConfig.context.getBean(CompanyService.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<CompanyDTO> companies = companyService.getAll();

		req.setAttribute("companies", companies);
		RequestDispatcher rd = req.getRequestDispatcher(VIEW_ADDCOMPUTER);
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String computerName = req.getParameter("computerName");

		String introduced = req.getParameter("introduced");	

		String discontinued = req.getParameter("discontinued");
		if (discontinued == null) discontinued = "";

		String companyId = req.getParameter("companyId");
		System.out.println(Long.valueOf(companyId));

		ComputerDTO compDTO = new ComputerDTO(0L, computerName, introduced, discontinued, Long.valueOf(companyId), "unknown");

		try {
			computerService.create(ComputerMapper.dtoToComputer(compDTO));
		} catch (ValidatorException | TimestampException e) {
			req.setAttribute("exception", e.getMessage());
			doGet(req, resp);
			return;
		}

		resp.sendRedirect("Dashboard");
	}
}
