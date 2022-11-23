package org.kubernetesbigdataeg.tamer.entities.userRole;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user_roles")
@Schema(description = "Represents a user role")
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC,
        getterVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC,
        setterVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC)
public class UserRole extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "roleSequence", sequenceName = "role_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "roleSequence")
    @Schema(readOnly = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(required = true)
    @NotBlank(message = "environment name may not be blank")
    private String name;

    @Column(nullable = false)
    @Schema(required = true)
    @NotBlank(message = "environment type (onprem, aks, gks, eks)")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc(){
        return this.description;
    }

    public void setDesc(String description){
        this.description = description;
    }
}