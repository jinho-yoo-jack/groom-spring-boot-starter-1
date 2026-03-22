package com.study.profile_stack_api.domain.techstack.controller;

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

class TechStackControllerIntegrationTest extends ControllerIntegrationTestSupport {

    @Test
    @DisplayName("기술 스택 생성, 조회, 수정, 검색, 삭제가 동작한다")
    void techStackCrudFlow() throws Exception {
        String accessToken = login("javakim", "password123").path("accessToken").asText();
        String email = "techstack-" + uniqueSuffix + "@example.com";

        MvcResult profileCreateResult = mockMvc.perform(post("/api/v1/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(toJson(Map.of(
                                "name", "Tech Stack Owner",
                                "email", email,
                                "bio", "tech stack integration test",
                                "position", "BACKEND",
                                "careerYears", 3,
                                "githubUrl", "https://github.com/tech-" + uniqueSuffix,
                                "blogUrl", "https://tech.example.com/" + uniqueSuffix
                        ))))
                .andExpect(status().isCreated())
                .andReturn();

        long profileId = readBody(profileCreateResult).path("data").path("id").asLong();

        MvcResult createResult = mockMvc.perform(post("/api/v1/profiles/{profileId}/tech-stacks", profileId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(toJson(Map.of(
                                "name", "Java",
                                "category", "LANGUAGE",
                                "proficiency", "ADVANCED",
                                "yearsOfExp", 3
                        ))))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode createData = readBody(createResult).path("data");
        long techStackId = createData.path("id").asLong();
        assertThat(createData.path("name").asText()).isEqualTo("Java");

        MvcResult listResult = mockMvc.perform(get("/api/v1/profiles/{profileId}/tech-stacks", profileId))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(readBody(listResult).path("data")).isNotEmpty();

        MvcResult getResult = mockMvc.perform(get("/api/v1/profiles/{profileId}/tech-stacks/{id}", profileId, techStackId))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(readBody(getResult).path("data").path("id").asLong()).isEqualTo(techStackId);

        MvcResult searchResult = mockMvc.perform(get("/api/v1/profiles/{profileId}/tech-stacks", profileId)
                        .param("category", "LANGUAGE")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(readBody(searchResult).path("data").path("content")).isNotEmpty();

        MvcResult updateResult = mockMvc.perform(put("/api/v1/profiles/{profileId}/tech-stacks/{id}", profileId, techStackId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(toJson(Map.of(
                                "proficiency", "INTERMEDIATE",
                                "yearsOfExp", 5
                        ))))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(readBody(updateResult).path("data").path("proficiency").asText()).isEqualTo("INTERMEDIATE");

        MvcResult deleteResult = mockMvc.perform(delete("/api/v1/profiles/{profileId}/tech-stacks/{id}", profileId, techStackId)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(readBody(deleteResult).path("data").path("deletedId").asLong()).isEqualTo(techStackId);
    }

    @Test
    @DisplayName("존재하지 않는 기술 스택 조회는 404를 반환한다")
    void getTechStackFailsWhenNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/profiles/{profileId}/tech-stacks/{id}", 1L, 999999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("다른 사용자의 프로필에는 기술 스택을 생성할 수 없다")
    void createTechStackFailsForAnotherUsersProfile() throws Exception {
        String ownerToken = login("javakim", "password123").path("accessToken").asText();
        String otherUsername = "stackuser" + uniqueSuffix;
        signup(otherUsername, "password123");
        String otherToken = login(otherUsername, "password123").path("accessToken").asText();

        MvcResult profileCreateResult = mockMvc.perform(post("/api/v1/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + ownerToken)
                        .content(toJson(Map.of(
                                "name", "Protected Profile",
                                "email", "protected-" + uniqueSuffix + "@example.com",
                                "bio", "protected profile",
                                "position", "BACKEND",
                                "careerYears", 3,
                                "githubUrl", "https://github.com/protected-" + uniqueSuffix,
                                "blogUrl", "https://protected.example.com/" + uniqueSuffix
                        ))))
                .andExpect(status().isCreated())
                .andReturn();

        long profileId = readBody(profileCreateResult).path("data").path("id").asLong();

        MvcResult createResult = mockMvc.perform(post("/api/v1/profiles/{profileId}/tech-stacks", profileId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + otherToken)
                        .content(toJson(Map.of(
                                "name", "Java",
                                "category", "LANGUAGE",
                                "proficiency", "ADVANCED",
                                "yearsOfExp", 3
                        ))))
                .andExpect(status().isUnauthorized())
                .andReturn();

        JsonNode error = readBody(createResult).path("error");
        assertThat(error.path("code").asText()).isEqualTo("AUTH_ERROR");
    }
}
