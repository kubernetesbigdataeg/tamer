package org.kubernetesbigdataeg.tamer.entities.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Schema(description = "Represents a user")
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC,
        getterVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC,
        setterVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC)
@NamedQuery(
        name = "User.findByUsernameAndPassword",
        query = "SELECT usr FROM User usr WHERE usr.username = :username AND usr.password = :password")
@NamedQuery(
        name = "User.findByUsername",
        query = "SELECT usr FROM User usr WHERE usr.username = :username")
@UserDefinition
public class User extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "userSequence", sequenceName = "user_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "userSequence")
    @Schema(readOnly = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(required = true)
    @NotBlank(message = "username/email may not be blank")
    //@Size(min = 5, max = 45, message = "username/email should have size [{min},{max}]")
    @Username
    @Email
    private String username;

    @Column(nullable = false)
    @Schema(required = true)
    @NotBlank(message = "password may not be blank")
    //@Size(min = 4, max = 72, message = "password should have size [{min},{max}]")
    @Password
    private String password;

    @Column
    @Schema
    @Roles
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        // TODO: salt auto-generated and database stored instead of stored in properties file.
        byte[] salt = ConfigProvider.getConfig().getValue("tamer.pass-salt", String.class).getBytes();
        this.password = BcryptUtil.bcryptHash(password, 10, salt);
    }

    public String getRoles() {
        return this.role;
    }

    public void addRole(String role) {
        this.role = role;
    }

    public void delRole(String role) {
        /*
        if (this.role != null) {
            ArrayList<String> roles_list = new ArrayList<String>(Arrays.asList(this.roles.split(",")));
            roles_list.remove(role);
            this.roles = String.join(",", roles_list);
        }
         */
    }

    public void clearRoles() {
        this.role = "";
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", role=" + role + "]";
    }

    public static List<String> getAttributes(){
        Object user = new User();
        Field[] fields = user.getClass().getDeclaredFields();
        List<String> fields_list = new ArrayList<>();
        for (Field field : fields) {
            String s = field.getName();
            // Discard hibernate objects
            if (!s.startsWith("$$")){
                fields_list.add(s);
            }
        }
        return fields_list;
    }
}
