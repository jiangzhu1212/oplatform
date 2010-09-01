package com.risetek.operation.platform.base.client.entry;

import java.io.Serializable;

public class RoleOperation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer Id;
	
   	private Integer roleId;
	
	private String operationMode;
	
	private String operationAction;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getOperationMode() {
		return operationMode;
	}

	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}

	public String getOperationAction() {
		return operationAction;
	}

	public void setOperationAction(String operationAction) {
		this.operationAction = operationAction;
	}

}
