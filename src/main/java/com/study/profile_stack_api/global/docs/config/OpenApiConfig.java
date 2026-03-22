package com.study.profile_stack_api.global.docs.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI 문서 설정
 *
 * 핵심 아이디어: API 문서의 메타데이터(제목, 버전, 설명)과
 * 보안 스키마(JWT)를 중앙에서 관리
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        // Step 1: API 기본 정보 설정
        Info info = new Info()
                .title("Profile TechStack API")
                .version("1.0.0")
                .description("")
                .contact(new Contact()
                        .name("개발팀")
                        .email("dev@profiletechstack.com"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

        // Step 2: 서버 URL 설정
        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("로컬 개발 서버");

        // Step 3: JWT 보안 스키마 정의
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("JWT Authentication")
                .description("JWT 토큰을 입력하세요. 'Bearer' 접두사는 자동으로 추가됩니다.");

        // Step 4: 보안 요구사항 설정
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");

        // Step 5: OpenAPI 객체 조립
        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}
