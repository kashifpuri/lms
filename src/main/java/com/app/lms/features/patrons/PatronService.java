package com.app.lms.features.patrons;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PatronService {
	
	private final PatronRepository patronRepository;
	private final PatronMapper patronMapper;
	
	
	PatronService(PatronRepository patronRepository, 
			PatronMapper patronMapper) {
		this.patronRepository = patronRepository;
		this.patronMapper = patronMapper;
		
	}
	
	public List<com.app.lms.patron.data.dto.Patron> getPatrons() {
		List<Patron> patronList = patronRepository.findAll();
		List<com.app.lms.patron.data.dto.Patron> dtoPatronList = patronMapper.mapPatron(patronList);
		return dtoPatronList;
		
	}
	
	public Optional<com.app.lms.patron.data.dto.Patron> getPatronDetail(final Integer patronId) {
		
		Optional<Patron> newPatron = patronRepository.findById(patronId);
		if (newPatron.isPresent()) {
			return Optional.of(patronMapper.mapPatron(newPatron.get()));
		} 
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patron Not Found");
	}
	
	public com.app.lms.patron.data.dto.Patron addPatron(final com.app.lms.patron.data.dto.Patron patron) {
		Patron newPatronEntity = patronMapper.mapPatron(patron);
		Patron newPatron = patronRepository.save(newPatronEntity);
		return patronMapper.mapPatron(newPatron);
		
	}
	
	public boolean updatePatron(final com.app.lms.patron.data.dto.Patron patron) {
		
		if(getPatronDetail(patron.getPatronId()).isPresent()) {
			Patron newPatronEntity = patronMapper.mapPatron(patron);
			patronRepository.save(newPatronEntity);
			return true;
		} 
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patron Not Found");
	}
	
	public void deletePatron(final Integer patronId) {
		patronRepository.deleteById(patronId);
	}

}
