package ai.budding.dto;

import java.util.UUID;

import fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GradeDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("descritpion")
    private String descritpion;
}
