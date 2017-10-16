package cn.com.flaginfo.platform.export.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.flaginfo.platform.export.dao.ExportDao;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class ExportService {
	
	@Autowired
	private ExportDao exportDao;
	
	public List listExportTasks(Date startTime, Date endTime, String status,
			String taskId) {
		return exportDao.listExportTasks(startTime, endTime, status,taskId);
	}
	
	
}
