package ai.budding.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "first_name",
        "last_name",
        "email_id",
        "mobile_number",
        "photo",
        "zip",
        "city",
        "state",
        "country",
        "user_type"
})
public class User {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("mobile_number")
    private String mobileNumber;
    @JsonProperty("password")
    private String password;
    @JsonProperty("dob")
    private Long dob;
    @JsonProperty("photo")
    private String photo;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("zip")
    private String zip;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("country")
    private String country;

    @JsonProperty("user_type")
    private String userType;

    @JsonIgnore
    private boolean verified;
}
