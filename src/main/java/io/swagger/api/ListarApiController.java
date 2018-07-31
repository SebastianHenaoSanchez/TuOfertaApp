package io.swagger.api;

import io.swagger.model.JsonApiBodyRequest;
import io.swagger.model.JsonApiBodyResponseErrors;
import io.swagger.model.RegistrarRequest;
import io.swagger.repository.UserRepository;
import io.swagger.utils.FlagsInformation;

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
import java.util.ArrayList;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-25T19:33:57.780Z")

@Controller
public class ListarApiController implements ListarApi {

    private static final Logger log = LoggerFactory.getLogger(ListarApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    UserRepository personaRepository;
    
    FlagsInformation error = new FlagsInformation();

    @org.springframework.beans.factory.annotation.Autowired
    public ListarApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<JsonApiBodyRequest> listarEstadoEstadoGet(@ApiParam(value = "rol de personas a retornar",required=true) @PathVariable("estado") String estado) {
    	 String accept = request.getHeader("Accept");
         if (accept != null && accept.contains("application/json")) {
             List<RegistrarRequest> persona = personaRepository.findByEstado(estado);
             
             JsonApiBodyRequest body = new JsonApiBodyRequest();
 			body.setPersona(persona);
 			return new ResponseEntity<JsonApiBodyRequest>(body, HttpStatus.NOT_IMPLEMENTED);
         }

         return new ResponseEntity<JsonApiBodyRequest>(HttpStatus.NOT_IMPLEMENTED);
         
     }

    public ResponseEntity<?> listarGet() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<RegistrarRequest> persona = (List<RegistrarRequest>) personaRepository.findAll() ;
			JsonApiBodyRequest body = new JsonApiBodyRequest();
				body.setPersona(persona);
		
			if (persona.isEmpty()) {
				JsonApiBodyResponseErrors responseError = new JsonApiBodyResponseErrors();
        		responseError.setCodigo(error.CODE_BD_VACIA);
        		responseError.setDetalle(error.BD_VACIA);
        		return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.FAILED_DEPENDENCY);
			}else {
				
				return new ResponseEntity<JsonApiBodyRequest>(body, HttpStatus.NOT_IMPLEMENTED);
        
			}
        }

        return new ResponseEntity<JsonApiBodyRequest>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> listarIdGet(@ApiParam(value = "ID of pet that needs to be fetched",required=true) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            RegistrarRequest persona = personaRepository.findOne(id);
            
			if (persona == null) {
				JsonApiBodyResponseErrors responseError = new JsonApiBodyResponseErrors();
				responseError.setCodigo(error.CODE_1001);
				responseError.setDetalle(error.MSN_CODE_1001);
				return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.FAILED_DEPENDENCY);
			} else {
				JsonApiBodyRequest body = new JsonApiBodyRequest();
				List<RegistrarRequest> lista = new ArrayList<RegistrarRequest>();
				lista.add(persona);
				for (RegistrarRequest registrarRequest : lista) {
					System.out.println(registrarRequest.getCorreo());
				}
				body.setPersona(lista);
				return new ResponseEntity<JsonApiBodyRequest>(body, HttpStatus.NOT_IMPLEMENTED);

			}
		}

        return new ResponseEntity<JsonApiBodyRequest>(HttpStatus.NOT_IMPLEMENTED);
    } 

    public ResponseEntity<?> listarRolRolGet(@ApiParam(value = "rol de personas a retornar",required=true) @PathVariable("rol") String rol) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<RegistrarRequest> persona = personaRepository.findByRol(rol);
           
            JsonApiBodyRequest body = new JsonApiBodyRequest();
			body.setPersona(persona);
			return new ResponseEntity<JsonApiBodyRequest>(body, HttpStatus.NOT_IMPLEMENTED);
        }

        return new ResponseEntity<JsonApiBodyRequest>(HttpStatus.NOT_IMPLEMENTED);
        
    }

}
