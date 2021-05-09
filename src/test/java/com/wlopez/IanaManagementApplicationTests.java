package com.wlopez;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.sun.media.sound.InvalidFormatException;
import com.wlopez.component.AppUtilities;

@SpringBootTest
class IanaManagementApplicationTests {

	boolean errorDetected = Boolean.TRUE;

	@Test
	/**
	 * Validacion de la función de Conversion del registro leido Invalido
	 */
	void probeBadStringToRangeLocation() {

		String probeString = "";
		AppUtilities appUtilities = new AppUtilities();

		try {
			assertThat(appUtilities.stringToRangeLocation(probeString)).isNull();
		} catch (InvalidFormatException excpt) {
			assertThat(errorDetected).isTrue();
		}

	}

	@Test
	/**
	 * Validacion de la función de Conversion del registro leido Valido
	 */
	void probeOkStringToRangeLocation() {

		String probeString = "16800640,\"16800767\",\"JP\",\"JAPAN\",\"OKAYAMA\",\"OKAYAMA\",\"34.6647\",\"133.925\",\"ENERGIA COMMUNICATIONS INC.\"";
		long probeLong = 16800767L;
		AppUtilities appUtilities = new AppUtilities();

		try {
			assertThat(appUtilities.stringToRangeLocation(probeString).getIpTo()).isEqualTo(probeLong);
		} catch (InvalidFormatException excpt) {
			assertThat(errorDetected).isFalse();
		}

	}

	@Test
	/**
	 * Validacion anatomias Ok dirección IP
	 */
	void probeOkValidaOctetosIp() {

		String probeString = "198.168.1.1";
		AppUtilities appUtilities = new AppUtilities();
		assertThat(appUtilities.validaOctetosIp(probeString)).isEqualTo(Boolean.TRUE);
	}

	@Test
	/**
	 * Validacion anatomias Bad dirección IP
	 */
	void probeBadValidaOctetosIp() {

		String probeString = "198.380.1.1";
		AppUtilities appUtilities = new AppUtilities();
		assertThat(appUtilities.validaOctetosIp(probeString)).isEqualTo(Boolean.FALSE);
	}

	@Test
	/**
	 * Validación Conversión direccion IP en formato de Cadena a Decimal 
	 */
	void probeFromIPformatToDecimal() {

		String probeString = "201.184.37.54";
		AppUtilities appUtilities = new AppUtilities();
		assertThat(appUtilities.fromIPformatToDecimal(probeString)).isEqualByComparingTo(3384288566L);
	}	

}
