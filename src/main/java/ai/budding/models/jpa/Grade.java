package ai.budding.models.jpa;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "title",
        "description",
        "created_on",
        "modified_on"
})
@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("descritpion")
    private String descritpion;

    @Column(name = "created_on", nullable = false)
    @CreatedDate
    private Date createdOn;

    @Column(name = "modified_on")
    @LastModifiedDate
    private Date modifiedOn;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "virtual_id", nullable = false)
    private VirtualClass virtualClass;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;
}
