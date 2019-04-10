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
import com.excilys.exception.ComputerException;
import com.excilys.exception.TimestampException;
import com.excilys.exception.ValidatorException;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {

	private static final long serialVersionUID = -1342895835008890400L;
	private static final String VIEW_EDIT = "WEB-INF/views/editComputer.jsp";
	private ComputerService computerService;
	private CompanyService companyService;
	
	public EditServlet() {
		computerService = AppConfig.context.getBean(ComputerService.class);
		companyService = AppConfig.context.getBean(CompanyService.class);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<CompanyDTO> companies = companyService.getAll();

		req.setAttribute("companies", companies);

		String id = req.getParameter("id");
		if(id != null) {
			try {
				Computer comp = computerService.findById(Long.valueOf(id));
				req.setAttribute("computer", ComputerMapper.computerToDTO(comp));
			}catch (ComputerException e) {
				resp.sendRedirect("err/404.html");
				return;
			}
		}

		RequestDispatcher rd = req.getRequestDispatcher(VIEW_EDIT);
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String computerId = req.getParameter("id");
		String computerName = req.getParameter("computerName");
		String introduced = req.getParameter("introduced");
		String discontinued = req.getParameter("discontinued");

		if (discontinued == null) 
			discontinued = "";

		String companyId = req.getParameter("companyId");
		ComputerDTO compDTO = new ComputerDTO(Long.valueOf(computerId), computerName, introduced, discontinued, Long.valueOf(companyId), "unknown");

		try {
			computerService.update(ComputerMapper.dtoToComputer(compDTO));
		} catch (ValidatorException | TimestampException e) {
			req.setAttribute("exception", e.getMessage());
			doGet(req, resp);
			return;
		}

		resp.sendRedirect("Dashboard");
	}
}
