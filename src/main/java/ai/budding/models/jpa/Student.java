package ai.budding.models.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "students")
public class Student {

    @Id
    private UUID id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("mobile_number")
    private String mobileNumber;
    @JsonProperty("verified")
    private boolean verified;
    @JsonProperty("photo")
    private String photo;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("dob")
    private Long dob;
    @JsonIgnore
    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    Set<VirtualClass> classes;
    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<StudentClassQuestion> classQuestions;

    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Section> sections;

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    private Long createdOn;

    @Column(name = "modified_on")
    @LastModifiedDate
    private Long modifiedOn;
}