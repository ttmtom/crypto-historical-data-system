package org.example.cryptohistoricaldatasystem.service;

import org.example.cryptohistoricaldatasystem.entity.GoldenRecord;
import org.example.cryptohistoricaldatasystem.repository.GoldenRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VerificationService {

    private final GoldenRecordRepository goldenRecordRepository;

    public VerificationService(GoldenRecordRepository goldenRecordRepository) {
        this.goldenRecordRepository = goldenRecordRepository;
    }

    @Transactional
    public void saveGoldenRecords(List<GoldenRecord> goldenRecords) {
        goldenRecordRepository.saveAll(goldenRecords);
    }
}
