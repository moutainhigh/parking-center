package com.spm.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.spm.dto.MonthlyCardDto;
import com.spm.dto.ResultObject;
import com.spm.search.form.MonthlyCradSearchForm;

public interface MonthlyCradService {
	ResultObject<List<MonthlyCardDto>> getAllMonthlyCard();
	
	ResultObject<List<MonthlyCardDto>> getAllMonthlyCard(MonthlyCradSearchForm monthlyCradSearchForm, Pageable pageable);
	
	MonthlyCardDto getMonthlyCardById(Long monthlyCardId);
	
	boolean addMonthlyCard(MonthlyCardDto monthlyCardDto);
	
	void deleteMonthlyCard(Long id);
	
	boolean updateMonthlyCard(MonthlyCardDto monthlyCardDto);
	
}
