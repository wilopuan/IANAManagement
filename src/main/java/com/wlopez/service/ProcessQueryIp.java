package com.wlopez.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.wlopez.component.AppUtilities;
import com.wlopez.entity.RangeLocation;
import com.wlopez.model.ProccessResponse;
import com.wlopez.model.RangeLocatioFormatQry;
import com.wlopez.service.impl.RangeLocationServiceImpl;

@Service("processQueryIp")
public class ProcessQueryIp {

	private Logger logger = LoggerFactory.getLogger(ProcessFileService.class);
	
	@Autowired
	@Qualifier("rangeLocationServiceImpl")
	private RangeLocationServiceImpl rangeLocationServiceImpl;
	
	@Autowired
	@Qualifier("appUtilities")
	private AppUtilities appUtilities;		
	
	
	public ProccessResponse doQuery(String ipParam){
		
		List<Object> resultComment = new ArrayList<Object>();
		
		ProccessResponse proccessResponse = new ProccessResponse();
		
		if (!appUtilities.validaOctetosIp(ipParam)){
			proccessResponse.setStatus(HttpStatus.NOT_ACCEPTABLE);
			proccessResponse.setComment("La direccion IP esta fuera de un formato valido ");
			resultComment.add("-");
			proccessResponse.setItems(resultComment);			
			logger.info("Error en peticion de consulta: " + ipParam);			
			return proccessResponse;			
		}
		
		long ipDecimal = appUtilities.fromIPformatToDecimal(ipParam);
		
		List<RangeLocation> locationList = rangeLocationServiceImpl.findRecordsWithIpInRange(ipDecimal);
		
		List<Object> locationResul = new ArrayList<Object>();
		
		RangeLocatioFormatQry rangeLocatioFormatQry = null; 
		
		if (locationList.isEmpty()) {
			proccessResponse.setStatus(HttpStatus.PRECONDITION_FAILED);
			proccessResponse.setComment("No hubo resultados para la consulta");
			resultComment.add("-");
			proccessResponse.setItems(resultComment);			
			logger.info("Error en peticion de consulta: " + ipParam);			
			return proccessResponse;
		}
		
		proccessResponse.setStatus(HttpStatus.OK);
		proccessResponse.setComment("Consulta satisfactoria");
		for (RangeLocation recordF: locationList ){
			rangeLocatioFormatQry = new RangeLocatioFormatQry();
			rangeLocatioFormatQry.convertFromRangeLocation(recordF);
			locationResul.add(rangeLocatioFormatQry);
		}
		proccessResponse.setItems(locationResul);
		
		return proccessResponse;
	}
	
}
