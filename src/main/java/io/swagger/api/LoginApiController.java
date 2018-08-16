package io.swagger.api;

import io.swagger.model.JsonApiBodyRequest;
import io.swagger.model.JsonApiBodyRequestLogin;
import io.swagger.model.JsonApiBodyResponseErrors;
import io.swagger.model.JsonApiBodyResponseSuccess;
import io.swagger.model.RegistrarRequest;
import io.swagger.repository.UserRepository;
import io.swagger.utils.Encriptado_MD5;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-08-16T19:45:02.366Z")

@Controller
public class LoginApiController implements LoginApi {

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    UserRepository personaRepository;
    
    JsonApiBodyResponseSuccess respuestaExitosa = new JsonApiBodyResponseSuccess();
    JsonApiBodyResponseErrors  responseError = new JsonApiBodyResponseErrors();
    
    @org.springframework.beans.factory.annotation.Autowired
    public LoginApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> loginPost(@ApiParam(value = "Json a ingresar" ,required=true )  @Valid @RequestBody JsonApiBodyRequestLogin body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
        	
            Encriptado_MD5 encriptar = new Encriptado_MD5();
			String contraseñaIngresada = encriptar.encriptar(body.getContrasena());
			
			//buscamos la persona que tiene el correo ingresado
			List<RegistrarRequest> persona = personaRepository.findByCorreo(body.getCorreo());
			
			//preguntamos si encontro a alguien con ese correo
			if (persona == null || persona.isEmpty()) {
				responseError.setCodigo("343");
				responseError.detalle("correo no existe");
				return new ResponseEntity<JsonApiBodyResponseErrors>(responseError,HttpStatus.NOT_IMPLEMENTED);
			}else {
				//si el correo existe en la BD ahora preguntamos si las contraseñas coinciden
				String contraseñaGuardada = persona.get(0).getContrasena();
				if(contraseñaGuardada.equals(contraseñaIngresada)) {
					
					JsonApiBodyRequest datosPersona = new JsonApiBodyRequest();
			    	datosPersona.setPersona(persona);
					respuestaExitosa.setEstado(datosPersona.getPersona().get(0).getEstado());
					respuestaExitosa.setId(datosPersona.getPersona().get(0).getId());
					respuestaExitosa.setNombre(datosPersona.getPersona().get(0).getNombre());
				
				 return new ResponseEntity<JsonApiBodyRequest>(datosPersona, HttpStatus.OK);
				}else {
					responseError.setCodigo("2423");
					responseError.setDetalle("contraseña incorrecta");
					return new ResponseEntity<JsonApiBodyResponseErrors>(responseError,HttpStatus.NOT_IMPLEMENTED);
				}
				
			}
        }

        return new ResponseEntity<JsonApiBodyResponseSuccess>(HttpStatus.NOT_IMPLEMENTED);
    }

}
