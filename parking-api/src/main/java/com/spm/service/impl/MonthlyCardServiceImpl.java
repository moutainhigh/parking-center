package com.spm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.dto.MonthlyCardDto;
import com.spm.entity.MonthlyCardEntity;
import com.spm.repository.MonthlyCardRepository;
import com.spm.service.MonthlyCardService;

/**
 * Created by Vincent on 02/10/2018
 */

@Service
public class MonthlyCardServiceImpl implements MonthlyCardService {

	@Autowired
	private MonthlyCardRepository monthlyCardRepository;

	ModelMapper mapper;

	private List<MonthlyCardDto> map(List<MonthlyCardEntity> source) {

		ArrayList<MonthlyCardDto> rtn = new ArrayList<>();
		source.stream().map((entity) -> {
			MonthlyCardDto dto = new MonthlyCardDto();
			mapper.map(entity, dto);
			return dto;
		}).forEachOrdered((dto) -> {
			rtn.add(dto);
		});
		return rtn;
	}
	
	private List<MonthlyCardEntity> reMap(List<MonthlyCardDto> source) {

		ArrayList<MonthlyCardEntity> rtn = new ArrayList<>();
		source.stream().map((dto) -> {
			MonthlyCardEntity entity = new MonthlyCardEntity();
			mapper.map(dto, entity);
			return entity;
		}).forEachOrdered((entity) -> {
			rtn.add(entity);
		});
		return rtn;
	}

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public MonthlyCardDto save(MonthlyCardDto monthlyCardDto) {
		MonthlyCardEntity entity = new MonthlyCardEntity();
		mapper.map(monthlyCardDto, entity);
		entity = monthlyCardRepository.save(entity);
		mapper.map(entity, monthlyCardDto);
		return monthlyCardDto;
	}
	

	@Override
	public void delete(MonthlyCardDto monthlyCardDto) {
		MonthlyCardEntity entity = new MonthlyCardEntity();
		mapper.map(monthlyCardDto, entity);
		monthlyCardRepository.delete(entity);

	}


	@Override
	public List<MonthlyCardDto> findAll() {
		List<MonthlyCardEntity> entities = monthlyCardRepository.findAll();
		return this.map(entities);
	}

	@Override
	public List<MonthlyCardDto> save(List<MonthlyCardDto> monthlyCardDtos) {
		List<MonthlyCardEntity> monthlyCardsEntities = reMap(monthlyCardDtos);
		
		monthlyCardsEntities = monthlyCardRepository.saveAll(monthlyCardsEntities);
		monthlyCardDtos = map(monthlyCardsEntities);
		return monthlyCardDtos;
	}

}