package pe.pugavalera.cursos.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profesor {
	private Integer idProfesor;
	private String nombres;
	private String apellidos;
	private String dni;
	private String celular;
	private String direccion;
	private String carrera;
	private String universidad;
}