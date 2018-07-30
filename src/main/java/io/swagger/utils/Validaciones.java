package io.swagger.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {


		public static boolean validarCorreo(String correo) {
				// Patrón para validar el email
				boolean valido = false;
		        Pattern pattern = Pattern
		                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		 
		        // El email a validar
		        String email = correo;
		        Matcher mathcer = pattern.matcher(email);
		        if (mathcer.find() == true) {
		        	valido = true;
		        } else {
		            valido = false;
		        }
		        return valido;
			}  
		 

}
