package registraduria.Seguridad.seguridad.Repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import registraduria.Seguridad.seguridad.Modelos.PermisoRol;

public interface RepositorioPermisosRoles extends MongoRepository<PermisoRol,String> {
    @Query("{'rol.$id': ObjectId(?0),'permiso.$id': ObjectId(?1)}")
    PermisoRol getPermisoRol(String id_rol, String id_permiso);
}