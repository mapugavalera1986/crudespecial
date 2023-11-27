package pe.pugavalera.cursos.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pe.pugavalera.cursos.modelos.Auth;
import pe.pugavalera.cursos.modelos.Usuario;

@Service
public class IngresoService {
	
	private Logger logger = LoggerFactory.getLogger(IngresoService.class);
	
	@Autowired
	private RestTemplate restTemplate;

	@Value("${api.superapi.baseUri}")
	private String baseUri;

	//Con esta, vamos a probar si funciona ver la Token
	public Auth generarToken(Usuario registrado) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> entity = new HttpEntity<Object>(registrado, headers);
		try {
			ResponseEntity<Auth> response = restTemplate.exchange(baseUri+"auth/login",
					HttpMethod.POST,entity,Auth.class);
			if(response.getStatusCode() == HttpStatus.OK) {
				logger.info("Ya generaste una token");
				return response.getBody();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR - " + e.getMessage());
		}
		return null;
	}
	
	//Aquí se ven las páginas de inicio para administradores y usuarios
	//Primero, administradores
	
	
	
}
