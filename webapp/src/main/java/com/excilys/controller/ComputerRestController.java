package com.excilys.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.dto.ComputerDTO;
import com.excilys.exception.ComputerException;
import com.excilys.exception.TimestampException;
import com.excilys.exception.ValidatorException;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.service.ComputerService;

@RestController
@RequestMapping(path = "/computers", produces = "application/json")
public class ComputerRestController {
	private ComputerService computerService;
	private static final Logger LOG = LoggerFactory.getLogger(ComputerRestController.class);

	public ComputerRestController(ComputerService computerService) {
		this.computerService = computerService;
	}
	
	@GetMapping
	public ResponseEntity<List<ComputerDTO>> getAll(@RequestParam Map<String, String> paths) {
		Page<ComputerDTO> pageComputer = new Page<ComputerDTO>(computerService.getAll(), computerService.getAll().size());

		String search = paths.get("search");
		if(search != null && !search.equals("")) {
			pageComputer.setList(computerService.findByName(search));
		}

		String sortBy = paths.get("sortBy");
		if(sortBy != null && !sortBy.equals("")) {
			pageComputer.setList(ComputerMapper.sortBy(pageComputer.getList(), sortBy.toUpperCase()));
		}

		String asc = paths.get("asc");
		if(asc == null) asc = "true";
		else if(asc != "") {
			if(!Boolean.valueOf(asc))
				Collections.reverse(pageComputer.getList());
		}

		String maxElement = paths.get("maxElement");
		if(maxElement != null && !maxElement.equals("")) {
			pageComputer.setMaxElement(Integer.valueOf(maxElement));
		}

		String numPage = paths.get("numPage");
		if(numPage != null && !numPage.equals("")) {
			pageComputer.setNumPage(Integer.valueOf(numPage));
		}

		return new ResponseEntity<List<ComputerDTO>>(pageComputer.currentPage(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ComputerDTO> findById(@PathVariable Long id) {
		ComputerDTO compDTO;
		try {
			compDTO = ComputerMapper.computerToDTO(computerService.findById(id));
			return new ResponseEntity<ComputerDTO>(compDTO, HttpStatus.OK);
		} catch (ComputerException e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<ComputerDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<ComputerDTO> createComputer(@Validated @RequestBody ComputerDTO computer){
		Computer comp = new Computer();
		
		try {
			comp = ComputerMapper.dtoToComputer(computer);
			computerService.create(comp);
			return new ResponseEntity<ComputerDTO>(computer, HttpStatus.OK);
		} catch (TimestampException e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<ComputerDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

	@PutMapping("/{id}")
	public ResponseEntity<ComputerDTO> updateComputer(@Validated @RequestBody ComputerDTO computer, @PathVariable Long id) {
		Computer comp = new Computer();
		try {
			comp = ComputerMapper.dtoToComputer(computer);
			computerService.update(comp);
			return new ResponseEntity<ComputerDTO>(computer, HttpStatus.OK);
		} catch (ValidatorException e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<ComputerDTO>(HttpStatus.NOT_FOUND);
		}catch (TimestampException e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<ComputerDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		try {
			computerService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ValidatorException e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
