package ai.budding.models.jpa;

// import ai.budding.models.jpa.quiz.ClassQuiz;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@JsonPropertyOrder({
        "id",
        "title",
        "description"
})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "class_plan_items")
public class ClassPlanItem {

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
    @Column(name = "duration_in_minutes")
    @JsonProperty("duration_in_minutes")
    private Short durationInMinutes;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "play_list_id", referencedColumnName = "id")
    private PlayList playList;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    private ClassPlan classPlan;

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    private Long createdOn;

    @Column(name = "modified_on")
    @LastModifiedDate
    private Long modifiedOn;

    @Transient
    public void update(ClassPlanItem other) {
        title = other.getTitle();
        description = other.getDescription();
        durationInMinutes = other.getDurationInMinutes();
    }
}
