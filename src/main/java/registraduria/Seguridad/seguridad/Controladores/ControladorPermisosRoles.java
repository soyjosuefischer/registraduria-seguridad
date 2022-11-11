package registraduria.Seguridad.seguridad.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import registraduria.Seguridad.seguridad.Modelos.Permiso;
import registraduria.Seguridad.seguridad.Modelos.PermisoRol;
import registraduria.Seguridad.seguridad.Modelos.Rol;
import registraduria.Seguridad.seguridad.Repositorios.RepositorioPermiso;
import registraduria.Seguridad.seguridad.Repositorios.RepositorioPermisosRoles;
import registraduria.Seguridad.seguridad.Repositorios.RepositorioRol;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/permiso-rol")
public class ControladorPermisosRoles {
    @Autowired
    private RepositorioPermisosRoles miRepositorioPermisoRoles;

    @Autowired
    private RepositorioPermiso miRepositorioPermiso;

    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("")
    public List<PermisoRol> index() {
        return this.miRepositorioPermisoRoles.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("rol/{id_rol}/permiso/{id_permiso}")
    public PermisoRol create(@PathVariable String id_rol, @PathVariable String id_permiso) {
        PermisoRol nuevo = new PermisoRol();
        Rol elRol = this.miRepositorioRol.findById(id_rol).get();
        Permiso elPermiso = this.miRepositorioPermiso.findById(id_permiso).get();

        if (elRol != null && elPermiso != null) {
            nuevo.setPermiso(elPermiso);
            nuevo.setRol(elRol);
            return this.miRepositorioPermisoRoles.save(nuevo);
        }
        
        else{
            return null;
        }
    }

    @GetMapping("{id}")
    public PermisoRol show(@PathVariable String id) {
        PermisoRol permisoRolActual =this.miRepositorioPermisoRoles.findById(id).orElse(null);
        return permisoRolActual;
    }

    @PutMapping("{id}/rol/{id_rol}/permiso/{id_permiso}")
    public PermisoRol update(@PathVariable String id, @PathVariable String id_rol, @PathVariable String id_permiso){
        PermisoRol permisoRolActual = this.miRepositorioPermisoRoles.findById(id).orElse(null);
        Rol elRol = this.miRepositorioRol.findById(id_rol).get();
        Permiso elPermiso = this.miRepositorioPermiso.findById(id_permiso).get();

        if (permisoRolActual != null && elPermiso != null && elRol != null) {
            permisoRolActual.setPermiso(elPermiso);
            permisoRolActual.setRol(elRol);
            return this.miRepositorioPermisoRoles.save(permisoRolActual);
        }
        
        else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        PermisoRol permisoRolActual =this.miRepositorioPermisoRoles.findById(id).orElse(null);

        if (permisoRolActual != null) {
            this.miRepositorioPermisoRoles.delete(permisoRolActual);
        }
    }

    @GetMapping("validar-permiso/rol/{id_rol}")
    public PermisoRol getPermiso(@PathVariable String id_rol, @RequestBody Permiso infoPermiso) {
        Permiso elPermiso = this.miRepositorioPermiso.getPermiso(infoPermiso.getUrl(),infoPermiso.getMetodo());
        Rol elRol = this.miRepositorioRol.findById(id_rol).get();

        if (elPermiso != null && elRol != null) {
            return this.miRepositorioPermisoRoles.getPermisoRol(elRol.get_id(),elPermiso.get_id());
        }
        
        else {
            return null;
        }
    }
}