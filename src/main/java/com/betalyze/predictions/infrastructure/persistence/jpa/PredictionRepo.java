package com.betalyze.predictions.infrastructure.persistence.jpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.betalyze.predictions.domain.model.PredictionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepo extends JpaRepository<PredictionEntity, UUID> {

    @Query(value = """
           SELECT 
               p.id,
               p.match_id,
               p.created_at,
               p.updated_at,
               p.home_win_prob,
               p.draw_prob,
               p.away_win_prob,
               p.predicted_outcome,
               p.result_confidence,
               p.result_confidence_score,
               p.home_expected_goals,
               p.away_expected_goals,
               p.total_expected_goals,
               p.over_2_5_prob,
               p.under_2_5_prob,
               p.over_under_recommendation,
               p.over_under_confidence,
               p.over_under_confidence_score,
               p.btts_probability,
               p.btts_recommendation,
               p.actual_outcome,
               p.correct,
               COALESCE(ht.team_image_url, 'https://via.placeholder.com/150') as home_team_image,
               COALESCE(at.team_image_url, 'https://via.placeholder.com/150') as away_team_image,
               m.league_name,
               m.season,
               COALESCE(ht.team_name, 'Unknown') as home_team_name,
               COALESCE(at.team_name, 'Unknown') as away_team_name,
               m.match_date  -- ✅ AGREGADO AQUÍ (índice 28)
           FROM predictions p
           JOIN matches m ON p.match_id = m.id
           LEFT JOIN teams ht ON ht.id = m.home_team_id
           LEFT JOIN teams at ON at.id = m.away_team_id
           ORDER BY m.match_date DESC
           """, nativeQuery = true)
    List<Object[]> findAllWithImages();

    @Query(value = """
           SELECT 
               p.id,
               p.match_id,
               p.created_at,
               p.updated_at,
               p.home_win_prob,
               p.draw_prob,
               p.away_win_prob,
               p.predicted_outcome,
               p.result_confidence,
               p.result_confidence_score,
               p.home_expected_goals,
               p.away_expected_goals,
               p.total_expected_goals,
               p.over_2_5_prob,
               p.under_2_5_prob,
               p.over_under_recommendation,
               p.over_under_confidence,
               p.over_under_confidence_score,
               p.btts_probability,
               p.btts_recommendation,
               p.actual_outcome,
               p.correct,
               COALESCE(ht.team_image_url, 'https://via.placeholder.com/150') as home_team_image,
               COALESCE(at.team_image_url, 'https://via.placeholder.com/150') as away_team_image,
               m.league_name,
               m.season,
               COALESCE(ht.team_name, 'Unknown') as home_team_name,
               COALESCE(at.team_name, 'Unknown') as away_team_name,
               m.match_date  -- ✅ AGREGADO AQUÍ (índice 28)
           FROM predictions p
           JOIN matches m ON p.match_id = m.id
           LEFT JOIN teams ht ON ht.id = m.home_team_id
           LEFT JOIN teams at ON at.id = m.away_team_id
           WHERE EXTRACT(YEAR FROM m.match_date) = CAST(:season AS INTEGER)
           ORDER BY m.match_date DESC
           """, nativeQuery = true)
    List<Object[]> findBySeason(@Param("season") String season);

    @Query(value = """
           SELECT 
               p.id,
               p.match_id,
               p.created_at,
               p.updated_at,
               p.home_win_prob,
               p.draw_prob,
               p.away_win_prob,
               p.predicted_outcome,
               p.result_confidence,
               p.result_confidence_score,
               p.home_expected_goals,
               p.away_expected_goals,
               p.total_expected_goals,
               p.over_2_5_prob,
               p.under_2_5_prob,
               p.over_under_recommendation,
               p.over_under_confidence,
               p.over_under_confidence_score,
               p.btts_probability,
               p.btts_recommendation,
               p.actual_outcome,
               p.correct,
               COALESCE(ht.team_image_url, 'https://via.placeholder.com/150') as home_team_image,
               COALESCE(at.team_image_url, 'https://via.placeholder.com/150') as away_team_image,
               m.league_name,
               m.season,
               COALESCE(ht.team_name, 'Unknown') as home_team_name,
               COALESCE(at.team_name, 'Unknown') as away_team_name,
               m.match_date  -- ✅ AGREGADO AQUÍ (índice 28)
           FROM predictions p
           JOIN matches m ON p.match_id = m.id
           LEFT JOIN teams ht ON ht.id = m.home_team_id
           LEFT JOIN teams at ON at.id = m.away_team_id
           WHERE m.match_date BETWEEN :startDate AND :endDate
           ORDER BY m.match_date DESC
           """, nativeQuery = true)
    List<Object[]> findByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query(value = """
           SELECT 
               p.id,
               p.match_id,
               p.created_at,
               p.updated_at,
               p.home_win_prob,
               p.draw_prob,
               p.away_win_prob,
               p.predicted_outcome,
               p.result_confidence,
               p.result_confidence_score,
               p.home_expected_goals,
               p.away_expected_goals,
               p.total_expected_goals,
               p.over_2_5_prob,
               p.under_2_5_prob,
               p.over_under_recommendation,
               p.over_under_confidence,
               p.over_under_confidence_score,
               p.btts_probability,
               p.btts_recommendation,
               p.actual_outcome,
               p.correct,
               COALESCE(ht.team_image_url, 'https://via.placeholder.com/150') as home_team_image,
               COALESCE(at.team_image_url, 'https://via.placeholder.com/150') as away_team_image,
               m.league_name,
               m.season,
               COALESCE(ht.team_name, 'Unknown') as home_team_name,
               COALESCE(at.team_name, 'Unknown') as away_team_name,
               m.match_date  -- ✅ AGREGADO AQUÍ (índice 28)
           FROM predictions p
           JOIN matches m ON p.match_id = m.id
           LEFT JOIN teams ht ON ht.id = m.home_team_id
           LEFT JOIN teams at ON at.id = m.away_team_id
           WHERE EXTRACT(YEAR FROM m.match_date) = CAST(:season AS INTEGER)
             AND m.match_date BETWEEN :startDate AND :endDate
           ORDER BY m.match_date DESC
           """, nativeQuery = true)
    List<Object[]> findBySeasonAndDateRange(
            @Param("season") String season,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query(value = """
           SELECT 
               p.id,
               p.match_id,
               p.created_at,
               p.updated_at,
               p.home_win_prob,
               p.draw_prob,
               p.away_win_prob,
               p.predicted_outcome,
               p.result_confidence,
               p.result_confidence_score,
               p.home_expected_goals,
               p.away_expected_goals,
               p.total_expected_goals,
               p.over_2_5_prob,
               p.under_2_5_prob,
               p.over_under_recommendation,
               p.over_under_confidence,
               p.over_under_confidence_score,
               p.btts_probability,
               p.btts_recommendation,
               p.actual_outcome,
               p.correct,
               COALESCE(ht.team_image_url, 'https://via.placeholder.com/150') as home_team_image,
               COALESCE(at.team_image_url, 'https://via.placeholder.com/150') as away_team_image,
               m.league_name,
               m.season,
               COALESCE(ht.team_name, 'Unknown') as home_team_name,
               COALESCE(at.team_name, 'Unknown') as away_team_name,
               m.match_date  -- ✅ AGREGADO AQUÍ (índice 28)
           FROM predictions p
           JOIN matches m ON p.match_id = m.id
           LEFT JOIN teams ht ON ht.id = m.home_team_id
           LEFT JOIN teams at ON at.id = m.away_team_id
           WHERE p.id = :id
           """, nativeQuery = true)
    Optional<Object[]> findByIdWithImages(@Param("id") UUID id);
}
