package com.wlopez.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.media.sound.InvalidFormatException;
import com.wlopez.entity.RangeLocation;
import com.wlopez.service.impl.RangeLocationServiceImpl;

@Component
@Scope("prototype")
public class RunnableTask implements Runnable {
	
	@Autowired
	@Qualifier("rangeLocationServiceImpl")
	private RangeLocationServiceImpl rangeLocationServiceImpl;
	
	@Autowired
	@Qualifier("appUtilities")
	private AppUtilities appUtilities;

	
	private Logger logger = LoggerFactory.getLogger(RunnableTask.class);


	/**
	 * Ruta de proceso
	 */
	private String rutaSource;

	@Override
	public void run() {

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String readedRecord;
		int countRecord = 0;
		RangeLocation rangeLocation = null;

		try {
			archivo = new File(this.rutaSource);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			while ((readedRecord = br.readLine()) != null) {
				countRecord++;
				try {
					rangeLocation = appUtilities.stringToRangeLocation(readedRecord);
					rangeLocationServiceImpl.addRecord(rangeLocation);
				} catch (InvalidFormatException e) {
					logger.error("Error In Record (Not Added): "+countRecord+" : "+e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
		}
		
		logger.info("Para Archivo: "+this.rutaSource+" Se han procesado: "+countRecord+" Registros");

	}

	public String getRutaSource() {
		return rutaSource;
	}

	public void setRutaSource(String rutaSource) {
		this.rutaSource = rutaSource;
	}

	public RunnableTask(String rutaSource) {
		this.rutaSource = rutaSource;
	}

	public RunnableTask() {

	}

}
