package com.fisglobal.waho.services;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fisglobal.waho.beans.ReportBean;
import com.fisglobal.waho.beans.ReportRecord;
import com.fisglobal.waho.model.UserRole;

@Service
@Transactional
public class ReportService {

	@PersistenceContext
	private EntityManager em;


	public List<ReportRecord> getReportRecords(ReportBean reportBean) {
		List<ReportRecord>  reportRecords = new ArrayList<ReportRecord>();
		String sql = "SELECT" + 
				" u.last_name," + 
				" u.first_name," + 
				" u.user_eid," + 
				" DATE_FORMAT(t.sched_time, \"%Y-%m-%d %r\") as sched_time," + 
				" t.sched_time_status," + 
				" t.remarks," + 
				" (SELECT CONCAT(x.last_name,\", \",x.first_name,\" (\",x.user_eid,\")\") FROM wh_users x WHERE x.user_id = u.user_parent_id) as manager" + 
				" FROM wh_users u, wh_user_shift_schedules s, wh_shift_sched_times t" + 
				" WHERE" + 
				" u.user_id = s.user_id AND" + 
				" s.shft_sched_id = t.shift_sched_id";
				
		if (!reportBean.getEmp_userid().isEmpty()) {
			sql = sql + " AND u.user_id in (:employee)";
		}
		
		if (!reportBean.getMgr_userid().isEmpty()) {
			sql = sql + " AND u.user_parent_id in (:manager)";
		}
		
		if (!reportBean.getStatus().isEmpty()) {
			sql = sql + " AND t.sched_time_status in (:status)";
		}
		
		if (reportBean.getScheddatefrom() != null) {
			sql = sql + " AND t.sched_time >= :scheddatefrom";
		}
		
		if (reportBean.getScheddateto() != null) {
			sql = sql + " AND t.sched_time <= :scheddateto";
		}
		
		
				
		sql = sql + " ORDER BY" + 
				" manager, u.user_eid, t.sched_time";
		Query query = em.createNativeQuery(sql);
		
		
		
		
		
		if (!reportBean.getEmp_userid().isEmpty()) {
			query.setParameter("employee", reportBean.getEmp_userid());
		}
		
		if (!reportBean.getMgr_userid().isEmpty()) {
			query.setParameter("manager", reportBean.getMgr_userid());
		}
		
		if (!reportBean.getStatus().isEmpty()) {
			query.setParameter("status", reportBean.getStatus());
		}
		
		if (reportBean.getScheddatefrom() != null) {
			
//			query.setParameter("scheddatefrom", localDateToDate(reportBean.getScheddatefrom()));
			query.setParameter("scheddatefrom", reportBean.getScheddatefrom());
		}
		
		if (reportBean.getScheddateto() != null) {
			query.setParameter("scheddateto", reportBean.getScheddateto());
		}
		


		//		if (!query.getResultList().isEmpty()) {

		List<Object> os = query.getResultList();
		for (Object o : os) {
			Object[] cols = (Object[]) o;
			ReportRecord reportRecord = new ReportRecord();
			reportRecord.setLast_name((String)cols[0]);
			reportRecord.setFirst_name((String)cols[1]);
			reportRecord.setUser_eid((String)cols[2]);
			reportRecord.setSched_time((String)cols[3]);
			reportRecord.setSched_time_status((String)cols[4]);
			reportRecord.setRemarks((String)cols[5]);
			reportRecord.setManager((String)cols[6]);

			reportRecords.add(reportRecord);
		}

		return reportRecords;
		//		} else {
		//			return null;
		//		}
	}
	
//	public static Date localDateToDate(LocalDateTime localDate) {
//	      Calendar calendar = Calendar.getInstance();
//	      calendar.clear();
//	      //assuming start of day
//	      calendar.set(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
//	      return calendar.getTime();
//	  }


}
