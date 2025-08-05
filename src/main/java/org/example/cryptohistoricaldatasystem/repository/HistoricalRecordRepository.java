package org.example.cryptohistoricaldatasystem.repository;

import org.example.cryptohistoricaldatasystem.entity.HistoricalRecord;
import org.example.cryptohistoricaldatasystem.entity.HistoricalRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricalRecordRepository extends JpaRepository<HistoricalRecord, HistoricalRecordId> {
}
