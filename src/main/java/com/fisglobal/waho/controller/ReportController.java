package com.fisglobal.waho.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fisglobal.waho.beans.MessageBean;
import com.fisglobal.waho.beans.ReportBean;
import com.fisglobal.waho.beans.ReportRecord;
import com.fisglobal.waho.model.User;
import com.fisglobal.waho.model.UserRole;
import com.fisglobal.waho.services.ReportService;
import com.fisglobal.waho.services.UserRoleService;
import com.fisglobal.waho.services.UserService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@CrossOrigin(origins = {"${cross.origins}", "*"})
//@RestController
@RequestMapping("/api")
@Controller
public class ReportController {
	static Logger log = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private ReportService reportService;

	@Value( "${jasper.report.default}" )
	private String jasperfile_default;


	@PostMapping(path = "/generatereportpdf", consumes = "application/json", produces = "application/pdf")
	public ResponseEntity<?> generatereportpdf(@RequestBody ReportBean reportBean, @RequestHeader HttpHeaders headers, HttpServletRequest request){
		log.debug("generatereportpdf() - Start");

		int last_updated_by = Integer.valueOf(reportBean.getLast_updated_by());

		List<UserRole>  userRoles = userRoleService.getRoleByUserId(last_updated_by);
		if(userRoles != null) {

			boolean isAdmin = false;
			for (UserRole userRole : userRoles) {
				if ("ADMIN".equals(userRole.getRoleCd())) {
					isAdmin = true;
					break;
				}
			}

			if (isAdmin == false) {
				log.debug("generatereportpdf() - Return");
				return new ResponseEntity<Object>(new MessageBean("You must be an Administrator to Generate Report!"), HttpStatus.OK);
			}

			try {


				List<Integer> userid = new ArrayList<Integer>();
				List<String> disp = new ArrayList<String>();
				for (String rb : reportBean.getEmp()) {
					String[] values = rb.split("-~-");
					userid.add(Integer.valueOf(values[0]));
					disp.add(values[1]);
				}
				reportBean.setEmp_userid(userid);
				reportBean.setEmp_disp(disp);

				userid = new ArrayList<Integer>();
				disp = new ArrayList<String>();
				for (String rb : reportBean.getMgr()) {
					String[] values = rb.split("-~-");
					userid.add(Integer.valueOf(values[0]));
					disp.add(values[1]);
				}
				reportBean.setMgr_userid(userid);
				reportBean.setMgr_disp(disp);

				List<ReportRecord>  reportRecords = reportService.getReportRecords(reportBean);
				byte[] bytes = createReport(reportRecords, last_updated_by, reportBean);

				String pattern = "yyyyMMddHHmmssSSS";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new java.util.Date());


				log.debug("generatereportpdf() - Return");
				return ResponseEntity
						.ok()
						// Specify content type as PDF
						.header("Content-Type", "application/pdf; charset=UTF-8")
						// Tell browser to display PDF if it can
						.header("Content-Disposition", "inline; filename=\"" + "fiswfhreport" + last_updated_by + date + ".pdf\"")
						.body(bytes);



			} catch (final Exception e) {
				log.error("generatereportpdf()", e);
				e.printStackTrace();
				return new ResponseEntity<Object>(new MessageBean("Generate Report Failed!"), HttpStatus.OK);
			}


		} else {
			log.debug("generatereportpdf() - Return");
			return new ResponseEntity<Object>(new MessageBean("You must be an Administrator to GeneOrate Report!"), HttpStatus.OK);
		}
	}




	@PostMapping(path = "/generatereportxls", consumes = "application/json", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public ResponseEntity<?> generatereportxls(@RequestBody ReportBean reportBean, @RequestHeader HttpHeaders headers, HttpServletRequest request){
		log.debug("generatereport() - Start");

		int last_updated_by = Integer.valueOf(reportBean.getLast_updated_by());

		List<UserRole>  userRoles = userRoleService.getRoleByUserId(last_updated_by);
		if(userRoles != null) {

			boolean isAdmin = false;
			for (UserRole userRole : userRoles) {
				if ("ADMIN".equals(userRole.getRoleCd())) {
					isAdmin = true;
					break;
				}
			}

			if (isAdmin == false) {
				log.debug("generatereportxls() - Return");
				return new ResponseEntity<Object>(new MessageBean("You must be an Administrator to Generate Report!"), HttpStatus.OK);
			}

			try {


				List<Integer> userid = new ArrayList<Integer>();
				List<String> disp = new ArrayList<String>();
				for (String rb : reportBean.getEmp()) {
					String[] values = rb.split("-~-");
					userid.add(Integer.valueOf(values[0]));
					disp.add(values[1]);
				}
				reportBean.setEmp_userid(userid);
				reportBean.setEmp_disp(disp);

				userid = new ArrayList<Integer>();
				disp = new ArrayList<String>();
				for (String rb : reportBean.getMgr()) {
					String[] values = rb.split("-~-");
					userid.add(Integer.valueOf(values[0]));
					disp.add(values[1]);
				}
				reportBean.setMgr_userid(userid);
				reportBean.setMgr_disp(disp);

				List<ReportRecord>  reportRecords = reportService.getReportRecords(reportBean);
				byte[] bytes = createReport(reportRecords, last_updated_by, reportBean);

				String pattern = "yyyyMMddHHmmssSSS";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new java.util.Date());


				log.debug("generatereportxls() - Return");
				return ResponseEntity
						.ok()
						// Specify content type as PDF
						.header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
						// Tell browser to display PDF if it can
						.header("Content-Disposition", "inline; filename=\"" + "fiswfhreport" + last_updated_by + date + ".xlsx\"")
						.body(bytes);



			} catch (final Exception e) {
				log.error("generatereportxls()", e);
				e.printStackTrace();
				return new ResponseEntity<Object>(new MessageBean("Generate Report Failed!"), HttpStatus.OK);
			}


		} else {
			log.debug("generatereportxls() - Return");
			return new ResponseEntity<Object>(new MessageBean("You must be an Administrator to GeneOrate Report!"), HttpStatus.OK);
		}
	}


	private byte[] createReport(final List<ReportRecord> reportRecords, int last_updated_by, ReportBean reportBean) throws JRException, IOException {
		//		final InputStream stream = this.getClass().getResourceAsStream("/Default.jrxml");
		//		final JasperReport report = JasperCompileManager.compileReport(stream);

		String jasperfilestr = "";
		if ("Default".equals(reportBean.getType())) {
			jasperfilestr = this.jasperfile_default;
		} else {
			jasperfilestr = this.jasperfile_default;
		}

		File jasperfile = new File(jasperfilestr);
		final JasperReport report = (JasperReport) JRLoader.loadObject(jasperfile);

		final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(reportRecords);

		final Map<String, Object> parameters = new HashMap<>();

		Optional<User> optional = userService.findById(last_updated_by);
		if(optional.isPresent()) {
			User currentUser = optional.get();
			parameters.put("currentUser", currentUser.getLastName() + ", " + currentUser.getFirstName() + " (" + currentUser.getUserEid() + ")");
		}

		if (!reportBean.getEmp_disp().isEmpty()) {
			parameters.put("employee", reportBean.getEmp_disp().toString());
		} else {
			parameters.put("employee", "All");
		}

		if (!reportBean.getMgr_disp().isEmpty()) {
			parameters.put("manager", reportBean.getMgr_disp().toString());
		} else {
			parameters.put("manager", "All");
		}

		if (!reportBean.getStatus().isEmpty()) {
			parameters.put("status", reportBean.getStatus().toString());
		} else {
			parameters.put("status", "All");
		}

		if (reportBean.getScheddatefrom() != null) {

			String pattern = "dd-MMM-yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(reportBean.getScheddatefrom());
			parameters.put("datefrom", date);
		}

		if (reportBean.getScheddateto() != null) {

			String pattern = "dd-MMM-yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(reportBean.getScheddateto());
			parameters.put("dateto", date);
		}

		parameters.put("format", reportBean.getFormat());

		final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

		byte[] bytes = null;
		if ("PDF".equals(reportBean.getFormat())) {
			bytes = JasperExportManager.exportReportToPdf(print);
		} else if ("XLS".equals(reportBean.getFormat())) {
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(new SimpleExporterInput(print));
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));

			SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
			reportConfig.setSheetNames(new String[] { "FIS Work From Home Report" });
			exporter.setConfiguration(reportConfig);

			exporter.exportReport();
			bytes = byteArrayOutputStream.toByteArray();
		}

		return bytes;

	}
}

