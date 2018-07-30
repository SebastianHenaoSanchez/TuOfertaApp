package io.swagger.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;


import io.swagger.model.RegistrarRequest;

@EnableScan
public interface UserRepository extends CrudRepository<RegistrarRequest, String>{
	//public List<User> findByEmail(String email);
	public List<RegistrarRequest> findByRol(String rol);
	public List<RegistrarRequest> findByEstado(String estado);
	public List<RegistrarRequest> findByCorreo(String correo);
	public List<RegistrarRequest> findByToken(String token);
}
