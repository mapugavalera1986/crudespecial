package pe.pugavalera.cursos.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

	private Integer idCurso;
	private String nombre;
	private Integer horas;
	private Integer dias;
}