package io.swagger.api;

import io.swagger.model.JsonApiBodyRequest;
import io.swagger.model.JsonApiBodyResponseErrors;
import io.swagger.model.JsonApiBodyResponseSuccess;
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
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-25T19:33:57.780Z")

@Controller

public class EditarApiController implements EditarApi {

    private static final Logger log = LoggerFactory.getLogger(EditarApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    UserRepository personaRepository;
    
    JsonApiBodyResponseSuccess respuestaExitosa = new JsonApiBodyResponseSuccess();
    JsonApiBodyResponseErrors responseError = new JsonApiBodyResponseErrors();
    FlagsInformation  error = new FlagsInformation();

    @org.springframework.beans.factory.annotation.Autowired
    public EditarApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> editarPut(@ApiParam(value = "Pet object that needs to be added to the store" ,required=true )  @Valid @RequestBody JsonApiBodyRequest body) {
        String accept = request.getHeader("Accept");
        
        String 	nombre = body.getPersona().get(0).getNombre();
        String id = body.getPersona().get(0).getId();
        String estado = body.getPersona().get(0).getEstado();
        String rol =body.getPersona().get(0).getRol();
        
     
      
        if (accept != null && accept.contains("application/json")) {
        	
        	List<RegistrarRequest> root;
        	root = personaRepository.findByToken(body.getPersona().get(0).getToken());
        	if(root == null || root.isEmpty()) {
        		responseError.setCodigo("0000000");
        		responseError.setDetalle("token no existe");
        		return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.NOT_IMPLEMENTED);
        	}	
        	if(root.get(0).getId().equals("cf7e2532-7483-4cd2-b970-20b065dd58dd")) {
        		//el admin root solo se edita asi mismo
        		if(id.equals("cf7e2532-7483-4cd2-b970-20b065dd58dd")) {
        			body.getPersona().get(0).setToken("");
    	       		RegistrarRequest persona = personaRepository.save(body.getPersona().get(0)); 
    	            
    	             respuestaExitosa.setId(id);
    	             respuestaExitosa.setEstado(estado);
    	             respuestaExitosa.setNombre(nombre);
    	            return new ResponseEntity<JsonApiBodyResponseSuccess>(respuestaExitosa,HttpStatus.NOT_IMPLEMENTED);
    	       	}else {
    	       		//si el root va a editar un super admin
    	       		if (rol == "super administrador") {
    	       			body.getPersona().get(0).setToken("");
    	       			RegistrarRequest persona = personaRepository.save(body.getPersona().get(0)); 
        	            
          	             respuestaExitosa.setId(id);
          	             respuestaExitosa.setEstado(estado);
          	             respuestaExitosa.setNombre(nombre);
          	            return new ResponseEntity<JsonApiBodyResponseSuccess>(respuestaExitosa,HttpStatus.NOT_IMPLEMENTED);	
    	       		}else {
    	       			responseError.setCodigo("034");
    	       			responseError.setDetalle("el root solo puede editar super admins o asi mismo");
    		      		return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.NOT_IMPLEMENTED);
    	       		}
    	       	}
        	}
        	if(root.get(0).getRol().equals("super administrador")) {
        		if(root.get(0).getId().equals(id)) {
        			body.getPersona().get(0).setToken("");
        			RegistrarRequest persona = personaRepository.save(body.getPersona().get(0)); 
    	            
     	             respuestaExitosa.setId(id);
     	             respuestaExitosa.setEstado(estado);
     	             respuestaExitosa.setNombre(nombre);
     	            return new ResponseEntity<JsonApiBodyResponseSuccess>(respuestaExitosa,HttpStatus.NOT_IMPLEMENTED);
       			
        		}
        		if (rol.equals("administrador")) {
        			body.getPersona().get(0).setToken("");
        			RegistrarRequest persona = personaRepository.save(body.getPersona().get(0)); 
    	            
      	             respuestaExitosa.setId(id);
      	             respuestaExitosa.setEstado(estado);
      	             respuestaExitosa.setNombre(nombre);
      	            return new ResponseEntity<JsonApiBodyResponseSuccess>(respuestaExitosa,HttpStatus.NOT_IMPLEMENTED);
        			
        		}else {
        			responseError.setCodigo("4545");
    	       		responseError.setDetalle("el super admin solo puede editar administradores o asi mismo");
    	      		return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.NOT_IMPLEMENTED);
        		}
       		
        	}
        	if(root.get(0).getRol().equals("usuario")) {
        		if(root.get(0).getId().equals(id)) {
        			
        			body.getPersona().get(0).setToken("");
        			RegistrarRequest persona = personaRepository.save(body.getPersona().get(0)); 
    	            
     	             respuestaExitosa.setId(id);
     	             respuestaExitosa.setEstado(estado);
     	             respuestaExitosa.setNombre(nombre);
     	            return new ResponseEntity<JsonApiBodyResponseSuccess>(respuestaExitosa,HttpStatus.NOT_IMPLEMENTED);
       			
        			
        		}
        		else {
        			responseError.setCodigo("9385");
    	       		responseError.setDetalle("el usuario solo puede editarse asi mismo");
    	      		return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.NOT_IMPLEMENTED);
        		}
        		
        	}
        	if(root.get(0).getRol().equals("administrador")) {
        		if(root.get(0).getId().equals(id)) {
        			body.getPersona().get(0).setToken("");
        			RegistrarRequest persona = personaRepository.save(body.getPersona().get(0)); 
    	            
    	             respuestaExitosa.setId(id);
    	             respuestaExitosa.setEstado(estado);
    	             respuestaExitosa.setNombre(nombre);
    	            return new ResponseEntity<JsonApiBodyResponseSuccess>(respuestaExitosa,HttpStatus.NOT_IMPLEMENTED);
        		}else {
        			responseError.setCodigo("98342");
    	       		responseError.setDetalle("el administrador solo puede editarse asi mismo");
    	      		return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.NOT_IMPLEMENTED);
        		}
        	}
        	
        }

        return new ResponseEntity<JsonApiBodyResponseSuccess>(HttpStatus.NOT_IMPLEMENTED);
    }

}
