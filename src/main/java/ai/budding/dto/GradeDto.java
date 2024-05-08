package ai.budding.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GradeDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("descritpion")
    private String descritpion;

    @JsonProperty("institution_id")
    private UUID institutionId;

    @JsonProperty("virtualId")
    private UUID virtualId;
}
