package com.study.profile_stack_api.domain.profile.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.study.profile_stack_api.support.ControllerIntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileControllerIntegrationTest extends ControllerIntegrationTestSupport {

    @Test
    @DisplayName("프로필 생성, 조회, 수정, 검색, 삭제가 동작한다")
    void profileCrudFlow() throws Exception {
        String accessToken = login("javakim", "password123").path("accessToken").asText();
        String email = "profile-" + uniqueSuffix + "@example.com";

        MvcResult createResult = mockMvc.perform(post("/api/v1/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(toJson(Map.of(
                                "name", "API Tester",
                                "email", email,
                                "bio", "profile integration test",
                                "position", "BACKEND",
                                "careerYears", 2,
                                "githubUrl", "https://github.com/" + uniqueSuffix,
                                "blogUrl", "https://blog.example.com/" + uniqueSuffix
                        ))))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode createData = readBody(createResult).path("data");
        long profileId = createData.path("id").asLong();

        assertThat(createData.path("email").asText()).isEqualTo(email);

        MvcResult getResult = mockMvc.perform(get("/api/v1/profiles/{id}", profileId))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(readBody(getResult).path("data").path("id").asLong()).isEqualTo(profileId);

        MvcResult searchResult = mockMvc.perform(get("/api/v1/profiles")
                        .param("name", "API")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(readBody(searchResult).path("data").path("content")).isNotEmpty();

        MvcResult updateResult = mockMvc.perform(put("/api/v1/profiles/{id}", profileId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(toJson(Map.of(
                                "bio", "updated profile integration test",
                                "careerYears", 4
                        ))))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode updateData = readBody(updateResult).path("data");
        assertThat(updateData.path("careerYears").asInt()).isEqualTo(4);

        MvcResult deleteResult = mockMvc.perform(delete("/api/v1/profiles/{id}", profileId)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(readBody(deleteResult).path("data").path("deletedId").asLong()).isEqualTo(profileId);
    }

    @Test
    @DisplayName("로그인하지 않고 프로필을 생성하면 401을 반환한다")
    void createProfileFailsWithoutAuthentication() throws Exception {
        mockMvc.perform(post("/api/v1/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(Map.of(
                                "name", "Unauthorized",
                                "email", "unauthorized-" + uniqueSuffix + "@example.com",
                                "bio", "no auth",
                                "position", "BACKEND",
                                "careerYears", 1,
                                "githubUrl", "https://github.com/no-auth",
                                "blogUrl", "https://no-auth.dev"
                        ))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("다른 사용자의 프로필은 수정할 수 없다")
    void updateProfileFailsForAnotherUser() throws Exception {
        String ownerToken = login("javakim", "password123").path("accessToken").asText();
        String otherUsername = "otheruser" + uniqueSuffix;
        signup(otherUsername, "password123");
        String otherToken = login(otherUsername, "password123").path("accessToken").asText();

        MvcResult createResult = mockMvc.perform(post("/api/v1/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + ownerToken)
                        .content(toJson(Map.of(
                                "name", "Owner Profile",
                                "email", "owner-" + uniqueSuffix + "@example.com",
                                "bio", "owner profile",
                                "position", "BACKEND",
                                "careerYears", 2,
                                "githubUrl", "https://github.com/owner-" + uniqueSuffix,
                                "blogUrl", "https://owner.example.com/" + uniqueSuffix
                        ))))
                .andExpect(status().isCreated())
                .andReturn();

        long profileId = readBody(createResult).path("data").path("id").asLong();

        MvcResult updateResult = mockMvc.perform(put("/api/v1/profiles/{id}", profileId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + otherToken)
                        .content(toJson(Map.of("bio", "hacked"))))
                .andExpect(status().isUnauthorized())
                .andReturn();

        JsonNode error = readBody(updateResult).path("error");
        assertThat(error.path("code").asText()).isEqualTo("AUTH_ERROR");
    }
}
