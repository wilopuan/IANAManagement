package com.wlopez.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wlopez.component.AppUtilities;
import com.wlopez.model.ProccessResponse;
import com.wlopez.service.ProcessFileService;
import com.wlopez.service.ProcessQueryIp;
import com.wlopez.service.impl.RangeLocationServiceImpl;


@RestController
@RequestMapping("/rest")
public class ServiceController {

	@Value("${filePathToProcess}")
	private String filePathP;

	@Autowired
	@Qualifier("processFileService")
	private ProcessFileService processFileService;	

	@Autowired
	@Qualifier("processQueryIp")
	private ProcessQueryIp processQueryIp;		
	
	@Autowired
	@Qualifier("rangeLocationServiceImpl")
	private RangeLocationServiceImpl rangeLocationServiceImpl;
	
	@Autowired
	@Qualifier("appUtilities")
	private AppUtilities appUtilities;		
	
	
	@GetMapping("/isalive")
	public ResponseEntity<String> isAlive(){
		return new ResponseEntity<String>("Respondiendo... ",HttpStatus.OK);
	}	

	/**
	 * Procesamiento del archivo
	 * @param nameFile
	 * @return
	 */
	@GetMapping("/processfile/{nameFile}")
	public ResponseEntity<ProccessResponse> processfile(@PathVariable("nameFile") String nameFile){
		
		String completPath = filePathP + nameFile; 	
		ProccessResponse proccessResponse = processFileService.doProcessFile(completPath);   
		return new ResponseEntity<ProccessResponse>(proccessResponse,proccessResponse.getStatus());
		
	}		

	/**
	 * Procesamiento de Consulta
	 * @param ipvalue
	 * @return
	 */
	@GetMapping("/queryfromip/{ipvalue}")
	public ResponseEntity<ProccessResponse> queryip(@PathVariable("ipvalue") String ipvalue){
		
		ProccessResponse proccessResponse = processQueryIp.doQuery(ipvalue);   
		return new ResponseEntity<ProccessResponse>(proccessResponse,proccessResponse.getStatus());
		
	}	
	
	
}