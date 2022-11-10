package registraduria.Seguridad.seguridad.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import registraduria.Seguridad.seguridad.Modelos.Permiso;
import registraduria.Seguridad.seguridad.Modelos.PermisoRol;
import registraduria.Seguridad.seguridad.Modelos.Rol;
import registraduria.Seguridad.seguridad.Repositorios.RepositorioPermiso;
import registraduria.Seguridad.seguridad.Repositorios.RepositorioPermisoRol;
import registraduria.Seguridad.seguridad.Repositorios.RepositorioRol;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/permiso-rol")
public class ControladorPermisoRol {
    @Autowired
    private RepositorioPermisoRol miRepositorioPermisoRol;
    @Autowired
    private RepositorioRol miRepositorioRol;
    @Autowired
    private RepositorioPermiso miRepositorioPermiso;

    @PostMapping("/rol/{idRol}/permiso/{idPermiso}")
    public PermisoRol crearPermisosRol(@PathVariable String idRol, @PathVariable String idPermiso) {
        Rol rol = miRepositorioRol
                .findById(idRol)
                .orElseThrow(RuntimeException::new);

        Permiso permiso = miRepositorioPermiso
                .findById(idPermiso)
                .orElseThrow(RuntimeException::new);

        PermisoRol permisoRol = new PermisoRol(rol, permiso);
        return miRepositorioPermisoRol.save(permisoRol);
    }

    @GetMapping
    public List<PermisoRol> buscarTodosPermisosRol() {
        return miRepositorioPermisoRol.findAll();
    }

//    @GetMapping("validar-permiso/rol/{idRol}")
//    public PermisoRol validarPermisosDelRol(@PathVariable String idRol, @RequestBody Permiso infoPermiso, HttpServletResponse response) throws IOException {
//
//        //Buscar en base de datos el rol y permiso
//        Rol rolActual = miRepositorioRol.findById(idRol).orElse(null);
//        Permiso permisoActual = miRepositorioPermiso.findByUrlAndMethod(infoPermiso.getUrl(), infoPermiso.getMetodo());
//
//        //Validar si existe el rol y el permiso en base de datos
//        if (rolActual != null && permisoActual != null) {
//
//            String idRolActual = rolActual.get_id();
//            String idPermisoActual = permisoActual.get_id();
//            log.info("idRolActual: {}, idPermisoActual: {}", idRolActual, idPermisoActual);
//
//            //Buscar en la tabla PermisosRol si el rol tiene asociado el permiso.
//            PermisoRol permisosRolActual = miRepositorioPermisoRol.findByRolAndPermissions(idRolActual, idPermisoActual);
//            log.info("El permisosRol que encontró en BD fue: {}", permisosRolActual);
//
//            if (permisosRolActual != null) {
//                return permisosRolActual;
//            } else {
//                log.error("NO se encuentra el PermisosRol en base de datos");
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//                return null;
//            }
//        } else {
//            log.error("NO se encuentra el rol o el permiso en base de datos");
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//            return null;
//        }

    @GetMapping("{id}")
    public PermisoRol show(@PathVariable String id) {
        return miRepositorioPermisoRol
                .findById(id)
                .orElse(null);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        miRepositorioPermisoRol.deleteById(id);
    }

    /**
     * Modificación Rol y Permiso
     * @param id
     * @param id_rol
     * @param id_permiso
     * @return
     */
    @PutMapping("{id}/rol/{id_rol}/permiso/{id_permiso}")
    public PermisoRol update(@PathVariable String id,@PathVariable String id_rol,@PathVariable String id_permiso){
        PermisoRol permisosRolesActual=this.miRepositorioPermisoRol
                .findById(id)
                .orElse(null);
        Rol elRol=this.miRepositorioRol.findById(id_rol).get();
        Permiso elPermiso=this.miRepositorioPermiso.findById(id_permiso).get();
        if(permisosRolesActual!=null && elPermiso!=null && elRol!=null){
            permisosRolesActual.setPermiso(elPermiso);
            permisosRolesActual.setRol(elRol);
            return this.miRepositorioPermisoRol.save(permisosRolesActual);
        }else{
            return null;
        }
    }
}