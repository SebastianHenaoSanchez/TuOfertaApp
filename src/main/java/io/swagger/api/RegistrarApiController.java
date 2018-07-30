package io.swagger.api;

import io.swagger.model.JsonApiBodyRequest;
import io.swagger.model.JsonApiBodyResponseErrors;
import io.swagger.model.JsonApiBodyResponseSuccess;
import io.swagger.model.RegistrarRequest;
import io.swagger.repository.UserRepository;
import io.swagger.utils.FlagsInformation;
import io.swagger.utils.Validaciones;

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

	public ResponseEntity<?> registrarPost(@ApiParam(value = "body", required = true) @Valid @RequestBody JsonApiBodyRequest body) {
		String accept = request.getHeader("Accept");

		String nombre = body.getPersona().get(0).getNombre();
		String id = body.getPersona().get(0).getId();
		String estado = body.getPersona().get(0).getEstado();
		String rol = body.getPersona().get(0).getRol();

		JsonApiBodyResponseErrors responseError = new JsonApiBodyResponseErrors();
		JsonApiBodyResponseSuccess respuestaExitosa = new JsonApiBodyResponseSuccess();
		respuestaExitosa.setId(id);
		respuestaExitosa.setEstado(estado);
		respuestaExitosa.setNombre(nombre);

		if (accept != null && accept.contains("application/json")) {
			List<RegistrarRequest> correo = userRepository.findByCorreo(body.getPersona().get(0).getCorreo());
			// mirar si el correo ya existe

			if (correo.isEmpty()) {

				if (Validaciones.validarCorreo(body.getPersona().get(0).getCorreo())) {
					System.out.println("correo melo");

				} else {
					responseError.setCodigo(error.CODE_6002);
					responseError.setDetalle(error.MSN_CODE_6002);
					return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.FAILED_DEPENDENCY);

				}

				if (body.getPersona().get(0).getRol().equalsIgnoreCase("super administrador master")) {

					responseError.setCodigo(error.SUPERADMINMASTER_ERROR_CODE);
					responseError.setDetalle(error.SUPERADMINMASTER_ERROR_MSN);
					return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.FAILED_DEPENDENCY);
				} else {
					
					//lista de personas de acuerdo a un token
					List<RegistrarRequest> personas;	
					String token = body.getPersona().get(0).getToken();
					if (rol.equals("administrador")) {
						personas = userRepository.findByToken(token);
						//pregunta si el token esta en la db
						if (personas.isEmpty() || personas.equals(null)) {
							responseError.setCodigo("1");
							responseError.setDetalle("este rol no existe");
							return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.FAILED_DEPENDENCY);
						}else {
							//pregunta si el rol de la persona con ese token es super administrador  
							if(personas.get(0).getRol().equals("super administrador")) {
								body.getPersona().get(0).setToken("");
								RegistrarRequest persona = userRepository.save(body.getPersona().get(0));
								return new ResponseEntity<JsonApiBodyResponseSuccess>(respuestaExitosa, HttpStatus.NOT_IMPLEMENTED);
								
								
							}else {
								responseError.setCodigo("2");
								responseError.setDetalle("solo un super administrador puede crear administradores");
								return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.FAILED_DEPENDENCY);
							}
						}

					}else if (rol== "usuario") {
						RegistrarRequest persona = userRepository.save(body.getPersona().get(0));
						return new ResponseEntity<JsonApiBodyResponseSuccess>(respuestaExitosa, HttpStatus.NOT_IMPLEMENTED);
						
						
						
					}else if (rol == "super administrador") {
						
					}else {
						responseError.setCodigo(error.CODE_2001);
						responseError.setDetalle(error.MSN_CODE_2001);
						return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.FAILED_DEPENDENCY);
					}
					
					responseError.setCodigo("3");
					responseError.setDetalle("algo falló");
					return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.FAILED_DEPENDENCY);
					

				}

			} else {
				responseError.setCodigo(error.CODE_6001);
				responseError.setDetalle(error.MSN_CODE_6001);
				return new ResponseEntity<JsonApiBodyResponseErrors>(responseError, HttpStatus.FAILED_DEPENDENCY);
			}

		} else {
			return new ResponseEntity<JsonApiBodyResponseSuccess>(HttpStatus.NOT_IMPLEMENTED);
		}

	}

}
