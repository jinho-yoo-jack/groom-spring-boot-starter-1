package com.study.profile_stack_api.domain.profile.mapper;

import com.study.profile_stack_api.domain.profile.dto.request.ProfileCreateRequest;
import com.study.profile_stack_api.domain.profile.dto.request.ProfileUpdateRequest;
import com.study.profile_stack_api.domain.profile.dto.response.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-17T22:40:19+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.18 (Microsoft)"
)
@Component
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public ProfileResponse toResponse(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        ProfileResponse.ProfileResponseBuilder profileResponse = ProfileResponse.builder();

        profileResponse.id( profile.getId() );
        profileResponse.name( profile.getName() );
        profileResponse.email( profile.getEmail() );
        profileResponse.bio( profile.getBio() );
        if ( profile.getCareerYears() != null ) {
            profileResponse.careerYears( profile.getCareerYears() );
        }
        profileResponse.githubUrl( profile.getGithubUrl() );
        profileResponse.blogUrl( profile.getBlogUrl() );
        profileResponse.createdAt( profile.getCreatedAt() );
        profileResponse.updatedAt( profile.getUpdatedAt() );

        profileResponse.positionName( profile.getPosition().name() );
        profileResponse.positionIcon( profile.getPosition().getIcon() );

        return profileResponse.build();
    }

    @Override
    public List<ProfileResponse> toResponseList(List<Profile> profiles) {
        if ( profiles == null ) {
            return null;
        }

        List<ProfileResponse> list = new ArrayList<ProfileResponse>( profiles.size() );
        for ( Profile profile : profiles ) {
            list.add( toResponse( profile ) );
        }

        return list;
    }

    @Override
    public Profile toEntity(ProfileCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Profile.ProfileBuilder profile = Profile.builder();

        profile.name( request.getName() );
        profile.email( request.getEmail() );
        profile.bio( request.getBio() );
        profile.careerYears( request.getCareerYears() );
        profile.githubUrl( request.getGithubUrl() );
        profile.blogUrl( request.getBlogUrl() );

        profile.position( com.study.profile_stack_api.domain.profile.entity.Position.valueOf(request.getPosition().toUpperCase()) );

        return profile.build();
    }

    @Override
    public void updateEntityFromRequest(ProfileUpdateRequest request, Profile profile) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            profile.setName( request.getName() );
        }
        if ( request.getEmail() != null ) {
            profile.setEmail( request.getEmail() );
        }
        if ( request.getBio() != null ) {
            profile.setBio( request.getBio() );
        }
        if ( request.getCareerYears() != null ) {
            profile.setCareerYears( request.getCareerYears() );
        }
        if ( request.getGithubUrl() != null ) {
            profile.setGithubUrl( request.getGithubUrl() );
        }
        if ( request.getBlogUrl() != null ) {
            profile.setBlogUrl( request.getBlogUrl() );
        }

        profile.setPosition( request.getPosition() != null ? com.study.profile_stack_api.domain.profile.entity.Position.valueOf(request.getPosition()) : profile.getPosition() );
    }
}
