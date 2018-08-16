package io.swagger.api;

import io.swagger.model.JsonApiBodyRequest;
import io.swagger.model.JsonApiBodyRequestDelete;
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
import java.util.ArrayList;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-07-25T19:33:57.780Z")

@Controller
public class EliminarApiController implements EliminarApi {

    private static final Logger log = LoggerFactory.getLogger(EliminarApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    UserRepository personaRepository;

    FlagsInformation error = new FlagsInformation();
 
    @org.springframework.beans.factory.annotation.Autowired
    public EliminarApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> eliminarIdDelete(@ApiParam(value = "body",required=true) @Valid @RequestBody JsonApiBodyRequestDelete body) {
        String accept = request.getHeader("Accept");

		String id = body.getPersona().get(0).getId();
	
		
        JsonApiBodyResponseErrors responseError = new JsonApiBodyResponseErrors();
       
      
        if (accept != null && accept.contains("application/json")) {
        	List<RegistrarRequest> root;
        	root = personaRepository.findByToken(body.getPersona().get(0).getToken());
        	
        	//no dejar que se elimine super admin root
        	System.out.println("antes de buscar por id");
        	RegistrarRequest persona=personaRepository.findOne(body.getPersona().get(0).getId());
        	System.out.println("antes del null");
        	if (persona == null) {
        		System.out.println("entro al null");
    			responseError.setCodigo(error.CODE_1001);
    			responseError.setDetalle(error.MSN_CODE_1001);
    			return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.NOT_IMPLEMENTED);
    		}
        	System.out.println("despues del null");
        	if(persona.getId().equals("cf7e2532-7483-4cd2-b970-20b065dd58dd")) {
        		responseError.setCodigo(error.SUPERADMIN_ROOT_ELIMINAR_ERROR_CODE);
        		responseError.setDetalle(error.SUPERADMIN_ROOT_ELIMINAR_MSN);
        		return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.NOT_IMPLEMENTED);
        	}
        	//miramos si el token obtenido esta en la BD
        	if(root == null || root.isEmpty()) {
        		responseError.setCodigo("0000000");
        		responseError.setDetalle("token no existe");
        		return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.NOT_IMPLEMENTED);
        	}
        	//miramos si la persona que realiza la accion es el root
        	if (root.get(0).getRol().equals("super administrador") && root.get(0).getId().equals("cf7e2532-7483-4cd2-b970-20b065dd58dd")) {
        		System.out.println("entro aqui a eliminar");	
		        		JsonApiBodyResponseSuccess respuestaExitosa = new JsonApiBodyResponseSuccess();
		        		respuestaExitosa.setEstado("eliminada");
		        		respuestaExitosa.setId(persona.getId());
		        		respuestaExitosa.setNombre(persona.getNombre());
		        		List<RegistrarRequest> lista = new ArrayList<RegistrarRequest>();
						lista.add(persona);
						
						personaRepository.delete(id);
		
		        		
						return new ResponseEntity<JsonApiBodyResponseSuccess>(respuestaExitosa, HttpStatus.OK);
		        		
        	}else {
        		responseError.setCodigo("9999999");
        		responseError.setDetalle("no tiene permiso para eliminar");
        		return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.NOT_IMPLEMENTED);
        		
        	}
        	
      
            
        }

        return new ResponseEntity<JsonApiBodyRequest>(HttpStatus.NOT_IMPLEMENTED);
    }

}
