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

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.service.CompanyService;
import com.excilys.validator.ComputerValidator;

@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<CompanyDTO> companies = CompanyService.getAll();

		HttpSession session = req.getSession();
		session.setAttribute("companies", companies);

		req.setAttribute("companies", companies);
		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/views/addComputer.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		if(!introduced.equals(""))
			introduced += " 00:00:00";

		String discontinued = request.getParameter("discontinued");
		if(!discontinued.equals(""))
			discontinued += " 00:00:00";

		String companyId = request.getParameter("companyId");

		ComputerDTO compDTO = new ComputerDTO(0L, computerName, introduced, discontinued, Long.valueOf(companyId), "");

		Computer newComp = ComputerMapper.dtoToComputer(compDTO);
		if(ComputerValidator.isCreatable(newComp))
			ComputerDAO.create(ComputerMapper.dtoToComputer(compDTO));

		response.sendRedirect("Dashboard");
	}
}
