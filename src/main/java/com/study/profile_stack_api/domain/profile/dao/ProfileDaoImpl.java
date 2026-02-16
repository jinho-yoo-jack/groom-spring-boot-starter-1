package com.study.profile_stack_api.domain.profile.dao;

import com.study.profile_stack_api.domain.profile.entity.Position;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileDaoImpl implements ProfileDao{

    private final ProfileRepository profileRepository;

    @Override
    public Profile saveProduct(Profile profile) {
        Profile saveProfile = profileRepository.save(profile);
        return saveProfile;
    }

    @Override
    public Profile findById(Long id) {
        Profile profile = profileRepository.findById(id).orElse(null);
        return profile;
    }

    @Override
    public List<Profile> findByPosition(String position) {
        List<Profile> profile = profileRepository.findByPosition(position);
        return profile;
    }

    @Override
    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}
