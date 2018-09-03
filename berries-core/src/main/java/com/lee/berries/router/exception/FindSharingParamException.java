package com.lee.berries.router.exception;

import com.lee.berries.router.model.BaseSharing;

public class FindSharingParamException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6107256975792583847L;

	public FindSharingParamException() {
        super();
    }
	
	public FindSharingParamException(String message) {
        super(message);
    }
	
	public FindSharingParamException(BaseSharing baseSharing) {
        super("not find sharing colmn value for " + baseSharing.getTableName() + "." + baseSharing.getColumnName());
    }
}
