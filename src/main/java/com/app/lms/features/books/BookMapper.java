package com.app.lms.features.books;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookMapper {
	
	
	@Mapping(target = "bookId", source = "id", qualifiedByName = "mapId")
	//@Mapping(target = "type", source = "bookType", qualifiedByName = "mapBookTypeToType")
	com.app.lms.book.data.dto.Book mapBook(Book book);
	
	
	List<com.app.lms.book.data.dto.Book> mapBook(List<Book> bookList);
	
	//@Mapping(target = "bookType", source = "type", qualifiedByName = "mapTypeToBookType")
	@Mapping(target = "id", source = "bookId", qualifiedByName = "mapId")
	Book mapBook(com.app.lms.book.data.dto.Book book);
	
	
	@Named("mapId")
    default String mapId(Integer id) {
		return (id != null) ? String.valueOf(id.intValue()) : null ;
    }
	
	/*@Named("mapBookTypeToType")
    default String mapBookTypeToType(String bookType) {
		return bookType ;
    }
	
	@Named("mapTypeToBookType")
    default String mapTypeToBookType(String type) {
		return type ;
    }
	 */

}
