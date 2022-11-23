package org.kubernetesbigdataeg.tamer.entities.user;

import java.util.List;
import java.util.Optional;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.eclipse.microprofile.config.ConfigProvider;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class UsersRepository implements PanacheRepository<User> {
    @PersistenceContext
    EntityManager em;

    public List<User> list(int pageIndex, int pageSize, List<String> sortQuery){
        Page page = Page.of(pageIndex, pageSize);
        PanacheQuery<User> users;
        if (sortQuery.size() > 0){
            Sort sort = Sort.by(String.join(",", sortQuery));
            users = findAll(sort);
        }else{
            users = findAll();
        }
        return users.page(page).list();
    }

    public User get(Long id){
        return findById(id);
    }

    public Optional<User> getByUsername(String username){
        TypedQuery<User> query = em.createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst();
    }

    public Optional<User> getByUsernameAndPassword(String username, String password){
        byte[] salt = ConfigProvider.getConfig().getValue("tamer.pass-salt", String.class).getBytes();
        String password_hashed = BcryptUtil.bcryptHash(password, 10, salt);
        TypedQuery<User> query = em.createNamedQuery("User.findByUsernameAndPassword", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password_hashed);
        Optional<User> u = query.getResultStream().findFirst();
        return u;
    }

    public Long add(User userToSave) throws Exception{
        persistAndFlush(userToSave);
        return userToSave.getId();
    }

    public Boolean update(Long id, String name, String password, String roles, Boolean enabled){
        User user = findById(id);
        if(user == null) {
            return false;
        } else if (user.getUsername().equals(ConfigProvider.getConfig().getValue("kloudhealth.adminuser.username", String.class))) {
            return false;
        } else if (name == null && password == null && roles == null && enabled == null) {
            return false;
        } else {
            if(password != null){
                user.setPassword(password);
            }
            if(roles != null){
                user.clearRoles();
                String[] roles_list = roles.split(",");
                for (String role : roles_list) {
                    user.addRole(role);
                }
            }
            persistAndFlush(user);
            return true;
        }
    }

    public Boolean delete(Long id){
        User user_to_delete = findById(id);
        if (user_to_delete == null) {
            return false;
        } else if (user_to_delete.getUsername().equals(ConfigProvider.getConfig().getValue("kloudhealth.adminuser.username", String.class))) {
            return false;
        } else {
            return deleteById(id);
        }
    }

    public Boolean updatePassword(Long id, String newpassword){
        User user = findById(id);
        if(user == null) {
            return false;
        } else if (newpassword == null || newpassword == "") {
            return false;
        } else {
            user.setPassword(newpassword);
            persistAndFlush(user);
            return true;
        }
    }
}
