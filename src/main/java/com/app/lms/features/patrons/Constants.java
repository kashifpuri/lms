package com.app.lms.features.patrons;

public final class Constants {
	
	 private static final String BASE_URL = "/api/v1/";
	 public static final String GET_PATRONS = BASE_URL + "patrons";
	 public static final String ADD_PATRON = BASE_URL + "patron";
	 public static final String DELETE_PATRON = BASE_URL + "patron/{patronId}";
	 public static final String GET_PATRON_DETAIL = BASE_URL + "patron/{patronId}";
	 public static final String UPDATE_PATRON = BASE_URL + "patron/{patronId}";

}
