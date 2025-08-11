package org.example.cryptohistoricaldatasystem.service;

import org.example.cryptohistoricaldatasystem.entity.HistoricalRecord;
import org.example.cryptohistoricaldatasystem.repository.HistoricalRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VerificationService {

    private final HistoricalRecordRepository historicalRecordRepository;

    public VerificationService(HistoricalRecordRepository historicalRecordRepository) {
        this.historicalRecordRepository = historicalRecordRepository;
    }

    @Transactional
    public void saveHistoricalRecords(List<HistoricalRecord> historicalRecords) {
        historicalRecordRepository.saveAll(historicalRecords);
    }
}
