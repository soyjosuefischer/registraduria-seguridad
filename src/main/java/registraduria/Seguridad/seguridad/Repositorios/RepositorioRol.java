package registraduria.Seguridad.seguridad.Repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import registraduria.Seguridad.seguridad.Modelos.PermisoRol;
import registraduria.Seguridad.seguridad.Modelos.Rol;

public interface RepositorioRol extends MongoRepository<Rol,String> {
    @Query("{'rol.$_id':ObjectId(?0),'permiso.$_id':ObjectId(?1)}")
    public PermisoRol findRolandPermission(String idRol, String idPermiso);
}