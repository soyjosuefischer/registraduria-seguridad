package registraduria.Seguridad.seguridad.Repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import registraduria.Seguridad.seguridad.Modelos.PermisoRol;

public interface RepositorioPermisoRol  extends MongoRepository<PermisoRol, String> {}