package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<CompanyDTO> companies = CompanyService.getInstance().getAll();

		req.setAttribute("companies", companies);
		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/addComputer.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String computerName = request.getParameter("computerName");
		
		String introduced = request.getParameter("introduced");	
		if(!introduced.equals("")) introduced += " 00:00:00";

		String discontinued = request.getParameter("discontinued");
		if (discontinued == null) discontinued = "";
		else if (!discontinued.equals("")) discontinued += " 00:00:00";

		String companyId = request.getParameter("companyId");

		ComputerDTO compDTO = new ComputerDTO(0L, computerName, introduced, discontinued, Long.valueOf(companyId), "unknown");

		boolean success = ComputerService.getInstance().create(ComputerMapper.dtoToComputer(compDTO));
		if(success) response.sendRedirect("Dashboard");
		else {
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/addComputer.jsp");
			rd.forward(request, response);
		}
	}
}
