package org.example.cryptohistoricaldatasystem.repository;

import org.example.cryptohistoricaldatasystem.model.HistoricalRecord;
import org.example.cryptohistoricaldatasystem.model.HistoricalRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricalRecordRepository extends JpaRepository<HistoricalRecord, HistoricalRecordId> {
}
