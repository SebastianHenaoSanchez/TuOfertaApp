/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.JsonApiBodyRequest;
import io.swagger.model.JsonApiBodyRequestLogin;
import io.swagger.model.JsonApiBodyResponseErrors;
import io.swagger.model.JsonApiBodyResponseSuccess;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-08-16T19:45:02.366Z")

@Api(value = "login", description = "the login API")
public interface LoginApi {

    @ApiOperation(value = "login de personas", nickname = "loginPost", notes = "", response = JsonApiBodyRequest.class, tags={ "login", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "loggeado correctamente", response = JsonApiBodyRequest.class),
        @ApiResponse(code = 400, message = "Datos incompletos o incorrectos en el loggeo", response = JsonApiBodyResponseErrors.class) })
    @RequestMapping(value = "/login",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<?> loginPost(@ApiParam(value = "Json a ingresar" ,required=true )  @Valid @RequestBody JsonApiBodyRequestLogin body);

    
    @ApiOperation(value = "login de personas", nickname = "loginPost", notes = "", response = JsonApiBodyRequest.class, tags={ "login", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "loggeado correctamente", response = JsonApiBodyRequest.class),
        @ApiResponse(code = 400, message = "Datos incompletos o incorrectos en el loggeo", response = JsonApiBodyResponseErrors.class) })
    @RequestMapping(value = "/login",
        produces = { "application/json" }, 
        
        method = RequestMethod.GET)
    ResponseEntity<?> loginGet(@ApiParam(value = "Json a ingresar" ,required=true )  @Valid @RequestParam String correo,@ApiParam(value = "Json a ingresar" ,required=true )  @Valid @RequestParam String contrasena );

}
