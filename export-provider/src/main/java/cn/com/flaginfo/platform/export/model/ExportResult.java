/**
 * 2014年3月10日上午9:49:53
 * 
 * Copyright 2009-2014 Flaginfo , Inc. All rights reserved.
 * FLAGINFO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.flaginfo.platform.export.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author chengbin.luo
 */
public class ExportResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7995671452383141954L;

	private int rows;
	private Date startTime;
	private Date endTime;
    private long totalTime;
	private long writeTime;
	private String exportPath;
	private String status;
	private String remark;
	private String exportId;

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public long getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(long writeTime) {
		this.writeTime = writeTime;
	}
	
	public void addWriteTime(Long WriteTime) {
	    this.writeTime += writeTime;
	}

	public String getExportPath() {
		return exportPath;
	}

	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}
	
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
       return this.endTime;
    }

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the exportId
	 */
	public String getExportId() {
		return exportId;
	}

	/**
	 * @param exportId the exportId to set
	 */
	public void setExportId(String exportId) {
		this.exportId = exportId;
	}
    
}
