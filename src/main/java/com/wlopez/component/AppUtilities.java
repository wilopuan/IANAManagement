package com.wlopez.component;

import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import org.springframework.stereotype.Component;

import com.sun.media.sound.InvalidFormatException;
import com.wlopez.entity.RangeLocation;

@Component("appUtilities")
public class AppUtilities {

	/**
	 * Cantidad de tokens esperada
	 */
	private final static int TOKENS_QUANTITY = 9;

	private final static String FORMAT_ERROR = "Se esperaba un valor Numerico";
	private final static String QUANTITY_ERROR = "Se esperaban " + TOKENS_QUANTITY + " campos en el registro";

	/**
	 * Convierte una cadena en registro
	 * 
	 * @param record
	 * @return
	 * @throws InvalidFormatException
	 */
	public RangeLocation stringToRangeLocation(String record) throws InvalidFormatException {

		RangeLocation rangeLocation = new RangeLocation();
		StringTokenizer tokensRecord = null;

		/*
		 * Formato Esperado en record
		 * 16800640,"16800767","JP","JAPAN","OKAYAMA","OKAYAMA","34.6647",
		 * "133.925","ENERGIA COMMUNICATIONS INC."
		 */
		record = record.replaceAll("\"", "");
		tokensRecord = new StringTokenizer(record, ",");

		if (tokensRecord.countTokens() < TOKENS_QUANTITY) {
			throw new InvalidFormatException(QUANTITY_ERROR);
		}

		try {
			// field-1
			rangeLocation.setIpFrom(Long.parseLong(tokensRecord.nextToken()));
			// field-2
			rangeLocation.setIpTo(Long.parseLong(tokensRecord.nextToken()));
			// field-3
			rangeLocation.setCountryCode(truncateIfgreater(tokensRecord.nextToken(), 16));
			// field-4
			rangeLocation.setCountry(truncateIfgreater(tokensRecord.nextToken(), 32));
			// field-5
			rangeLocation.setRegion(truncateIfgreater(tokensRecord.nextToken(), 64));
			// field-6
			rangeLocation.setCity(truncateIfgreater(tokensRecord.nextToken(), 64));
			// field-7
			rangeLocation.setLatitude(truncateIfgreater(tokensRecord.nextToken(), 16));
			// field-8
			rangeLocation.setLongitude(truncateIfgreater(tokensRecord.nextToken(), 16));
			// field-9
			rangeLocation.setCompany(truncateIfgreater(tokensRecord.nextToken(), 128));

		} catch (NumberFormatException ex) {
			throw new InvalidFormatException(FORMAT_ERROR);
		}

		return rangeLocation;
	}

	/**
	 * Validacion de la anatomia de una direccionIP
	 * 
	 * @param target
	 * @return
	 */
	public boolean validaOctetosIp(String target) {

		StringTokenizer tokenString = new StringTokenizer(target, ".");
		int numericValue = 0;

		if (tokenString.countTokens() != 4) {
			return Boolean.FALSE;
		}

		while (tokenString.hasMoreTokens()) {

			try {
				numericValue = Integer.parseInt(tokenString.nextToken());
			} catch (NumberFormatException ex) {
				return Boolean.FALSE;
			}
			if (numericValue < 0 || numericValue >= 255) {
				return Boolean.FALSE;
			}
		}

		return Boolean.TRUE;

	}

	/**
	 * Retorna los n caracteres de la variable en parametro
	 *
	 * @param String
	 *            Cadena a truncar
	 * @param int
	 *            posiciones a truncar
	 * @return String cadena truncada
	 */
	private String truncateIfgreater(String target, int lonParser) {
		if (target == null) {
			return target;
		}
		if (target.length() <= lonParser) {
			return target;
		} else {

		}
		return target.substring(0, lonParser);
	}

	/**
	 * Verificación de la existencia de un erchivo en ruta
	 * 
	 * @param pathName
	 * @return
	 */
	public boolean fileExist(String pathName) {
		File archivo = null;
		FileReader fr = null;
		boolean ioRessult = Boolean.TRUE;

		try {
			archivo = new File(pathName);
			fr = new FileReader(archivo);
		} catch (Exception e) {
			ioRessult = Boolean.FALSE;
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				ioRessult = Boolean.FALSE;
				;
			}
		}

		return ioRessult;
	}
	
	/**
	 * Confierte desde una cadena con Diracción IP Valida a un Decimal
	 * @param ipvalue
	 * @return
	 */
	public long fromIPformatToDecimal(String ipvalue){

		final Double FACTOR = new Double(256);
		Double sumSegment = 0.0; 
		
		StringTokenizer tokenString = new StringTokenizer(ipvalue, ".");
				
		Double segment1 = new Double(Integer.parseInt(tokenString.nextToken()));
		segment1 = segment1 * Math.pow(FACTOR, 3);
		
		Double segment2 = new Double(Integer.parseInt(tokenString.nextToken()));
		segment2 = segment2 * Math.pow(FACTOR, 2);
		
		Double segment3 = new Double(Integer.parseInt(tokenString.nextToken()));
		segment3 = segment3 * Math.pow(FACTOR, 1);
		
		Double segment4 = new Double(Integer.parseInt(tokenString.nextToken()));
		segment4 = segment4 * Math.pow(FACTOR, 0);
		
		sumSegment = (segment1+segment2+segment3+segment4);
		
		return sumSegment.longValue(); 
	}	

}
