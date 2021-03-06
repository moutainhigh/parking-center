package com.spm.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spm.common.util.DateUtil;
import com.spm.common.util.constant.SessionConstants;
import com.spm.dto.EmployeeDto;
import com.spm.dto.ResultObject;
import com.spm.dto.RevenueDto;
import com.spm.export.RevenueExport;
import com.spm.search.form.RevenueSearchForm;
import com.spm.service.EmployeeService;
import com.spm.service.RevenueService;

/**
 * Created by Vincent on 02/10/2018
 */
@Controller
@RequestMapping("/revenue")
public class RevenueController {

	@Autowired
	private RevenueService revenueService;
	
	@Autowired
	private EmployeeService employeeServie;
	
	
	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String getRevenue(
			@RequestParam(name="employeeId", required=false)String employeeId,
			@RequestParam(name="dateFrom", required=false)String dateFrom,
			@RequestParam(name="hourFrom", required=false)String hourFrom,
			@RequestParam(name="minFrom", required=false)String minFrom,
			@RequestParam(name="dateTo", required=false)String dateTo,
			@RequestParam(name="hourTo", required=false)String hourTo,
			@RequestParam(name="minTo", required=false)String minTo,
			
			Model model,  HttpServletRequest request) throws ParseException {
		
		List<String> projects = (List<String>)request.getSession().getAttribute(SessionConstants.PROJECT_SESSION_NAME);
		
		RevenueSearchForm revenueSearchForm = new RevenueSearchForm();
		revenueSearchForm.setProjectId(Integer.valueOf(projects.get(0)));
		revenueSearchForm.setEmployeeId(employeeId);
		revenueSearchForm.setDateFrom(dateFrom);
		revenueSearchForm.setHourFrom(hourFrom);
		revenueSearchForm.setMinFrom(minFrom);
		if(dateFrom ==  null || dateFrom.isEmpty()) {
			revenueSearchForm.setDateFrom(DateUtil.getCurrentDateString());
			revenueSearchForm.setHourFrom("00");
			revenueSearchForm.setMinFrom("00");
		}
		
		revenueSearchForm.setDateTo(dateTo);
		revenueSearchForm.setHourTo(hourTo);
		revenueSearchForm.setMinTo(minTo);
		if(dateTo == null || dateTo.isEmpty()) {
			revenueSearchForm.setDateTo(revenueSearchForm.getDateFrom());
			revenueSearchForm.setHourTo("23");
			revenueSearchForm.setMinTo("59");
		}
		
		
		model.addAttribute("revenueSearchForm", revenueSearchForm);
		
		ResultObject<List<RevenueDto>> revenueMap = revenueService.getAllRevenue(revenueSearchForm);
		
		ResultObject<List<EmployeeDto>>  employees = employeeServie.getAllEmployeeByProjectId(Long.valueOf(projects.get(0)));
		
		model.addAttribute("employees", employees.getData());
		
		model.addAttribute("revenues", revenueMap.getData());
		return "revenuePage";
	}
	
	@RequestMapping(value = "/export", method = {RequestMethod.GET})
	public RevenueExport exportRevenue(
			@RequestParam(name="employeeId", required=false)String employeeId,
			@RequestParam(name="dateFrom", required=false)String dateFrom,
			@RequestParam(name="hourFrom", required=false)String hourFrom,
			@RequestParam(name="minFrom", required=false)String minFrom,
			@RequestParam(name="dateTo", required=false)String dateTo,
			@RequestParam(name="hourTo", required=false)String hourTo,
			@RequestParam(name="minTo", required=false)String minTo,
			Model model,  HttpServletRequest request) throws ParseException {
		
		List<String> projects = (List<String>)request.getSession().getAttribute("projects");
		
		RevenueSearchForm revenueSearchForm = new RevenueSearchForm();
		revenueSearchForm.setProjectId(Integer.valueOf(projects.get(0)));
		revenueSearchForm.setEmployeeId(employeeId);
		revenueSearchForm.setDateFrom(dateFrom);
		revenueSearchForm.setHourFrom(hourFrom);
		revenueSearchForm.setMinFrom(minFrom);
		if(dateFrom ==  null || dateFrom.isEmpty()) {
			revenueSearchForm.setDateFrom(DateUtil.getCurrentDateString());
			revenueSearchForm.setHourFrom("00");
			revenueSearchForm.setMinFrom("00");
		}
		
		revenueSearchForm.setDateTo(dateTo);
		revenueSearchForm.setHourTo(hourTo);
		revenueSearchForm.setMinTo(minTo);
		if(dateTo == null || dateTo.isEmpty()) {
			revenueSearchForm.setDateTo(revenueSearchForm.getDateFrom());
			revenueSearchForm.setHourTo("23");
			revenueSearchForm.setMinTo("59");
		}
		
		ResultObject<List<RevenueDto>> revenueMap = revenueService.getAllRevenue(revenueSearchForm);
		model.addAttribute("revenueDtos", revenueMap.getData());
		model.addAttribute("fileName", "revenues.xls");
		return new RevenueExport();
	}
	
	
}
