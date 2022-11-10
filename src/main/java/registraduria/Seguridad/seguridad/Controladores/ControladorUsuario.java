package registraduria.Seguridad.seguridad.Controladores;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import registraduria.Seguridad.seguridad.Modelos.Rol;
import registraduria.Seguridad.seguridad.Modelos.Usuario;
import registraduria.Seguridad.seguridad.Repositorios.RepositorioRol;
import registraduria.Seguridad.seguridad.Repositorios.RepositorioUsuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/usuarios")

public class ControladorUsuario {
    @Autowired
    private RepositorioUsuario miRepositorioUsuario;

    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("")
    public List<Usuario> index() {
        return this.miRepositorioUsuario.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario create(@RequestBody Usuario infoUsuario) {
        infoUsuario.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
        return this.miRepositorioUsuario.save(infoUsuario);
    }

    @GetMapping("{id}")
    public Usuario show(@PathVariable String id) {
        return miRepositorioUsuario
                .findById(id)
                .orElse(new Usuario("", "", ""));
    }

    @PutMapping("{id}")
    public Usuario update(@PathVariable String id, @RequestBody Usuario infoUsuario) {
        Usuario usuarioActual = this.miRepositorioUsuario
                .findById(id)
                .orElse(null);

        if (usuarioActual != null) {
            usuarioActual.setSeudonimo(infoUsuario.getSeudonimo());
            usuarioActual.setCorreo(infoUsuario.getCorreo());
            usuarioActual.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
            return this.miRepositorioUsuario.save(usuarioActual);
        } 
        
        else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Usuario usuarioActual = this.miRepositorioUsuario
                .findById(id)
                .orElse(null);

        if (usuarioActual != null) {
            this.miRepositorioUsuario.delete(usuarioActual);
        }
    }

    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }     
        return sb.toString();
    }

    @PostMapping("validarUsuario")
    public Usuario validarUsuario(@RequestBody Usuario infoUsuario) {
        log.info("Validando el usuario, request body: {}", infoUsuario);

        Usuario usuarioActual = miRepositorioUsuario.findByEmail(infoUsuario.getCorreo());
        String contrasenaUsuario = convertirSHA256(infoUsuario.getContrasena());
        String contrasenaBaseDatos = infoUsuario.getContrasena();

        if (contrasenaUsuario.equals(contrasenaBaseDatos)) {
            return usuarioActual;
        } 
        
        else {
            return null;
        }
    }

    /**
     * Relación (1 a n) entre rol y usuario
     * @param id
     * @param id_rol
     * @return
     */
    @PutMapping("{id}/rol/{id_rol}")
    public Usuario asignarRolAUsuario(@PathVariable String id,@PathVariable String id_rol){
        Usuario usuarioActual=this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
        Rol rolActual=this.miRepositorioRol
                .findById(id_rol)
                .orElse(null);
        if (usuarioActual!=null && rolActual!=null){
            usuarioActual.setRol(rolActual);
            return this.miRepositorioUsuario.save(usuarioActual);
        }else{
            return null;
        }

    }
}