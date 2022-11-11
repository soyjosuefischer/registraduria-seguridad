package registraduria.Seguridad.seguridad.Repositorios;

import registraduria.Seguridad.seguridad.Modelos.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioRol extends MongoRepository<Rol,String> {}