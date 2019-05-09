package com.excilys.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.dto.CompanyDTO;
import com.excilys.exception.ValidatorException;
import com.excilys.service.CompanyService;

@RestController
@RequestMapping(path = "/company", produces = "application/json")
public class CompanyRestController {
	private CompanyService companyService;
	private static final Logger LOG = LoggerFactory.getLogger(CompanyRestController.class);

	public CompanyRestController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@GetMapping
	public ResponseEntity<List<CompanyDTO>> getAll(@RequestParam Map<String, String> paths) {		
		return new ResponseEntity<List<CompanyDTO>>(companyService.getAll(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		try {
			companyService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ValidatorException e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
