package io.swagger.api;

import io.swagger.model.JsonApiBodyRequest;
import io.swagger.model.JsonApiBodyResponseErrors;
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

    @org.springframework.beans.factory.annotation.Autowired
    public EliminarApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<JsonApiBodyRequest> eliminarIdDelete(@ApiParam(value = "Pet id to delete",required=true) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
        	RegistrarRequest persona = personaRepository.findOne(id);
        	personaRepository.delete(id);
        	JsonApiBodyRequest body = new JsonApiBodyRequest();
        	List<RegistrarRequest> lista = new ArrayList<RegistrarRequest>();
			lista.add(persona);
        	body.setPersona(lista);
            	
            return new ResponseEntity<JsonApiBodyRequest>(body, HttpStatus.NOT_IMPLEMENTED);
            
        }

        return new ResponseEntity<JsonApiBodyRequest>(HttpStatus.NOT_IMPLEMENTED);
    }

}
