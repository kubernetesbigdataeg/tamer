package org.kubernetesbigdataeg.tamer.entities.userRole;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class UserRoleRepository implements PanacheRepository<UserRole> {
    @PersistenceContext
    EntityManager em;

    public List<UserRole> listAll() {
        PanacheQuery<UserRole> userRole;
        userRole = findAll();
        return userRole.list();
    }

    public UserRole get(Long id){
        return findById(id);
    }
}
