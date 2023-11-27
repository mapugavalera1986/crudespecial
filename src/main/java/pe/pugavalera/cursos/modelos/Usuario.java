package pe.pugavalera.cursos.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	private Integer id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String country;
	private String role;
}
