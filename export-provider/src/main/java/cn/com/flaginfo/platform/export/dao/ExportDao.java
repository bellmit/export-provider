package cn.com.flaginfo.platform.export.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.flaginfo.platform.common.base.BaseDao;
import cn.com.flaginfo.platform.export.model.ExportResult;

@Repository
public class ExportDao extends BaseDao{
	
	public ExportDao() {
		tableName = "dc_task";
	}
	
	public List listExportTasks(Date startTime, Date endTime, String status,
			String taskId) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
				.append(" select id, sp_id, operator, operator ")
				.append(" ,create_time,status,start_time,end_time,remark ")
				.append(" ,export_num,export_cost,export_title, source ")
				.append(" ,file_type,file_path,is_down,type, request_param ")
				.append(" from dc_task ")
				.append(" where create_time >=:startTime and create_time <=:endTime ")
				.append(" and status=:status and id =:id and sp_id =:spId and type =:type ")
				.append(" and file_type =:fileType and operator_id =:operatorId and source=:source ")
				.append(" order by create_time desc ");
		return createDBOperation(sqlBuilder.toString())
				.setParameter("taskId", taskId).setDate("startTime", startTime)
				.setDate("endTime", endTime).setParameter("status", status)
				.list();
		
	}
	
}
