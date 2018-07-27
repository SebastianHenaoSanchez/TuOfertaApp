package io.swagger.api;

import io.swagger.model.JsonApiBodyRequest;
import io.swagger.model.JsonApiBodyResponseErrors;
import io.swagger.model.JsonApiBodyResponseSuccess;
import io.swagger.model.RegistrarRequest;
import io.swagger.repository.UserRepository;
import io.swagger.utils.FlagsInformation;

import com.amazonaws.services.directconnect.model.transform.NewPrivateVirtualInterfaceAllocationMarshaller;
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
public class RegistrarApiController implements RegistrarApi {

	@Autowired
	UserRepository userRepository;
	
    private static final Logger log = LoggerFactory.getLogger(RegistrarApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    FlagsInformation error = new FlagsInformation();

    @org.springframework.beans.factory.annotation.Autowired
    public RegistrarApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> registrarPost(@ApiParam(value = "body" ,required=true )  @Valid @RequestBody JsonApiBodyRequest body) {
        String accept = request.getHeader("Accept");
        String 	nombre = body.getPersona().get(0).getNombre();
        String id = body.getPersona().get(0).getId();
        String estado = body.getPersona().get(0).getEstado();
        
        JsonApiBodyResponseSuccess respuestaExitosa = new JsonApiBodyResponseSuccess();
        respuestaExitosa.setId(id);
        respuestaExitosa.setEstado(estado);
        respuestaExitosa.setNombre(nombre);
   
        if (accept != null && accept.contains("application/json")) {
     
        	if(body.getPersona().get(0).getRol().equalsIgnoreCase("super administrador master")) {
        		JsonApiBodyResponseErrors responseError = new JsonApiBodyResponseErrors();
        		responseError.setCodigo(error.SUPERADMINMASTER_ERROR_CODE);
        		responseError.setDetalle(error.SUPERADMINMASTER_ERROR_MSN);
        		return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.FAILED_DEPENDENCY);
        	}else {
            	RegistrarRequest persona = userRepository.save(body.getPersona().get(0));
                return new ResponseEntity<JsonApiBodyResponseSuccess>(respuestaExitosa, HttpStatus.NOT_IMPLEMENTED);
        	}
          
		} else {
			return new ResponseEntity<JsonApiBodyResponseSuccess>(HttpStatus.NOT_IMPLEMENTED);
		}
        
	}

}
