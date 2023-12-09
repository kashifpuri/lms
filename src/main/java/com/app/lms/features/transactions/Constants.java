package com.app.lms.features.transactions;

public final class Constants {
	
	 private static final String BASE_URL = "/api/v1/";
	 public static final String GET_TRANSACTIONS = BASE_URL + "transactions";
	 public static final String ADD_TRANSACTION = BASE_URL + "transaction";
	 public static final String DELETE_TRANSACTION = BASE_URL + "transaction/{transactionId}";
	 public static final String GET_TRANSACTION_DETAIL = BASE_URL + "transaction/{transactionId}";
	 public static final String UPDATE_TRANSACTION = BASE_URL + "transaction/{transactionId}";

}
