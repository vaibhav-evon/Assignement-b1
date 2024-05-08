package ai.budding.models.jpa;

// import ai.budding.utils.C;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title",
        "teacher_id",
        "start_time",
        "end_time",
        "subject",
        "description",
        "duration_seconds",
        "repeats",
        "created_on",
        "modified_on"
})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "virtual_classes")
public class VirtualClass {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @JsonProperty("title")
    @Column(name = "title")
    private String title;
    @JsonProperty("teacher_id")
    @Column(name = "teacher_id")
    private UUID teacherId;
    @JsonProperty("start_time")
    @Column(name = "start_time")
    private Long startTime;
    @JsonProperty("end_time")
    @Column(name = "end_time")
    private Long endTime = 0L;
    @JsonProperty("duration_seconds")
    @Column(name = "duration_seconds")
    private Integer durationSeconds;
    @JsonProperty("repeats")
    @Column(name = "repeats")
    private String repeats = null;
    @JsonProperty("subject")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;
    @JsonProperty("classPlan")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    private ClassPlan classPlan;
    @Transient
    private URI meetingStartUrl;
    @Transient
    private URI meetingJoinUrl;
    @JsonProperty("description")
    @Column(columnDefinition = "TEXT", name = "description")
    private String description;
    @JsonIgnore
    @Column(name = "attendee_password")
    private String attendeePassword;
    @JsonIgnore
    @Column(name = "moderator_password")
    private String moderatorPassword;
    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
    @JoinTable(name = "class_members", joinColumns = @JoinColumn(name = "class_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<Student> students = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "virtualClass", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<StudentClassQuestion> classQuestions;

    @JsonIgnore
    @OneToMany(mappedBy = "virtualClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Grade> grades;

    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    private Long createdOn;

    @Column(name = "modified_on")
    @LastModifiedDate
    private Long modifiedOn;

    @JsonProperty("start_url")
    public URI getMeetingStartUrl() {
        return meetingStartUrl;
    }

    @JsonProperty("join_url")
    public URI getMeetingJoinUrl() {
        return meetingJoinUrl;
    }

    @JsonProperty("next_time")
    @Transient
    public Long getNextTime() {
        final Instant start = Instant.ofEpochSecond(startTime);
        @SuppressWarnings("unused")
        final Instant end = Instant.ofEpochSecond(endTime).plusSeconds(durationSeconds);
        final Instant now = Instant.now();
        Instant next;
        if (now.isAfter(start)) {
            final long days = Duration.between(start, now).toDays();
            if (repeats.equals("daily")) {
                next = start.plusSeconds((days + 1) * (60 * 60 * 24));
                return next.getEpochSecond();
            } else if (repeats.equals("weekly")) {
                long weeksSinceStart = days / 7;
                next = start.plusSeconds((weeksSinceStart + 1) * (60 * 60 * 24 * 7));
                return next.getEpochSecond();
            }
        } else {
            return start.getEpochSecond();
        }
        return 0L;
    }

    @JsonProperty("student_count")
    public int getStudentCount() {
        return students.size();
    }

    @JsonIgnore
    public void merge(VirtualClass that) {
        this.title = that.title;
        this.startTime = that.startTime;
        this.durationSeconds = that.getDurationSeconds();
        this.subject = that.subject;
        this.repeats = that.getRepeats();
        this.description = that.description;
    }

    @PrePersist
    protected void setMeetingPasswords() {
        // this.attendeePassword = C.getRandomString(12);
        // this.moderatorPassword = C.getRandomString(12);
        if (repeats.equals("once")) {
            endTime = startTime + durationSeconds;
        }
    }
}
