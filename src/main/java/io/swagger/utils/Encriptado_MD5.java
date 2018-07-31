package io.swagger.utils;

import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;

public class Encriptado_MD5 {
	
	public String encriptar (String clave) {
		String TextoEncriptado = DigestUtils.md5Hex(clave);
		//System.out.println("Calve encriptada: "+ TextoEncriptado);
		return TextoEncriptado;
	}

}
