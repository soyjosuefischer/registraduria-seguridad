package registraduria.Seguridad.seguridad.Repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import registraduria.Seguridad.seguridad.Modelos.Usuario;

public interface RepositorioUsuario extends MongoRepository<Usuario,String> {
    @Query("{'correo':?0}")
    public Usuario findByEmail(String correo);
}