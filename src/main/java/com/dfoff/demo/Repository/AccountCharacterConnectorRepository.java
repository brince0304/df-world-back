package com.dfoff.demo.Repository;

public interface AccountCharacterConnectorRepository extends org.springframework.data.jpa.repository.JpaRepository<com.dfoff.demo.Domain.AccountCharacterConnector, java.lang.Long> {
    java.util.List<com.dfoff.demo.Domain.AccountCharacterConnector> findByUserAccount(com.dfoff.demo.Domain.UserAccount userAccount);
}

