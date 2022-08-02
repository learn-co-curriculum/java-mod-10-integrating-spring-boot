package com.example.restservice;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HaystackRepository extends CrudRepository<Haystack, Long> {
    @Transactional
    @Modifying
    @Query(
            value = "TRUNCATE haystack; DROP INDEX IF EXISTS haystack_value_idx",
            nativeQuery = true)
    void truncateTable();

    @Transactional
    @Modifying
    @Query(
            value = "CREATE INDEX ON haystack (value)",
            nativeQuery = true)
    void indexBTree();

    @Transactional
    @Modifying
    @Query(
            value = "CREATE INDEX ON haystack USING HASH (value) ",
            nativeQuery = true)
    void indexHash();

    @Query(
            value = "SELECT * FROM haystack WHERE value = 'needle'",
            nativeQuery = true)
    Haystack seqScan();

    @Query(
            value = "EXPLAIN ANALYZE SELECT * FROM haystack WHERE value = 'needle'",
            nativeQuery = true)
    List<String> seqScanPerf();

    @Query(
            value = "SELECT haystack.id, haystack.uuid, haystack.value FROM haystack INNER JOIN haystackuuid ON haystack.uuid = haystackuuid.uuid WHERE value = 'needle'",
            nativeQuery = true)
    Haystack tableJoin();

    @Query(
            value = "EXPLAIN ANALYZE SELECT * FROM haystack INNER JOIN haystackuuid ON haystack.uuid = haystackuuid.uuid WHERE value = 'needle'",
            nativeQuery = true)
    List<String> tableJoinPerf();
}

