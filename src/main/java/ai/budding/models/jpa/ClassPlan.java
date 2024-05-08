package ai.budding.models.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "class_plans")
public class ClassPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @JsonProperty("teacher_id")
    @Column(name = "teacher_id")
    private UUID teacherId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;

    @JsonProperty("class_plan_items")
    @OneToMany(mappedBy = "classPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<ClassPlanItem> classPlanItems;
    @JsonIgnore
    @OneToMany(mappedBy = "classPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<VirtualClass> virtualClasses;

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    private Long createdOn;

    @Column(name = "modified_on")
    @LastModifiedDate
    private Long modifiedOn;
}