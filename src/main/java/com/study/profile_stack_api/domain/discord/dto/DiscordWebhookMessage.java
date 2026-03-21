package com.study.profile_stack_api.domain.discord.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscordWebhookMessage {
    private String username;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private String content;

    private List<Embed> embeds;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Embed {
        private String title;
        private String description;
        private Integer color;  // 16진수 색상 (0x00FF00 = 녹색)
        private String timestamp;
        private Footer footer;
        private List<Field> fields;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Field {
        private String name;
        private String value;
        private boolean inline;  // true: 같은 줄, false: 새 줄
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Footer {
        private String text;
        @JsonProperty("icon_url")
        private String iconUrl;
    }
}
