package org.kubernetesbigdataeg.tamer.entities.catalog;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class CatalogRepository implements PanacheRepository<Catalog> {
    @PersistenceContext
    EntityManager em;

    public List<Catalog> listAll() {
        PanacheQuery<Catalog> service;
        service = findAll();
        return service.list();
    }

    public Catalog get(Long id){
        return findById(id);
    }
}
