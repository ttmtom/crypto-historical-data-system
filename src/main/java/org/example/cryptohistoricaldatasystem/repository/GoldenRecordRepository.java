package org.example.cryptohistoricaldatasystem.repository;

import org.example.cryptohistoricaldatasystem.entity.GoldenRecord;
import org.example.cryptohistoricaldatasystem.entity.GoldenRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoldenRecordRepository extends JpaRepository<GoldenRecord, GoldenRecordId> {
}
