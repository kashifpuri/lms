package com.app.lms.features.patrons;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PatronMapper {
	
	
	@Mapping(target = "patronId", source = "id", qualifiedByName = "mapId")
	//@Mapping(target = "type", source = "patronType", qualifiedByName = "mapPatronTypeToType")
	com.app.lms.patron.data.dto.Patron mapPatron(Patron patron);
	
	
	List<com.app.lms.patron.data.dto.Patron> mapPatron(List<Patron> patronList);
	
	@Mapping(target = "id", source = "patronId", qualifiedByName = "mapId")
	Patron mapPatron(com.app.lms.patron.data.dto.Patron patron);
	
	
	@Named("mapId")
    default String mapId(Integer id) {
		return (id != null) ? String.valueOf(id.intValue()) : null ;
    }
	
	/*@Named("mapPatronTypeToType")
    default String mapPatronTypeToType(String patronType) {
		return patronType ;
    }
	
	@Named("mapTypeToPatronType")
    default String mapTypeToPatronType(String type) {
		return type ;
    }
	 */

}
