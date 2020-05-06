package com.viettel.im.database.BO;

import com.viettel.database.BO.BasicBO;

/**
 * CommCounterParams entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CommCounterParams extends BasicBO  {

	// Fields

	private Long counterParamId;
	private Long counterId;
	private String paramName;
	private String dataType;
	private Long paramOrder;
    private Long status;

	// Constructors

	/** default constructor */
	public CommCounterParams() {
	}

	/** minimal constructor */
	public CommCounterParams(Long counterParamId) {
		this.counterParamId = counterParamId;
	}

	/** full constructor */
	public CommCounterParams(Long counterParamId, Long counterId,
			String paramName, String dataType, Long paramOrder) {
		this.counterParamId = counterParamId;
		this.counterId = counterId;
		this.paramName = paramName;
		this.dataType = dataType;
		this.paramOrder = paramOrder;
	}

	// Property accessors

	public Long getCounterParamId() {
		return this.counterParamId;
	}

	public void setCounterParamId(Long counterParamId) {
		this.counterParamId = counterParamId;
	}

	public Long getCounterId() {
		return this.counterId;
	}

	public void setCounterId(Long counterId) {
		this.counterId = counterId;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Long getParamOrder() {
		return this.paramOrder;
	}

	public void setParamOrder(Long paramOrder) {
		this.paramOrder = paramOrder;
	}

    /**
     * @return the status
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Long status) {
        this.status = status;
    }

}