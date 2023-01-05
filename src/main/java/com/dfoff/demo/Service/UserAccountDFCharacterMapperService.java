package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.DFCharacter;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Domain.UserAccountDFCharacterMapper;
import com.dfoff.demo.Repository.Character.UserAccountDFCharacterMapperRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class UserAccountDFCharacterMapperService {
    private final UserAccountDFCharacterMapperRepository userAccountDFCharacterMapperRepository;

    public void mapUserAccountAndDFCharacter(UserAccount.UserAccountDTO userAccountDTO, DFCharacter.DFCharacterDTO dfCharacterDTO) {
        userAccountDFCharacterMapperRepository.save(UserAccountDFCharacterMapper.builder()
                .userAccount(UserAccount.UserAccountDTO.toEntity(userAccountDTO))
                .dfCharacter(DFCharacter.DFCharacterDTO.toEntity(dfCharacterDTO))
                .build());
    }
    public Set<DFCharacter.DFCharacterDTO> getDFCharactersByUserAccount(UserAccount.UserAccountDTO userAccountDTO) {
        return DFCharacter.DFCharacterDTO
                .fromMapper(UserAccountDFCharacterMapper.UserAccountDFCharacterMapperDTO
                        .from(userAccountDFCharacterMapperRepository
                                .findAllByUserAccount(UserAccount.UserAccountDTO
                                        .toEntity(userAccountDTO))));
    }

    public void deleteMapping(UserAccount.UserAccountDTO userAccountDTO, DFCharacter.DFCharacterDTO dfCharacterDTO) {
        if(userAccountDFCharacterMapperRepository
                .existsByUserAccountAndDfCharacter(UserAccount.UserAccountDTO.toEntity(userAccountDTO), DFCharacter.DFCharacterDTO.toEntity(dfCharacterDTO))) {
            userAccountDFCharacterMapperRepository.delete(UserAccountDFCharacterMapper.builder()
                    .userAccount(UserAccount.UserAccountDTO.toEntity(userAccountDTO))
                    .dfCharacter(DFCharacter.DFCharacterDTO.toEntity(dfCharacterDTO))
                    .build());
        }
    }

}
