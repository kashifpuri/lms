package com.app.lms.features.patrons;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static com.app.lms.features.patrons.Constants.ADD_PATRON;
import static com.app.lms.features.patrons.Constants.DELETE_PATRON;
import static com.app.lms.features.patrons.Constants.GET_PATRONS;
import static com.app.lms.features.patrons.Constants.GET_PATRON_DETAIL;
import static com.app.lms.features.patrons.Constants.UPDATE_PATRON;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.app.lms.patron.data.dto.AddPatronResponse;
import com.app.lms.patron.data.dto.PatronsListResponse;

@RestController
public class PatronController {

	private final PatronService patronService;
	
	PatronController(PatronService patronService) {
		this.patronService = patronService;
	}
	
	@GetMapping(value = GET_PATRONS, produces = MediaType.APPLICATION_JSON_VALUE)
	public PatronsListResponse getPatronsList() {
		PatronsListResponse response = new PatronsListResponse();
		response.setPatrons(patronService.getPatrons());
		return response;
	}
	
	@GetMapping(value = GET_PATRON_DETAIL, produces = MediaType.APPLICATION_JSON_VALUE)
	public com.app.lms.patron.data.dto.Patron getPatronDetail(
			@PathVariable(value = "patronId", required = true) @Valid @NotNull Integer patronId) {
		Optional<com.app.lms.patron.data.dto.Patron> patron = 
				patronService.getPatronDetail(patronId);
		return patron.get();
	}
	
	@PostMapping(value = ADD_PATRON, produces = MediaType.APPLICATION_JSON_VALUE)
	public AddPatronResponse addPatron(
			@Valid @RequestBody com.app.lms.patron.data.dto.Patron patron) {
		AddPatronResponse response = new AddPatronResponse();
		response.setPatron(patronService.addPatron(patron));
		return response;
	}
	
	@PutMapping(value = UPDATE_PATRON, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updatePatron(
			@Valid @RequestBody com.app.lms.patron.data.dto.Patron patron) {
		patronService.updatePatron(patron);
	}
	
	@DeleteMapping(value = DELETE_PATRON, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deletePatron(@PathVariable(value = "patronId", required = true) @Valid @NotNull Integer patronId) {
		patronService.deletePatron(patronId);
	}
	
	
}
