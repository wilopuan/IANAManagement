package com.wlopez.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.wlopez.component.AppUtilities;
import com.wlopez.component.RunnableTask;
import com.wlopez.model.ProccessResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("processFileService")
public class ProcessFileService {

	private Logger logger = LoggerFactory.getLogger(ProcessFileService.class);

    @Autowired
    private TaskExecutor taskExecutor;	
	
	@Autowired
	@Qualifier("appUtilities")
	private AppUtilities appUtilities;
	
    @Autowired
    private ApplicationContext applicationContext;	

	public ProccessResponse doProcessFile(String pathAndName) {

		logger.info("Iniciando Petición de proceso en archivo: " + pathAndName);

		ProccessResponse proccessResponse = new ProccessResponse();
		List<Object> resultComment = new ArrayList<Object>();

		if (appUtilities.fileExist(pathAndName)) {
			proccessResponse.setStatus(HttpStatus.OK);
			proccessResponse.setComment("Se ha lanzado el procesamiento del Archivo");
			resultComment.add("Esta Operacion no entrega resultados");
			proccessResponse.setItems(resultComment);
			
			RunnableTask procThread = applicationContext.getBean(RunnableTask.class);
			procThread.setRutaSource(pathAndName);
	        taskExecutor.execute(procThread);			

			logger.info("Proceso en progreso para archivo: " + pathAndName);

		} else {
			proccessResponse.setStatus(HttpStatus.NOT_ACCEPTABLE);
			proccessResponse.setComment("No se encontro el Archivo Solicitado");
			logger.info("Error en peticion para archivo: " + pathAndName);
		}

		return proccessResponse;
	}

}
