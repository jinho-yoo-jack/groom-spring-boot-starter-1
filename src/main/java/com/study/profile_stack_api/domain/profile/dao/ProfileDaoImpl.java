package com.study.profile_stack_api.domain.profile.dao;

import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
    public Page<Profile> findAllWithPaging(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Profile> profilePage = profileRepository.findAll(pageable);

        return profilePage;
    }

    @Override
    public Optional<Profile> findById(Long id) {
        return profileRepository.findById(id);
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

    @Override
    public boolean existsByEmail(String email) {
        return profileRepository.existsByEmail(email);
    }

    @Override
    public boolean existsById(Long id) {
        return profileRepository.existsById(id);
    }
}
