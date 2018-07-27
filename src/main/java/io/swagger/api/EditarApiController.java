package io.swagger.api;

import io.swagger.model.JsonApiBodyRequest;
import io.swagger.model.JsonApiBodyResponseErrors;
import io.swagger.model.JsonApiBodyResponseSuccess;
import io.swagger.model.RegistrarRequest;
import io.swagger.repository.UserRepository;

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

    @org.springframework.beans.factory.annotation.Autowired
    public EditarApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<JsonApiBodyResponseSuccess> editarPut(@ApiParam(value = "Pet object that needs to be added to the store" ,required=true )  @Valid @RequestBody JsonApiBodyRequest body) {
        String accept = request.getHeader("Accept");
        String 	nombre = body.getPersona().get(0).getNombre();
        String id = body.getPersona().get(0).getId();
        String estado = body.getPersona().get(0).getEstado();
        if (accept != null && accept.contains("application/json")) {
        	
        	RegistrarRequest persona = personaRepository.save(body.getPersona().get(0)); 
            
        	 JsonApiBodyResponseSuccess respuestaExitosa = new JsonApiBodyResponseSuccess();
             respuestaExitosa.setId(id);
             respuestaExitosa.setEstado(estado);
             respuestaExitosa.setNombre(nombre);
            return new ResponseEntity<JsonApiBodyResponseSuccess>(respuestaExitosa,HttpStatus.NOT_IMPLEMENTED);
            
        }

        return new ResponseEntity<JsonApiBodyResponseSuccess>(HttpStatus.NOT_IMPLEMENTED);
    }

}
